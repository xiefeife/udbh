package com.tydic.udbh.service.impl;

import com.tydic.udbh.service.HelloService;
import com.tydic.udbh.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class HelloServiceImpl implements HelloService {

    @Autowired
    private SleepService sleepService;

    @Override
    public String synchSayHello() {
        try {
            sleepService.syncSleep();
            return "hello world,这是同步方法";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String asynchSayHello() {
        try {
            System.out.println("主线程 "+Thread.currentThread().getName());
            sleepService.asyncSleep();
            return "hello world,这是异步方法";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
