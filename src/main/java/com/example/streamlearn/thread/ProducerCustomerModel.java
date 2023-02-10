package com.example.streamlearn.thread;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerCustomerModel {

    ThreadLocal threadLocal = new ThreadLocal<String>();
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList();
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    producer.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        new Thread(()->{
            try {
                consumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
