package com.healspan.claim.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private EncryptionUtil() {
    }

    private static String SECRET_KEY;

    @Value("${healspan.test}")
    public void setKey(String secret) {
        EncryptionUtil.SECRET_KEY = secret;
    }

    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);
    private static SecretKeySpec secretKey;
    private static final String ALGORITHM = "AES";

    private static void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            byte[] key;
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);

        } catch (NoSuchAlgorithmException e) {
            logger.error("EncryptionUtil::prepareSecreteKey::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            prepareSecreteKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.error("EncryptionUtil::encrypt::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            prepareSecreteKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.error("EncryptionUtil::decrypt::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
