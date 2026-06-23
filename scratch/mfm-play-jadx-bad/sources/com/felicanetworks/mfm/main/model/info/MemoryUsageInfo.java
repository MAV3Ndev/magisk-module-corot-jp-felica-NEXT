package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryUsageInfo {
    private int _assigned;
    private int _free;
    private String _sysCode;
    private int _used;

    public String toString() {
        return "MemoryUsageInfo{_sysCode='" + this._sysCode + "', _assigned=" + this._assigned + ", _free=" + this._free + ", _used=" + this._used + '}';
    }

    public MemoryUsageInfo(String sysCode, int used, int free) {
        this._sysCode = sysCode;
        this._free = free;
        this._used = used;
        this._assigned = used + free;
    }

    public String getSystemCode() {
        return this._sysCode;
    }

    public int getAssignedBlock() {
        return this._assigned;
    }

    public int getFreeBlock() {
        return this._free;
    }

    public int getUsedBlock() {
        return this._used;
    }
}
