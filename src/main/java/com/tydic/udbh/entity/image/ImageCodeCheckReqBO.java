package com.tydic.udbh.entity.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 图片验证码校验入参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageCodeCheckReqBO implements Serializable {

    private static final long serialVersionUID = 1075235008172299145L;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号长度应为11位")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 5,max = 5,message = "图形验证码长度应为5位")
    private String code;
}
