package com.signalway.entity;

public class ErrorCode {

 /**
  * 广义成功
  */
 public final static String Success = "0";
 /**
  * 无效参数
  */
 public final static String InvalidArguments = "4";
 /**
  * 未授权/登录失败/鉴权失败 
  */
 public final static String Unauthorized = "1";
 /**
  * 广义失败
  */
 public final static String Failure = "3";
 
 /**
  * 服务器繁忙,请稍候重试！
  */
 public final static String TooBusy = "-2";
 /**
  * 访问太频繁,本次请求被拒绝！
  */
 public final static String TooOften = "-3";

}