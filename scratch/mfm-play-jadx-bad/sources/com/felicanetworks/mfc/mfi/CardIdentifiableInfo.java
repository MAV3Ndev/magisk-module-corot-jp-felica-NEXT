package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CardIdentifiableInfo {
    public int areaCode;
    public List<CardIdBlockInfo> blockInfoList;
    public Map<String, List<String>> maskedBlockListByCidMap;
    public String serviceId;
    public int systemCode;

    public static class Cache implements Serializable {
        private static final long serialVersionUID = 8359345992573042620L;
        public int areaCode;
        public CardIdBlockInfo.Cache[] blockInfoArray;
        public Map<String, List<String>> maskedBlockListByCidMap;
        public String serviceId;
        public int systemCode;
    }

    public CardIdentifiableInfo() {
    }

    public CardIdentifiableInfo(Cache cache) {
        this.serviceId = cache.serviceId;
        this.systemCode = cache.systemCode;
        this.areaCode = cache.areaCode;
        if (cache.blockInfoArray != null) {
            this.blockInfoList = new ArrayList();
            for (CardIdBlockInfo.Cache cache2 : cache.blockInfoArray) {
                this.blockInfoList.add(new CardIdBlockInfo(cache2));
            }
        }
        this.maskedBlockListByCidMap = cache.maskedBlockListByCidMap;
    }

    public Cache getCacheableData() {
        Cache cache = new Cache();
        cache.serviceId = this.serviceId;
        cache.systemCode = this.systemCode;
        cache.areaCode = this.areaCode;
        List<CardIdBlockInfo> list = this.blockInfoList;
        if (list != null) {
            int size = list.size();
            cache.blockInfoArray = new CardIdBlockInfo.Cache[size];
            for (int i = 0; i < size; i++) {
                cache.blockInfoArray[i] = this.blockInfoList.get(i).getCacheableData();
            }
        }
        cache.maskedBlockListByCidMap = this.maskedBlockListByCidMap;
        return cache;
    }
}
