package com.felicanetworks.mfc.mfi.fws;

import android.content.Intent;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
class DeprecatedStartTask extends AsyncParentTaskBase<Result> {
    private static final List<String> VALID_RESULT_CODE_LIST_LOGIN;
    private Result mResult;

    class Result {
        String accountId;
        Map<String, CardIdentifiableInfo.Cache> cardIdInfoMap;
        Map<String, CompleteCardInfo.Cache> cardInfoMap;
        Intent intent;
        a jwsCreator;
        String loginTokenId;
        boolean noLogin;

        Result() {
        }
    }

    static class AdditionalParams {
        boolean noLogin = false;
        boolean isAdmin = false;
        int layoutType = 1;
        boolean isPrivileged = false;

        AdditionalParams() {
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_LOGIN = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_INVALID_AUTH_CODE);
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    DeprecatedStartTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String accountIssuer, String accountName, int code, boolean isSilentStart, boolean skipAgreement, String appCallerInfo, String appIdInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) {
        this(taskId, executor, listener, accountIssuer, accountName, code, isSilentStart, skipAgreement, appCallerInfo, appIdInfo, fwsClient, chipHolder, dataManager, null);
    }

    DeprecatedStartTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String accountIssuer, String accountName, int code, boolean isSilentStart, boolean skipAgreement, String appCallerInfo, String appIdInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, AdditionalParams params) {
        super(taskId, executor, listener);
        this.mResult = new Result();
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        onFinishLogin(false, 200, ObfuscatedMsgUtil.executionPoint());
    }

    private void onFinishLogin(boolean isSuccess, int errType, String errMsg) {
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
        } else {
            onFinished(isSuccess, errType, errMsg);
        }
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(Result result) {
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized Result getResult2() {
        return this.mResult;
    }
}
