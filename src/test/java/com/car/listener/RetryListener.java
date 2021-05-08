package com.car.listener;

import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.IAnnotationTransformer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer{

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
//        Class<? extends IRetryAnalyzer> myRetry = iTestAnnotation.getRetryAnalyzerClass();
        IRetryAnalyzer myRetry = iTestAnnotation.getRetryAnalyzer();



        if (myRetry == null){
            iTestAnnotation.setRetryAnalyzer(MyRetry.class);
        }

    }




}
