package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FelicaReaderFactory {
    private static final String TRAFFIC_SERVICE_KEY = "Traffic Service";
    private static Map<String, BalanceReader> balanceReaderMap = new HashMap();
    private static SasReader sasReader = null;

    static void createBalanceReader(Boolean bool) {
        balanceReaderMap.clear();
        balanceReaderMap.put(FeliCaParams.SERVICE_ID_DCARD, new DcmxMiniBalanceReader());
        balanceReaderMap.put("SV000011", new WaonBalanceReader(bool));
        balanceReaderMap.put("SV000013", new EdyBalanceReader(bool));
        balanceReaderMap.put("SV000075", new NanacoBalanceReader(bool));
        balanceReaderMap.put(TRAFFIC_SERVICE_KEY, new TrafficBalanceReader(bool));
    }

    static SasReader createSasReader() {
        SasReader sasReader2 = sasReader;
        if (sasReader2 == null) {
            sasReader = new SasReader();
        } else {
            sasReader2.initializeDataList();
        }
        return sasReader;
    }

    static BalanceReader getBalanceReader(String str) {
        if (ServiceGroupType.resolve(str) == ServiceGroupType.TRANSPORTATION) {
            return balanceReaderMap.get(TRAFFIC_SERVICE_KEY);
        }
        return balanceReaderMap.get(str);
    }
}
