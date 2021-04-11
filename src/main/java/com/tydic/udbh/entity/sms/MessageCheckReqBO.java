package com.tydic.udbh.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 短信验证码校验入参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageCheckReqBO implements Serializable {

    private static final long serialVersionUID = 4679980502741578694L;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号长度应为11位")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Length(min=6,max = 6,message = "短信验证码长度应该为6位")
    private String code;

}
