package com.amazonaws.mobileconnectors.pinpoint.internal.core.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;

/* JADX INFO: loaded from: classes.dex */
public class AndroidAppDetails {
    private static final Log log = LogFactory.getLog((Class<?>) AndroidAppDetails.class);
    private String appId;
    private String appTitle;
    private Context applicationContext;
    private String packageName;
    private String versionCode;
    private String versionName;

    public AndroidAppDetails() {
    }

    public AndroidAppDetails(Context context, String str) {
        Context applicationContext = context.getApplicationContext();
        this.applicationContext = applicationContext;
        try {
            PackageManager packageManager = applicationContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.applicationContext.getPackageName(), 0);
            this.appTitle = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageInfo.packageName, 0));
            this.packageName = packageInfo.packageName;
            this.versionCode = String.valueOf(packageInfo.versionCode);
            this.versionName = packageInfo.versionName;
            this.appId = str;
        } catch (PackageManager.NameNotFoundException unused) {
            log.warn("Unable to get details for package " + this.applicationContext.getPackageName());
            this.appTitle = "Unknown";
            this.packageName = "Unknown";
            this.versionCode = "Unknown";
            this.versionName = "Unknown";
        }
    }

    public AndroidAppDetails(String str, String str2, String str3, String str4, String str5) {
        this.packageName = str;
        this.versionCode = str2;
        this.versionName = str3;
        this.appTitle = str4;
        this.appId = str5;
    }

    public String packageName() {
        return this.packageName;
    }

    public String versionName() {
        return this.versionName;
    }

    public String versionCode() {
        return this.versionCode;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public String getAppId() {
        return this.appId;
    }
}
