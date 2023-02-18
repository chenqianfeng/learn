package com.example.streamlearn.thread;

import java.util.concurrent.CountDownLatch;

public class CountdownLaunchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    Thread.sleep((int)(Math.random()*10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"work done!");
                countDownLatch.countDown();
            }, "thread:" + i).start();
        }
        countDownLatch.await();
        System.out.println("all work done");
    }

}
