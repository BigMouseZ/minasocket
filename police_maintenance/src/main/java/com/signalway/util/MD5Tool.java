package com.signalway.util;


import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ZhangGang
 * @createTime 2018/8/20
 **/
public class MD5Tool {

    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_256 = "SHA-256";
    private static MessageDigest messageDigest;

    protected MD5Tool() {
        throw new UnsupportedOperationException(); // 防止子类调用
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    /**
     * @param file 文件
     * @return md5串
     * @throws
     * @Description 功能：对一个文件获取md5值
     **/
    public static String getFileMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * MD5算法加密
     *
     * @param strSrc 字符加密
     * @return strDes
     */
    public static String getStringMd5(String strSrc) {

        return encrypt(strSrc, MD5);

    }

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用MD5
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return strDes
     */
    public static String encrypt(String strSrc, String encName) {
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                messageDigest = MessageDigest.getInstance(MD5);
            }
            messageDigest.update(bt);
            strDes = bytes2Hex(messageDigest.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * @param bts 加密入参
     * @return des
     * @Description 功能：字节数组md5加密
     **/
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = Integer.toHexString(bts[i] & 0xFF);
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
