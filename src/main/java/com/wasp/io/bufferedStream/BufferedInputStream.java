package com.wasp.io.bufferedStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferedInputStream extends InputStream {
    private static final int DEFAULT_SIZE = 20;
    private InputStream target;
    private byte[] buffer;
    private int offset;
    private boolean closed = false;

    public BufferedInputStream(InputStream target) {
        this.target = target;
        buffer = new byte[DEFAULT_SIZE];
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }

    @Override
    public void close() throws IOException {
        while (buffer != null) {
            buffer = null;
            InputStream in = target;
            target = null;
            in.close();
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return super.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return super.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return super.readNBytes(b, off, len);
    }
}
