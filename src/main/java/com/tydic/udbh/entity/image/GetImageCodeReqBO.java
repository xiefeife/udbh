package com.tydic.udbh.entity.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 获取图片验证码请求入参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetImageCodeReqBO implements Serializable {

    private static final long serialVersionUID = -1073458252707471097L;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号格式不对")
    private String mobile;

}
