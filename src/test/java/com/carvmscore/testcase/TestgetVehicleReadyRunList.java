package com.carvmscore.testcase;

import com.car.library.Common;
import com.car.library.ExcelData;
import jxl.read.biff.BiffException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class TestgetVehicleReadyRunList {
    @DataProvider(name="carvmscore-getVehicleReadyRunList")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        ExcelData e=new ExcelData("carvmscore", "testgetVehicleReadyRunList");
        return e.getExcelData();
    }

    @Test(dataProvider="carvmscore-getVehicleReadyRunList")
    public  void  testLogin_ams(HashMap<String, String> data) throws Exception {
        //得到入参
        String login_param = data.get("parameters");
        System.out.println("取到的参数是："+login_param);

        //请求url
//        String url = Common.DOMAIN + data.get("domain") + "?q="+en_loginparam+"&cid="+Common.CID +"&uid="+start_uid+"&sign="+sign;;
        String login_domain = Common.carvmscoreDomain + data.get("domain");

        String result = Common.sendPost2(login_domain,login_param);
//        String result = Common.httpClient(url);

        //对json进行解析
        JSONObject obj = new JSONObject(result);
        System.out.println("login result="+result);
        String actual_status = obj.getString("status");
        String actual_code = obj.getString("code");

//        JSONObject obj_content = new JSONObject(obj.getString("status"));
//        String actual_status = obj_content.getString("msg");

//        Assert.assertNotNull(uid);
        Assert.assertEquals(actual_status,data.get("expected_status"));
//        Assert.assertEquals(actual_code,data.get("expected_code"));

        //打印log
        Reporter.log("login sucess, login actual_status is "+actual_status);
    }















}
