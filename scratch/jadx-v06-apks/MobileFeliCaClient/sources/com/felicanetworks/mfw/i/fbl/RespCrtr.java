package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.Item;
import com.felicanetworks.mfw.i.cmn.NwConUtil;

/* JADX INFO: loaded from: classes.dex */
public class RespCrtr {
    public static String createResp(ArrayList arrayList) {
        String str = "pftype=" + NwConUtil.encode("02") + "&issuer=" + NwConUtil.encode(Property.sChipIssuerId) + "&appver=" + NwConUtil.encode(Property.sApplicationVersion) + "&langcode=" + NwConUtil.encode(Property.LANGUAGE_CODE) + "&councode=" + NwConUtil.encode(Property.COUNTRY_CODE);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        if (arrayList == null) {
            return stringBuffer.toString();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            Item item = (Item) arrayList.get(i);
            stringBuffer.append("&");
            stringBuffer.append(item.getKey());
            stringBuffer.append("=");
            stringBuffer.append(NwConUtil.encode(item.getValue()));
        }
        return stringBuffer.toString();
    }
}
