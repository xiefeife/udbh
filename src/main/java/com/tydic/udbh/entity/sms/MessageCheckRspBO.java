package com.tydic.udbh.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 短信验证码校验出参
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/6 17:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageCheckRspBO implements Serializable {

    private static final long serialVersionUID = 8886407898600513294L;

    private Boolean res;

    private String overtime;

}
