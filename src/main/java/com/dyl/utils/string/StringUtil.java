package com.dyl.utils.string;

/**
 * @author Dong YL
 * @version V1.0 2019/9/20 19:02
 */
public class StringUtil {

    /**
     * media+8位序列
     *
     * @param n -
     * @return -
     */
    public static String to6bytes(Integer n) {
        String ns = String.valueOf(n);
        String[] ss = {"000000", "00000", "0000", "000", "00", "0"};
        return ss[ns.length()] + ns;
    }

}
