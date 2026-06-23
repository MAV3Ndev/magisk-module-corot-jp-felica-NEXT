package com.felicanetworks.common.cmnview;

import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class ViewLayer implements FunctionCodeInterface {
    public BaseView activeView = null;
    public BaseWindowView windowView = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 5;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 0;
    }
}
