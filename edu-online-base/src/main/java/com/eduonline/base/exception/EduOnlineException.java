package com.eduonline.base.exception;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 本项目自定义异常类型
 * @date 2023/3/21 16:56
 */
public class EduOnlineException extends RuntimeException {

    private String errMessage;

    public EduOnlineException() {
    }

    public EduOnlineException(String message) {
        super(message);
        this.errMessage = message;

    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public static void cast(String message){
        throw new EduOnlineException(message);
    }
    public static void cast(CommonError error){
        throw new EduOnlineException(error.getErrMessage());
    }

}
