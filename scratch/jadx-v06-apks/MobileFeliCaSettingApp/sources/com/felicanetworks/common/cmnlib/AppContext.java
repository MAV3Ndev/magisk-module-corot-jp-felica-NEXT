package com.felicanetworks.common.cmnlib;

import android.content.Context;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.mfc.Felica;

/* JADX INFO: loaded from: classes.dex */
public class AppContext implements FunctionCodeInterface {
    public Context androidContext = null;
    public LogMgr logMgr = null;
    public SgMgr sgMgr = null;
    public Felica felica = null;
    public String idm = null;
    public String userAgent = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 0;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 0;
    }
}
