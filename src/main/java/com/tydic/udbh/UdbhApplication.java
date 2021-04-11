package com.tydic.udbh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan("com.tydic.udbh.repository")
@SpringBootApplication
@EnableAsync
@ServletComponentScan
public class UdbhApplication {


    public static void main(String[] args) {
        SpringApplication.run(UdbhApplication.class, args);
    }

}
