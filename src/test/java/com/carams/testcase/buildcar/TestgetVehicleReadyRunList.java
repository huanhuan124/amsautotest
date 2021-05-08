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


/**
 * Created by zenghuan on 2016/8/12.
 * MD5Sign("cid=800001;q={id:01,va:11};uid=88871edc-5dd7-4882-a907-05f3a82ced2dp2pxxxszyzxxx")
 */

//用的doGet
public class TestgetVehicleReadyRunList {

//返回一个对象数组
    @DataProvider(name="buildcar-testgetVehicleReadyRunList")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        ExcelData e=new ExcelData("buildcar", "testgetVehicleReadyRunList");
        return e.getExcelData();
    }


    @Test(dataProvider="buildcar-testgetVehicleReadyRunList")
    public  void  testLogin_getVehicleReadyRunList(HashMap<String, String> data) throws Exception {
         //得到入参
        String param = data.get("parameters");
        System.out.println("取到的参数是："+param);
        System.out.println(Common.saveDatas);

        //替换变量参数
        String replaceParam = Common.getCommonParam(param);
        System.out.println("testLogin_getVehicleReadyRunList被替换掉的参数为："+replaceParam);

        //获取将要存储的车辆id
        String save = data.get("save");
        System.out.println("取到的save参数是："+save);

        //拼接url
        String url = Common.carvmscoreDomain + data.get("url") + replaceParam;
        System.out.println("url***:"+url);

        //执行get请求
        String  result = Common.doGet(url,save);

       System.out.println("返回结果为：："+result);
       JSONObject jsonResult = JSONObject.parseObject(result);

        //取出xid的值
        JsonPath path = JsonPath.compile(save);
        Integer xid = path.read(jsonResult);
        System.out.println("xid的值："+xid);

        //将xid存入公共参数池map中，用于以后接口的调用
        Common.saveDatas.put("vechile_id",xid.toString());
        System.out.println(xid.getClass());
        System.out.println(Common.saveDatas);

        String actual_status = jsonResult.getString("status");
        Assert.assertEquals(actual_status,data.get("expected_status"));

//        //打印log
        Reporter.log(" getVehicleReadyRunList sucess, actual_status is "+actual_status);
    }


}
