package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StdInOutHelper {
    private ByteArrayInputStream bais;
    private ByteArrayOutputStream baos;

    public StdInOutHelper(byte[] programInput) {
        this.bais = new ByteArrayInputStream(programInput);
        System.setIn(this.bais);
        this.baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(this.baos);
        System.setOut(printStream);
    }

    public StdInOutHelper(String programInput) {
        this(programInput.getBytes());
    }

    public byte[] readOutputAndClose() {
        this.flush();
        byte[] output =  this.baos.toByteArray();
        this.close();
        return output;
    }

    private void flush() {
        BinaryStdOut.flush();
    }

    private void close() {
        BinaryStdIn.close();
        BinaryStdOut.close();
    }
}
