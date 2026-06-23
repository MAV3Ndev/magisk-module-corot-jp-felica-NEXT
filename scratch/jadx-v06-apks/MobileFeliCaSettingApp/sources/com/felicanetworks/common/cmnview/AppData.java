package com.felicanetworks.common.cmnview;

import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class AppData implements FunctionCodeInterface {
    private static AppData _instance = new AppData();
    public LogMgr logMgr = null;
    public AppContext appContext = null;
    public Locale locale = null;
    public boolean applicationDisabled = true;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 0;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 0;
    }

    public static AppData getInstance() {
        return _instance;
    }
}
