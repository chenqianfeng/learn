package com.example.streamlearn.proxy;

import com.example.streamlearn.annotation.Aspect;

/**
 * @program streamLearn
 * @ClassName ProxyTest
 * @description 动态代理
 * @create 2023-01-31 18:14
 **/
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        IOrder order = IAspect.getProxy(Order.class, "com.example.streamlearn.proxy.TimeUsageAspect");
        order.pay();
        order.show();
    }
}
