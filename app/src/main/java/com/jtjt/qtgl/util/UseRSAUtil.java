package com.jtjt.qtgl.util;



import android.util.Log;

import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * RSA加解密
 * @author jiafuwei
 * created at 2017/6/8
 */
public class UseRSAUtil {

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static final String KEY_ALGORITHM = "RSA";
    private static KeyFactory keyFactory = null;
    public static String PrivateKey = "MIICXAIBAAKBgQCcqnM1l9E8NdGdnGWk7Crc+9ocX3san/cnn3y4LTrVRqBcEuEw\n" +
            "qJ3wnvJkVk47cZyjc2yljr6pOG0ik8iB7ypKI4qG9AnNsnFdQynr6stK1tVhQ1aD\n" +
            "BPANkGE18J+DwM1Vq9Fb7945IA/AM4vPs1Oo3rhDvc6bmK3KTW8LdJ8spwIDAQAB\n" +
            "AoGATvD4cpXYQa2gvPl0okC6bHWfdtKJ1mWqhVWw46Eu1w7qeNqui/U6+GcvHm36\n" +
            "p6tc0WO06EtgOSaMn3K2Avr/ZGhig7QL9H4k2TBdzSirQTUSOwjaYqMssHaTA2cq\n" +
            "/vnSCgAfHHCSuPmrveHgzfR8utAJyQqE8/+PgJwo7TMSL9kCQQDK+81Q87gvgEsW\n" +
            "RbamLhzRePmQ9CYh7/+j/4kZfHFeGYGFybNoz4n00O7xn1rj5LPR66SObakzMDsz\n" +
            "cXhMyxcbAkEAxZWtm+0INzs3y4Q3c1NLdg8DqYE2cnWad9zZf85BEhBx/z6zv1Y9\n" +
            "hN7VXn4q3Cw1bQO/NPnO1U/J8XFH7bMdZQJAae9OMRBxMME47s/FSidIvP3w6Xiy\n" +
            "KgYSGen0CEwM8npHQaaJroqZyh38hjePfN5QBp9/iyGqK1/hlbCDgzh+vwJABMBc\n" +
            "tGs2z101MkezrapLHkOuKt1YmdgKJufAszoahnyuwRyY5fkE8bUKHIOcND876as1\n" +
            "Zei7+JU6nfvDZwvvoQJBAK7Z6C28BEaN7k1aUGVOnUcSFiBiDE5GMCDVg4cqWNYK\n" +
            "8yWl/p3Y3kIthCANS8uuZafnPBk1dnBL9uc3Il7tKD4=";
    public static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcqnM1l9E8NdGdnGWk7Crc+9oc\n" +
            "X3san/cnn3y4LTrVRqBcEuEwqJ3wnvJkVk47cZyjc2yljr6pOG0ik8iB7ypKI4qG\n" +
            "9AnNsnFdQynr6stK1tVhQ1aDBPANkGE18J+DwM1Vq9Fb7945IA/AM4vPs1Oo3rhD\n" +
            "vc6bmK3KTW8LdJ8spwIDAQAB";

    static {
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     * @param dataStr 要解密的数据
     * @return 解密后的原数据
     * @throws Exception
     */
    public static String decrypt(String dataStr) throws Exception{
        //要加密的数据
        System.out.println("要解密的数据:"+dataStr);
        //对私钥解密
        Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(PrivateKey);
        //Log.i("机密",""+decodePrivateKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
        byte[] encodedData = Base64.decode(dataStr);
        byte[] decodedData = cipher.doFinal(encodedData);
        String decodedDataStr = new String(decodedData,"utf-8");
        System.out.println("私钥解密后的数据:"+decodedDataStr);

        Log.e("私钥解密后的数据","私钥解密后的数据"+decodedDataStr);
        return decodedDataStr;
    }

    public  static Key getPrivateKeyFromBase64KeyEncodeStr(String keyStr) {
        byte[] keyBytes = Base64.decode(keyStr);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        Key privateKey=null;
        try {
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 获取base64加密后的字符串的原始公钥
     * @param keyStr
     * @return
     */
    public static Key getPublicKeyFromBase64KeyEncodeStr(String keyStr) {
        byte[] keyBytes = Base64.decode(keyStr);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        Key publicKey = null;
        try {
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 公钥加密方法
     * @param dataStr 要加密的数据
     * @param dataStr 公钥base64字符串
     * @return 加密后的base64字符串
     * @throws Exception
     */
    public static String encryptPublicKey(String dataStr) throws Exception{
        //要加密的数据
        System.out.println("要加密的数据:"+dataStr);
        byte[] data = dataStr.getBytes();
        // 对公钥解密
        Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
        byte[] encodedData = cipher.doFinal(data);
        String encodedDataStr = new String(Base64.encode(encodedData));
        System.out.println("公钥加密后的数据:"+encodedDataStr);

        Log.e("加密后数据","加密后数据"+encodedDataStr);
        return encodedDataStr;
    }

    /**
     * 使用公钥进行分段加密
     * @param dataStr 要加密的数据
     * @return 公钥base64字符串
     * @throws Exception
     */
//    public static String encryptByPublicKey(String dataStr)
//            throws Exception {
//        //要加密的数据
//        System.out.println("要加密的数据:"+dataStr);
//        byte[] data = dataStr.getBytes();
//        // 对公钥解密
//        Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        // 对数据加密
//
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//
//        Log.e("获取数据解析","获取数据解析-----"+keyFactory.getAlgorithm());
//        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
//        int inputLen = data.length;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        // 对数据分段加密
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);
//            }
////            Base64.encode(cache);
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_ENCRYPT_BLOCK;
//        }
//        byte[] encryptedData = out.toByteArray();
//        out.close();
//
//
//        String encodedDataStr = new String(Base64.encode(encryptedData));
//        System.out.println("公钥加密后的数据:"+encodedDataStr);
//        return encodedDataStr;
//    }
    public static String encryptByPublicKey(String dataStr)
            throws Exception {
        //要加密的数据
        System.out.println("要加密的数据:"+dataStr);
        byte[] data = dataStr.getBytes();
        // 对公钥解密
        Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 对数据加密

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        Log.e("获取数据解析","获取数据解析-----"+keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
//            Base64.encode(cache);
            out.write(   Base64.encode(cache), 0,    Base64.encode(cache).length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        String    encodedDataStr =new String(encryptedData);
//        String encodedDataStr = new String(Base64.encode(encryptedData));
        System.out.println("公钥加密后的数据:"+encodedDataStr);
        return encodedDataStr;
    }

    /**
     * 使用私钥进行分段解密
     * @param dataStr 使用base64处理过的密文
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decryptByPrivateKey(String dataStr)
            throws Exception {

        byte[] encryptedData = dataStr.getBytes();

//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(PrivateKey);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
        int inputLen = dataStr.length();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        Log.e("打印分段解密","打印分段解密"+inputLen);
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 172) {
                cache = cipher.doFinal(encryptedData, offSet, 172);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            };
            Log.e("打印分段解密","打印分段解密"+cache.toString());

            out.write(  Base64.decode(cache), 0,   Base64.decode(cache).length);
            i++;
            offSet = i * 172;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String decodedDataStr = new String(decryptedData,"utf-8");
        System.out.println("私钥解密后的数据:"+decodedDataStr);
        return decodedDataStr;
    }
//    public static String decryptByPrivateKey(String dataStr)
//            throws Exception {
//
//        byte[] encryptedData = Base64.decode(dataStr);
//
////        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(PrivateKey);
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
//        int inputLen = encryptedData.length;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        // 对数据分段解密
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
//            }
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_DECRYPT_BLOCK;
//        }
//        byte[] decryptedData = out.toByteArray();
//        out.close();
//        String decodedDataStr = new String(decryptedData,"utf-8");
//        System.out.println("私钥解密后的数据:"+decodedDataStr);
//        return decodedDataStr;
//    }


    public static void main(String[] args) throws Exception {

    }
}