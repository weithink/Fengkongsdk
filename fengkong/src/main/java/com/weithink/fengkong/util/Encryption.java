package com.weithink.fengkong.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.weithink.fengkong.util.DateUtil.stampToDate;

public class Encryption {
    static String key = "WRZWEZWUIE72$^$*";
    //                  74BC0D0EE24D0FDA
    static String iv = "WRZWEZWUIE72$^$*";

    public static byte[] encryptAES(byte[] encryptString) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("utf8"));
        SecretKeySpec skey = new SecretKeySpec(key.getBytes("utf8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString);
//        String rs = asHex(encryptedData);
//        return rs;
        return encryptedData;
    }

    public static String encryptAESWhitBase64(byte[] encryptString) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("utf8"));
        SecretKeySpec skey = new SecretKeySpec(key.getBytes("utf8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString);
//        String rs = asHex(encryptedData);
//        return rs;
        return Base64.encode(encryptedData);
    }

     private static String asHex(byte[] buf) {
        char[] HEX_CHARS = "0123456789abcdef".toCharArray();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    public static byte[] decryptAES(byte[] decryptString) throws Exception {
        byte[] byteMi = decryptString;
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("utf8"));
        SecretKeySpec skey = new SecretKeySpec(key.getBytes("utf8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skey, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return decryptedData;
    }
    public static void setKeyIv(String key1, String iv1) {
        key = key1;
        iv = iv1;
    }
    public static void setKeyIv(String keyAndIv) {
        key = getKey(keyAndIv);
        iv = getKey(keyAndIv);
    }
    public static String getKey(String tsmp) {
        String dateStr = tsmp;
        long convertDate = stampToDate(dateStr);
        long r = convertDate*10;
        int ns = Integer.parseInt(dateStr.substring(dateStr.indexOf(".")+1));
        r *= ns;
        String md5 = MD5(r+"");
        String sub = md5.substring(0, 16).toUpperCase();
        return sub;
    }
    public static String MD5(final String text) {
//        return hash(text, "MD5");
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] buffer = digest.digest(text.getBytes());
            // byte -128 ---- 127
            StringBuffer sb = new StringBuffer();
            for (byte b : buffer) {
                int a = b & 0xff;
                // Log.d(TAG, "" + a);
                String hex = Integer.toHexString(a);

                if (hex.length() == 1) {
                    hex = 0 + hex;
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
