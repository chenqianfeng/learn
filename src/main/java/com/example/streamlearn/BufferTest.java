package com.example.streamlearn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @program streamLearn
 * @ClassName BufferTest
 * @description buffer原理
 * @create 2023-01-30 14:53
 **/
public class BufferTest {

    public static void main(String[] args) throws Exception {
        String fileName = "bigFile";
        genFile(fileName);
//        readFile(fileName);
//        read_noi(fileName);
    }

    private static void read_noi(String fileName) throws Exception {
        var channel = new FileInputStream(fileName).getChannel();
        var buffer = ByteBuffer.allocate(1024*8);
        var start = System.currentTimeMillis();

        while (channel.read(buffer) != -1){
            buffer.flip();
            buffer.clear();
        }
        System.out.format("during %d ms", System.currentTimeMillis() - start);
    }

    private static void readFile(String fileName) throws Exception {
        var fin = new BufferedInputStream(new FileInputStream(fileName), 64*1024);
        var start = System.currentTimeMillis();
        int b;
        byte[] bytes = new byte[8*1024];
        while ((b = fin.read(bytes)) != -1){
        }
        System.out.format("during %d ms", System.currentTimeMillis() - start);
    }

    private static void genFile(String fileName) throws Exception {
        Random r = new Random();
        var fout = new BufferedOutputStream(new FileOutputStream(fileName), 1024*1024);
        var start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000_000; i++) {
            for (int j = 0; j < 5; j++) {
                fout.write(97 + r.nextInt(5));
            }
            // 写入一个空格用于分割单词
            fout.write(' ');
        }
        fout.close();
        System.out.format("during %d ms", System.currentTimeMillis() - start);
    }

}
