package com.eduonline.base.exception;

import java.io.Serializable;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 和前端约定返回的异常信息模型
 * @date 2023/3/21 16:55
 */
public class RestErrorResponse implements Serializable {

 private String errMessage;

 public RestErrorResponse(String errMessage){
  this.errMessage= errMessage;
 }

 public String getErrMessage() {
  return errMessage;
 }

 public void setErrMessage(String errMessage) {
  this.errMessage = errMessage;
 }
}
