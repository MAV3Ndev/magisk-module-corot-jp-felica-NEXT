package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryUsageInfos {
    private boolean _memoryUsage;
    private List<MemoryUsageInfo> _memoryUsageInfoList;
    private String _seid;

    public String toString() {
        return "MemoryUsageInfos{_memoryUsageInfoList=" + this._memoryUsageInfoList + ", _memoryUsage=" + this._memoryUsage + ", _seid=" + this._seid + '}';
    }

    public void setMemoryUsageInfoList(List<MemoryUsageInfo> memoryUsageInfoList) {
        this._memoryUsageInfoList = memoryUsageInfoList;
    }

    public void setMemoryUsage(boolean memoryUsage) {
        this._memoryUsage = memoryUsage;
    }

    public void setSeid(String seid) {
        this._seid = seid;
    }

    public boolean isDisplayInfoList() {
        return this._memoryUsageInfoList != null;
    }

    public List<MemoryUsageInfo> getMemoryUsageInfoList() {
        return this._memoryUsageInfoList;
    }

    public boolean getMemoryUsage() {
        return this._memoryUsage;
    }

    public String getSeid() {
        return this._seid;
    }
}
