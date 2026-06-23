package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class MemoryUsageInfo {
    private int _assigned;
    private int _free;
    private String _sysCode;
    private int _used;

    public String toString() {
        return "MemoryUsageInfo{_sysCode='" + this._sysCode + "', _assigned=" + this._assigned + ", _free=" + this._free + ", _used=" + this._used + '}';
    }

    public MemoryUsageInfo(String str, int i, int i2) {
        this._sysCode = str;
        this._free = i2;
        this._used = i;
        this._assigned = i + i2;
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
