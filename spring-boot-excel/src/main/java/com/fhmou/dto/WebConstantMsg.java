package com.fhmou.dto;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
public enum WebConstantMsg {

    OK("操作成功", 200),
    SEND_SUCCESS("发送成功", 201),

    BAD_REQUEST("请求参数有误", 400),
    UNAUTHORIZED("没有权限操作", 401),
    REQUEST_EMPTY_ERROR("请求参数不能为空", 402),
    NOT_ACCEPTABLE("请求资源的类型不一致", 406),
    NOT_LOGIN("请登录后重试", 407),
    PUSH_EXIST("已推荐，请勿重复推荐", 408),
    HAS_BEEN_SEND("手机验证码短信已发送，15分钟内有效",409),
    VALIDATE_IS_NULL("手机验证码为空或者已过期",410),
    USER_NON_EXISTENT("用户不存在",411),
    USER_ACCOUNT_PSW_ERROR("用户名或者密码错误",412),

    INTERNAL_SERVER_ERROR("内部服务器错误", 500),
    USER_EXIST("用户已被注册", 501),
    LINK_INVALID("链接已失效", 502),
    LINK_NOT_EXIST("链接不存在", 503),
    FILE_NOT_NULL("文件不能为空", 504),
    UPLOAD_ERROR("上传失败，请重试", 505),
    SKU_PUSH_EXIST("商品已被推荐，不能重复推荐", 506),
    SKU_COVER_NO_MAIN("商品主图不为能空", 507),
    ENTERPRISE_NOT_NULL("企业不为能空", 508),
    VALIDATE_CODE_ERROR("验证码错误", 509),
    VALIDATE_CODE_INVALID("验证码失效", 510),
    MESSAGE_SEND_FAILURE("短信发送失败",511),
    UPDATE_FAILURE("更新信息失败",512),
    ADD_FAILURE("新增失败",513);

    private String name;
    private int code;

    WebConstantMsg(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static WebConstantMsg getInstanceByCode(int code) {
        for (WebConstantMsg msg : WebConstantMsg.values()) {
            if (code == msg.getCode()) {
                return msg;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
