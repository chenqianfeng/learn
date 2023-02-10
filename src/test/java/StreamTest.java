import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @program streamLearn
 * @ClassName StreamTest
 * @description
 * @create 2023-01-30 10:45
 **/
public class StreamTest {

    @Test
    public void test_parallel(){
        byte[] bytes = new byte[]{0,1,2,3,4,5,6,7,8,9,' '};
        byte[] bytes1 = new byte[]{0,1,2,3,4,5,6,7,8,9,' '};
        byte[] res = new byte[0];
        System.arraycopy(bytes, 0, res, 0, bytes.length);
        System.arraycopy(bytes1, 0, res, bytes.length, bytes1.length);
        System.out.println(res.length);
    }

    @Test
    public void test_bytes(){
        byte[] res = new byte[10];
        res[0] = 'a';
        res[1] = 'b';
        res = Arrays.copyOfRange(res, 0, res.length);
        System.out.println(new String(res));
    }

    @Test
    public void test_bytes2(){
        char a = 97;
        int b = a;
        System.out.println(b);
    }

    @Test
    public void test_stream_sum(){
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

    }

    @Test
    public void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }
}
