package com.last.demo.controller;

import javax.activation.MailcapCommandMap;

/**
 * @author: xiehh
 * @Date:2020/12/4 11:43
 * @ClassName:Thread
 */
public class ManyThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("执行了" + i + "次");
        }
    }

    public static void main(String[] args) {
        ManyThread manyThread = new ManyThread();
        Thread t1 = new Thread(manyThread,"这是线程名");
        t1.start();
    }


}
