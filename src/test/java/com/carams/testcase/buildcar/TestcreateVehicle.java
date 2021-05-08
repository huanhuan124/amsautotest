package com.carams.testcase.buildcar;

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



/**
 * Created by zenghuan on 2016/8/12.
 * MD5Sign("cid=800001;q={id:01,va:11};uid=88871edc-5dd7-4882-a907-05f3a82ced2dp2pxxxszyzxxx")
 */
//post
public class TestcreateVehicle {

//返回一个对象数组
    @DataProvider(name="buildcar-testcreateVehicle")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        ExcelData e=new ExcelData("buildcar", "testcreateVehicle");
        return e.getExcelData();
    }


    @Test(dataProvider="buildcar-testcreateVehicle")
    public  void  testLogin_createVehicle(HashMap<String, String> data) throws Exception {
         //得到入参
        String param = data.get("parameters");
        System.out.println("取到的参数是："+param);

        //获取随机产生的车架号，并存入公共数据池
        String frameNO = randomUtil.getRandomframeNo();
        System.out.println("产生的车架号是："+frameNO);
        Common.saveDatas.put("frameNo",frameNO);
        System.out.println(Common.saveDatas);

        //替换掉变量参数
        String replaceParam = Common.getCommonParam(param);
        System.out.println("testLogin_createVehicle被替换掉的参数为："+replaceParam);


//        String login_domain = "http://carvmscoretest.zuche.com/carvmscore/vehiclemanage/readyrun/vehicleReadyRunVueController/createVehicle.do_";
        String url = Common.carvmscoreDomain + data.get("url");

//        JSONObject paramObject = JSON.parseObject(param);
////        paramObject.put("frameNo","JMETERCAR95090456");
//        //给参数中的某些数据赋值
//        paramObject.getJSONObject("baseInfoVo").remove("frameNo");
//        paramObject.getJSONObject("baseInfoVo").remove("engineNo");
//        paramObject.getJSONObject("baseInfoVo").remove("contractNo");
//
//        paramObject.getJSONObject("baseInfoVo").put("frameNo","JMETERCAR95090456");
//        paramObject.getJSONObject("baseInfoVo").put("engineNo","JMETERCAR95090456");
//        paramObject.getJSONObject("baseInfoVo").put("contractNo","JMETERCAR95090456");
//
//        String param2 = paramObject.toString();
//        System.out.println("参数是："+param2);

        JSONObject obj = Common.post(url,replaceParam);


        //对json进行解析
        System.out.println("createVehicle result="+obj);
        String actual_status = obj.getString("status");
        Assert.assertEquals(actual_status,data.get("expected_status"));

        //打印log
        Reporter.log("testcreateVehicle sucess, actual_status is "+actual_status);
    }


}
