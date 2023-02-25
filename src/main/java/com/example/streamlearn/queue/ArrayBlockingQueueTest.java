package com.example.streamlearn.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ArrayBlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue;
        queue = new ArrayBlockingQueue(1000);
//        queue = new LinkedBlockingDeque();
//        queue = new LinkedBlockingQueue();
//        queue = new PriorityBlockingQueue();
//        queue = new DelayQueue();
//        queue = new SynchronousQueue();
//        queue = new LinkedTransferQueue();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++)
                    try {
                        queue.put((int)(Math.random()*10));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            }).start();
        }

        new Thread(()->{
            while (true)
                try {
                    System.out.println("take:"+queue.take());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }).start();
    }

}
