package com.tydic.udbh.entity.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图片验证码校验出参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageCodeCheckRspBO implements Serializable {

    private static final long serialVersionUID = -5326219483042222545L;

    private Boolean res;
}
