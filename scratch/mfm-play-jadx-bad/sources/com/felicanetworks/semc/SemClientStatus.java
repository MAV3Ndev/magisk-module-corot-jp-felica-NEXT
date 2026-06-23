package com.felicanetworks.semc;

import com.felicanetworks.semc.util.LogMgrUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientStatus {
    public final boolean isConnected;
    public final boolean isTsmSequenceStarted;

    public SemClientStatus(boolean z, boolean z2) {
        LogMgrUtil.log(6, "000 connected=" + z + " started=" + z2);
        this.isConnected = z;
        this.isTsmSequenceStarted = z2;
        LogMgrUtil.log(6, "999");
    }
}
