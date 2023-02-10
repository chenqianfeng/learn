package com.example.streamlearn.sf.sort;

/**
 * @program streamLearn
 * @ClassName main
 * @description \
 * @create 2023-02-01 15:55
 **/
public class main {

    public static void main(String[] args) {
        Sort sort = new Sort();
        int[] ints = new int[0];
//        ints = sort.insertSort();// 直接插入排序
//        ints = sort.selectSort();// 选择排序
//        ints = sort.bubbleSort();// 冒泡排序
        ints = sort.quickSort();// 快速排序

        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }
}
