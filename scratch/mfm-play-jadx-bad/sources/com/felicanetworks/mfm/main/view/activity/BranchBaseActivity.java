package com.felicanetworks.mfm.main.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BranchBaseActivity extends BaseActivity {
    private Insets mInsets = Insets.NONE;
    public Toolbar mToolbar;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        adjustViewToSystemBar();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToSystemBar();
    }

    private void adjustViewToSystemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ll_branch_layout), new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.activity.BranchBaseActivity$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m413x6212a491(view, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToSystemBar$0$com-felicanetworks-mfm-main-view-activity-BranchBaseActivity, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m413x6212a491(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        if (this.mInsets.top != insets.top) {
            Toolbar toolbar = this.mToolbar;
            toolbar.setPadding(toolbar.getPaddingLeft(), (this.mToolbar.getPaddingTop() + insets.top) - this.mInsets.top, this.mToolbar.getPaddingRight(), this.mToolbar.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams();
            layoutParams.height = (layoutParams.height + insets.top) - this.mInsets.top;
            this.mToolbar.setLayoutParams(layoutParams);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (this.mInsets.left != insets.left) {
            marginLayoutParams.leftMargin = (marginLayoutParams.leftMargin + insets.left) - this.mInsets.left;
        }
        if (this.mInsets.right != insets.right) {
            marginLayoutParams.rightMargin = (marginLayoutParams.rightMargin + insets.right) - this.mInsets.right;
        }
        view.setLayoutParams(marginLayoutParams);
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
