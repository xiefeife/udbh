package com.last.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: xiehh
 * @Date:2020/11/30 12:05
 * @ClassName:CommonConfig
 */
@Data
@Component
public class CommonConfig {
    /**
     * OSS图片转发路径
     */
//    @Value("${oss.tigong}")
    private String ossImagesProvider;

    public String appendOssImagesProviderPrefix(String url) {
        return ossImagesProvider + url;
    }

    public String removeOssImagesProviderPrefix(String url) {
        if (url.startsWith(ossImagesProvider)) {
            return url.substring(ossImagesProvider.length());
        }
        return "";
    }

}
