package com.felicanetworks.mfm.main.view.activity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class BranchBaseActivity extends BaseActivity {
    public Toolbar mToolbar;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.branch_activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
