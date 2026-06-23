package com.felicanetworks.mfm.main.model.info;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FpAreaInfo {
    private long _fpNumber;
    private List<FpServiceInfo> _fpServiceList;
    private int _totalPocket;
    private int _usedPocket;

    public FpAreaInfo(long j, int i, int i2, List<FpServiceInfo> list) {
        this._fpNumber = j;
        this._totalPocket = i;
        this._usedPocket = i2;
        this._fpServiceList = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<FpServiceInfo> it = this._fpServiceList.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        return "FpAreaInfo{_fpNumber=" + this._fpNumber + ", _totalPocket=" + this._totalPocket + ", _usedPocket=" + this._usedPocket + ", _fpServiceList=" + sb.toString() + '}';
    }

    public long getFpNum() {
        return this._fpNumber;
    }

    public int getTotalPocket() {
        return this._totalPocket;
    }

    public int getUsedPocket() {
        return this._usedPocket;
    }

    public List<FpServiceInfo> getFpServiceList() {
        return this._fpServiceList;
    }
}
