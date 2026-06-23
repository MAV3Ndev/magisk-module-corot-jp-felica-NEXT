package com.felicanetworks.common.cmnctrl.data;

/* JADX INFO: loaded from: classes.dex */
public class VersionInformationData {
    public int version;
    public String versionName;

    public VersionInformationData(int i, String str) {
        this.version = i;
        this.versionName = str;
    }

    public String toString() {
        return "VersionInformationData[" + this.version + ", " + this.versionName + "]";
    }
}
