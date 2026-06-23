package com.felicanetworks.common.cmnview;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseWindowView extends BaseView implements FunctionCodeInterface {
    @Override // com.felicanetworks.common.cmnview.BaseView
    public void onActivityDestroy() {
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void onActivityResume() {
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void onConfigurationChanged(Configuration configuration) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    public BaseWindowView(Activity activity) {
        super(activity);
    }
}
