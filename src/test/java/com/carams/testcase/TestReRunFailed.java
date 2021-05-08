package com.carams.testcase;
import com.car.listener.MyRetry;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestReRunFailed{
    @Test
    public void test01() {
        System.out.println("test01");
    }

@Test(retryAnalyzer = MyRetry.class)
    public void test02() {
        Assert.assertTrue(10==11);
        System.out.println("test02");
    }

    @Test
    public void test03() {
        System.out.println("test03");
    }

}
