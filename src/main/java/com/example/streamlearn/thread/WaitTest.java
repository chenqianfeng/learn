package com.example.streamlearn.thread;

import java.util.Date;

/**
 * @program streamLearn
 * @ClassName WaitTest
 * @description
 * @create 2023-02-06 18:31
 **/
public class WaitTest {
    public static void main(String[] args) throws InterruptedException {
//        syncWait();

    }

    private static void syncWait() throws InterruptedException {
        Object obj = new Object();
        Thread t1 = new Thread(()->{
            try {
                System.out.println("t1 before");
                synchronized(obj){
                    System.out.println("t1"+new Date().getTime());
                    obj.wait();
                }
                System.out.println("t1 after");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                System.out.println("t2 before");
                synchronized(obj){
                    System.out.println("t2"+new Date().getTime());
                    obj.wait();
                }
                System.out.println("t2 after");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(1);
        synchronized (obj){
            obj.notifyAll();
        }
    }
}
