package com.felicanetworks.common.cmnctrl.packages;

import android.content.pm.PackageInfo;
import android.os.Build;
import com.felicanetworks.common.cmnctrl.data.VersionInformationData;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PackageAccess implements FunctionCodeInterface {
    protected AppContext context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 2;
    }

    public PackageAccess(AppContext appContext) {
        this.context = appContext;
    }

    public VersionInformationData getAppVersionInfo() throws PackageAccessException {
        try {
            PackageInfo packageInfo = this.context.androidContext.getPackageManager().getPackageInfo(this.context.androidContext.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= 28) {
                return new VersionInformationData((int) packageInfo.getLongVersionCode(), packageInfo.versionName);
            }
            return new VersionInformationData(packageInfo.versionCode, packageInfo.versionName);
        } catch (Exception e) {
            throw new PackageAccessException(e, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e), 1);
        }
    }

    public int getAppEnableInfo(String str) throws PackageAccessException {
        try {
            return this.context.androidContext.getPackageManager().getApplicationEnabledSetting(str);
        } catch (IllegalArgumentException e) {
            throw new PackageAccessException(e, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e), 0);
        }
    }
}
