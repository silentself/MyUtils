package com.dyl.entities.email;

import lombok.Data;

/**
 * @Description
 * @Date 2019/9/18 10:11
 * @Author Dong YL
 * @Email silentself@126.com
 */
@Data
public class MailForm {

    //发件人地址
    private String from;
    //邮件主题
    private String subject;
    //邮件内容
    private String content;
    //收件人地址
    private String to;

}
