package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaReaderFactory {
    private static final String TRAFFIC_SERVICE_KEY = "Traffic Service";
    private static Map<String, BalanceReader> balanceReaderMap = new HashMap();
    private static SasReader sasReader = null;
    private static QUICPayReader quicPayReader = null;

    static void createBalanceReader(Boolean isHistory) {
        balanceReaderMap.clear();
        balanceReaderMap.put(FeliCaParams.SERVICE_ID_DCARD, new DcmxMiniBalanceReader());
        balanceReaderMap.put("SV000011", new WaonBalanceReader(isHistory));
        balanceReaderMap.put("SV000013", new EdyBalanceReader(isHistory));
        balanceReaderMap.put("SV000075", new NanacoBalanceReader(isHistory));
        balanceReaderMap.put(TRAFFIC_SERVICE_KEY, new TrafficBalanceReader(isHistory));
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

    static QUICPayReader createQUICPayReader() {
        QUICPayReader qUICPayReader = quicPayReader;
        if (qUICPayReader == null) {
            quicPayReader = new QUICPayReader();
        } else {
            qUICPayReader.initializeDataList();
        }
        return quicPayReader;
    }

    static BalanceReader getBalanceReader(String serviceId) {
        if (ServiceGroupType.resolve(serviceId) == ServiceGroupType.TRANSPORTATION) {
            return balanceReaderMap.get(TRAFFIC_SERVICE_KEY);
        }
        return balanceReaderMap.get(serviceId);
    }
}
