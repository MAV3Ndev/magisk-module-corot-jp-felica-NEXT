package com.felicanetworks.mfw.i.cmn;

import android.util.Base64;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class Base64Util {
    public static byte[] decode(String str) {
        LogMgr.log(4, "%s : target = %s", "000", str);
        DebugLogger.set("Base64Util.decode() target = " + str);
        if (str == null || !StringUtil.isValidBase64(str)) {
            LogMgr.log(1, "800 target = %s", str);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [target = " + str + "]");
            throw new SysException((Class<?>) Base64Util.class, "decode", stringBuffer.toString());
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 0);
            LogMgr.log(4, "%s", "999");
            return bArrDecode;
        } catch (IllegalArgumentException unused) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Illegal argument.");
            stringBuffer2.append(" [target = " + str + "]");
            throw new SysException((Class<?>) Base64Util.class, "decode", stringBuffer2.toString());
        }
    }
}
