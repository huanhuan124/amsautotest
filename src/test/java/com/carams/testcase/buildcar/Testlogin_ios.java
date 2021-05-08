package com.carams.testcase.buildcar;

import com.alibaba.fastjson.JSONObject;
import com.car.library.Common;
import com.car.library.ExcelData;
import com.jayway.jsonpath.JsonPath;
import jxl.read.biff.BiffException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;




//1、集成到jenkins  2、公共的内容抽出来  3、单个测试用例不用登陆怎么测

/**
 * Created by zenghuan on 2016/8/12.
 * MD5Sign("cid=800001;q={id:01,va:11};uid=88871edc-5dd7-4882-a907-05f3a82ced2dp2pxxxszyzxxx")
 */
//post

public class Testlogin_ios {
//返回一个对象数组
    @DataProvider(name="buildcar_testlogin_ios")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        ExcelData e=new ExcelData("buildcar", "testlogin_ios");
        return e.getExcelData();
    }


    @Test(dataProvider="buildcar_testlogin_ios")
    public  void  testLogin_ios(HashMap<String, String> data) throws Exception {
         //得到入参
        String param = data.get("parameters");
        System.out.println("取到的参数是："+param);

        //取出uid
        String save = data.get("save");
        System.out.println("取到的save参数是："+save);

//        String login_domain = "http://apiproxytest.zuche.com/resource/carbosapi/common/login/v1";
        String url = Common.apiproxyDomain + data.get("url");
        System.out.println("url是："+url);
//        String result = Common.sendPost2(login_domain,login_param);
//        String result = Common.httpClient(url);

        JSONObject jsonResult = Common.post(url,param);

        //取出uid的值
        JsonPath path = JsonPath.compile(save);
        String uid = path.read(jsonResult);
        System.out.println("uid的值："+uid);

        //将uid存入公共参数池map中，用于以后接口的调用
        Common.saveDatas.put("uid",uid);
        System.out.println(Common.saveDatas);
        System.out.println(Common.saveDatas);


        //对json进行解析
//        JSONObject obj = new JSONObject(result);
        System.out.println("testlogin_ios result="+jsonResult);
        String actual_status = jsonResult.getString("status");
        String actual_msg = jsonResult.getString("msg");

        //验证结果
        Assert.assertEquals(actual_status,data.get("expected_status"));
        Assert.assertEquals(actual_msg,"成功");

        //打印log
        Reporter.log("testlogin_ios sucess, testlogin_ios actual_status is "+actual_status);
    }


}
