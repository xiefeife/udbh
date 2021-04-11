package com.last.demo.util.io;

/**
 * @author: xiehh
 * @Date:2020/12/4 10:33
 * @ClassName:TestThread
 */
public class TestThread implements  Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("一边工作");
        }
    }

    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        testThread.run();
    }

}
