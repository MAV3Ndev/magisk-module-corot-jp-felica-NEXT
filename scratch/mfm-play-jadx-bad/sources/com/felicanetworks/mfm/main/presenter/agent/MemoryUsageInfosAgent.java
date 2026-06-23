package com.felicanetworks.mfm.main.presenter.agent;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryUsageInfosAgent {
    private boolean _memoryUsage;
    private List<MemoryUsageInfoAgent> _memoryUsageInfoAgentList;
    private String _seid;

    public void setMemoryUsageInfoAgentList(List<MemoryUsageInfoAgent> memoryUsageInfoAgentList) {
        this._memoryUsageInfoAgentList = memoryUsageInfoAgentList;
    }

    public void setMemoryUsage(boolean memoryUsage) {
        this._memoryUsage = memoryUsage;
    }

    public void setSeid(String seid) {
        this._seid = seid;
    }

    public boolean isDisplayInfoList() {
        return this._memoryUsageInfoAgentList != null;
    }

    public List<MemoryUsageInfoAgent> getMemoryUsageInfoAgentList() {
        return this._memoryUsageInfoAgentList;
    }

    public boolean getMemoryUsage() {
        return this._memoryUsage;
    }

    public String getSeid() {
        return this._seid;
    }
}
