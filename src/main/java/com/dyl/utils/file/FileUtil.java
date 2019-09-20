package com.dyl.utils.file;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description 文件处理工具类
 * @Date 2019/7/5 18:37
 * @Author Dong YL
 * @Email silentself@126.com
 */
public class FileUtil extends File {

    private static int file_count = 0;

    private static String location;

    private static String img_base_path;

    private static String USER_DIR = "user.dir";

    public static String PATH_SEPARATOR = "/";

    public static String FILE_TYPE_SEPARATOR = ".";


    private static String TMP_FILE_PATH = "data" + PATH_SEPARATOR + "tmp";
    private static String KEYWORD_TMP = "tmp";
    private static String KEYWORD_MD5 = "MD5";


    static {
        location = getProperty();
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
    }

    public FileUtil(String pathname) {
        super(pathname);
    }

    public FileUtil(URI uri) {
        super(uri);
    }

    /**
     * 获取文件存储目录
     *
     * @return
     */
    public static String getBasePath() {
        if (StringUtils.isBlank(location)) {
            location = getProperty();
        }
        return location;
    }

    /**
     * 设置文件存储目录
     *
     * @return
     */
    private static String getProperty() {
        return System.getProperty(USER_DIR) + PATH_SEPARATOR + TMP_FILE_PATH;
    }

    /**
     * 将文件下载到本地
     *
     * @param url
     * @param filename 文件原名称
     * @return 10002-文件网络地址异常,10003-文件创建失败
     */
    public static File loadNetFileToLocal(String url, String filename) throws IOException {
        assert (url.isEmpty() || !verifyUrlNet(url));
        File source = File.createTempFile(getFileSerialNum(), filename, new File(FileUtil.getBasePath()));
        try (InputStream inputStream = new URL(url).openStream();
             OutputStream outputStream = new FileOutputStream(source);) {
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);
            outputStream.write(getData);
            outputStream.flush();
        }
        if (!verifyFileExists(source)) {
            throw new RuntimeException();
        }
        return source;
    }

    public static synchronized String getFileSerialNum() {
        file_count++;
        return KEYWORD_TMP + "(" + file_count + ")";
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 验证文件是否创建成功/存在
     *
     * @param source 文件地址(包括文件名+格式)
     * @return
     */
    public static Boolean verifyFileExists(File source) {
        boolean flag = true;
        if (!source.exists() || source.isDirectory()) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证地址是否正确
     *
     * @param url
     * @return true地址可用, false地址异常
     * @throws IOException
     */
    public static Boolean verifyUrlNet(String url) throws IOException {
        Response execute = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request okRequest = new Request.Builder().url(url).build();
            execute = client.newCall(okRequest).execute();
            return execute.isSuccessful();
        } finally {
            if (execute != null) {
                execute.body().close();
            }
        }
    }

    /**
     * 获取文件md5
     *
     * @param file
     * @return
     */
    public static String getMd5(File file) throws IOException, NoSuchAlgorithmException {
        assert !file.isFile();

        byte buffer[] = new byte[1024];
        int len;
        try (FileInputStream in = new FileInputStream(file);) {
            MessageDigest digest = MessageDigest.getInstance(KEYWORD_MD5);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        }
    }


}