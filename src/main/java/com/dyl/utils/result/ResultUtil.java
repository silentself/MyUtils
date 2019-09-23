package com.dyl.utils.result;

import com.dyl.entities.result.ResultConstant;
import com.dyl.entities.result.VarysResult;

/**
 * @version: 1.0
 * @Description:自定义返回结果工具类
 * @author: tomcat
 * @date: 2018年11月1日下午1:31:58
 */
public class ResultUtil {

    /*
     * **************************************************************************
     * 调用过程无异常的返回方法
     * **************************************************************************
     */

    /**
     * status为OK，status、msg为null
     *
     * @return
     */
    public static <T> VarysResult<T> doSuccess() {
        return VarysResultMethod.doSuccess(null, null);
    }

    /**
     * status为OK，msg为null，data自定义
     *
     * @param data
     * @return
     */
    public static <T> VarysResult<T> doSuccess(T data) {
        return VarysResultMethod.doSuccess(data);
    }

    /**
     * status为OK，msg、data自定义
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> VarysResult<T> doSuccess(String msg, T data) {
        return VarysResultMethod.doSuccess(msg, data);
    }

    /*
     * **************************************************************************
     * 失败的返回方法
     * **************************************************************************
     */

    /**
     * data为null,status为error,msg自定义
     *
     * @param msg
     * @return
     */
    public static <T> VarysResult<T> doFailure(String msg) {
        return VarysResultMethod.doFailure(null, msg);
    }

    /**
     * data为null,status为error,msg自定义
     *
     * @param msg
     * @return
     */
    public static <T> VarysResult<T> doFailure(T data, String msg) {
        return VarysResultMethod.doFailure(data, msg);
    }

    /*
     * **************************************************************************
     * 自定义响应状态
     * **************************************************************************
     */

    /**
     * 自定义status，data、msg为null
     *
     * @param resultCode
     * @return
     */
    public static <T> VarysResult<T> response(int resultCode) {
        return VarysResultMethod.response(resultCode, null, null);
    }

    /**
     * 自定义status、msg,data为null
     *
     * @param resultCode
     * @param msg
     * @return
     */
    public static <T> VarysResult<T> response(int resultCode, String msg) {
        return VarysResultMethod.response(resultCode, msg, null);
    }

    /**
     * 自定义status、msg、data
     *
     * @param resultCode
     * @param msg
     * @param data
     * @return
     */
    public static <T> VarysResult<T> response(int resultCode, String msg, T data) {
        return VarysResultMethod.response(resultCode, msg, data);
    }

    static class VarysResultMethod {
        private static <T> VarysResult<T> doSuccess(String msg, T data) {
            return new VarysResult<T>().setData(data).setStatus(ResultConstant.OK).setMessage(msg);
        }

        private static <T> VarysResult<T> doSuccess(T data) {
            return new VarysResult<T>().setData(data).setStatus(ResultConstant.OK);
        }

        private static <T> VarysResult<T> doFailure(T data, String msg) {
            return new VarysResult<T>().setStatus(ResultConstant.ERROR).setData(data).setMessage(msg);
        }

        private static <T> VarysResult<T> response(int resultCode, String msg, T data) {
            return new VarysResult<T>().setData(data).setMessage(msg).setStatus(String.valueOf(resultCode));
        }
    }
}
