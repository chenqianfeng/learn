package com.example.streamlearn;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program streamLearn
 * @ClassName StreamLearn
 * @description
 * @create 2023-01-29 14:36
 **/
public class StreamLearn {

    public static void main(String[] args) {
        testRandom();
    }

    private static void testRandom() {
        var r = new Random();
        var collection = IntStream.range(0, 200_000_000)
                .map(x-> r.nextInt(10_000_000))
                .boxed()
                .collect(Collectors.toList());
        long timeMillis = System.currentTimeMillis();
        System.out.println(collection.stream().max(Comparator.comparingInt(a -> a)).get());
        System.out.println("time:" + (System.currentTimeMillis()-timeMillis));
//        System.out.println(Runtime.getRuntime().availableProcessors());
        timeMillis = System.currentTimeMillis();
        System.out.println(collection.parallelStream().max(Comparator.comparingInt(a -> a)).get());
        System.out.println("time:" + (System.currentTimeMillis()-timeMillis));

    }



}
