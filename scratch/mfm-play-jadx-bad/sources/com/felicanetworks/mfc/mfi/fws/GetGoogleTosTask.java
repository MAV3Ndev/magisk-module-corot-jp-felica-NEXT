package com.felicanetworks.mfc.mfi.fws;

import android.content.Intent;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.OpenIdConnectManager;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
public class GetGoogleTosTask extends AsyncParentTaskBase<Intent> implements OpenIdConnectManager.Listener {
    private static final List<Integer> VALID_ERROR_TYPE_LIST;
    private final int mAccountCode;
    private OpenIdConnectManager mOpenIdConnect;
    private Intent mResult;

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_ERROR_TYPE_LIST = arrayList;
        arrayList.add(216);
        arrayList.add(217);
        arrayList.add(219);
        arrayList.add(205);
        arrayList.add(200);
        arrayList.add(Integer.valueOf(MfiClientCallbackConst.TYPE_SIGN_IN_REQUIRED));
    }

    GetGoogleTosTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, int code) {
        super(taskId, executor, listener);
        this.mOpenIdConnect = new OpenIdConnectManager(this);
        this.mAccountCode = code;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (this.mAccountCode == 0) {
            LogMgr.log(2, "700 Code is zero.");
            onFinished(false, 221, null);
            return;
        }
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        PreAccountCache preAccountCache = PreAccountCache.getInstance();
        if (this.mAccountCode != preAccountCache.getAccountCodeCache()) {
            try {
                preAccountCache.clearLoginData();
            } catch (MfiClientException e) {
                LogMgr.printStackTrace(7, e);
            }
            LogMgr.log(2, "701 Code is invalid.");
            onFinished(false, 221, null);
            return;
        }
        if (!OpenIdConnectSharedPreferences.isAgreeCooperateAccount(felicaAdapter) || !OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(felicaAdapter)) {
            LogMgr.log(2, "702 Mfi tos not agreed.");
            onFinished(false, 246, null);
        } else {
            OpenIdConnectManager openIdConnectManager = new OpenIdConnectManager(this);
            this.mOpenIdConnect = openIdConnectManager;
            openIdConnectManager.connect();
            LogMgr.log(6, "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
    public void onConnected() {
        LogMgr.log(6, "000");
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            this.mOpenIdConnect.disconnect();
        } else {
            PreAccountCache preAccountCache = PreAccountCache.getInstance();
            this.mOpenIdConnect.getAuthCode(preAccountCache.getAccountIssuerCache(), preAccountCache.getAccountNameCache(), preAccountCache.getAccountForce(), true, preAccountCache.shouldSkipAgreementPage());
            LogMgr.log(6, "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
    public void onGetAuthCode(String authCode, String issuer, String name) {
        LogMgr.log(6, "000");
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            this.mOpenIdConnect.disconnect();
        } else {
            onFinished(true, 0, null);
            this.mOpenIdConnect.disconnect();
            LogMgr.log(6, "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
    public void onError(int type, String msg) {
        LogMgr.log(6, "000");
        boolean z = false;
        if (!VALID_ERROR_TYPE_LIST.contains(Integer.valueOf(type))) {
            LogMgr.log(6, "003 error type is " + type);
            msg = ObfuscatedMsgUtil.executionPoint();
            type = 200;
        } else if (type == 222) {
            LogMgr.log(6, "001");
            Intent intent = new Intent(OpenIdConnectConst.ACTION_OPENID_CONNECT_ACTIVITY);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setClassName("com.felicanetworks.mfm.main", OpenIdConnectConst.OPENID_CONNECT_ACTIVITY_CLASS_NAME);
            intent.putExtra("code", this.mAccountCode);
            setResult(intent);
            z = true;
        } else {
            LogMgr.log(6, "002");
        }
        onFinished(z, type, msg);
        this.mOpenIdConnect.disconnect();
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Intent result) {
        LogMgr.log(6, "000");
        this.mResult = result;
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Intent getResult2() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mResult;
    }
}
