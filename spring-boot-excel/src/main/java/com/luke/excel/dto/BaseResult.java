package com.luke.excel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * functional describe: 返回结果信息
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
@Data
public class BaseResult implements Serializable {

    /** 回复信息 */
    private String message;

    /** 是否成功 */
    private boolean success;

    /** 状态码 */
    private int code;

    /** 返回数据结果 */
    private Object data;

    public BaseResult() {}

    public BaseResult(String message, boolean success, int code) {
        this.message = message;
        this.success = success;
        this.code = code;
    }

    public BaseResult(String message, boolean success, int code, Object data) {
        this.message = message;
        this.success = success;
        this.code = code;
        this.data = data;
    }
}
