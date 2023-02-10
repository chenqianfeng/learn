package com.example.streamlearn;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @program streamLearn
 * @ClassName ChineseTest
 * @description 中文乱码
 * @create 2023-01-30 16:17
 **/
public class ChineseTest {

    public static void main(String[] args) {
        var text = "带弧度压过镀硬铬31大叔杜伊了吗拉卡德那接口";
        var charSet = StandardCharsets.UTF_8;
        var bytes = charSet.encode(text).array();
        int length = bytes.length/10;

        var bbuf = ByteBuffer.allocate(20);
        var cbuf = CharBuffer.allocate(12);
        byte[] notReadBytes;
        for (int i = 0; i < length; i++) {
            // 每次读取 11 个字节
            var copyBytes = Arrays.copyOfRange(bytes, i*11, i * 11 + 11);
            bbuf.put(copyBytes);
            bbuf.flip();
            charSet.newDecoder().decode(bbuf, cbuf, true);
            cbuf.flip();

            var tmp = new char[cbuf.length()];

            if (cbuf.hasRemaining()){
                cbuf.get(tmp);
                System.out.println(new String(tmp) + " ");
            }
            cbuf.clear();
            notReadBytes = Arrays.copyOfRange(bbuf.array(), bbuf.position(), bbuf.limit());
            bbuf.clear();
            if (notReadBytes.length>0)
                bbuf.put(notReadBytes);
        }

    }

}
