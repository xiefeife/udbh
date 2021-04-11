package com.last.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: xiehh
 * @Date:2020/11/28 15:21
 * @ClassName:PoiService
 */
public interface PoiService {

    Map<String,String> export(HttpServletResponse response, HttpServletRequest req);
}
