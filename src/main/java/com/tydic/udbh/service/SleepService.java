package com.tydic.udbh.service;

public interface SleepService {

    /**
     * 异步方法
     * @throws InterruptedException
     */
    void asyncSleep() throws InterruptedException;

    /**
     * 同步方法
     * @throws InterruptedException
     */
    void syncSleep() throws InterruptedException;
}
