package com.dyl.entities.email;

import lombok.Data;

/**
 * @author Dong YL
 * silentself@126.com
 * 2019/9/18 10:11
 */
@Data
public class MailForm {

    //发件人名称
    private String from;
    //邮件主题
    private String subject;
    //邮件内容
    private String content;
    //收件人名称
    private String to;

}
