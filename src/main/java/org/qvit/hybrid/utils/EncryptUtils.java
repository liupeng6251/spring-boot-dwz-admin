package org.qvit.hybrid.utils;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 
 * @ClassName 类名：EncryptUtils
 * @Description 功能说明：
 *              <p>
 *              加密解密工具类
 *              </p>
 ************************************************************************ 
 * @date 创建日期：2014-10-20
 * @author 创建人： liupeng
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2014-10-20 liupeng 创建该类功能。 修订记录*************************************
 * 
 *          </p>
 */

public class EncryptUtils {

    private static final String SYMMETRY_ENCRYPT_ALGORITHM = "AES";

    private static final int SYMMETRY_ENCRYPT_KEY_SIZE = 128;

    private static final Charset ENCODING = Charset.forName("UTF-8");

    private static final String SIGN_MD5_ALGORITHM = "SHA-512";

    private static final String SYMMETRY_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private static final String DISSYMMETRY_SIGNATURE_ALGORITHM = "SHA512withRSA";

    private static final String DISSYMMETRY_ENCRYPT_ALGORITHM = "RSA";

    private static final String IV_PARAMETER_STR = "v=3^%5$9+_O{P<:K";

    private static final int SIGNATURE_KEY_LENGTH = 1024;

    private static final String HMAC_ALGORITHM = "HmacMD5";

    /**
     * 
     * <p>
     * 函数名称： md5
     * </p>
     * <p>
     * 功能说明：对明文进行md5
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param plaintext
     *            明文
     * @param salt
     *            盐
     * @return [Base64]摘要结果
     * @throws RuntimeException
     */
    public static String md5(String plaintext, String salt) {
        assert plaintext != null : "plaintext may arg not be null";
        assert salt != null : "salt may arg not be null";
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(SIGN_MD5_ALGORITHM);
            digest.reset();
            digest.update(salt.getBytes(ENCODING));
            digest.update(plaintext.getBytes(ENCODING));
            bytes = digest.digest();
            String encoded = Base64.encodeBase64String(bytes);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String md5(String plaintext) {
        assert plaintext != null : "plaintext may arg not be null";
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(SIGN_MD5_ALGORITHM);
            digest.reset();
            digest.update(plaintext.getBytes(ENCODING));
            bytes = digest.digest();
            String encoded = Base64.encodeBase64String(bytes);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * <p>
     * 函数名称： hmac
     * </p>
     * <p>
     * 功能说明：对明文进行hmac
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param hmacKey
     *            key[Base64 key String]
     * @param data
     *            明文
     * @return [Base64]hmac结果
     * @throws RuntimeException
     */
    public static String hmac(String hmacKey, String data) {
        assert !StringUtils.isEmpty(data) : "hmacKey may arg not be null";
        assert data != null : "data may arg not be null";
        Mac mac;
        try {
            SecretKey key = new SecretKeySpec(Base64.decodeBase64(hmacKey), HMAC_ALGORITHM);
            mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(key);
            byte[] doFinal = mac.doFinal(data.getBytes(ENCODING));
            return Base64.encodeBase64String(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * <p>
     * 函数名称： hmac
     * </p>
     * <p>
     * 功能说明：对明文进行hmac
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param hmacKey[Key]
     * @param data
     *            明文
     * @return [Base64 hamc 结果
     */
    public static String hmac(Key hmacKey, String data) {
        assert hmacKey != null : "hmacKey may arg not be null";
        assert data != null : "data may arg not be null";
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(hmacKey);
            byte[] doFinal = mac.doFinal(data.getBytes(ENCODING));
            return Base64.encodeBase64String(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 
     * <p>
     * 函数名称： createMacKey
     * </p>
     * <p>
     * 功能说明：创建 mac key
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @return Base64 mac key String
     * @throws RuntimeException
     */
    public static String createMacKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(HMAC_ALGORITHM);
            SecretKey generateKey = keyGenerator.generateKey();
            return Base64.encodeBase64String(generateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 
     * <p>
     * 函数名称： encrypt
     * </p>
     * <p>
     * 功能说明：对明文加密
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param keyStr
     *            加密key [Base64]
     * @param plainText
     *            需要加密的明文
     * @param ivParamStr
     *            IV 16字节的字符向量，加解密需要用同样的字符
     * @return 加密后的内容[base64]
     * @throws RuntimeException
     */
    public static String encrypt(String keyStr, String plainText, String ivParamStr) {
        Key key = initKey(keyStr);
        try {
            return encrypt(key, plainText, ivParamStr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * <p>
     * 函数名称： encrypt
     * </p>
     * <p>
     * 功能说明： 对明文加密
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param keyStr
     *            加密key [Base64]
     * @param plainText
     *            需要加密的明文
     * @return 加密后的内容[base64]
     * @throws RuntimeException
     */
    public static String encrypt(String keyStr, String plainText) {
        return encrypt(keyStr, plainText, IV_PARAMETER_STR);
    }

    /**
     * 
     * <p>
     * 函数名称： encrypt
     * </p>
     * <p>
     * 功能说明：对明文加密
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param key
     *            加密key [Base64]
     * @param plainText
     *            需要加密的明文
     * @param ivParamStr
     *            IV 16字节的字符向量，加解密需要用同样的字符
     * @return 加密后的内容[base64]
     * @throws Exception
     */
    public static String encrypt(Key key, String plainText, String ivParamStr) throws Exception {
        assert plainText != null : "plainText may arg not be null";
        assert key != null : "key may arg not be null";
        assert ivParamStr != null : "ivParamStr may arg not be null";

        byte[] plainByte = plainText.getBytes(ENCODING);
        Cipher cipher = Cipher.getInstance(SYMMETRY_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, getIvParameter(ivParamStr));
        byte[] doFinal = cipher.doFinal(plainByte);
        return Base64.encodeBase64String(doFinal);
    }

    /**
     * 
     * <p>
     * 函数名称： decrypt
     * </p>
     * <p>
     * 功能说明：对密文解密
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param keyStr
     *            加密key
     * @param cipherText
     *            密文
     * @return
     */
    public static String decrypt(String keyStr, String cipherText) {
        return decrypt(keyStr, cipherText, IV_PARAMETER_STR);
    }

    /**
     * 
     * <p>
     * 函数名称： decrypt
     * </p>
     * <p>
     * 功能说明：解密数据
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param keyStr
     *            [Base64 key 字符串]
     * @param cipherText
     *            加密的字符串
     * @param ivParamStr
     *            初始向量字符串
     * @return 解密字符串，Base64编码
     * @throws RuntimeException
     */
    public static String decrypt(String keyStr, String cipherText, String ivParamStr) {
        Key key = initKey(keyStr);
        try {
            return decrypt(key, cipherText, ivParamStr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * <p>
     * 函数名称： decrypt
     * </p>
     * <p>
     * 功能说明：解密数据
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param key
     *            key
     * @param cipherText
     *            需要解密的字符串[Base64]
     * @param ivParamStr
     *            初始向量字符串
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decrypt(Key key, String cipherText, String ivParamStr) throws Exception {
        assert cipherText != null : "cipherText may arg not be null";
        assert key != null : "key may arg not be null";
        assert ivParamStr != null : "ivParamStr may arg not be null";

        Cipher cipher = Cipher.getInstance(SYMMETRY_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, getIvParameter(ivParamStr));
        byte[] doFinal = cipher.doFinal(Base64.decodeBase64(cipherText));
        return new String(doFinal, ENCODING);

    }

    /**
     * 
     * <p>
     * 函数名称： sign
     * </p>
     * <p>
     * 功能说明：用私钥对数据签名
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：liupeng songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param key
     *            私钥 PrivateKey
     * @param data
     * @return 签名后的数据 Base64
     * @throws RuntimeException
     */
    public static String sign(PrivateKey key, String data) {
        assert data != null : "data may arg not be null";
        assert key != null : "key may arg not be null";
        try {
            Signature signer = Signature.getInstance(DISSYMMETRY_SIGNATURE_ALGORITHM);
            signer.initSign(key);
            signer.update(data.getBytes(ENCODING));
            byte[] bytes = signer.sign();
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： sign
     * </p>
     * <p>
     * 功能说明：用私钥对数据签名
     * 
     * </p>
     * <p>
     * 参数说明：
     * </p>
     * 
     * @param privateKeyStr
     * @param data
     * 
     * 
     * @date 创建时间：2014-10-20 @author 作者：liupeng @return String @throws
     */
    public static String sign(String privateKeyStr, String data) {
        PrivateKey key;
        try {
            key = initPrivateKey(privateKeyStr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return sign(key, data);
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： initPrivateKey
     * </p>
     * <p>
     * 功能说明：将私钥字符串转换成私钥
     * 
     * </p>
     * <p>
     * 参数说明：
     * </p>
     * 
     * @param privateKeyStr
     *            私钥串 Base64
     * @return 对应的私钥
     * 
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey initPrivateKey(String privateKeyStr) {
        assert !StringUtils.isEmpty(privateKeyStr) : "privateKeyStr may arg not be empty";
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKeyStr);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            return (PrivateKey) priKey;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： initPublicKey
     * </p>
     * <p>
     * 功能说明：初始化公钥，讲字符串转换成公钥
     * 
     * </p>
     * <p>
     * 参数说明：
     * </p>
     * 
     * @param publicKeyStr
     *            公钥对应的字符串base64 @return 对应的公钥
     * 
     * 
     * @date 创建时间：2014-10-20 @author 作者：liupeng @return PublicKey @throws
     */
    public static PublicKey initPublicKey(String publicKeyStr) {
        assert !StringUtils.isEmpty(publicKeyStr) : "publicKeyStr may arg not be empty";
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKeyStr);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            Key pubKey = keyFactory.generatePublic(x509KeySpec);
            return (PublicKey) pubKey;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： verifySignature
     * </p>
     * <p>
     * 功能说明：验证签名
     * 
     * </p>
     * <p>
     * 参数说明：
     * </p>
     * 
     * @param signature
     *            签名 @param data 数据 @param key 公钥 @return Boolean 验证结果
     * 
     * 
     * @date 创建时间：2014-10-20 @author 作者：liupeng @return boolean @throws
     */
    public static boolean verifySignature(String signature, String data, PublicKey key) {
        assert !StringUtils.isEmpty(signature) : "signature may arg not be empty";
        assert data != null : "data may arg not be empty";
        assert key != null : "key may arg not be empty";

        try {
            byte[] bytes = Base64.decodeBase64(signature);
            Signature signer = Signature.getInstance(DISSYMMETRY_SIGNATURE_ALGORITHM);
            signer.initVerify(key);
            signer.update(data.getBytes(ENCODING));
            return signer.verify(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Invalid signature: Problem verifying signature: " + e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： verifySignature
     * </p>
     * <p>
     * 功能说明：用公钥进行签名验证
     * 
     * </p>
     * <p>
     * 参数说明：
     * </p>
     * 
     * @param signature
     * @param data
     *            @param publicKeyStr
     * 
     * 
     * @date 创建时间：2014-10-20 @author 作者：liupeng @return boolean @throws
     */
    public static boolean verifySignature(String signature, String data, String publicKeyStr) {
        PublicKey key = initPublicKey(publicKeyStr);
        return verifySignature(signature, data, key);
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： decryptByPrivateKey
     * </p>
     * <p>
     * 功能说明：用私钥对数据进行解密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param key
     * @return string
     */
    public static String decryptByPrivateKey(String data, PrivateKey key) {
        assert data != null : "data may arg not be empty";
        assert key != null : "key may arg not be empty";
        try {
            byte[] content = Base64.decodeBase64(data);
            Cipher cipher = Cipher.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(content);
            return new String(doFinal, ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 
     * 
     * <p>
     * 函数名称： decryptByPrivateKey
     * </p>
     * <p>
     * 功能说明：用私钥对数据进行解密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param privateKeyStr
     *            私钥字符串
     * @return string
     */
    public static String decryptByPrivateKey(String data, String privateKeyStr) {
        PrivateKey key = initPrivateKey(privateKeyStr);
        return decryptByPrivateKey(data, key);

    }

    /**
     * 
     * 
     * <p>
     * 函数名称： decryptByPublicKey
     * </p>
     * <p>
     * 功能说明：用公钥进行解密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param key
     *            公钥
     * @return String
     */
    public static String decryptByPublicKey(String data, PublicKey key) {
        assert data != null : "data may arg not be empty";
        assert key != null : "key may arg not be empty";
        try {
            byte[] centext = Base64.decodeBase64(data);
            Cipher cipher = Cipher.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(centext);
            return new String(doFinal, ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： decryptByPublicKey
     * </p>
     * <p>
     * 功能说明：用公钥进行解密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param publicKeyStr
     *            公钥字符串
     * @return String
     */
    public static String decryptByPublicKey(String data, String publicKeyStr) {
        PublicKey key = initPublicKey(publicKeyStr);
        return decryptByPublicKey(data, key);
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： encryptByPublicKey
     * </p>
     * <p>
     * 功能说明：公钥进行加密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param publicKeyStr
     * @return String
     */
    public static String encryptByPublicKey(String data, String publicKeyStr) {
        PublicKey key = initPublicKey(publicKeyStr);
        return encryptByPublicKey(data, key);
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： encryptByPublicKey
     * </p>
     * <p>
     * 功能说明：公钥进行加密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param key
     * @return String
     */
    public static String encryptByPublicKey(String data, PublicKey key) {
        assert data != null : "data may arg not be empty";
        assert key != null : "key may arg not be empty";
        try {
            // 对数据加密
            Cipher cipher = Cipher.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(data.getBytes(ENCODING));
            return Base64.encodeBase64String(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * <p>
     * 函数名称： encryptByPrivateKey
     * </p>
     * <p>
     * 功能说明：私钥对数据进行加密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param key
     * @return String
     */
    public static String encryptByPrivateKey(String data, PrivateKey key) {
        assert data != null : "data may arg not be empty";
        assert key != null : "key may arg not be empty";
        // 对数据加密
        try {
            Cipher cipher = Cipher.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(data.getBytes(ENCODING));
            return Base64.encodeBase64String(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 
     * 
     * <p>
     * 函数名称： encryptByPrivateKey
     * </p>
     * <p>
     * 功能说明：私钥对数据进行加密
     * </p>
     * 
     * @date 创建时间：2014-10-20
     * @author 作者：liupeng E-mail:liupeng6251@163.com
     *         <p>
     *         参数说明：
     *         </p>
     * @param data
     * @param key
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKeyStr) {
        PrivateKey key = initPrivateKey(privateKeyStr);
        return encryptByPrivateKey(data, key);
    }

    /**
     * 
     * <p>
     * 函数名称： initKey
     * </p>
     * <p>
     * 功能说明：初始化key，将一个字符串转换成key
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param keyStr
     *            base64 字符串
     * @return Key
     */
    public static Key initKey(String keyStr) {
        assert !StringUtils.isEmpty(keyStr) : "keyStr may arg not be empty";
        SecretKeySpec key = new SecretKeySpec(Base64.decodeBase64(keyStr), SYMMETRY_ENCRYPT_ALGORITHM);
        return key;
    }

    /**
     * 
     * <p>
     * 函数名称： createKeyPair
     * </p>
     * <p>
     * 功能说明：生成keyPair 使用 RSA
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @return KeyPair
     *         {"privateKey":Base64(KeyPair.Private),"publicKey":Base64(KeyPair.
     *         Public)}
     * @throws RuntimeException
     */
    public static Map<String, String> createKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(DISSYMMETRY_ENCRYPT_ALGORITHM);
            keyGen.initialize(SIGNATURE_KEY_LENGTH);
            KeyPair pair = keyGen.generateKeyPair();
            Map<String, String> map = new HashMap<String, String>(2);
            map.put("privateKey", Base64.encodeBase64String(pair.getPrivate().getEncoded()));
            map.put("publicKey", Base64.encodeBase64String(pair.getPublic().getEncoded()));
            return map;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 
     * <p>
     * 函数名称： createKey
     * </p>
     * <p>
     * 功能说明：创建Key，使用 AES 算法
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @return Base64（SecretKey）
     * @throws RuntimeException
     */
    public static String createKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(SYMMETRY_ENCRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        keyGenerator.init(SYMMETRY_ENCRYPT_KEY_SIZE);
        SecretKey key = keyGenerator.generateKey();
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 
     * <p>
     * 函数名称： getIvParameter
     * </p>
     * <p>
     * 功能说明：用IV字符串创建IvParameterSpec
     * </p>
     * 
     * @date 创建时间：2014-11-6
     * @author 作者：songdl
     *         <p>
     *         参数说明：
     *         </p>
     * @param ivParamStr
     *            初始向量字符串
     * @return IvParameterSpec
     * @throws RuntimeException
     */
    private static IvParameterSpec getIvParameter(String ivParamStr) {
        try {
            IvParameterSpec iv = new IvParameterSpec(ivParamStr.getBytes(ENCODING));
            return iv;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
