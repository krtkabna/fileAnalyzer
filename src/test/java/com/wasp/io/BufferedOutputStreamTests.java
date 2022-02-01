package com.wasp.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BufferedOutputStreamTests {
    private static final String FILE_PATH = "src/test/resources/test.txt";
    private static final String OUT = "hello io";
    private static final String OUT_WITH_OFFSET = "hi everyone";
    private static final int OUT_WITH_OFFSET_SIZE_TRIMMED = "everyone".length();
    private OutputStream bufferedOutputStream;

    @BeforeEach
    public void init() {
        bufferedOutputStream = createOutputStream();
    }

//    @AfterEach
//    public void clear() throws IOException {
//        clearFile();
//    }

    private void clearFile() throws IOException {
        try (InputStream fileInputStream = createInputStream()) {
            clearFile(fileInputStream);
        }
    }

    private void clearFile(InputStream fileInputStream) throws IOException {
        while (fileInputStream.read() != -1) {
            bufferedOutputStream.write(-1);
        }
    }

    @Test
    void write() throws IOException {
        try {
            for (byte b : OUT.getBytes()) {
                bufferedOutputStream.write(b);
            }
        } finally {
            bufferedOutputStream.close();
        }

        compareContentsAndPrint();
    }

    @Test
    void writeFromByteArray() throws IOException {
        byte[] bytes = OUT.getBytes();

        try {
            bufferedOutputStream.write(bytes);
        } finally {
            bufferedOutputStream.close();
        }

        compareContentsAndPrint();
    }

    @Test
    void writeLenFromOffset() throws IOException {
        byte[] bytes = OUT_WITH_OFFSET.getBytes();

        try {
            bufferedOutputStream.write(bytes, 3, OUT_WITH_OFFSET_SIZE_TRIMMED);
        } finally {
            bufferedOutputStream.close();
        }

        compareContentsAndPrintWithOffset();
    }

    @Test
    void writeWithoutFlush() throws IOException {
        byte[] bytes = OUT.getBytes();
        bufferedOutputStream.write(bytes);

        InputStream in = createInputStream();
        assertEquals(-1, in.read());
    }

    @Test
    void flush() throws IOException {
        byte[] bytes = OUT.getBytes();
        bufferedOutputStream.write(bytes);

        InputStream in = createInputStream();
        assertEquals(-1, in.read());

        bufferedOutputStream.flush();
        compareContentsAndPrint();
    }

    @Test
    void close() throws IOException {
        bufferedOutputStream.close();
        assertThrows(IOException.class, () -> bufferedOutputStream.write(OUT.getBytes()));
    }

    private RuntimeException re(IOException e) {
        return new RuntimeException(e.getMessage(), e);
    }

    private InputStream createInputStream() {
        try {
            return new FileInputStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw re(e);
        }
    }

    private OutputStream createOutputStream() {
        try {
            return new BufferedOutputStream(new FileOutputStream(FILE_PATH));
        } catch (FileNotFoundException e) {
            throw re(e);
        }
    }

    /**
     * Compares <code>BufferedOutputStream</code>'s contents with <code>toWrite</code> and prints them
     *
     * @throws IOException
     */
    private void compareContentsAndPrint() throws IOException {
        try (InputStream in = createInputStream()) {
            int read;
            for (byte b : OUT.getBytes()) {
//                System.out.println(in.read()); //fixme remove
                if ((read = in.read()) != -1) {
                    System.out.print((char) read);
                    assertEquals(read, b);
                }
            }
            System.out.println("\n========");
        }
    }

    private void compareContentsAndPrintWithOffset() throws IOException {
        try (InputStream in = createInputStream()) {
            byte[] src = OUT_WITH_OFFSET.getBytes();
            byte[] bytes = new byte[OUT_WITH_OFFSET_SIZE_TRIMMED];
            //for 'hello io' srcPos = 6, length = 2
            //TODO fix test
            System.arraycopy(src, 3, src, 0, bytes.length - 1);//everyone
            int read;
            for (byte b : bytes) {
                read = in.read();
                if (read != -1) {
                    System.out.print((char) read);
                    assertEquals(read, b);
                }
            }
            System.out.println("\n========");
        }
    }
}
