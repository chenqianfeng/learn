package com.example.streamlearn.thread;

import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserTest {

    static Phaser phaser = new Phaser();
    static ExecutorService executorService = Executors.newCachedThreadPool();

    static class Worker implements Runnable{
        @Override
        public void run() {
            phaser.register();
            while (true) {
                try {
                    Thread.sleep(50);
                    System.out.println("i'm working @" + phaser.getPhase());
                    phaser.arriveAndAwaitAdvance();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        PhaserTest phaserTest = new PhaserTest();
        PhaserTest.run();
    }

    public static void run() {
        phaser.register();
        executorService.execute(new Worker());
        executorService.execute(new Worker());
        executorService.execute(new Worker());
        executorService.execute(new Worker());
        while (true){
            phaser.arriveAndAwaitAdvance();
            System.out.println("sync......"+phaser.getPhase());
        }
    }

}
