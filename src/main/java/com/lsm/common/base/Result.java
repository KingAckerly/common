package com.lsm.common.base;

/**
 * 统一返回结果
 *
 * @param <T>
 */
public class Result<T> {
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 时间戳
     */
    private Long timeStamp;
    /**
     * 具体对象
     */
    private T data;

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public Result<T> setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", timeStamp=" + timeStamp +
                ", data=" + data +
                '}';
    }
}
