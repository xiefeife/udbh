package com.tydic.udbh.async;

import cn.hutool.core.date.DateUtil;
import com.tydic.udbh.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.time.Instant;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    /**
     * 异步方法
     *
     * @return
     */
    @RequestMapping("/asyn")
    public String getAsynHello() {
        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.asynchSayHello();

        long f = Instant.now().toEpochMilli();
        return s + " 时间: " + (f - n);

    }

    /**
     * 同步方法
     * * @return
     */
    @RequestMapping("/sync")
    public String getSyncHello() {

        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.synchSayHello();

        long f = Instant.now().toEpochMilli();
        return s + " 时间: " + (f - n);
    }
}
