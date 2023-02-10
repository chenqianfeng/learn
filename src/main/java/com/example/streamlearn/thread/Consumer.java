package com.example.streamlearn.thread;

import java.util.Queue;

class Consumer{
    Queue<Integer> queue;

    public Consumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (queue) {
                if (queue.size() == 0) {
                    continue;
                }
                Integer peek = queue.poll();
                System.out.println(Thread.currentThread().getName() + "消费: " + peek + "===size:" + queue.size());
            }
            Thread.sleep((int)(Math.random()*5));
        }
    }
}