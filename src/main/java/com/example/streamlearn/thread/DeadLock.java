package com.example.streamlearn.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program streamLearn
 * @ClassName DeadLock
 * @description 死锁问题
 * @create 2023-02-08 18:23
 **/
public class DeadLock {

    static class Friend{
        ReentrantLock lock = new ReentrantLock();

        String name;

        public Friend(String name) {
            this.name = name;
        }

        public void bow(Friend friend){
            lock.lock();
            System.out.println(name+":"+friend.name+"向"+name+"鞠躬");
            friend.release(this);
            lock.unlock();
        }

        public void release(Friend friend){
            lock.lock();
            System.out.println(name+":"+name+"回了"+friend.name);
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Friend h = new Friend("小红");
        Friend m = new Friend("小明");

        new Thread(()->{
            h.bow(m);
        }).start();
        new Thread(()->{
            m.bow(h);
        }).start();
    }
}
