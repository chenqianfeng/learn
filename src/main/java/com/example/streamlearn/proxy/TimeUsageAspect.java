package com.example.streamlearn.proxy;

/**
 * @program streamLearn
 * @ClassName TimeUsageAspect
 * @description
 * @create 2023-01-31 18:23
 **/
public class TimeUsageAspect implements IAspect {

    private long start;

    @Override
    public void before() {
        start = System.currentTimeMillis();
    }

    @Override
    public void after() {
        System.out.format("usage: %d ms\n", System.currentTimeMillis() - start);
    }
}
