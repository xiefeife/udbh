package com.tydic.udbh.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 短信信息
 *
 * @author Cloud(郭云峰)
 * @date 2021/2/2 11:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 2140025219341425569L;

    /**
     * 模板参数
     */
    private Object paramInfo;

    /**
     * 模板信息
     */
    private String templateId;

    /**
     * 发送者
     */
    private String sender;

    /**
     * 接收人
     */
    private String recipient;
}
