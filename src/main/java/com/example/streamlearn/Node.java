package com.example.streamlearn;

import java.util.concurrent.atomic.AtomicInteger;

class Node {

    AtomicInteger addTime = new AtomicInteger();

    int[] one = new int[5];
    int[] two = new int[5 * 5];
    int[] three = new int[5 * 5 * 5];
    int[] four = new int[5 * 5 * 5 * 5];
    int[] five = new int[5 * 5 * 5 * 5 * 5];

    public void addStr(String str) {
        addTime.incrementAndGet();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] - 97);
        }
        switch (str.length()) {
            case 1 -> {
                synchronized (one) {
                    int index = chars[0];
                    one[index]+=1;
                }
            }
            case 2 -> {
                synchronized (two) {
                    int index = chars[0] * 5 + chars[1];
                    two[index]+=1;
                }
            }
            case 3 -> {
                synchronized (three) {
                    int index = chars[0] * 5 * 5 + chars[1] * 5 + chars[2];
                    three[index]+=1;
                }
            }
            case 4 -> {
                synchronized (four) {
                    int index = chars[0] * 5 * 5 * 5 + chars[1] * 5 * 5 + chars[2] * 5
                            + chars[3];
                    four[index]+=1;
                }
            }
            case 5 -> {
                synchronized (five) {
                    int index = chars[0] * 5 * 5 * 5 * 5 + chars[1] * 5 * 5 * 5
                            + chars[2] * 5 * 5 + chars[3] * 5 + chars[4];
                    five[index]+=1;
                }
            }
            default -> {
            }
        }
    }

    public int getStringCount(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] - 97);
        }
        return switch (str.length()) {
            case 1 -> one[chars[0]];
            case 2 -> two[chars[0] * 5 + chars[1]];
            case 3 -> three[chars[0] * 5 * 5 + chars[1] * 5 + chars[2]];
            case 4 -> four[chars[0] * 5 * 5 * 5 + chars[1] * 5 * 5 + chars[2] * 5 + chars[3]];
            case 5 -> five[chars[0] * 5 * 5 * 5 * 5 + chars[1] * 5 * 5 * 5 + chars[2] * 5 * 5 + chars[3] * 5
                    + chars[4]];
            default -> 0;
        };
    }

    int getStrNumCount(){
        int sum=0;
        sum = getSum(sum, one);
        sum = getSum(sum, three);
        sum = getSum(sum, two);
        sum = getSum(sum, four);
        sum = getSum(sum, five);
        return sum;
    }

    private int getSum(int sum, int[] data) {
        for (int i = 0; i < data.length; i++) {
            sum +=data[i];
        }
        return sum;
    }
}
