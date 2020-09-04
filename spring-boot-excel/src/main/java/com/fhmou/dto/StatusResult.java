package com.fhmou.dto;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
public class StatusResult extends BaseResult {

    public StatusResult() {
    }

    public StatusResult (WebConstantMsg constantMsg) {
        setResult(constantMsg);
    }

    public void setResult(WebConstantMsg constantMsg) {
        setCode(constantMsg.getCode());
        setMessage(constantMsg.getName());
        if(getCode() == 200) {
            setSuccess(true);
        }
    }
}