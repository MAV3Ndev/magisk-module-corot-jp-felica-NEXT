package com.felicanetworks.mfm.main.policy.err;

import androidx.core.view.PointerIconCompat;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.BasicAuthentication;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;
import com.felicanetworks.mfm.main.policy.err.legacy.ErrorLogData;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ReportFatalErrorProtocol extends Protocol {
    protected ReportFatalErrorProtocol(NetworkExpert net) {
        super(net);
    }

    public List<NetworkExpert.Request> create(List<ErrorLogData> logs, String idm) {
        String str;
        Object obj;
        Object obj2;
        String str2;
        String str3;
        Object obj3;
        String str4;
        String str5;
        String str6;
        ArrayList arrayList = new ArrayList();
        try {
            String str7 = "ai=" + Sg.getValue(Sg.Key.SETTING_MFM_ID);
            String str8 = (String) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_TROUBLE_READ_TIMEOUT)).intValue();
            String strSimpleVersionName = Information.simpleVersionName();
            DataCheckerUtil.checkLessEqualLength(strSimpleVersionName.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(strSimpleVersionName);
            String str9 = "A02B1";
            StringBuilder sb = new StringBuilder(1024);
            StringBuilder sb2 = new StringBuilder(1024);
            Iterator<ErrorLogData> it = logs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = str8;
                    break;
                }
                ErrorLogData next = it.next();
                String str10 = str9;
                if (next.log.startsWith(str9)) {
                    str = str8;
                    if (1024 > sb2.length()) {
                        if (sb2.length() != 0) {
                            sb2.append(",");
                        }
                        sb2.append(URLEncoder.encode(next.log, "US-ASCII"));
                        if (1024 < sb2.length()) {
                            sb2.delete(PointerIconCompat.TYPE_GRABBING, sb2.length()).append("...");
                        }
                    }
                } else {
                    str = str8;
                    if (1024 > sb.length()) {
                        if (sb.length() != 0) {
                            sb.append(",");
                        }
                        sb.append(URLEncoder.encode(next.log, "US-ASCII"));
                        if (1024 < sb.length()) {
                            sb.delete(PointerIconCompat.TYPE_GRABBING, sb.length()).append("...");
                        }
                    }
                }
                if (1024 <= sb2.length() && 1024 <= sb.length()) {
                    break;
                }
                str9 = str10;
                str8 = str;
            }
            if (sb.length() != 0) {
                if (idm == null || "".equals(idm)) {
                    str6 = str7 + "&av=" + strSimpleVersionName + "&d=" + new String(sb);
                } else {
                    str6 = str7 + "&av=" + strSimpleVersionName + "&idm=" + idm + "&d=" + new String(sb);
                }
                byte[] bytes = str6.getBytes(StandardCharsets.UTF_8);
                HashMap map = new HashMap();
                map.put("user-agent", Information.userAgent());
                map.put("content-type", "application/x-www-form-urlencoded");
                map.put("content-length", Integer.toString(bytes.length));
                if (BasicAuthentication.isNeedBasicAuthentication()) {
                    obj = "user-agent";
                    str2 = str;
                    obj3 = "application/x-www-form-urlencoded";
                    str4 = "";
                    obj2 = "content-length";
                    str3 = "&idm=";
                    arrayList.add(BasicAuthentication.addBasicAuthorization(new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_POST, map, iIntValue, iIntValue2, bytes)));
                } else {
                    obj = "user-agent";
                    str2 = str;
                    obj3 = "application/x-www-form-urlencoded";
                    str4 = "";
                    obj2 = "content-length";
                    str3 = "&idm=";
                    arrayList.add(new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_POST, map, iIntValue, iIntValue2, bytes));
                }
            } else {
                obj = "user-agent";
                obj2 = "content-length";
                str2 = str;
                str3 = "&idm=";
                obj3 = "application/x-www-form-urlencoded";
                str4 = "";
            }
            if (sb2.length() != 0) {
                if (idm == null || str4.equals(idm)) {
                    str5 = "ai=02B1&av=" + strSimpleVersionName + "&d=" + new String(sb2);
                } else {
                    str5 = "ai=02B1&av=" + strSimpleVersionName + str3 + idm + "&d=" + new String(sb2);
                }
                byte[] bytes2 = str5.getBytes(StandardCharsets.UTF_8);
                HashMap map2 = new HashMap();
                map2.put(obj, Information.userAgent());
                map2.put("content-type", obj3);
                map2.put(obj2, Integer.toString(bytes2.length));
                if (BasicAuthentication.isNeedBasicAuthentication()) {
                    arrayList.add(BasicAuthentication.addBasicAuthorization(new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_POST, map2, iIntValue, iIntValue2, bytes2)));
                    return arrayList;
                }
                arrayList.add(new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_POST, map2, iIntValue, iIntValue2, bytes2));
            }
            return arrayList;
        } catch (Exception e) {
            LogUtil.warning(e);
            return arrayList;
        }
    }
}
