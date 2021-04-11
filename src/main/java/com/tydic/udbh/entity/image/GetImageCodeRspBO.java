package com.tydic.udbh.entity.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 获取图片验证码请求出参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetImageCodeRspBO implements Serializable {

    private static final long serialVersionUID = -6348052098941898437L;

    private String src;
}
