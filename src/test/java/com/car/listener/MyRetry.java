package com.car.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class MyRetry implements IRetryAnalyzer{
    //设置当前失败执行次数
    private int retrycount=1;
    //设置最大失败执行次数
    private  static int maxRetryCount=3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if(retrycount < maxRetryCount){
            retrycount++;
            return true;
        }
        return false;
    }
}
