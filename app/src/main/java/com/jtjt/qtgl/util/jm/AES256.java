package com.jtjt.qtgl.util.jm;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {



    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";



    /**
     * 加密
     * @param srcData 原数据
     * @param key 密钥
     * @param iv 密钥向量
     * @return
     * @throws Exception
     */
    public static byte[] AES_cbc_encrypt(byte[] srcData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] encData = cipher.doFinal(srcData);
        return encData;
    }
    /**
     * 解密
     * @param encData 需解密数据
     * @param key 密钥
     * @param iv 密钥向量
     * @return
     * @throws Exception
     */
    public static String AES_cbc_decrypt(byte[] encData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return new String(cipher.doFinal(encData), Charset.forName("UTF8"));
    }


    private static IvParameterSpec createIV(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(password);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }


        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    /**
     * 说明: AES256加密
     *
     * @param stringToEncode
     *            明文
     * @param keyString
     *            密钥
     * @return Bses64格式密文
     */
    public static String AES256Encode(String stringToEncode, String keyString,String iv)
            throws NullPointerException {
        if (keyString.length() == 0 || keyString == null) {
            return null;
        }
        if (stringToEncode.length() == 0 || stringToEncode == null) {
            return null;
        }
        try {
            SecretKeySpec skeySpec = getKey(keyString);
            byte[] data = stringToEncode.getBytes("UTF8");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, createIV(iv));
            String encrypedValue = Base64.encodeToString(cipher.doFinal(data),
                    Base64.DEFAULT);
            return encrypedValue;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 说明 :AES256解密
     *
     * @param text
     *            Base64格式密文
     * @param keyString
     *            密钥
     * @return String格式明文
     */
    public static String AES256Decrypt(String text, String keyString,String iv) {
        // byte[] rawKey = getRawKey(key);
        if (keyString.length() == 0 || keyString == null) {
            return null;
        }
        if (text.length() == 0 || text == null) {
            return null;
        }
        try {
            SecretKey key = getKey(keyString);
            byte[] data = Base64.decode(text, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv));
            byte[] decrypedValueBytes = (cipher.doFinal(data));
            String decrypedValue = new String(decrypedValueBytes, "UTF-8");
            return decrypedValue;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 说明 :将密钥转行成SecretKeySpec格式
     *
     * @param password
     *            16进制密钥
     * @return SecretKeySpec格式密钥
     */
    private static SecretKeySpec getKey(String password)
            throws UnsupportedEncodingException {
        // 如果为128将长度改为128即可
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        // explicitly fill with zeros
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = toByte(password);
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length
                : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    /**
     *
     * 说明 :随机生成一组AES密钥
     *
     * @return 16进制AES密钥
     */
    public static String getRawKey() {
        KeyGenerator kgen = null;
        SecureRandom sr = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            } else {
                sr = SecureRandom.getInstance("SHA1PRNG");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        kgen.init(256, sr); // 256 bits or 128 bits,192bits
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        String result = bytes2Hex(raw);
        return result;
    }







    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 将16进制转换为byte数组
     *
     * @param hexString
     *            16进制字符串
     * @return byte数组
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

}
