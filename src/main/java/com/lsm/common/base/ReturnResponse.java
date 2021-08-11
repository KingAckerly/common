package com.lsm.common.base;

/**
 * 统一返回
 */
public class ReturnResponse {

    /**
     * 有参成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result().setCode("0000").setMsg("成功").setSuccess(true).setTimeStamp(System.currentTimeMillis()).setData(data);
    }

    /**
     * 无参成功
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String msg) {
        return new Result().setCode("0000").setMsg(msg).setSuccess(true).setTimeStamp(System.currentTimeMillis()).setData(null);
    }

    /**
     * 请求参数校验失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> failParame(String msg) {
        return new Result().setCode("8888").setMsg(msg).setSuccess(false).setTimeStamp(System.currentTimeMillis()).setData(null);
    }

    /**
     * 异常,指定msg返回,无data
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> assembleRespError(String msg) {
        return new Result<T>().setCode("9999").setMsg(msg).setTimeStamp(System.currentTimeMillis()).setData(null);
    }
}
