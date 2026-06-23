package com.felicanetworks.mfc.mfi;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CardIdBlockInfo {
    public byte[] blockMask;
    public int blockNumber;
    public int serviceCode;

    public static class Cache implements Serializable {
        private static final long serialVersionUID = -8930766401218409987L;
        public byte[] blockMask;
        public int blockNumber;
        public int serviceCode;
    }

    public CardIdBlockInfo() {
    }

    public CardIdBlockInfo(Cache cache) {
        this.serviceCode = cache.serviceCode;
        this.blockNumber = cache.blockNumber;
        this.blockMask = cache.blockMask;
    }

    public Cache getCacheableData() {
        Cache cache = new Cache();
        cache.serviceCode = this.serviceCode;
        cache.blockNumber = this.blockNumber;
        cache.blockMask = this.blockMask;
        return cache;
    }
}
