package com.example.streamlearn.annotation;

import com.example.streamlearn.proxy.IOrder;
import com.example.streamlearn.proxy.Order;

/**
 * @program streamLearn
 * @ClassName TestFactory
 * @description
 * @create 2023-02-01 13:27
 **/
public class TestFactory {

    public static void main(String[] args) throws Exception {
        IOrder order = ObjectFactory.newInstance(Order.class);
        order.pay();
        order.show();
    }
}
