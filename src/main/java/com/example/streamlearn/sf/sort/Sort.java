package com.example.streamlearn.sf.sort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @program streamLearn
 * @ClassName 排序算法
 * @description
 * @create 2023-02-01 13:50
 **/
public class Sort {

    private int[] source;

    Sort(){
        source = new int[100000];
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            source[i] = random.nextInt(100_000);
        }
    }

    // source 9 8 10 154 12 35
    public int[] insertSort(){
        for (int i =1; i<source.length; i++){
            int cur = source[i];
            if (cur >= source[i-1]) continue;
            for (int j = i-1; j>=0; j--){
                int tmp = source[j];
                source[j] = cur;
                source[j+1] = tmp;
                if (j == 0 || source[j]> source[j-1])
                    break;
            }
        }
        return source;
    }

    public int[] selectSort(){
        for (int i = 0; i<source.length; i++){
            int minIndex = minIndex(i, source.length, source);
            int tmp = source[i];
            source[i] = source[minIndex];
            source[minIndex] = tmp;
        }
        return source;
    }

    static public int minIndex(int i, int j, int[] source) {
        int minIndex = i;
        int minValue = source[i];
        for (int k = i; k<j; k++){
            if (source[k]<minValue) {
                minIndex = k;
                minValue = source[k];
            }
        }
        return minIndex;
    }

    public int[] bubbleSort() {
        boolean flag = true;
        for (int i = source.length-1; i > 0; i--){
            flag = true;
            for (int j = 0; j < i ; j++) {
                if (source[j]>source[j+1]){
                    int tmp = source[j+1];
                    source[j+1] = source[j];
                    source[j] = tmp;
                    flag = false;
                }
            }
            if (flag) break;
        }
        return source;
    }

    public int[] quickSort() {
        int[] target = new int[source.length];
        List<Integer> sorted = sort(Arrays.stream(source).boxed().collect(Collectors.toList()));
        for (int i = 0; i < sorted.size(); i++) {
            target[i] = sorted.get(i);
        }
        return target;
    }

    private List<Integer> sort(List<Integer> list) {
        if (list.size()==1) return list;
        int x = list.get(0);
        List left = list.stream().filter(a->a<x).collect(Collectors.toList());
        List middle = list.stream().filter(a->a==x).collect(Collectors.toList());
        List right = list.stream().filter(a->a>x).collect(Collectors.toList());
        if (left.size()>1) sort(left);
        if (right.size()>1) sort(right);
        left.addAll(middle);
        left.addAll(right);
        return left;
    }
}
