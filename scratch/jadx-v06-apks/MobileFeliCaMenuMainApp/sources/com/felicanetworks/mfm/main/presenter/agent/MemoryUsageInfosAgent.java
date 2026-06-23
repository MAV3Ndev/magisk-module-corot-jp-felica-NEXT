package com.felicanetworks.mfm.main.presenter.agent;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageInfosAgent {
    private boolean _memoryUsage;
    private List<MemoryUsageInfoAgent> _memoryUsageInfoAgentList;
    private String _seid;

    public void setMemoryUsageInfoAgentList(List<MemoryUsageInfoAgent> list) {
        this._memoryUsageInfoAgentList = list;
    }

    public void setMemoryUsage(boolean z) {
        this._memoryUsage = z;
    }

    public void setSeid(String str) {
        this._seid = str;
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
