package com.amazonaws.mobileconnectors.pinpoint.internal.core.util;

/* JADX INFO: loaded from: classes.dex */
public class SDKInfo {
    private final String name;
    private final String version;

    public SDKInfo(String str, String str2) {
        this.name = str;
        this.version = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String toString() {
        return this.name + "-" + this.version;
    }
}
