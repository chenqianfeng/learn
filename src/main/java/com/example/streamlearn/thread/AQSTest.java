package com.example.streamlearn.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @program streamLearn
 * @ClassName AQSTest,用AQS实现一个ReenterLock
 * @description
 * @create 2023-02-08 17:39
 **/
public class AQSTest {

    static class Mutex extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0,1);
        }
        @Override
        protected boolean tryRelease(int arg) {
            return compareAndSetState(1,0);
        }
    }

    public static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Mutex lock = new Mutex();
        Thread t1 = new Thread(()->{
            lock.acquire(0);
            for (int j = 0; j < 10000; j++) {
                i++;
            }
            lock.release(0);
        });
        Thread t2 = new Thread(()->{
            lock.acquire(0);
            for (int j = 0; j < 10000; j++) {
                i++;
            }
            lock.release(0);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
