package com.carams.testcase.buildcar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.library.Common;
import com.car.library.ExcelData;
import com.car.library.randomUtil;
import jxl.read.biff.BiffException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import com.car.library.reportUtil;


/**
 * Created by zenghuan on 2016/8/12.
 * MD5Sign("cid=800001;q={id:01,va:11};uid=88871edc-5dd7-4882-a907-05f3a82ced2dp2pxxxszyzxxx")
 */

//    doPost3
//@Listeners({AutoTestListener.class})
public class Testlogin {

//返回一个对象数组
    @DataProvider(name="buildcar_amslogin")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        //资产后台登录
        ExcelData e=new ExcelData("buildcar", "testlogin_ams");
        return e.getExcelData();
    }

    @Test(dataProvider="buildcar_amslogin")
//    @Test(dataProvider="crm-login",retryAnalyzer = MyRetry.class)
    public  void  testLogin_ams(HashMap<String, String> data) throws Exception {

        //获取配置文件
        Common.getProperties();
         //得到入参
        String param = data.get("parameters");
        System.out.println("取到的参数是："+param);

        String url = Common.caradminDomain + data.get("url");
        System.out.println("url:"+url);

        String result = Common.doPost(url,param);

        System.out.println("随机产生的车牌号是："+randomUtil.getRandomvehicleNo());
        System.out.println("随机产生的车架号是："+randomUtil.getRandomframeNo());

        //对json进行解析
        JSONObject obj = JSON.parseObject(result);

        System.out.println("testlogin_ams result="+obj);
        String actual_status = obj.getString("status");
        Assert.assertEquals(actual_status,data.get("expected_status"));


        //打印log
        Reporter.log("testlogin_ams sucess,  返回的status是： "+actual_status);
//        reportUtil.log("返回状态码:"+actual_status);



    }


}
