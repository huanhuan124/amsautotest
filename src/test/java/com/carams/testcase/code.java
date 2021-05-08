package com.carams.testcase;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONObject;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.PrintWriter;
//import net.sf.json.JSONObject;

//import net.sf.json.JSONObject;

public class code {

    public String doPost(String url) {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //创建http请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            //创建请求内容
            String jsonStr = "{\"qry_by\":\"name\", \"name\":\"Tim\"}";
            StringEntity entity = new StringEntity(jsonStr);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
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

    public String doPost2(String url) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("loginId", "UA244Y8SbzJRKNVluixyKA==");
        params.put("password", "qLpmhT5PIxYdFCh/kZ+dcw==");
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        HttpPost httpPost = new HttpPost(url);
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
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
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
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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

    public void doPost3() {
    }

    /**
     * 66      * 向指定URL发送POST方法的请求
     * 67      *
     * 68      * @param url   发送请求的URL
     * 69      * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式或者是json。
     * 70      * @return URL所代表远程资源的响应
     * 71
     */
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
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //适用于第一种参数
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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


    public static void main(String[] args) throws Exception {
        code http = new code();


//        System.out.println("Testing 1 - Do Http GET request");
//        http.doGet("http://localhost:8080");

        System.out.println("\nTesting 2 - Do Http POST request");
//        http.doPost("http://localhost:8080/json");
        http.doPost2("http://caradmintest.zuche.com/system/login.do_");

        // 调用天气预报接口请求参数方式二
        String postUrl = "http://caradmintest.zuche.com/system/login.do_";
        System.out.println(postUrl);
        String postParamsOne = "&loginId=8ksOKJWwq8iwJfv6KgefOw%3D%3D&password=38zQ7ioJlJWPUOUZe%2BkAHA%3D%3D";
//        String postParamsOne = "&loginId=8ksOKJWwq8iwJfv6KgefOw==&password=38zQ7ioJlJWPUOUZe+kAHA==";

        String postResultOne = sendPost2(postUrl, postParamsOne);
        System.out.println("POST请求参数一：" + postParamsOne);
        System.out.println("POST请求响应结果：" + postResultOne);

//        String postParamsTwo = "{'cityname':'上海市'," + "'key':'1234567890'}";
//        String postParamsTwo = "{\"loginId\":\"8ksOKJWwq8iwJfv6KgefOw==\",\"password\":\"38zQ7ioJlJWPUOUZe+kAHA==\"}";
        String postParamsTwo = "{'loginId':'8ksOKJWwq8iwJfv6KgefOw%3D%3D'," + "'password':'38zQ7ioJlJWPUOUZe%2BkAHA%3D%3D'}";
//        String postParamsTwo = "{'loginId':'8ksOKJWwq8iwJfv6KgefOw,"+"'password':'38zQ7ioJlJWPUOUZe+kAHA'}";
//        String postParamsTwo = "{'loginId':'zenghuan','password':'zeng.1234'}";
//        JSONObject jsonPostParamsTwo = JSONObject.fromObject(postParamsTwo);
        System.out.println(postParamsTwo.getClass());
        JSONObject jsonPostParamsTwo  = new JSONObject(postParamsTwo);
        System.out.println("----------------");

     // 发送POST请求
        System.out.println(jsonPostParamsTwo);
        System.out.println(jsonPostParamsTwo.getClass());
        String postResultTwo = sendPost2(postUrl, jsonPostParamsTwo.toString());

        System.out.println("POST请求参数二：" + jsonPostParamsTwo);
        System.out.println("POST请求响应结果：" + postResultTwo);




    }

}
