package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes3.dex */
public class AssetsUtil {
    private static final String ENCODE_TYPE = "UTF-8";

    /* JADX WARN: Can't wrap try/catch for region: R(9:7|(4:73|8|71|9)|(7:60|10|(1:12)(1:75)|68|19|41|42)|13|(2:58|15)|68|19|41|42) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0050, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
    
        com.felicanetworks.mfc.util.LogMgr.printStackTrace(7, r8);
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String load(String str) throws Throwable {
        BufferedReader bufferedReader;
        InputStream inputStreamOpen;
        LogMgr.log(5, "000");
        InputStream inputStream = null;
        string = null;
        String string = null;
        if (str == null || str.length() == 0) {
            LogMgr.log(2, "700 name is null or empty.");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStreamOpen = FelicaAdapter.getInstance().getResources().getAssets().open(str);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen, "UTF-8"));
            } catch (IOException e) {
                e = e;
                bufferedReader = null;
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
                inputStream = inputStreamOpen;
                if (inputStream != null) {
                }
                if (bufferedReader != null) {
                }
            }
        } catch (IOException e2) {
            e = e2;
            inputStreamOpen = null;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            if (inputStream != null) {
            }
            if (bufferedReader != null) {
            }
        }
        while (true) {
            try {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuffer.append(line);
                } catch (IOException e3) {
                    e = e3;
                    LogMgr.log(2, "701 Failed to read assets file. msg:" + e.getMessage());
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (IOException e4) {
                            LogMgr.printStackTrace(7, e4);
                        }
                    }
                    if (bufferedReader != null) {
                    }
                    LogMgr.log(5, "999");
                    return string;
                }
                bufferedReader.close();
                LogMgr.log(5, "999");
                return string;
            } catch (Throwable th3) {
                th = th3;
                inputStream = inputStreamOpen;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e5) {
                        LogMgr.printStackTrace(7, e5);
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                        throw th;
                    } catch (IOException e6) {
                        LogMgr.printStackTrace(7, e6);
                        throw th;
                    }
                }
                throw th;
            }
        }
        string = stringBuffer.toString();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (IOException e7) {
                LogMgr.printStackTrace(7, e7);
            }
        }
        bufferedReader.close();
        LogMgr.log(5, "999");
        return string;
    }
}
