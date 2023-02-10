package com.example.streamlearn.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @program streamLearn
 * @ClassName SemaphoreTest
 * @description AQS信号量实现
 * @create 2023-02-09 17:43
 **/
public class SemaphoreTest {

    static class MySemaphore extends AbstractQueuedSynchronizer{

        MySemaphore(int num){
            setState(num);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            int num = getState();
            if (num == 0) return -1;
            if (compareAndSetState(num, num-1))
                return num-1;
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int num = getState();
            if (compareAndSetState(num, num+1))
                return true;
            return false;
        }
    }

    public static void main(String[] args) {
        MySemaphore mySemaphore = new MySemaphore(3);
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                mySemaphore.tryAcquireShared(0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1->go");
                mySemaphore.tryReleaseShared(0);
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                mySemaphore.tryAcquireShared(0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2->go");
                mySemaphore.tryReleaseShared(0);
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                mySemaphore.tryAcquireShared(0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("3->go");
                mySemaphore.tryReleaseShared(0);
            }
        }).start();
    }
}
