package com.felicanetworks.mfw.i.cmn;

import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.fbl.Property;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class NwConUtil {
    private static final int CONNECT_TIMEOUT = 60000;
    private static final String ENCODING = "UTF-8";
    public static final String FAILED_ENCODE = "failed to encode.";
    private static final int MAX_BUFFER_SIZE = 153600;
    private static final int READ_BUFFER_SIZE = 4096;
    private static final int READ_TIMEOUT = 60000;
    private static HttpURLConnection sConnection;
    private static StringBuffer sUrlBuff = new StringBuffer();

    private static boolean isControlCharacter(char c) {
        if (c >= 0 && c <= '\t') {
            return true;
        }
        if (11 > c || c > '\f') {
            return (14 <= c && c <= 31) || c == 127;
        }
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public static void get(String str, String str2, NwConUtilListener nwConUtilListener) throws IOException {
        LogMgr.log(4, "%s : url = %s, parameter = %s, listener = %s", "000", str, str2, nwConUtilListener);
        if (str == null || nwConUtilListener == null || !StringUtil.isValidURL(str) || Property.sUserAgent == null) {
            LogMgr.log(1, "800 url = %s, listener = %s, UA = %s", str, nwConUtilListener, Property.sUserAgent);
            throw new SysException((Class<?>) NwConUtil.class, "get", "Illegal argument [url = " + str + ", parameter = " + str2 + ", listener = " + nwConUtilListener + "]");
        }
        RespData respData = new RespData();
        sUrlBuff.setLength(0);
        sUrlBuff.append(str);
        if (str2 != null) {
            LogMgr.log(7, "%s", "001");
            if (str.indexOf(63) == -1) {
                LogMgr.log(7, "%s", "002");
                sUrlBuff.append("?");
            } else {
                LogMgr.log(7, "%s", "003");
                sUrlBuff.append('&');
            }
            sUrlBuff.append(str2);
        }
        try {
            sConnection = (HttpURLConnection) new URL(sUrlBuff.toString()).openConnection();
            sConnection.setRequestProperty("User-Agent", Property.sUserAgent);
            sConnection.setRequestProperty("Accept-Encoding", "identity");
            sConnection.setConnectTimeout(60000);
            sConnection.setReadTimeout(60000);
            respData.setStatusCode(sConnection.getResponseCode());
            String contentType = sConnection.getContentType();
            if (contentType == null) {
                LogMgr.log(1, "%s null Conetnt-Type", "804");
                throw new IOException("failed to communicate.");
            }
            respData.setContentType(contentType);
            ByteBuffer byteBuffer = new ByteBuffer(MAX_BUFFER_SIZE);
            byte[] bArr = new byte[4096];
            try {
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(sConnection.getInputStream());
                    byteBuffer.setLength(0);
                    int i = 0;
                    while (true) {
                        int i2 = bufferedInputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        byteBuffer.append(bArr, 0, i2);
                        i += i2;
                    }
                    if (i == 0) {
                        LogMgr.log(1, "%s null entity", "805");
                        throw new IOException("failed to communicate.");
                    }
                    respData.setMessageBody(createString(byteBuffer.getBytes(), i, ENCODING));
                    HttpURLConnection httpURLConnection = sConnection;
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Exception unused) {
                        }
                    }
                    nwConUtilListener.webUtilExpired(respData);
                    LogMgr.log(4, "%s", "999");
                } catch (Exception e) {
                    LogMgr.log(1, "%s Exception(%s)", "806", e.toString());
                    throw new IOException(FAILED_ENCODE);
                }
            } catch (Throwable th) {
                HttpURLConnection httpURLConnection2 = sConnection;
                if (httpURLConnection2 != null) {
                    try {
                        httpURLConnection2.disconnect();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            LogMgr.log(1, "%s Exception(%s)", "806", e2.toString());
            HttpURLConnection httpURLConnection3 = sConnection;
            if (httpURLConnection3 != null) {
                httpURLConnection3.disconnect();
            }
            throw new IOException("failed to communicate.");
        }
    }

    private static String createString(byte[] bArr, int i, String str) throws IOException {
        LogMgr.log(4, "%s", "000");
        for (int i2 = 0; i2 < i; i2++) {
            if (isControlCharacter((char) bArr[i2])) {
                LogMgr.log(1, "800 control char(%x)", Byte.valueOf(bArr[i2]));
                throw new IOException(FAILED_ENCODE);
            }
        }
        try {
            CharBuffer charBufferDecode = Charset.forName(str).newDecoder().decode(java.nio.ByteBuffer.wrap(bArr, 0, i));
            LogMgr.log(4, "%s", "999");
            return charBufferDecode.toString();
        } catch (Exception e) {
            LogMgr.log(1, "%s Exception(%s)", "801", e.toString());
            throw new IOException(FAILED_ENCODE);
        }
    }

    public static void post(String str, String str2, NwConUtilListener nwConUtilListener) throws IOException {
        LogMgr.log(4, "%s : url = %s, parameter = %s, listener = %s", "000", str, str2, nwConUtilListener);
        throw new SysException((Class<?>) NwConUtil.class, "post", "unexpected call.");
    }

    public static String encode(String str) {
        LogMgr.log(4, "%s : target = %s", "000", str);
        if (str == null) {
            LogMgr.log(1, "800 target = %s", str);
            throw new SysException((Class<?>) NwConUtil.class, "encode", "Illegal argument [target = " + str + "]");
        }
        try {
            String strEncode = URLEncoder.encode(str, ENCODING);
            LogMgr.log(4, "%s", "999");
            return strEncode;
        } catch (UnsupportedEncodingException unused) {
            LogMgr.log(1, "801 UnsupportedEncodingException");
            throw new SysException((Class<?>) NwConUtil.class, "encode", FAILED_ENCODE);
        }
    }

    public static String decode(String str) {
        LogMgr.log(4, "%s : target = %s", "000", str);
        if (str == null) {
            LogMgr.log(1, "800 target = %s", str);
            throw new SysException((Class<?>) NwConUtil.class, "decode", "Illegal argument [target = " + str + "]");
        }
        try {
            String strDecode = URLDecoder.decode(str, ENCODING);
            LogMgr.log(4, "%s", "999");
            return strDecode;
        } catch (UnsupportedEncodingException unused) {
            LogMgr.log(1, "801 UnsupportedEncodingException");
            throw new SysException((Class<?>) NwConUtil.class, "decode", "failed to decode.");
        }
    }
}
