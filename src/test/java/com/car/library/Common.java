package com.car.library;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import  com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.apache.http.client.config.RequestConfig;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zenghuan on 2016/8/12.
 */
public class Common {
    public static final String CID = "800237";
    public static CookieStore  cookie_login;

    //测试环境：http://apiproxypre.maimaiche.com  预生产环境：http://apiproxypre.maimaiche.com  线上环境：http://apiproxypre.maimaiche.com
    public static  String caradminDomain ;
    public static  String carvmscoreDomain ;
    public static  String apiproxyDomain ;
    public static  String amslogin_param;
    public static String cid;
    public static Logger log = Logger.getLogger(Common.class);
    //公共参数数据池
    public static  Map<String, String> saveDatas = new HashMap<String, String>();


    //跟路径是否以‘/’结尾
    private static boolean rooUrlEndWithSlash = false;

    //替换符，如果数据中包含“${}”则会被替换成公共参数中存储的数据
    public  static Pattern replaceParamPattern = Pattern.compile("\\$\\{(.*?)\\}");


    /**
     * Created by zenghuan on 2016/8/12.
     * start 入参只有 cid,sign(SignUtils.doMD5Sign("cid=800236rhjkl3E!@#123"))
     * sign=2145506284105640491547371383847527613
     * 返回的uid=88dee55b-a2e9-4ad2-abd7-56c03f2101231470993932958
     * {"busiCode":"BASE000","code":1,"content":{"msg":"成功","re":null,"status":1001,"version":{"address":"","focus":false,"msg":"","newver":0,"upgrade":false}},"msg":"成功","status":"SUCCESS","uid":"88dee55b-a2e9-4ad2-abd7-56c03f2101231470993932958","version":"236"}
     */

    /*
     * 使用httpclient的方式获取接口内容，GET请求
     * 并对status 进行判断
     * 用于普通GET接口使用，其中使用了资产登陆接口所存的cookie
     *
     * */
    public static String doGet(String url,String save) throws JSONException {
        System.out.println("需要提取的参数是："+save);
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //存入cookie
        httpclient.setCookieStore(cookie_login);

        // 执行get请求.
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
             response = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                result = EntityUtils.toString(entity,"utf-8");//
                System.out.println("接口内容为----"+result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("接口内容为空------------------------------------");
        }
          return result;
    }

    //用于资产后台登陆接口，最后存储了cookie
    public static String doPost(String domain, String param) throws Exception{
        HashMap<String,String> params = new HashMap<String, String>();
        JSONObject urlObject = JSON.parseObject(param);
        System.out.println("json后的参数是："+urlObject);

        String loginId = urlObject.getString("loginId");
        String password = urlObject.getString("password");
        System.out.println("loginId后的参数是："+loginId);


        System.out.println("url:"+param);

        params.put("loginId",loginId);
        params.put("password",password);

        //创建httpClient对象

        DefaultHttpClient httpClient = new DefaultHttpClient();
        CloseableHttpResponse response = null;
        String result = "";
        HttpPost httpPost = new HttpPost(domain);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + Consts.UTF_8);
        List<NameValuePair> paramList = new LinkedList<NameValuePair>();
        BasicNameValuePair nameValuePair;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            paramList.add(nameValuePair);
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
        httpPost.setEntity(entity);
        response = httpClient.execute(httpPost);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookie并保存
        //05-08注释
        cookie_login= httpClient.getCookieStore();
        System.out.println("cookie:"+cookie_login);

        return result;
    }

    //用于普通post接口使用（移动端登录），其中使用了资产登陆接口所存的cookie
    public static JSONObject post(String url, String parms) {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        //保存cookie
        //2021-05-08注释
        httpClient.setCookieStore(cookie_login);
        HttpPost post = new HttpPost(url);

        post.addHeader("content-type", "application/json;charset=utf-8");
        post.setEntity(new StringEntity(parms, Charset.forName("utf-8")));
        HttpResponse response = null;
        String result = null;
        try {
//            Log.info("开始发送post请求，请求的URL: " + url);
//            Log.info("开始发送post请求，请求的参数: " + parms);
            response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
            System.out.println("请求不正确");
        }
        CookieStore cookieStore = httpClient.getCookieStore();
        System.out.println("cookie:"+cookieStore);
        JSONObject responseObject = JSON.parseObject(result);

        System.out.println("response: "+responseObject);
        return responseObject;
    }



    public static void printMap(Map<String, String> map){
        Set<Map.Entry<String,String>> entry = map.entrySet();
        for(Object s:entry){
            System.out.println(s);
        }

    }

    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json, text/plain, */*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
//            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //适用于第一种参数
            // &loginId=8ksOKJWwq8iwJfv6KgefOw%3D%3D&password=38zQ7ioJlJWPUOUZe%2BkAHA%3D%3D
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String doGet2(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //通过默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            //创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            //httpGet.addHeader("Connection", "keep-alive");
            //设置请求头信息
            httpGet.addHeader("Accept", "application/json");
            //配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000) //设置连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)//设置请求超时时间
                    .setSocketTimeout(60000)//设置数据读取超时时间
                    .build();
            //为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            //执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            //通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            //通过EntityUtils中的toString方法将结果转换为字符串，后续根据需要处理对应的reponse code
            result = EntityUtils.toString(entity);
            System.out.println(result);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost33(String domain, String url) throws JSONException{
        System.out.println("domain："+domain);
        System.out.println("url："+url);
        System.out.println(url.getClass());
//        JSONObject obj = new JSONObject(url);
        JSONObject obj = JSON.parseObject(url);
        System.out.println(obj.getClass());
//        System.out.println("login result="+url)

        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //创建http请求
            HttpPost httpPost = new HttpPost(domain);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Connection","keep-alive");
            //创建请求内容
//            String jsonStr = "{\"qry_by\":\"name\", \"name\":\"Tim\"}";
//            String jsonStr = "{\"loginId\":\"8ksOKJWwq8iwJfv6KgefOw==\",\"password\":\"38zQ7ioJlJWPUOUZe+kAHA==\"}";
            StringEntity entity = new StringEntity(url);
            System.out.println(entity);
            httpPost.setEntity(entity);
            System.out.println(httpPost);
            response = httpClient.execute(httpPost);
            System.out.println(response);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("result:-------"+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        System.out.println("post接口的结果是："+result);
        return result;
    }

    public static String  doPost2(String domain, String param) throws Exception{
        System.out.println(domain);
        System.out.println(param);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response2 = null;
        HttpEntity entity2 = null;
        try {
            HttpPost httpPost = new HttpPost(domain);
//                JSONObject jsonParam=new JSONObject(param);
//                System.out.println(jsonParam);
//                StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");

            StringEntity entity = new StringEntity(param.toString(),"utf-8");

            entity.setContentEncoding("UTF-8");
//                entity.setContentType("application/json");
            entity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(entity);
            response2 = httpclient.execute(httpPost);
            try {
                System.out.println(response2.getStatusLine());
//                    HttpEntity entity2 = response2.getEntity();
                entity2 = response2.getEntity();
                System.out.println(entity2.getClass());
                System.out.println("Response content: " + EntityUtils.toString(entity2));
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
                return EntityUtils.toString(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }



    }

    //删除创建的数据
    public static void cleanTestData(String sql) {
        String driver = "com.mysql.jdbc.Driver";
        //需要连接的数据库
        String url = "jdbc:mysql://10.104.50.25:3306/uc_db";
        String username = "uc_user";
        String password = "uc_usertest";
        try {
            Class.forName(driver);//加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //创建链接对象
            Connection con = DriverManager.getConnection(url, username, password);
            //创建sql语句执行对象
            Statement st = con.createStatement();
            //执行sql语句
            //  ResultSet rs = st.executeQuery(sql); 针对查询的执行语句
            int rows = st.executeUpdate(sql);//针对更新执行的语句
            System.out.println("删除数据成功");

            //rs = st.executeQuery(sql2);
            //对结果进行处理
           /* while (rs.next()){
                System.out.println("id:"+rs.getString("id"));
                System.out.println("id:"+rs.getString(1));
                System.out.println("book_order_no:"+rs.getString("book_order_no"));
            }*/

            //关闭相关的对象
           /* if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            */
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("================================= Complete clean data========================================================");

    }



    /**
     * 格式化url,替换路径参数等。
     * @param shortUrl
     * @return
     */
    private String parseUrl(String shortUrl) {
        // 替换url中的参数
        shortUrl = getCommonParam(shortUrl);
        if (shortUrl.startsWith("http")) {
            return shortUrl;
        }
        if (rooUrlEndWithSlash == shortUrl.startsWith("/")) {
            if (rooUrlEndWithSlash) {
                shortUrl = shortUrl.replaceFirst("/", "");
            } else {
                shortUrl = "/" + shortUrl;
            }
        }
//        return rootUrl + shortUrl;
        return shortUrl;
    }


    /**
     * 取公共参数 并替换参数，处理${}
     * @param param
     * @return
     */
    protected String getCommonParam2(String param) {
        if (stringUtil.isEmpty(param)) {
            return "";
        }
        Matcher m = replaceParamPattern.matcher(param);// 取公共参数正则
        while (m.find()) {
            String replaceKey = m.group(1);
            // 如果公共参数池中未能找到对应的值，该用例失败。
            Assert.assertNotNull(replaceKey,
                    String.format("格式化参数失败，公共参数中找不到%s。", replaceKey));
            String value;
            // 从公共参数池中获取值
            value = getSaveData(replaceKey);
            //如果值不为空，则将参数替换为公共参数池里读取到的value的值。
            if(null != value) {
                param = param.replace(m.group(), value);
                //如果值为空，则将参数替换为字符串“null”
            }else {
                param = param.replace(m.group(), "null");
            }
        }
        return param;
    }

    /**
     * 获取公共数据池中的数据
     * @param key
     * 公共数据的key
     * @return 对应的value
     */
    public  static String getSaveData(String key) {
        if ("".equals(key) || !saveDatas.containsKey(key)) {
            return null;
        } else {
            return saveDatas.get(key);
        }
    }


    /**
     * 取用例中的参数，处理${}，并替换参数
     * @param param
     * @return
     */
    public static  String getCommonParam(String param) {
        if (stringUtil.isEmpty(param)) {
            return "";
        }
        Matcher m = replaceParamPattern.matcher(param);// 取正则
        while (m.find()) {
            String replaceKey = m.group(1);
            System.out.println("replacekey为："+replaceKey);
            // 如果公共参数池中未能找到对应的值，该用例失败。
            Assert.assertNotNull(replaceKey,
                    String.format("格式化参数失败，公共参数中找不到%s。", replaceKey));
            String value;
            // 从公共参数池中获取值
            value = getSaveData(replaceKey);
            //如果值不为空，则将参数替换为公共参数池里读取到的value的值。
            if(null != value) {
                param = param.replace(m.group(), value);
                //如果值为空，则将参数替换为字符串“null”
            }else {
                param = param.replace(m.group(), "null");
            }
        }
        return param;
    }



    //读取配置文件的内容
    public static void getProperties() {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/config.properties");
        try {
            prop.load(in);
            caradminDomain = prop.getProperty("CARADMIN_ROOTURL")+ prop.getProperty("ENVNAME");
            System.out.println(caradminDomain);

            carvmscoreDomain = prop.getProperty("CARVMSCORE_ROOTURL")+prop.getProperty("ENVNAME");
            apiproxyDomain = prop.getProperty("APIPROXY_ROOTURL")+prop.getProperty("ENVNAME");
            amslogin_param = prop.getProperty("AMSLOGIN_PARAM");
            cid = prop.getProperty("CID");

//            CID = prop.getProperty("CID");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void setSaveDatas(String key, Integer value){
//        saveDatas.put(key,value);
//    }
//
//    public static String getSaveData(String key){
//        if ("".equals(key) || !saveDatas.containsKey(key)) {
//            return null;
//        } else {
//            return saveDatas.get(key);
//        }
//    }



}

