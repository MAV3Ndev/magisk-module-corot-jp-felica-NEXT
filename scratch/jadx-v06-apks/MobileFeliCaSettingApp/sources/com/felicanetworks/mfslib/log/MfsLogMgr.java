package com.felicanetworks.mfslib.log;

import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class MfsLogMgr extends LogMgr {
    @Override // com.felicanetworks.common.cmnlib.log.LogMgr, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.log.LogMgr, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 36;
    }

    public MfsLogMgr(MfsAppContext mfsAppContext) {
        super(mfsAppContext);
    }
}
