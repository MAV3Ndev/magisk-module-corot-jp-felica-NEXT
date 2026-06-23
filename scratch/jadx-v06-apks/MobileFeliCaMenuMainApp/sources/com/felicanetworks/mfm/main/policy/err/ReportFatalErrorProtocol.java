package com.felicanetworks.mfm.main.policy.err;

import androidx.core.view.PointerIconCompat;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;
import com.felicanetworks.mfm.main.policy.err.legacy.ErrorLogData;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ReportFatalErrorProtocol extends Protocol {
    protected ReportFatalErrorProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }

    public NetworkExpert.Request create(List<ErrorLogData> list, String str) {
        String str2;
        try {
            String str3 = "ai=" + Sg.getValue(Sg.Key.SETTING_MFM_ID);
            String str4 = (String) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_READ_TIMEOUT)).intValue();
            String strSimpleVersionName = Information.simpleVersionName();
            DataCheckerUtil.checkLessEqualLength(strSimpleVersionName.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(strSimpleVersionName);
            StringBuilder sb = new StringBuilder(1024);
            Iterator<ErrorLogData> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ErrorLogData next = it.next();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(URLEncoder.encode(next.log, "US-ASCII"));
                if (1024 == sb.length()) {
                    break;
                }
                if (1024 < sb.length()) {
                    sb.delete(PointerIconCompat.TYPE_GRABBING, sb.length()).append("...");
                    break;
                }
            }
            if (str == null || "".equals(str)) {
                str2 = str3 + "&av=" + strSimpleVersionName + "&d=" + new String(sb);
            } else {
                str2 = str3 + "&av=" + strSimpleVersionName + "&idm=" + str + "&d=" + new String(sb);
            }
            byte[] bytes = str2.getBytes(StandardCharsets.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", Information.userAgent());
            map.put("content-length", Integer.toString(bytes.length));
            map.put("content-type", "application/x-www-form-urlencoded");
            return new NetworkExpert.Request(str4, "POST", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            LogUtil.warning(e);
            return null;
        }
    }
}
