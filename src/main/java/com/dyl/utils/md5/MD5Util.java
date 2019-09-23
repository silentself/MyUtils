package com.dyl.utils.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串md5加密工具类
 *
 * @author Dong YL
 * @version V1.0 2019/9/23 16:18
 */
public class MD5Util {

    /**
     * 升级版的不需要手写循环
     *
     * @param str -
     * @return -
     * @throws NoSuchAlgorithmException -
     */
    public static String getStrMd5UpgradedVersion(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 调用update方法计算MD5函数(参数：将密码串转换为操作系统的字节编码)
        md.update(str.getBytes());
        // digest()最后返回md5的hash值，返回值为8位的字符串，但此方法要先调用update
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值,数值从1开始
        // BigInteger会把0省略掉，需补全至32位，重写一个方法将16位数转换为32位数
        String md5 = new BigInteger(1, md.digest()).toString(16);
        return fillMD5(md5);
    }

    // 将16位数转为32位
    private static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }

    /**
     * 手写循环的md5加密方法
     *
     * @param str -
     * @return -
     * @throws NoSuchAlgorithmException -
     */
    @Deprecated
    public static String getStrMd5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());
        byte[] hash = md5.digest();
        StringBuilder secpwd = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            if (v < 16) secpwd.append(0);
            secpwd.append(Integer.toString(v, 16));
        }
        return secpwd.toString();
    }

    /**
     * 获取文件md5
     *
     * @param file -
     * @return -
     */
    public static String getFileMd5(File file) throws IOException, NoSuchAlgorithmException {
        assert !file.isFile();
        byte[] buffer = new byte[1024];
        int len;
        try (FileInputStream in = new FileInputStream(file);) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            String str = new BigInteger(1, digest.digest()).toString();
            return fillMD5(str);
        }
    }


}
