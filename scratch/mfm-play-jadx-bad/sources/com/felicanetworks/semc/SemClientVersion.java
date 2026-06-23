package com.felicanetworks.semc;

import com.felicanetworks.semc.util.LogMgrUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientVersion {
    public final String additionalInformation;
    public final int majorVersionCode;
    public final int minorVersionCode;
    public final int revisionVersionCode;
    public final String version;

    public SemClientVersion(String str, int i, int i2, int i3, String str2) {
        LogMgrUtil.log(6, "000 version[" + str + "] major[" + i + "] minor[" + i2 + "] revision[" + i3 + "] add info[" + str2 + "]");
        this.version = str;
        this.majorVersionCode = i;
        this.minorVersionCode = i2;
        this.revisionVersionCode = i3;
        this.additionalInformation = str2;
        LogMgrUtil.log(6, "999");
    }
}
