package com.wasp.io.bufferedStream;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {
    private static final int DEFAULT_SIZE = 20;
    private OutputStream target;
    private byte[] buffer;
    private int offset;
    private boolean closed = false;

    public BufferedOutputStream(OutputStream target) {
        this.target = target;
        buffer = new byte[DEFAULT_SIZE];
    }

    @Override
    public void write(int b) throws IOException {
        throwIOExceptionIfClosed();
        flushBufferIfIndexOOB();
        buffer[offset++] = (byte) b;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throwIOExceptionIfClosed();

        if (len < buffer.length - offset) {
            System.arraycopy(b, off, buffer, offset, len);
            offset += len;
        } else if (len >= buffer.length) {
            flushBuffer();
            target.write(b, off, len);
        } else if (len > buffer.length - offset) {
            flushBuffer();
            System.arraycopy(b, off, buffer, 0, len);
            offset = len;
        }
    }

    @Override
    public void flush() throws IOException {
        flushBuffer();
        target.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        target.close();
        closed = true;
    }

    int getBufferSize() {
        return buffer.length;
    }

    private void flushBuffer() throws IOException {
        if (offset > 0) {
            target.write(buffer);
            offset = 0;
        }
    }

    private void flushBufferIfIndexOOB() throws IOException {
        if (offset >= buffer.length) flushBuffer();
    }

    private void throwIOExceptionIfClosed() throws IOException {
        if (closed) {
            throw new IOException("The stream is already closed");
        }
    }
}
