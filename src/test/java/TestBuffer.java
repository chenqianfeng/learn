import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program streamLearn
 * @ClassName TestBuffer
 * @description
 * @create 2023-02-22 13:55
 **/

public class TestBuffer {
    @Test
    public void test() throws Exception {
        File file = new File("test.mp3");
        FileInputStream fileInputStream = new FileInputStream(file);
        var in = fileInputStream.getChannel();
        A a = new A(in, 106);
        B b = new B(in, 320);
        Thread thread = new Thread(() -> a.read());
        Thread thread1 = new Thread(() -> b.read());
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
    }
    class A{
        private FileChannel channel;
        private ByteBuffer buffer;
        A(FileChannel channel, int size){
            this.channel = channel;
            this.buffer = ByteBuffer.allocate(size);
        }
        public void read() {
            while (true) {
                try {
                    if (-1 == channel.read(buffer)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.flip();
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }
        }
    }

    class B{
        private FileChannel channel;
        private ByteBuffer buffer;
        B(FileChannel channel, int size){
            this.channel = channel;
            this.buffer = ByteBuffer.allocate(size);
        }
        public void read() {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (-1 == channel.read(buffer)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.flip();
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }
        }
    }
}
