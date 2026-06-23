package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.MemoryUsageInfo;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageInfoAgent extends InfoAgent {
    private MemoryUsageInfo _client;

    public MemoryUsageInfoAgent(MemoryUsageInfo memoryUsageInfo) {
        if (memoryUsageInfo == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = memoryUsageInfo;
    }

    public String getSystemCode() {
        return this._client.getSystemCode();
    }

    public int getAssignedBlock() {
        return this._client.getAssignedBlock();
    }

    public int getFreeBlock() {
        return this._client.getFreeBlock();
    }

    public int getUsedBlock() {
        return this._client.getUsedBlock();
    }
}
