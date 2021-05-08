package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdIn;

import java.io.ByteArrayInputStream;

public class BytesReader {
    private final ByteArrayInputStream bais;

    public BytesReader(byte[] bytes) {
        this.bais = new ByteArrayInputStream(bytes);
        System.setIn(bais);
    }

    public String readString() {
        return BinaryStdIn.readString();
    }

    public int readInt() {
        return BinaryStdIn.readInt();
    }

    public void close() {
        BinaryStdIn.close();
    }
}
