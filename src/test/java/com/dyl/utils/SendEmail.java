package com.dyl.utils;

import com.dyl.entities.email.MailForm;
import com.dyl.utils.email.EmailUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description
 * @Date 2019/9/18 11:30
 * @Author Dong YL
 * @Email silentself@126.com
 */
public class SendEmail {

    @Before
    public void before(){
        EmailUtil.myEmailAccount = "silentself@126.com";
        EmailUtil.myEmailPassword = "***";
        EmailUtil.myEmailSMTPHost = "smtp.126.com";
    }

    @Test
    public void test() throws Exception {
        MailForm mailForm = new MailForm();
        mailForm.setContent("测试发送邮件能否成功");
        mailForm.setFrom("123");
        mailForm.setTo("456");
        mailForm.setSubject("测试邮件");

        EmailUtil.sendMsg("641211982@qq.com",mailForm);
    }
}
