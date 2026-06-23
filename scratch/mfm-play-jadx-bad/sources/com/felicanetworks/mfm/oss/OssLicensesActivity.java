package com.felicanetworks.mfm.oss;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.oss.util.LogMgr;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes3.dex */
public class OssLicensesActivity extends AppCompatActivity {
    private Insets mInsets = Insets.NONE;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Toolbar toolbar;
        TextView textView;
        OssLicensesInfo ossLicenseInfo;
        super.onCreate(bundle);
        LogMgr.log(6, "000");
        try {
            setContentView(R.layout.oss_licenses_activity);
            toolbar = (Toolbar) findViewById(R.id.toolbar_oss);
            ScrollView scrollView = (ScrollView) findViewById(R.id.lv_oss);
            textView = (TextView) findViewById(R.id.tv_oss);
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.oss.OssLicensesActivity.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    LogMgr.log(6, "000");
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    marginLayoutParams.leftMargin = insets.left;
                    marginLayoutParams.rightMargin = insets.right;
                    marginLayoutParams.topMargin = insets.top;
                    view.setLayoutParams(marginLayoutParams);
                    LogMgr.log(6, "999");
                    return WindowInsetsCompat.CONSUMED;
                }
            });
            adjustViewToNavigationBar(scrollView);
            ossLicenseInfo = OssLicensesInfoCache.getInstance().getOssLicenseInfo();
        } catch (Exception e) {
            LogMgr.log(1, "800 OssLicensesActivity#onCreate() is failed.");
            LogMgr.log(1, "801 class = " + e.getClass());
            LogMgr.log(1, "802 message = " + e.getMessage());
            finishAndRemoveTask();
        }
        if (ossLicenseInfo == null) {
            LogMgr.log(2, "700 OssLicense is null.");
            finishAndRemoveTask();
            return;
        }
        LogMgr.log(7, "001 getLibrary = " + ossLicenseInfo.getLibraryName());
        LogMgr.log(7, "002 getLength = " + ossLicenseInfo.getLength());
        LogMgr.log(7, "003 getOffset = " + ossLicenseInfo.getOffset());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ossLicenseInfo.getLibraryName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText(getLicense(this, ossLicenseInfo));
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[INVOKE, MOVE_EXCEPTION, INVOKE, MOVE_EXCEPTION] complete} */
    private String getLicense(Context context, OssLicensesInfo ossLicensesInfo) throws Exception {
        LogMgr.log(6, "000 libraryName = " + ossLicensesInfo.getLibraryName());
        int length = ossLicensesInfo.getLength();
        try {
            InputStream inputStreamOpenRawResource = context.getResources().openRawResource(R.raw.third_party_licenses);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    LogMgr.log(7, "001");
                    byte[] bArr = new byte[1024];
                    inputStreamOpenRawResource.skip(ossLicensesInfo.getOffset());
                    while (length > 0) {
                        int i = inputStreamOpenRawResource.read(bArr, 0, Math.min(length, 1024));
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                        length -= i;
                    }
                    String string = byteArrayOutputStream.toString(StringUtil.UTF_8);
                    byteArrayOutputStream.close();
                    if (inputStreamOpenRawResource != null) {
                        inputStreamOpenRawResource.close();
                    }
                    LogMgr.log(6, "999 license = " + string);
                    return string;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            LogMgr.log(1, "800 OssLicensesActivity#onCreate() is failed.");
            LogMgr.log(1, "801 class = " + e.getClass());
            LogMgr.log(1, "802 message = " + e.getMessage());
            throw e;
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        LogMgr.performanceIn("UI", "OssLicensesActivity", "onOptionsItemSelected");
        LogMgr.log(6, "000");
        if (menuItem.getItemId() == 16908332) {
            LogMgr.log(7, "001");
            finishAndRemoveTask();
        }
        LogMgr.log(6, "999");
        LogMgr.performanceOut("UI", "OssLicensesActivity", "onOptionsItemSelected");
        return true;
    }

    protected void adjustViewToNavigationBar(View view) {
        LogMgr.log(6, "000");
        if (view == null) {
            LogMgr.log(6, "998");
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.oss.OssLicensesActivity.2
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    LogMgr.log(6, "000");
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                    if (OssLicensesActivity.this.mInsets.bottom != insets.bottom) {
                        LogMgr.log(7, "001");
                        view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), view2.getPaddingRight(), (view2.getPaddingBottom() + insets.bottom) - OssLicensesActivity.this.mInsets.bottom);
                    }
                    OssLicensesActivity.this.mInsets = insets;
                    LogMgr.log(6, "999");
                    return windowInsetsCompat;
                }
            });
            LogMgr.log(6, "999");
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LogMgr.log(6, "000");
        super.onConfigurationChanged(configuration);
        try {
            adjustViewToNavigationBar(findViewById(R.id.lv_oss));
        } catch (Exception unused) {
        }
        LogMgr.log(6, "999");
    }
}
