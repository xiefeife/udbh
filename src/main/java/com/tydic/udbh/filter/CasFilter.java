package com.tydic.udbh.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CasFilter {

    @Bean
    public FilterRegistrationBean testFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        System.out.println("进入拦截器中");
        registrationBean.setFilter(new CasFiter());

        List<String> a = new ArrayList<>();
//        a.add("/bb");
        registrationBean.setUrlPatterns(a);
        Map<String,String> map = new HashMap<>();
//        map.put("111","222");
        registrationBean.setInitParameters(map);
        registrationBean.setName("filter");
        return registrationBean;
    }


}
