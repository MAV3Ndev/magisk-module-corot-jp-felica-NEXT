package com.felicanetworks.mfc.mfi.fws.json;

import android.util.Base64;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
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
        LogMgr.log(4, "%s", "000");
        if (str == null) {
            LogMgr.log(1, "%s %s", "800", "jwsStr is null.");
            throw new JwsException(0, null);
        }
        this.mJwsStr = str;
        try {
            String[] strArrSplit = split();
            this.mHeader = new JSONObject(Base64Util.decodeToUTF8String(strArrSplit[0]));
            this.mPayload = Base64Util.decodeToUTF8String(strArrSplit[1]);
            this.mSignature = Base64Util.decode(strArrSplit[2]);
            LogMgr.log(4, "%s", "999");
        } catch (Exception e) {
            String str2 = e.getClass().getSimpleName() + ":" + e.getMessage();
            LogMgr.log(1, "%s %s", "801", str2);
            LogMgr.printStackTrace(7, e);
            throw new JwsException(0, str2);
        }
    }

    public String getPayload() throws JwsException {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return this.mPayload;
    }

    public void verify(Map<String, String> map) throws JwsException {
        LogMgr.log(4, "%s", "000");
        if (map == null) {
            LogMgr.log(1, "%s %s", "800", "KeyMap is null.");
            throw new JwsException(1, "KeyMap is null.");
        }
        try {
            try {
                String string = this.mHeader.getString(NAME_TYPE);
                String string2 = this.mHeader.getString(NAME_ALGORITHM);
                String string3 = this.mHeader.getString(NAME_KEY_ID);
                if (!TYPE_JOSE.equals(string)) {
                    String str = "Not supported type. :" + string;
                    LogMgr.log(1, "%s %s", "802", str);
                    throw new JwsException(3, str);
                }
                if (!SUPPORTED_ALGORITHMS.containsKey(string2)) {
                    String str2 = "Not supported algorithm. :" + string2;
                    LogMgr.log(1, "%s %s", "803", str2);
                    throw new JwsException(5, str2);
                }
                if (!map.containsKey(string3)) {
                    String str3 = "Not supported kid. :" + string3;
                    LogMgr.log(1, "%s %s", "804", str3);
                    throw new JwsException(4, str3);
                }
                String str4 = map.get(string3);
                if (StringUtil.isEmpty(str4)) {
                    LogMgr.log(1, "%s %s", "805", "Public key is empty,");
                    throw new JwsException(1, "Public key is empty,");
                }
                try {
                    PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str4, 2)));
                    String str5 = SUPPORTED_ALGORITHMS.get(string2);
                    String strSubstring = this.mJwsStr.substring(0, this.mJwsStr.lastIndexOf(46));
                    Signature signature = Signature.getInstance(str5);
                    signature.initVerify(publicKeyGeneratePublic);
                    signature.update(strSubstring.getBytes());
                    if (!signature.verify(this.mSignature)) {
                        LogMgr.log(1, "%s %s", "808", "JWS is not verify.");
                        throw new JwsException(6, "JWS is not verify.");
                    }
                    LogMgr.log(4, "%s", "999");
                } catch (Exception e) {
                    String str6 = e.getClass().getSimpleName() + ":" + e.getMessage();
                    LogMgr.log(1, "%s %s", "807", str6);
                    throw new JwsException(1, str6);
                }
            } catch (Exception e2) {
                String str7 = e2.getClass().getSimpleName() + ":" + e2.getMessage();
                LogMgr.log(1, "%s %s", "809", str7);
                throw new JwsException(0, str7);
            }
        } catch (JwsException e3) {
            throw e3;
        }
    }

    public void verifyExp() throws JwsException {
        LogMgr.log(4, "%s", "000");
        long jOptLong = this.mHeader.optLong(NAME_EXPIRATION_TIME, -1L);
        if (!checkSpecifyExpirationTime(jOptLong)) {
            String str = String.format("Expiration time is invalid. :%d", Long.valueOf(jOptLong));
            LogMgr.log(1, "%s %s", "800", str);
            throw new JwsException(6, str);
        }
        LogMgr.log(4, "%s", "999");
    }

    public boolean checkExpirationTime(long j) {
        LogMgr.log(4, "%s", "000");
        try {
            long jOptLong = this.mHeader.optLong(NAME_EXPIRATION_TIME, -1L) - j;
            LogMgr.log(4, "%s", "999");
            return checkSpecifyExpirationTime(jOptLong);
        } catch (Exception e) {
            LogMgr.log(2, "%s %s", "800", e.getClass().getSimpleName() + ":" + e.getMessage());
            return false;
        }
    }

    private boolean checkSpecifyExpirationTime(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        if (j >= jCurrentTimeMillis) {
            return true;
        }
        LogMgr.log(6, "%s Expiration time is invalid. :%d < %d", "500", Long.valueOf(j), Long.valueOf(jCurrentTimeMillis));
        return false;
    }

    private String[] split() throws JwsException {
        String[] strArrSplit = this.mJwsStr.split(SEPARATE_CHAR);
        if (strArrSplit.length == 3) {
            return strArrSplit;
        }
        LogMgr.log(1, "%s %s", "800", "JWS format is invalid.");
        throw new JwsException(2, "JWS format is invalid.");
    }
}
