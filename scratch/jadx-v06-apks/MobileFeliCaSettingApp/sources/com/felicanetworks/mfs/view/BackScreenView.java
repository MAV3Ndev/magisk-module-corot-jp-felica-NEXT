package com.felicanetworks.mfs.view;

import android.app.Activity;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnview.BaseWindowView;
import com.felicanetworks.mfs.R;

/* JADX INFO: loaded from: classes.dex */
public class BackScreenView extends BaseWindowView implements FunctionCodeInterface {
    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 3;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 32;
    }

    public BackScreenView(Activity activity) {
        super(activity);
        activity.setContentView(R.layout.win_backscreen);
    }
}
