package com.example.streamlearn;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.Callable;

/**
 * @program streamLearn
 * @ClassName CountTaskWithNode
 * @description 带有数据结构的统计词频任务
 * @create 2023-01-31 20:48
 **/
public class CountTaskWithNode implements Callable {

    private final long start;
    private final long end;
    private final String fileName;
    private Node node;

    public CountTaskWithNode(long start, long end, String fileName, Node node) {
        this.start = start;
        this.end = end;
        this.fileName = fileName;
        this.node = node;
    }

    @Override
    public Node call() {
        FileChannel channel;
        try {
            channel = new RandomAccessFile(fileName, "r").getChannel();
            var mbuf = channel.map(
                    MapMode.READ_ONLY,
                    start,
                    end - start
            );
            var bytes = new byte[(int) (end - start)];
            mbuf.get(bytes);
            changeNode(node, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.node;
    }

    private static void changeNode(Node node, byte[] bytes) {
        byte[] tmp = new byte[5];
        int j = 0;
        for (int i = 0; i < bytes.length; i++) {
            var b = bytes[i];
            if (b != ' ') {
                tmp[j++] = b;
            } else if (j > 0) {
                byte[] word = new byte[j];
                System.arraycopy(tmp, 0, word, 0, word.length);
                node.addStr(new String(word));
                j = 0;
            }
        }
    }
}
