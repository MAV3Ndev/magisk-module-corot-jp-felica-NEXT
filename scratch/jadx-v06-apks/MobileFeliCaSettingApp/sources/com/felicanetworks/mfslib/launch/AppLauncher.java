package com.felicanetworks.mfslib.launch;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgrException;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class AppLauncher implements FunctionCodeInterface {
    private String addInfo;
    private MfsAppContext context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 24;
    }

    public AppLauncher(MfsAppContext mfsAppContext, String str) {
        this.context = mfsAppContext;
        this.addInfo = str;
    }

    public void startApplication() throws AppLauncherException {
        try {
            try {
                try {
                    this.context.activeActivity.startActivity(getBrowserAppIntent());
                } catch (Exception unused) {
                    throw new AppLauncherException(0, "Start failure");
                }
            } catch (Exception e) {
                throw new AppLauncherException(e, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e), 1);
            }
        } catch (SgMgrException e2) {
            throw new AppLauncherException(e2, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e2), 1);
        } catch (AppLauncherException e3) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e3);
            throw e3;
        }
    }

    private Intent getBrowserAppIntent() throws SgMgrException {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.addInfo));
        return intent;
    }

    public Intent getMarketAppIntent() throws SgMgrException {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.addInfo));
        intent.setPackage((String) this.context.sgMgr.getSgValue(SgMgr.KEY_APP_MARKETAPP_PKGNAME));
        return intent;
    }
}
