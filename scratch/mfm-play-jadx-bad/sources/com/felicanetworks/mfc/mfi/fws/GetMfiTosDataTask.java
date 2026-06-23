package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiTosData;
import com.felicanetworks.mfc.mfi.MfiTosDataJson;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.util.AssetsUtil;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class GetMfiTosDataTask extends AsyncParentTaskBase<Result> {
    private static final Map<String, String> MFI_TOS_DATA_ASSETS_MAP;
    private static final String MFI_TOS_DATA_HTML_TEXT_ENG_ASSETS_NAME = "mfitosdata_htmltext_eng.html";
    private static final String MFI_TOS_DATA_HTML_TEXT_JPN_ASSETS_NAME = "mfitosdata_htmltext_jpn.html";
    private static final String MFI_TOS_DATA_HTML_TEXT_PHONE_ENG_ASSETS_NAME = "mfitosdata_htmltext_phone_eng.html";
    private static final String MFI_TOS_DATA_HTML_TEXT_PHONE_JPN_ASSETS_NAME = "mfitosdata_htmltext_phone_jpn.html";
    private final int mAccountCode;
    private final Result mResult;

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Result result) {
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    static class Result {
        boolean isMfiTosAgreed;
        Map<String, String> mfiTosDataJsonMap;

        Result() {
        }
    }

    static {
        HashMap map = new HashMap();
        MFI_TOS_DATA_ASSETS_MAP = map;
        map.put(MfiClientCallbackConst.LOCAL_JAPANESE, MFI_TOS_DATA_HTML_TEXT_JPN_ASSETS_NAME);
        map.put(MfiClientCallbackConst.LOCAL_ENGLISH, MFI_TOS_DATA_HTML_TEXT_ENG_ASSETS_NAME);
    }

    private boolean isSmartWatch() {
        LogMgr.log(3, "000");
        boolean zHasSystemFeature = FelicaAdapter.getInstance().getPackageManager().hasSystemFeature("android.hardware.type.watch");
        LogMgr.log(3, "999");
        return zHasSystemFeature;
    }

    GetMfiTosDataTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, int code) {
        super(taskId, executor, listener);
        this.mResult = new Result();
        this.mAccountCode = code;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
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
        if (isStopped()) {
            LogMgr.log(2, "702 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        HashMap map = new HashMap();
        if (!isSmartWatch()) {
            setPhoneAssetsToMfiTosDataMap();
        }
        for (Map.Entry<String, String> entry : MFI_TOS_DATA_ASSETS_MAP.entrySet()) {
            String value = entry.getValue();
            String strLoad = AssetsUtil.load(value);
            if (strLoad == null) {
                LogMgr.log(2, "703 " + value + "load failed.");
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            try {
                map.put(entry.getKey(), new MfiTosDataJson(new MfiTosData(strLoad)).toString());
            } catch (JSONException e2) {
                LogMgr.log(2, "704 create mfiTosDataMapJson failed.");
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
                return;
            }
        }
        if (isStopped()) {
            LogMgr.log(2, "705 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (OpenIdConnectSharedPreferences.isAgreeCooperateAccount(felicaAdapter) && OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(felicaAdapter)) {
            LogMgr.log(6, "001");
            setMfiTosDataMapResult(map);
            setIsMfiTosAgreedResult(true);
        } else {
            LogMgr.log(6, "002");
            setMfiTosDataMapResult(map);
            setIsMfiTosAgreedResult(false);
        }
        onFinished(true, 0, null);
        LogMgr.log(6, "999");
    }

    private void setPhoneAssetsToMfiTosDataMap() {
        Map<String, String> map = MFI_TOS_DATA_ASSETS_MAP;
        map.put(MfiClientCallbackConst.LOCAL_JAPANESE, MFI_TOS_DATA_HTML_TEXT_PHONE_JPN_ASSETS_NAME);
        map.put(MfiClientCallbackConst.LOCAL_ENGLISH, MFI_TOS_DATA_HTML_TEXT_PHONE_ENG_ASSETS_NAME);
    }

    private synchronized void setMfiTosDataMapResult(Map<String, String> mfiTosDataJsonMap) {
        this.mResult.mfiTosDataJsonMap = mfiTosDataJsonMap;
    }

    private synchronized void setIsMfiTosAgreedResult(boolean isMfiTosAgreedResult) {
        this.mResult.isMfiTosAgreed = isMfiTosAgreedResult;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Result getResult2() {
        return this.mResult;
    }
}
