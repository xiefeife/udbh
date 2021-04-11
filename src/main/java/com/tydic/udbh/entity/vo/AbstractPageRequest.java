package com.last.demo.entity.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author: xiehh
 * @Date:2020/11/21 19:07
 * @ClassName:AbstractPageRequest
 */
@Data
public abstract class AbstractPageRequest {

    /**
     * 当前页面
     */
    @NotNull(message = "当前页不能为空")
    @Min(value = 1, message = "页码数最小为1")
    private Integer page;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    @Min(value = 1, message = "页面大小最小为1")
    private Integer pageSize;
}
