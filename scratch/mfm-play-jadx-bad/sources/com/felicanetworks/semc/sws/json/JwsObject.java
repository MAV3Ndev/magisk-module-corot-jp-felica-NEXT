package com.felicanetworks.semc.sws.json;

import android.util.Base64;
import com.felicanetworks.semc.util.Base64Util;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class JwsObject {
    private static final String ALGORITHM_RS256 = "RS256";
    private static final int INDEX_HEADER = 0;
    private static final int INDEX_PAYLOAD = 1;
    private static final int INDEX_SIGNATURE = 2;
    private static final String KEY_ALGORITHM = "RSA";
    private static final String NAME_ALGORITHM = "alg";
    private static final String NAME_EXPIRATION_TIME = "exp";
    private static final String NAME_KEY_ID = "kid";
    private static final String NAME_TYPE = "typ";
    private static final int PARTS_SIZE = 3;
    private static final String SEPARATE_CHAR = "\\.";
    private static final HashMap<String, String> SUPPORTED_ALGORITHMS;
    private static final String TYPE_JOSE = "JOSE";
    private final JSONObject mHeader;
    private final String mJwsStr;
    private final String mPayload;
    private final byte[] mSignature;

    static {
        HashMap<String, String> map = new HashMap<>();
        SUPPORTED_ALGORITHMS = map;
        map.put(ALGORITHM_RS256, "SHA256withRSA");
    }

    public JwsObject(String str) throws JwsException {
        LogMgr.log(6, "000 jwsStr[" + str + "]");
        if (str == null) {
            LogMgr.log(1, "800 errMsg[jwsStr is null.]");
            throw new JwsException(0, ObfuscatedMsgUtil.executionPoint());
        }
        this.mJwsStr = str;
        try {
            String[] strArrSplit = split();
            if (strArrSplit.length != 3) {
                LogMgr.log(1, "801 errMsg[Some of jws object is lacking. jwsDataArray length is less than 3.]");
                throw new JwsException(2, ObfuscatedMsgUtil.executionPoint());
            }
            String strDecodeToUTF8String = Base64Util.decodeToUTF8String(strArrSplit[0]);
            if (strDecodeToUTF8String == null) {
                LogMgr.log(1, "802 errMsg[JWSObject headerStr is null.]");
                throw new JwsException(2, ObfuscatedMsgUtil.executionPoint());
            }
            this.mHeader = new JSONObject(strDecodeToUTF8String);
            this.mPayload = Base64Util.decodeToUTF8String(strArrSplit[1]);
            this.mSignature = Base64Util.decode(strArrSplit[2]);
            LogMgr.log(6, "999");
        } catch (Exception e) {
            LogMgr.log(1, "803 errMsg[" + (e.getClass().getSimpleName() + ":" + e.getMessage()) + "]");
            LogMgr.printStackTrace(9, e);
            throw new JwsException(0, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public String getPayload() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mPayload;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public void verify(Map<String, String> map) throws JwsException {
        LogMgr.log(6, "000");
        if (map == null) {
            LogMgr.log(1, "800 errMsg[KeyMap is null.]");
            throw new JwsException(1, ObfuscatedMsgUtil.executionPoint());
        }
        try {
            try {
                String string = this.mHeader.getString("typ");
                String string2 = this.mHeader.getString(NAME_ALGORITHM);
                String string3 = this.mHeader.getString(NAME_KEY_ID);
                if (!TYPE_JOSE.equals(string)) {
                    LogMgr.log(1, "801 errMsg[" + ("Not supported type. :" + string) + "]");
                    throw new JwsException(3, ObfuscatedMsgUtil.executionPoint());
                }
                HashMap<String, String> map2 = SUPPORTED_ALGORITHMS;
                if (!map2.containsKey(string2)) {
                    LogMgr.log(1, "802 errMsg[" + ("Not supported algorithm. :" + string2) + "]");
                    throw new JwsException(5, ObfuscatedMsgUtil.executionPoint());
                }
                if (!map.containsKey(string3)) {
                    LogMgr.log(1, "803 errMsg[" + ("Not supported kid. :" + string3) + "]");
                    throw new JwsException(4, ObfuscatedMsgUtil.executionPoint(string3));
                }
                String str = map.get(string3);
                if (StringUtil.isEmpty(str)) {
                    LogMgr.log(1, "804 errMsg[Public key is empty,]");
                    throw new JwsException(1, ObfuscatedMsgUtil.executionPoint());
                }
                try {
                    PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 2)));
                    String str2 = map2.get(string2);
                    String str3 = this.mJwsStr;
                    String strSubstring = str3.substring(0, str3.lastIndexOf(46));
                    Signature signature = Signature.getInstance(str2);
                    signature.initVerify(publicKeyGeneratePublic);
                    signature.update(strSubstring.getBytes(Charset.defaultCharset()));
                    if (signature.verify(this.mSignature)) {
                        LogMgr.log(6, "999");
                    } else {
                        LogMgr.log(1, "806 errMsg[JWS is not verify.]");
                        throw new JwsException(6, ObfuscatedMsgUtil.executionPoint());
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "805 errMsg[" + (e.getClass().getSimpleName() + ":" + e.getMessage()) + "]");
                    throw new JwsException(1, ObfuscatedMsgUtil.executionPoint(e));
                }
            } catch (JwsException e2) {
                throw e2;
            }
        } catch (Exception e3) {
            LogMgr.log(1, "807 errMsg[" + (e3.getClass().getSimpleName() + ":" + e3.getMessage()) + "]");
            throw new JwsException(0, ObfuscatedMsgUtil.executionPoint(e3));
        }
    }

    public void verifyExp() throws JwsException {
        LogMgr.log(6, "000");
        long jOptLong = this.mHeader.optLong(NAME_EXPIRATION_TIME, -1L);
        if (!checkSpecifyExpirationTime(jOptLong)) {
            LogMgr.log(1, "800 errMsg[" + ("Expiration time is invalid. :" + jOptLong) + "]");
            throw new JwsException(6, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
    }

    public String getPayloadWithVerify(Map<String, String> map) throws JwsException {
        LogMgr.log(8, "000");
        try {
            verify(map);
            verifyExp();
            String payload = getPayload();
            LogMgr.log(8, "999 payloadData[" + payload + "]");
            return payload;
        } catch (JwsException e) {
            LogMgr.log(2, "800 verify failed.");
            throw e;
        }
    }

    public boolean checkExpirationTime(long j) {
        LogMgr.log(6, "000");
        try {
            long jOptLong = this.mHeader.optLong(NAME_EXPIRATION_TIME, -1L) - j;
            LogMgr.log(6, "999");
            return checkSpecifyExpirationTime(jOptLong);
        } catch (Exception e) {
            LogMgr.log(2, "800 errMsg[" + (e.getClass().getSimpleName() + ":" + e.getMessage()) + "]");
            return false;
        }
    }

    public long getExpirationTime() {
        long jOptLong;
        LogMgr.log(6, "000");
        try {
            jOptLong = this.mHeader.optLong(NAME_EXPIRATION_TIME, -1L);
        } catch (Exception e) {
            LogMgr.log(2, "800 errMsg[" + (e.getClass().getSimpleName() + ":" + e.getMessage()) + "]");
            jOptLong = 0;
        }
        LogMgr.log(6, "999");
        return jOptLong;
    }

    private boolean checkSpecifyExpirationTime(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        LogMgr.log(8, "000");
        if (j < jCurrentTimeMillis) {
            LogMgr.log(8, "998 Expiration time is invalid. :" + j + " < " + jCurrentTimeMillis);
            return false;
        }
        LogMgr.log(8, "999");
        return true;
    }

    private String[] split() throws JwsException {
        LogMgr.log(8, "000");
        String[] strArrSplit = this.mJwsStr.split(SEPARATE_CHAR);
        if (strArrSplit.length != 3) {
            LogMgr.log(1, "800 errMsg[JWS format is invalid.]");
            throw new JwsException(2, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(8, "999");
        return strArrSplit;
    }
}
