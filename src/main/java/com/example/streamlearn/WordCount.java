package com.example.streamlearn;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @program streamLearn
 * @ClassName WordCount
 * @description 统计词频
 * @create 2023-01-30 17:41
 **/
public class WordCount {

    public static void main(String[] args) throws Exception {
        var fileName = "bigFile";
        var map = new HashMap<String, Integer>();
        var node = new Node();
        var start = System.currentTimeMillis();
//        wordCount(fileName, map);
//        wordCountQuick(fileName, map);
        wordCountQuickWithNode(fileName, node);
        System.out.format("during %d ms\n", System.currentTimeMillis() - start);
        System.out.println("统计词数："+node.getStrNumCount());
        System.out.println("添加次数："+node.addTime.get());
        System.out.println(node.getStringCount("aaaaa"));
        System.out.println(node.getStringCount("abcde"));
    }

    private static void wordCountQuickWithNode(String fileName, Node node) {
        var pool = ForkJoinPool.commonPool();;
        long chunkSize = 1024 * 1024 * 6;
        var file = new File(fileName);
        var fileSize = file.length();
        long position = 0;
        var tasks = new ArrayList<Future<Node>>();
        while (position < fileSize) {
            var next = Math.min(position + chunkSize, fileSize);
            var task = new CountTaskWithNode(position, next, fileName, node);
            position = next;
            tasks.add(pool.submit(task));
        }
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i);
            System.out.println("倒数第"+(tasks.size()-i)+"个任务执行完成");
        }

    }

    public static void wordCountQuick(String fileName, HashMap<String, Integer> map)
            throws ExecutionException, InterruptedException {
        var pool = ForkJoinPool.commonPool();

        long chunkSize = 1024 * 1024;
        var file = new File(fileName);
        var fileSize = file.length();
        long position = 0;
        var tasks = new ArrayList<Future<HashMap<String, Integer>>>();
        while (position < fileSize) {
            var next = Math.min(position + chunkSize, fileSize);
            var task = new CountTask(position, next, fileName);
            position = next;
            var submit = pool.submit(task);
            tasks.add(submit);
        }
        for (var future : tasks) {
            HashMap<String, Integer> res = future.get();
            for (Entry<String, Integer> entry : res.entrySet()) {
                incKey(entry.getKey(), map, entry.getValue());
            }
        }
    }

    private static void wordCount(String fileName, HashMap<String, Integer> map) throws Exception {
        var channel = new FileInputStream(fileName).getChannel();
        var buffer = ByteBuffer.allocate(1024 * 8);
        int capacity;
        int j;          // 存放最后一个空格的下标
        byte[] tmp1 = new byte[0];    // 存放末尾空格后的byte
        while ((capacity = channel.read(buffer)) != -1) {
            buffer.flip();
            byte[] source = Arrays.copyOfRange(buffer.array(), 0, capacity);
            buffer.clear();
            readBuffer(source, tmp1, map);
        }
        System.out.println(111);
    }

    private static void readBuffer(byte[] source, byte[] tmp1, HashMap<String, Integer> map) {
        var str = new String(source);
        var strMap = countByStr(str);
        for (var entry : strMap.entrySet()) {
            var word = entry.getKey();
            incKey(word, map, entry.getValue());
        }
    }

    // 准确的单词
    private static void exactReadBuffer(byte[] source, byte[] tmp1, HashMap<String, Integer> map) {
        int j;
        byte[] tmp2;    // 存放末尾空格前的byte
        byte[] realBytes;// 存放去掉加前去尾后的字节

        // 找到最后一个空格的下标
        j = source.length - 1;
        for (int i = source.length - 1; i >= 0; i--) {
            if (source[i] == ' ') {
                j = i;
                break;
            }
        }
        tmp2 = new byte[j + 1];
        System.arraycopy(source, 0, tmp2, 0, j + 1);
        realBytes = new byte[tmp1.length + tmp2.length];
        System.arraycopy(tmp1, 0, realBytes, 0, tmp1.length);
        System.arraycopy(tmp2, 0, realBytes, tmp1.length, tmp2.length);
        tmp1 = new byte[source.length - j - 1];
        if (tmp1.length > 0) {
            System.arraycopy(source, j, tmp1, 0, source.length - j - 1);
        }
        var str = new String(realBytes);
        var strMap = countByStr(str);
        for (var entry : strMap.entrySet()) {
            var word = entry.getKey();
            incKey(word, map, entry.getValue());
        }
    }

    private static HashMap<String, Integer> countByStr(String str) {
        var map = new HashMap<String, Integer>();
        var tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            var word = tokenizer.nextToken();
            incKey(word, map, 1);
        }
        return map;
    }

    private static void incKey(String str, HashMap<String, Integer> map, Integer num) {
        var count = map.getOrDefault(str, 0);
        map.put(str, count + num);
    }


}
