package com.example.streamlearn.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicbarTest {

    static int page = 0;
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
        try {
            Thread.sleep((int)(Math.random()*10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sync");
        page++;
    });

    static void prepareOrder(){
        while (page<1000){
            try {
                System.out.println("order prepare ready;Time: "+page);
                cyclicBarrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void prepareProducts(){
        while (page<1000){
            try {
                System.out.println("product prepare ready;Time: "+page);
                cyclicBarrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        new Thread(CyclicbarTest::prepareOrder, "prepareOrder:").start();
        new Thread(CyclicbarTest::prepareProducts, "prepareProducts:").start();
    }
}
