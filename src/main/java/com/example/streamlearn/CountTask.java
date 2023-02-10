package com.example.streamlearn;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.concurrent.Callable;

class CountTask implements Callable<HashMap<String, Integer>> {

        private final long start;
        private final long end;
        private final String fileName;

        public CountTask(long start, long end, String fileName){
            this.start = start;
            this.end = end;
            this.fileName = fileName;
        }

        @Override
        public HashMap<String, Integer> call() throws Exception {
            var channel = new RandomAccessFile(fileName, "r").getChannel();
            // [start, end] -> Memory
            var mbuf = channel.map(
                    MapMode.READ_ONLY,
                    start,
                    end-start
            );
//            var startTime = System.currentTimeMillis();
            var bytes = new byte[(int)(end-start)];
            mbuf.get(bytes);
            HashMap<String, Integer> count = countByStr(bytes);
//            var dueEnd = System.currentTimeMillis();
//            System.out.println("执行任务耗时:"+(dueEnd-startTime));
            return count;
        }
    private static HashMap<String, Integer> countByStr(byte[] bytes) {
        var map = new HashMap<String, Integer>(16*1024);
        byte[] tmp = new byte[5];
        int j = 0;
        for (int i = 0; i < bytes.length; i++) {
            var b = bytes[i];
            if (b != ' ') {
                tmp[j++] = b;
            } else if (j>0){
                byte[] word = new byte[j];
                System.arraycopy(tmp,0,word,0,word.length);
                incKey(new String(word), map, 1);
                j = 0;
            }
        }
        return map;
    }

    private static void incKey(String str, HashMap<String, Integer> map, Integer num) {
        if (map.containsKey(str))
            map.put(str, map.get(str)+num);
        else
            map.put(str, num);
    }
}