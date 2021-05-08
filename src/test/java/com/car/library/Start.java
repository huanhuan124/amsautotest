package com.car.library;

//import com.zuche.baseModules.openAPI.common.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by zenghuan on 2016/8/12.
 * start 入参只有 cid,sign(SignUtils.doMD5Sign("cid=800236rhjkl3E!@#123"))
 * sign=2145506284105640491547371383847527613
 * 返回的uid=88dee55b-a2e9-4ad2-abd7-56c03f2101231470993932958
 * {"busiCode":"BASE000","code":1,"content":{"msg":"成功","re":null,"status":1001,"version":{"address":"","focus":false,"msg":"","newver":0,"upgrade":false}},"msg":"成功","status":"SUCCESS","uid":"88dee55b-a2e9-4ad2-abd7-56c03f2101231470993932958","version":"236"}
 */
public class Start {
    public static String start() throws JSONException {
//        String sign =  SignUtils.doMD5Sign("cid="+Common.CID+Common.KEY);
//        String url = Common.DOMAIN + "/carmapiproxy/action/sandroid/start" + "?cid="+Common.CID + "&sign="+sign;
//        String result = Common.httpClient(url);
//        JSONObject obj = new JSONObject(result);
//        String uid = obj.getString("uid");
//        return uid;
        return "abc";




    }

    /*
    * 只有cid和key得到的sign:25117798214865325610824896201898915865
http://apiproxytest.maimaiche.com/carmapiproxy/action/sandroid/start?cid=800235&sign=25117798214865325610824896201898915865
    * */
    public static void main(String args[]) throws JSONException {
        String uid=start();
        System.out.println("start uid="+uid);

    }
}
