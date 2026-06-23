package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageInfos {
    private boolean _memoryUsage;
    private List<MemoryUsageInfo> _memoryUsageInfoList;
    private String _seid;

    public String toString() {
        return "MemoryUsageInfos{_memoryUsageInfoList=" + this._memoryUsageInfoList + ", _memoryUsage=" + this._memoryUsage + ", _seid=" + this._seid + '}';
    }

    public void setMemoryUsageInfoList(List<MemoryUsageInfo> list) {
        this._memoryUsageInfoList = list;
    }

    public void setMemoryUsage(boolean z) {
        this._memoryUsage = z;
    }

    public void setSeid(String str) {
        this._seid = str;
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
