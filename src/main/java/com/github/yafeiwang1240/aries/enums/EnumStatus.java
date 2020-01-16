package com.github.yafeiwang1240.aries.enums;

/**
 * 状态
 *
 */
public enum EnumStatus {

    WAIT(1, "等待中"),
    RUNNABLE(2, "可执行"),
    RUNNING(3, "执行中"),
    SUCCESS(4, "成功"),
    FAILED(5, "执行结束"),
    SHUTDOWN(6, "关闭");

    private Integer code;
    private String desc;
    EnumStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
