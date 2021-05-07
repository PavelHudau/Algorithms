package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BytesWriter {
    private final ByteArrayOutputStream baos;

    public BytesWriter() {
        this.baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);
    }

    public void write(int value) {
        BinaryStdOut.write(value);
    }

    public void write(String value) {
        BinaryStdOut.write(value);
    }

    public byte[] readBytesAndClose() {
        this.flush();
        byte[] output = this.baos.toByteArray();
        this.close();
        return output;
    }

    private void flush() {
        BinaryStdOut.flush();
    }

    private void close() {
        BinaryStdOut.close();
    }
}
