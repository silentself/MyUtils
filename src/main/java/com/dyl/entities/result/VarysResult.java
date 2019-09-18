package com.dyl.entities.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Date 2019/9/18 14:35
 * @Author Dong YL
 * @Email silentself@126.com
 */
@Data
@Accessors(chain = true)
public class VarysResult<T> {

    private int code;
    private String message;
    private String status;
    private T data;
}
