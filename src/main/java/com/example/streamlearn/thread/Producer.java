package com.example.streamlearn.thread;

import java.util.Queue;
import java.util.Random;

class Producer{
    Queue<Integer> queue;

    public Producer(Queue<Integer> queue) {
        this.queue = queue;
    }

    public void produce() throws InterruptedException {
            while (true) {
                synchronized (queue) {
                    if (queue.size() == 10) {
                        continue;
                    }
                    int e = new Random().nextInt(10);
                    queue.add(e);
                    System.out.println(Thread.currentThread().getName() + "生产: " + e + "===size:" + queue.size());
                }
                Thread.sleep((int)(Math.random()*1000));
            }
        }
    }