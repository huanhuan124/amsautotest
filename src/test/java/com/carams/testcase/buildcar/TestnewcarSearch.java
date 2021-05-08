package com.carams.testcase.buildcar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.library.Common;
import com.car.library.ExcelData;
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
public class TestnewcarSearch {

    //返回一个对象数组
    @DataProvider(name="buildcar-testnewcarSearch")
    public Object[][] Numbers() throws BiffException, IOException {
        //获取Excel数据，得到一个map，依次传给test
        ExcelData e=new ExcelData("buildcar", "testnewcarSearch");
        return e.getExcelData();
    }


    @Test(dataProvider="buildcar-testnewcarSearch")
    public  void  test_newcarSearch(HashMap<String, String> data) throws Exception {
         //得到入参
        String param = data.get("parameters");
        System.out.println("取到的参数是："+param);

        //替换{}变量参数
        String replaceParam = Common.getCommonParam(param);
        System.out.println("test_newcarSearch被替换掉参数后为："+replaceParam);


        //提取taskid
        String save = data.get("save");
        System.out.println("save:"+save);

//        String url = "http://apiproxytest.zuche.com/action/bos/ams/newcar/task/search";
        //拼接url
        String url = Common.apiproxyDomain + data.get("url");
        System.out.println("url:"+url);
//        String result = Common.sendPost2(login_domain,login_param);
//        String result = Common.httpClient(url);

        JSONObject jsonResult = Common.post(url,replaceParam);

        //提取出taskId的值，因为dataList是json里面的数组，用jsonpath提取不出来，所以用的这个方法
        String content = jsonResult.getString("content");
        JSONObject contentObject = JSONObject.parseObject(content);
        JSONArray dataListArray = contentObject.getJSONArray("dataList");
        System.out.println(dataListArray);

        JSONObject o = (JSONObject) dataListArray.get(0);
        String taskId = o.getString("taskId");
        System.out.println(taskId);

        //将taskId存入公共参数池map中，用于以后接口的调用
        Common.saveDatas.put("taskId",taskId);
        System.out.println(Common.saveDatas);


        //对json进行解析
        System.out.println(" result="+jsonResult);
        String actual_status = jsonResult.getString("status");
        String actual_msg = jsonResult.getString("msg");

        //验证结果
        Assert.assertEquals(actual_status,data.get("expected_status"));
        Assert.assertEquals(actual_msg,data.get("expected_msg"));

        //打印log
        Reporter.log("testnewcarSearch sucess, testnewcarSearch actual_status is "+actual_status);
    }


}
