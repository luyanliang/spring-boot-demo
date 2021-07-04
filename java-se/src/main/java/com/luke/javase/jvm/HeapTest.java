package com.luke.javase.jvm;

import java.util.ArrayList;

/**
 * 堆内存测试
 * +
 */
public class HeapTest {

    byte[] a = new byte[1024 * 100]; // 100KB

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new HeapTest());
            Thread.sleep(10);
        }
    }
}
