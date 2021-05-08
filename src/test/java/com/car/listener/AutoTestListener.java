package com.car.listener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Iterator;

public class AutoTestListener implements ITestListener{
    @Override
    public void onStart(ITestContext context) {
        System.out.println("start test");
    }


    public void onTestStart(ITestResult result) {
        System.out.println("***start on test***"+ result.getMethod().getMethodName() );
    }


    public void onTestSuccess(ITestResult result) {
        System.err.println(result.getTestClass().getName() + "." + result.getMethod().getMethodName() + " : passed****");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.err.println(result.getTestClass().getName() + "." + result.getMethod().getMethodName() + " : failed");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Result fail but with success percentage");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.err.println(result.getTestClass().getName() + "." + result.getMethod().getMethodName() + " : skipped");
    }


    //但是实际上这样子会有一个问题，那就是测试报告中的用例数会增多，可能就会出现，
    // 我们执行一条用例，但是这条用例先失败后成功后，结果会显示之行了2条测试用例，
    // 但是对我们来说更希望是只是一条，所以为了计算测试用例的的个数，
    // 我们需要创建一个TestListner.java的类实现ITestListener 的接口

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("finish test");
//        Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
//        while (listOfFailedTests.hasNext()) {
//            ITestResult failedTest = listOfFailedTests.next();
//            ITestNGMethod method = failedTest.getMethod();
//            if (context.getFailedTests().getResults(method).size() > 1) {
//                listOfFailedTests.remove();
//            } else {
//                if (context.getPassedTests().getResults(method).size() > 0) {
//                    listOfFailedTests.remove();
//                }
//            }
//        }

    }
}
