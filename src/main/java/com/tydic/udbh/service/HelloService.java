package com.tydic.udbh.service;

public interface HelloService {

    /**
     *  异步方法
     * @return
     */
    String synchSayHello();

    /**
     * 同步方法
     * @return
     */
    String asynchSayHello();

}
