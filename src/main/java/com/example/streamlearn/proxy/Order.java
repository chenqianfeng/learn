package com.example.streamlearn.proxy;

import com.example.streamlearn.annotation.Aspect;
import java.util.concurrent.TimeUnit;

/**
 * @program streamLearn
 * @ClassName Order
 * @description
 * @create 2023-01-31 18:21
 **/
@Aspect(type = TimeUsageAspect.class)
public class Order implements IOrder{

    int status = 0;

    @Override
    public void pay() throws Exception {
        TimeUnit.MILLISECONDS.sleep(50);
    }

    @Override
    public void show() {
        System.out.println("status:"+status);
    }
}
