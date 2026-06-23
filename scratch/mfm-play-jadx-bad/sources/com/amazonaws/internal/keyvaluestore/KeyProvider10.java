package com.amazonaws.internal.keyvaluestore;

import android.content.SharedPreferences;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.Base64;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
class KeyProvider10 implements KeyProvider {
    private static final String AES_KEY_ALGORITHM = "AES";
    private static final int CIPHER_AES_GCM_NOPADDING_KEY_LENGTH_IN_BITS = 256;
    static final String KEY_ALIAS = "AesGcmNoPaddingEncryption10-encryption-key";
    private static final Log logger = LogFactory.getLog("KeyProvider10");
    private SharedPreferences sharedPreferences;

    KeyProvider10(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.amazonaws.internal.keyvaluestore.KeyProvider
    public synchronized Key generateKey(String str) throws KeyNotGeneratedException {
        SecretKey secretKeyGenerateKey;
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
            keyGenerator.init(256, secureRandom);
            secretKeyGenerateKey = keyGenerator.generateKey();
            SecretKey secretKeyGenerateKey2 = keyGenerator.generateKey();
            if (secretKeyGenerateKey2 == null) {
                throw new KeyNotGeneratedException("Error in generating the AES encryption key identified by the aesEncryptionKeyAlias: " + str);
            }
            byte[] encoded = secretKeyGenerateKey2.getEncoded();
            if (encoded == null || encoded.length == 0) {
                throw new KeyNotGeneratedException("Error in getting the encoded bytes for the AES encryption key identified by the aesEncryptionKeyAlias: " + str);
            }
            String strEncodeAsString = Base64.encodeAsString(encoded);
            if (strEncodeAsString == null) {
                throw new KeyNotGeneratedException("Error in Base64 encoding of the AES encryption key for the aesEncryptionKeyAlias: " + str);
            }
            this.sharedPreferences.edit().putString(str, strEncodeAsString).apply();
            logger.info("Generated and saved the AES encryption key identified by the aesEncryptionKeyAlias: " + str + " to SharedPreferences.");
        } catch (Exception e) {
            throw new KeyNotGeneratedException("Error in generating the AES Encryption key for the aesEncryptionKeyAlias", e);
        }
        return secretKeyGenerateKey;
    }

    @Override // com.amazonaws.internal.keyvaluestore.KeyProvider
    public synchronized Key retrieveKey(String str) throws KeyNotFoundException {
        byte[] bArrDecode;
        try {
            if (this.sharedPreferences.contains(str)) {
                logger.debug("Loading the encryption key from SharedPreferences");
                String string = this.sharedPreferences.getString(str, null);
                if (string == null) {
                    throw new KeyNotFoundException("SharedPreferences does not have the key for keyAlias: " + str);
                }
                bArrDecode = Base64.decode(string);
                if (bArrDecode == null || bArrDecode.length == 0) {
                    throw new KeyNotFoundException("Error in Base64 decoding the AES encryption key identified by the keyAlias: " + str);
                }
            } else {
                throw new KeyNotFoundException("SharedPreferences does not have the key for keyAlias: " + str);
            }
        } catch (Exception e) {
            throw new KeyNotFoundException("Error occurred while retrieving key for keyAlias: " + str, e);
        }
        return new SecretKeySpec(bArrDecode, AES_KEY_ALGORITHM);
    }

    @Override // com.amazonaws.internal.keyvaluestore.KeyProvider
    public synchronized void deleteKey(String str) {
        try {
            this.sharedPreferences.edit().remove(str).apply();
        } catch (Exception e) {
            logger.error("Error in deleting the AES key identified by " + str + " from SharedPreferences.", e);
        }
    }
}
