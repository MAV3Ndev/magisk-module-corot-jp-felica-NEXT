package com.felicanetworks.mfc.mfi.fws;

import android.content.Intent;
import android.os.Bundle;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.AccountCache;
import com.felicanetworks.mfc.mfi.CardCache;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.OpenIdConnectManager;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJson;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJsonArray;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsException;
import com.felicanetworks.mfc.mfi.fws.json.LoginRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.LoginResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.LoginTokenJws;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectSharedPreferences;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class StartTask extends AsyncParentTaskBase<Result> {
    private static final List<String> VALID_RESULT_CODE_LIST_LOGIN;
    private EventBroadcastSender.AccountChangeEvent mAccountChangeEvent;
    private int mAccountCode;
    private String mAccountIssuer;
    private String mAccountName;
    private final String mAppCallerInfo;
    private final String mAppIdInfo;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final boolean mIsAdmin;
    private final boolean mIsPrivileged;
    private boolean mIsSilentStart;
    private boolean mIsSuccessLoginToFws;
    private a mJwsCreator;
    private final int mLayoutType;
    private LoginResponseJson mLoginResponseJson;
    private LoginTokenJws mLoginToken;
    private final boolean mNoLogin;
    private Result mResult;
    private SeInfoEx mSeInfoEx;
    private final boolean mSkipAgreement;

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
        VALID_RESULT_CODE_LIST_LOGIN.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_LOGIN.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_LOGIN.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_LOGIN.add(FwsConst.RESULT_INVALID_AUTH_CODE);
        VALID_RESULT_CODE_LIST_LOGIN.add("4000");
        VALID_RESULT_CODE_LIST_LOGIN.add("9000");
        VALID_RESULT_CODE_LIST_LOGIN.add("9001");
        VALID_RESULT_CODE_LIST_LOGIN.add(FwsConst.RESULT_CONGESTED);
    }

    StartTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, int i2, boolean z, boolean z2, String str3, String str4, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager) {
        this(i, executorService, listener, str, str2, i2, z, z2, str3, str4, fwsClient, mfiChipHolder, dataManager, null);
    }

    StartTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, int i2, boolean z, boolean z2, String str3, String str4, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, AdditionalParams additionalParams) {
        super(i, executorService, listener);
        this.mAccountCode = 0;
        this.mResult = new Result();
        this.mIsSuccessLoginToFws = false;
        String str5 = OpenIdConnectManager.ISSUER_MAP.get(str);
        this.mAccountIssuer = str5;
        LogMgr.log(7, "%s accountIssuer=%s(%s)", "100", str, str5);
        this.mAccountName = str2;
        this.mAccountCode = i2;
        this.mIsSilentStart = z;
        this.mSkipAgreement = z2;
        this.mAppCallerInfo = str3;
        this.mAppIdInfo = str4;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        additionalParams = additionalParams == null ? new AdditionalParams() : additionalParams;
        this.mNoLogin = additionalParams.noLogin;
        this.mIsAdmin = additionalParams.isAdmin;
        this.mLayoutType = additionalParams.layoutType;
        this.mIsPrivileged = additionalParams.isPrivileged;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        int accountCodeCache;
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        setNoLoginResult(this.mNoLogin);
        a aVar = new a();
        this.mJwsCreator = aVar;
        setJwsCreator(aVar);
        try {
            if (Property.isChipGP()) {
                GpController gpController = this.mChipHolder.getGpController();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "a", "a");
                this.mJwsCreator.a(gpController);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "a", "a");
            } else {
                Felica felica = this.mChipHolder.getFelica();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_MFC, "a", "a");
                this.mJwsCreator.a(felica);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_MFC, "a", "a");
            }
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
        }
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (this.mSkipAgreement && (!this.mIsAdmin || !this.mNoLogin)) {
            OpenIdConnectSharedPreferences.agreeCooperateAccount(felicaAdapter);
        }
        if (createSeInfo() && startGetMfiControlInfo()) {
            boolean z = (this.mAccountIssuer == null || this.mAccountName == null) ? false : true;
            PreAccountCache preAccountCache = PreAccountCache.getInstance();
            if (this.mAccountCode == 0) {
                try {
                    preAccountCache.clearLoginData();
                } catch (MfiClientException e2) {
                    LogMgr.printStackTrace(7, e2);
                }
            } else {
                if (z) {
                    accountCodeCache = preAccountCache.getAccountCodeCache(this.mAccountIssuer, this.mAccountName);
                } else {
                    accountCodeCache = preAccountCache.getAccountCodeCache();
                    this.mAccountIssuer = preAccountCache.getAccountIssuerCache();
                    this.mAccountName = preAccountCache.getAccountNameCache();
                }
                if (this.mAccountCode != accountCodeCache) {
                    try {
                        preAccountCache.clearLoginData();
                    } catch (MfiClientException e3) {
                        LogMgr.printStackTrace(7, e3);
                    }
                    onFinishLogin(false, 221, null);
                    return;
                }
            }
            if (this.mIsSilentStart) {
                if (!this.mNoLogin) {
                    LogSender.prepareUUID(LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
                }
            } else {
                LogSender.prepareUUID(LogSender.EXTRA_VALUE_EVENT_NAME_START);
            }
            if (this.mNoLogin) {
                onFinishLogin(true, 0, null);
                return;
            }
            if (this.mAccountCode == 0) {
                if (this.mIsSilentStart && !OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(felicaAdapter) && !this.mSkipAgreement) {
                    AccountCache accountCache = AccountCache.getInstance();
                    accountCache.clearLoginTokenCache();
                    if (!z) {
                        this.mAccountIssuer = accountCache.getAccountIssuerCache();
                        this.mAccountName = accountCache.getAccountNameCache();
                        LogMgr.log(7, "001 account issuer=" + this.mAccountIssuer + ", name=" + this.mAccountName);
                    }
                } else {
                    AccountCache accountCache2 = AccountCache.getInstance();
                    if (z) {
                        LogMgr.log(7, "200");
                        this.mLoginToken = accountCache2.getLoginTokenCache(this.mAccountIssuer, this.mAccountName);
                    } else {
                        this.mLoginToken = accountCache2.getLoginTokenCache();
                        this.mAccountIssuer = accountCache2.getAccountIssuerCache();
                        this.mAccountName = accountCache2.getAccountNameCache();
                        LogMgr.log(7, "100 account issuer=" + this.mAccountIssuer + ", name=" + this.mAccountName);
                    }
                    LoginTokenJws loginTokenJws = this.mLoginToken;
                    if (loginTokenJws != null && loginTokenJws.optLoginTokenId() != null) {
                        LogMgr.log(7, "300");
                        setLoginTokenIdResult(this.mLoginToken.optLoginTokenId());
                        setAccountIdResult(this.mLoginToken.optAccountId());
                        onFinishLogin(true, 0, null);
                        Bundle bundle = new Bundle();
                        bundle.putString(LogSender.EXTRA_KEY_CACHED, Boolean.toString(true));
                        LogSender.send(felicaAdapter, LogSender.EXTRA_VALUE_EVENT_NAME_ACCOUNT_FIX, LogSender.EXTRA_VALUE_LOG_TYPE_ACTION, bundle);
                        return;
                    }
                }
            }
            if (this.mIsSilentStart) {
                if (!OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(felicaAdapter) && !this.mSkipAgreement) {
                    if (this.mAccountCode == 0) {
                        this.mAccountCode = createSilentStartCode();
                    }
                    preAccountCache.cacheAccountData(this.mAccountIssuer, this.mAccountName, this.mAccountCode, z, LogSender.getUUID(), this.mLayoutType, this.mAppCallerInfo, this.mAppIdInfo, this.mSkipAgreement);
                    requestSilentStartActivity();
                    onFinishLogin(false, 218, null);
                    return;
                }
                startGetAuthCode(z);
                return;
            }
            startGetAuthCode(z);
        }
    }

    private boolean createSeInfo() {
        LogMgr.log(6, "%s", "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        boolean z = true;
        try {
            mfiFelicaWrapper.open();
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "%s MfiFelicaException", "700");
            mfiFelicaWrapper.closeSilently();
            onFinishLogin(false, e.getType(), e.getMessage());
            z = false;
        } catch (GpException e2) {
            LogMgr.log(2, "%s GpException", "701");
            mfiFelicaWrapper.closeSilently();
            onFinishLogin(false, e2.getType(), e2.getMessage());
            z = false;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%s", "702", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            mfiFelicaWrapper.closeSilently();
            onFinishLogin(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
            z = false;
        }
        if ((!this.mIsAdmin || (this.mIsAdmin && !this.mIsPrivileged)) && this.mDataManager.getInitStatus(mfiFelicaWrapper) != 1) {
            throw new MfiFelicaException(31, null);
        }
        this.mSeInfoEx = this.mDataManager.createSeInfoEx(mfiFelicaWrapper);
        this.mDataManager.createSeInfo(mfiFelicaWrapper);
        mfiFelicaWrapper.close();
        LogMgr.log(6, "%s", "999");
        return z;
    }

    private boolean startGetMfiControlInfo() {
        if (this.mIsAdmin && this.mNoLogin) {
            return true;
        }
        MfiControlInfoCache mfiControlInfoCache = MfiControlInfoCache.getInstance();
        String datePattern = mfiControlInfoCache.getDatePattern();
        String timeZone = mfiControlInfoCache.getTimeZone();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String str = simpleDateFormat.format(new Date());
        if (mfiControlInfoCache.needUpdateContent(str)) {
            GetMfiControlInfoTask getMfiControlInfoTask = new GetMfiControlInfoTask(0, this.mFwsClient, FlavorConst.CONTENT_ID, this.mJwsCreator, this.mChipHolder, this.mDataManager);
            setStoppableSubTask(getMfiControlInfoTask);
            getMfiControlInfoTask.start();
            AccessFwsTask.Result result = getMfiControlInfoTask.getResult2();
            if (result.isSuccess) {
                MfiControlInfoCache.getInstance().cacheMfiControlInfo(((GetMfiControlInfoResponseJson) result.response).toString(), str);
            } else {
                onFinishLogin(false, result.errType, result.errMsg);
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int createSilentStartCode() {
        return (this.mAppIdInfo + System.currentTimeMillis()).hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestSilentStartActivity() {
        Intent intent = new Intent(OpenIdConnectConst.ACTION_OPENID_CONNECT_ACTIVITY);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setClassName("com.felicanetworks.mfm.main", OpenIdConnectConst.OPENID_CONNECT_ACTIVITY_CLASS_NAME);
        intent.putExtra("code", this.mAccountCode);
        setIntentResult(intent);
    }

    private void startGetAuthCode(boolean z) {
        LogMgr.log(6, "%s", "000");
        GetAuthCodeSubTask getAuthCodeSubTask = new GetAuthCodeSubTask(0, z);
        setStoppableSubTask(getAuthCodeSubTask);
        getAuthCodeSubTask.start();
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishGetAuthCode(String str, String str2, String str3) {
        LogMgr.log(6, "%s", "000");
        this.mAccountIssuer = str2;
        this.mAccountName = str3;
        LogMgr.log(7, "%s account issuer=%s, name=%s", "100", str2, str3);
        Bundle bundle = new Bundle();
        bundle.putString(LogSender.EXTRA_KEY_CACHED, Boolean.toString(false));
        LogSender.send(FelicaAdapter.getInstance(), LogSender.EXTRA_VALUE_EVENT_NAME_ACCOUNT_FIX, LogSender.EXTRA_VALUE_LOG_TYPE_ACTION, bundle);
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        AccountCache accountCache = AccountCache.getInstance();
        String accountNameCache = accountCache.getAccountNameCache();
        String accountIssuerCache = accountCache.getAccountIssuerCache();
        try {
            if (!this.mAccountName.equals(accountNameCache) || !this.mAccountIssuer.equals(accountIssuerCache)) {
                this.mAccountChangeEvent = new EventBroadcastSender.AccountChangeEvent();
                EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mAccountChangeEvent);
            }
        } catch (NullPointerException e) {
            LogMgr.log(2, "702 accountName or accountCache is null");
            LogMgr.printStackTrace(7, e);
        }
        try {
            loginToFws(str);
        } catch (Exception e2) {
            LogMgr.log(2, "701 " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            onFinishLogin(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
        LogMgr.log(6, "%s", "999");
    }

    private void loginToFws(String str) {
        LogMgr.log(6, "%s", "000");
        FwsLoginSubTask fwsLoginSubTask = new FwsLoginSubTask(0, this.mFwsClient, str);
        setStoppableSubTask(fwsLoginSubTask);
        fwsLoginSubTask.start();
        AccessFwsTask.Result result = fwsLoginSubTask.getResult2();
        this.mIsSuccessLoginToFws = false;
        if (!result.isSuccess) {
            if (215 != result.errType) {
                if (202 == result.errType && (MfiClientCallbackConst.MSG_COMMUNICATION_ERROR.equals(result.errMsg) || MfiClientCallbackConst.MSG_FORMAT_ERROR.equals(result.errMsg))) {
                    String str2 = result.errMsg;
                } else if (result.response != 0) {
                    ((LoginResponseJson) result.response).optResultCode();
                }
            }
            onFinishLogin(false, result.errType, result.errMsg);
        } else if (((LoginResponseJson) result.response).isSuccess()) {
            try {
                LoginTokenJws loginTokenJwsConvertLoginTokenJws = convertLoginTokenJws(((LoginResponseJson) result.response).optLoginToken());
                if (loginTokenJwsConvertLoginTokenJws == null || loginTokenJwsConvertLoginTokenJws.optLoginTokenId() == null) {
                    onFinishLogin(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                } else {
                    this.mIsSuccessLoginToFws = true;
                    this.mLoginToken = loginTokenJwsConvertLoginTokenJws;
                    setLoginTokenIdResult(loginTokenJwsConvertLoginTokenJws.optLoginTokenId());
                    setAccountIdResult(loginTokenJwsConvertLoginTokenJws.optAccountId());
                }
            } catch (JwsException unused) {
                onFinishLogin(false, 200, ObfuscatedMsgUtil.executionPoint());
                return;
            } catch (JSONException e) {
                LogMgr.log(2, "%s JSONException:%s", "700", e.getMessage());
                LogMgr.printStackTrace(7, e);
                onFinishLogin(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                return;
            }
        } else {
            LogMgr.log(2, "701 Unexpected result code " + ((LoginResponseJson) result.response).optResultCode());
            onFinishLogin(false, 200, ObfuscatedMsgUtil.executionPoint());
        }
        if (this.mIsSuccessLoginToFws) {
            this.mLoginResponseJson = (LoginResponseJson) result.response;
            startDisableCards(((LoginResponseJson) result.response).optRemainedCardIdentifiableInfoList());
        }
        LogMgr.log(6, "%s", "999");
    }

    private void startDisableCards(JSONArray jSONArray) {
        LogMgr.log(6, "%s", "000");
        if (jSONArray == null || jSONArray.length() == 0) {
            startDeleteCards(this.mLoginResponseJson.optRemainedDeleteCardIdentifiableInfoList(), this.mLoginResponseJson.optRemainedDeleteSimpleCardInfoList());
        } else {
            DisableRemainedCardsTask disableRemainedCardsTask = new DisableRemainedCardsTask(0, getResult2().loginTokenId, jSONArray);
            setStoppableSubTask(disableRemainedCardsTask);
            disableRemainedCardsTask.start();
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishDisableCards(boolean z, int i, String str) {
        LogMgr.log(6, "%s", "000");
        if (z) {
            startDeleteCards(this.mLoginResponseJson.optRemainedDeleteCardIdentifiableInfoList(), this.mLoginResponseJson.optRemainedDeleteSimpleCardInfoList());
        } else {
            onFinishLogin(false, i, str);
        }
        LogMgr.log(6, "%s", "999");
    }

    private void startDeleteCards(JSONArray jSONArray, JSONArray jSONArray2) {
        LogMgr.log(6, "%s", "000");
        if (jSONArray == null || jSONArray.length() == 0 || jSONArray2 == null || jSONArray2.length() == 0) {
            onFinishLogin(true, 0, null);
        } else {
            LogSender.send(FelicaAdapter.getInstance(), LogSender.EXTRA_VALUE_EVENT_NAME_CARD_DELETE, LogSender.EXTRA_VALUE_LOG_TYPE_DUMP);
            DeleteRemainedDeleteCardsTask deleteRemainedDeleteCardsTask = new DeleteRemainedDeleteCardsTask(0, getResult2().loginTokenId, jSONArray, jSONArray2);
            setStoppableSubTask(deleteRemainedDeleteCardsTask);
            deleteRemainedDeleteCardsTask.start();
        }
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishDeleteCards(boolean z, int i, String str) {
        LogMgr.log(6, "%s", "000");
        onFinishLogin(z, i, str);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinishLogin(boolean z, int i, String str) {
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (z) {
            try {
                if (!this.mNoLogin) {
                    AccountCache.getInstance().cacheLoginData(this.mLoginToken, this.mAccountIssuer, this.mAccountName);
                    PreAccountCache.getInstance().clearLoginData();
                    setCardListResult(CardCache.load(this.mAppCallerInfo, this.mAppIdInfo, this.mLoginToken.optAccountId()));
                }
            } catch (Exception e) {
                LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
                LogMgr.printStackTrace(7, e);
            }
        }
        onFinished(z, i, str);
    }

    private LoginTokenJws convertLoginTokenJws(String str) throws JSONException {
        LoginTokenJws loginTokenJws = new LoginTokenJws(str);
        loginTokenJws.verify(FlavorConst.SERVER_PUBLIC_KEYS);
        loginTokenJws.verifyExp();
        loginTokenJws.checkMembers();
        return loginTokenJws;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(Result result) {
    }

    private synchronized void setNoLoginResult(boolean z) {
        this.mResult.noLogin = z;
    }

    private synchronized void setIntentResult(Intent intent) {
        this.mResult.intent = intent;
    }

    private synchronized void setLoginTokenIdResult(String str) {
        this.mResult.loginTokenId = str;
    }

    private synchronized void setAccountIdResult(String str) {
        this.mResult.accountId = str;
        String accountId = AccountCache.getInstance().getAccountId();
        if (accountId != null && !accountId.equals(str)) {
            LogSender.send(FelicaAdapter.getInstance(), LogSender.EXTRA_VALUE_EVENT_NAME_CHANGED_ACCOUNT_ID, LogSender.EXTRA_VALUE_LOG_TYPE_DUMP);
        }
    }

    private synchronized void setCardListResult(CardCache.CardList cardList) {
        if (cardList != null) {
            this.mResult.cardInfoMap = cardList.cardInfoMap;
            this.mResult.cardIdInfoMap = cardList.cardIdInfoMap;
        }
    }

    private synchronized void setJwsCreator(a aVar) {
        this.mResult.jwsCreator = aVar;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized Result getResult2() {
        return this.mResult;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mAccountChangeEvent, this.mIsSuccessLoginToFws);
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    private class GetAuthCodeSubTask extends StoppableTaskBase<Void> implements OpenIdConnectManager.Listener {
        private final boolean mAccountForce;
        private boolean mFinished;
        private OpenIdConnectManager mOpenIdConnect;

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        protected GetAuthCodeSubTask(int i, boolean z) {
            super(i);
            this.mFinished = false;
            this.mOpenIdConnect = new OpenIdConnectManager(this);
            this.mAccountForce = z;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            OpenIdConnectManager openIdConnectManager = new OpenIdConnectManager(this);
            this.mOpenIdConnect = openIdConnectManager;
            openIdConnectManager.connect();
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        public synchronized void stop() {
            LogMgr.log(5, "%s", "000");
            super.stop();
            onError(215, null);
            LogMgr.log(5, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
        public void onConnected() {
            synchronized (this) {
                if (this.mFinished) {
                    LogMgr.log(2, "Already finished.");
                } else {
                    this.mOpenIdConnect.getAuthCode(StartTask.this.mAccountIssuer, StartTask.this.mAccountName, this.mAccountForce, StartTask.this.mIsSilentStart, StartTask.this.mSkipAgreement);
                }
            }
        }

        @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
        public void onGetAuthCode(final String str, final String str2, final String str3) {
            synchronized (this) {
                if (this.mFinished) {
                    LogMgr.log(2, "Already finished.");
                } else {
                    this.mFinished = true;
                    execute(new Runnable() { // from class: com.felicanetworks.mfc.mfi.fws.StartTask.GetAuthCodeSubTask.1
                        @Override // java.lang.Runnable
                        public void run() {
                            StartTask.this.onFinishGetAuthCode(str, str2, str3);
                            GetAuthCodeSubTask.this.mOpenIdConnect.disconnect();
                        }
                    });
                }
            }
        }

        @Override // com.felicanetworks.mfc.mfi.OpenIdConnectManager.Listener
        public void onError(final int i, final String str) {
            synchronized (this) {
                if (this.mFinished) {
                    LogMgr.log(2, "Already finished.");
                } else {
                    this.mFinished = true;
                    execute(new Runnable() { // from class: com.felicanetworks.mfc.mfi.fws.StartTask.GetAuthCodeSubTask.2
                        @Override // java.lang.Runnable
                        public void run() {
                            int i2;
                            if (StartTask.this.mIsSilentStart && ((i2 = i) == 218 || i2 == 222)) {
                                PreAccountCache preAccountCache = PreAccountCache.getInstance();
                                StartTask.this.mAccountCode = StartTask.this.createSilentStartCode();
                                preAccountCache.cacheAccountData(StartTask.this.mAccountIssuer, StartTask.this.mAccountName, StartTask.this.mAccountCode, GetAuthCodeSubTask.this.mAccountForce, LogSender.getUUID(), StartTask.this.mLayoutType, StartTask.this.mAppCallerInfo, StartTask.this.mAppIdInfo, StartTask.this.mSkipAgreement);
                                StartTask.this.requestSilentStartActivity();
                            }
                            StartTask.this.onFinishLogin(false, i, str);
                            GetAuthCodeSubTask.this.mOpenIdConnect.disconnect();
                        }
                    });
                }
            }
        }

        private void execute(Runnable runnable) {
            try {
                StartTask.this.mExecutor.submit(runnable);
            } catch (Exception e) {
                LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
                LogMgr.printStackTrace(7, e);
                try {
                    StartTask.this.onFinishLogin(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    this.mOpenIdConnect.disconnect();
                } catch (Exception e2) {
                    LogMgr.log(1, "%s %s:%s", "800", e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                }
            }
        }
    }

    private class FwsLoginSubTask extends AccessFwsTask<LoginResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private final String mAuthCode;
        private String mOperationId;

        FwsLoginSubTask(int i, FwsClient fwsClient, String str) {
            super(i, fwsClient);
            this.mOperationId = null;
            this.mAuthCode = str;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            this.mOperationId = FwsParamCreator.createOperationId();
            LoginRequestJson loginRequestJson = new LoginRequestJson();
            loginRequestJson.setRequestId(createRequestId());
            loginRequestJson.setOperationId(this.mOperationId);
            loginRequestJson.setAccountIssuer(StartTask.this.mAccountIssuer);
            loginRequestJson.setAuthCode(this.mAuthCode);
            loginRequestJson.setSeInfo(StartTask.this.mDataManager.getSeInfo(), Property.getSeType());
            if (OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(FelicaAdapter.getInstance())) {
                loginRequestJson.setAgreedTosVer(2);
            }
            return loginRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.login(str, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public LoginResponseJson convertResponse(String str) throws JSONException {
            return new LoginResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return StartTask.VALID_RESULT_CODE_LIST_LOGIN;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_LOGIN.msg;
        }

        private String getOperationId() {
            return this.mOperationId;
        }
    }

    private class DisableRemainedCardsTask extends StoppableTaskBase<Void> implements AsyncTaskBase.Listener {
        private final JSONArray mCardIdInfoList;
        private Iterator<Map.Entry<String, CardIdentifiableInfo>> mIterator;
        private final String mLoginTokenId;

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        DisableRemainedCardsTask(int i, String str, JSONArray jSONArray) {
            super(i);
            this.mLoginTokenId = str;
            this.mCardIdInfoList = jSONArray;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "%s", "000");
            try {
                Collection<CardIdentifiableInfo> collectionConvertCardIdentifiableInfoList = convertCardIdentifiableInfoList(this.mCardIdInfoList);
                try {
                    Map<String, CardIdentifiableInfo> remainedCardCiaInfoByCidMap = new CardIdentifiableInfoChecker().getRemainedCardCiaInfoByCidMap(StartTask.this.mChipHolder, collectionConvertCardIdentifiableInfoList);
                    this.mIterator = remainedCardCiaInfoByCidMap.entrySet().iterator();
                    if (remainedCardCiaInfoByCidMap.size() > 0) {
                        LogSender.send(FelicaAdapter.getInstance(), LogSender.EXTRA_VALUE_EVENT_NAME_CARD_DISABLE, LogSender.EXTRA_VALUE_LOG_TYPE_DUMP);
                    }
                    LogMgr.log(6, "%s RemainedCard %d -> %d", "500", Integer.valueOf(collectionConvertCardIdentifiableInfoList.size()), Integer.valueOf(remainedCardCiaInfoByCidMap.size()));
                    disableNextCard();
                    LogMgr.log(5, "%s", "999");
                } catch (FwsException e) {
                    LogMgr.log(2, "%s FwsException", "701");
                    StartTask.this.onFinishDisableCards(false, e.getType(), e.getMessage());
                }
            } catch (JSONException e2) {
                LogMgr.log(2, "%s JSONException", "700");
                LogMgr.printStackTrace(7, e2);
                StartTask.this.onFinishDisableCards(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        private Collection<CardIdentifiableInfo> convertCardIdentifiableInfoList(JSONArray jSONArray) throws JSONException {
            ArrayList arrayList = new ArrayList();
            Iterator<CardIdentifiableInfoJson> it = new CardIdentifiableInfoJsonArray(jSONArray.toString()).getCardIdentifiableInfoList().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getCardIdentifiableInfo());
            }
            return arrayList;
        }

        private void disableNextCard() {
            int i;
            Map.Entry<String, CardIdentifiableInfo> next;
            LogMgr.log(6, "%s", "000");
            Iterator<Map.Entry<String, CardIdentifiableInfo>> it = this.mIterator;
            if (it == null || !it.hasNext()) {
                StartTask.this.onFinishDisableCards(true, 0, null);
            } else {
                try {
                    next = this.mIterator.next();
                    LogMgr.log(7, "001 cid=" + next.getKey());
                    i = 7;
                } catch (Exception e) {
                    e = e;
                    i = 7;
                }
                try {
                    DisableCardTask disableCardTask = new DisableCardTask(0, StartTask.this.mExecutor, this, this.mLoginTokenId, next.getKey(), next.getValue().getCacheableData(), StartTask.this.mFwsClient, StartTask.this.mChipHolder, StartTask.this.mDataManager, false);
                    StartTask.this.setStoppableSubTask(disableCardTask);
                    disableCardTask.start();
                } catch (Exception e2) {
                    e = e2;
                    LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
                    LogMgr.printStackTrace(i, e);
                    StartTask.this.onFinishDisableCards(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                }
            }
            LogMgr.log(6, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            LogMgr.log(5, "%s isSuccess=%s", "000", Boolean.valueOf(z));
            if (!z) {
                if (212 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "701", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (207 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "702", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (211 != i) {
                    if (208 == i) {
                        LogMgr.log(2, "%s TYPE=%d", "704", Integer.valueOf(i));
                        str = ObfuscatedMsgUtil.executionPoint();
                    }
                    StartTask.this.onFinishDisableCards(false, i, str);
                } else {
                    LogMgr.log(2, "%s TYPE=%d", "703", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                }
                i = 200;
                StartTask.this.onFinishDisableCards(false, i, str);
            } else {
                disableNextCard();
            }
            LogMgr.log(5, "%s", "999");
        }
    }

    private class DeleteRemainedDeleteCardsTask extends StoppableTaskBase<Void> implements AsyncTaskBase.Listener {
        private static final String NAME_CID = "cid";
        private static final String NAME_SERVICE_ID = "serviceId";
        private static final String NAME_SERVICE_TYPE = "serviceType";
        private static final int TASK_ID_DELETE_CARD = 1;
        private static final int TASK_ID_GET_LINKAGE_DATA_LIST = 0;
        private final JSONArray mCardIdInfoListJSONArray;
        private final JSONArray mCardInfoListJSONArray;
        private Iterator<DeleteCardInfo> mDeleteCardInfoIterator;
        private Collection<DeleteCardInfo> mDeleteCardInfoList;
        private Iterator<String> mLinkageDataIterator;
        private Collection<String> mLinkageDataList;
        private final String mLoginTokenId;

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        private class DeleteCardInfo {
            private CardIdentifiableInfo cardIdInfo;
            private String cid;
            private String serviceType;

            private DeleteCardInfo() {
            }
        }

        DeleteRemainedDeleteCardsTask(int i, String str, JSONArray jSONArray, JSONArray jSONArray2) {
            super(i);
            this.mDeleteCardInfoList = new ArrayList();
            this.mLinkageDataList = new ArrayList();
            this.mLoginTokenId = str;
            this.mCardIdInfoListJSONArray = jSONArray;
            this.mCardInfoListJSONArray = jSONArray2;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "%s", "000");
            ArrayList arrayList = new ArrayList();
            try {
                List<CardIdentifiableInfoJson> cardIdentifiableInfoList = new CardIdentifiableInfoJsonArray(this.mCardIdInfoListJSONArray.toString()).getCardIdentifiableInfoList();
                HashMap map = new HashMap();
                for (int i = 0; i < cardIdentifiableInfoList.size(); i++) {
                    CardIdentifiableInfo cardIdentifiableInfo = cardIdentifiableInfoList.get(i).getCardIdentifiableInfo();
                    map.put(cardIdentifiableInfo.serviceId, cardIdentifiableInfo);
                }
                for (int i2 = 0; i2 < this.mCardInfoListJSONArray.length(); i2++) {
                    JSONObject jSONObject = this.mCardInfoListJSONArray.getJSONObject(i2);
                    DeleteCardInfo deleteCardInfo = new DeleteCardInfo();
                    deleteCardInfo.cid = jSONObject.getString(NAME_CID);
                    deleteCardInfo.serviceType = jSONObject.getString(NAME_SERVICE_TYPE);
                    String string = jSONObject.getString(NAME_SERVICE_ID);
                    if (map.containsKey(string)) {
                        deleteCardInfo.cardIdInfo = (CardIdentifiableInfo) map.get(string);
                        this.mDeleteCardInfoList.add(deleteCardInfo);
                        arrayList.add(deleteCardInfo.cid);
                    } else {
                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                    }
                }
                GetLinkageDataListTask getLinkageDataListTask = new GetLinkageDataListTask(0, StartTask.this.mExecutor, this, 2, (String[]) arrayList.toArray(new String[arrayList.size()]), StartTask.this.mDataManager, StartTask.this.mJwsCreator, StartTask.this.mFwsClient, StartTask.this.mChipHolder);
                StartTask.this.setStoppableSubTask(getLinkageDataListTask);
                getLinkageDataListTask.start();
                LogMgr.log(5, "%s", "999");
            } catch (FwsException e) {
                LogMgr.log(2, "%s JSONException", "701");
                LogMgr.printStackTrace(7, e);
                StartTask.this.onFinishDeleteCards(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            } catch (JSONException e2) {
                LogMgr.log(2, "%s JSONException", "700");
                LogMgr.printStackTrace(7, e2);
                StartTask.this.onFinishDisableCards(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        private void deleteNextCard() {
            String str;
            boolean z;
            LogMgr.log(6, "%s", "000");
            try {
            } catch (Exception e) {
                e = e;
                str = "%s";
            }
            try {
                if (!this.mDeleteCardInfoIterator.hasNext() || !this.mLinkageDataIterator.hasNext()) {
                    str = "%s";
                    z = false;
                    try {
                        StartTask.this.onFinishDeleteCards(true, 0, null);
                    } catch (Exception e2) {
                        e = e2;
                        LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
                        LogMgr.printStackTrace(7, e);
                        StartTask.this.onFinishDeleteCards(z, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                    }
                } else {
                    DeleteCardInfo next = this.mDeleteCardInfoIterator.next();
                    str = "%s";
                    DeleteCardTask deleteCardTask = new DeleteCardTask(1, StartTask.this.mExecutor, (AsyncTaskBase.Listener) this, this.mLoginTokenId, next.cid, next.cardIdInfo.getCacheableData(), this.mLinkageDataIterator.next(), StartTask.this.mFwsClient, StartTask.this.mChipHolder, StartTask.this.mDataManager, next.serviceType, false);
                    StartTask.this.setStoppableSubTask(deleteCardTask);
                    deleteCardTask.start();
                }
            } catch (Exception e3) {
                e = e3;
                z = false;
                LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
                LogMgr.printStackTrace(7, e);
                StartTask.this.onFinishDeleteCards(z, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                LogMgr.log(6, str, "999");
            }
            LogMgr.log(6, str, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            LogMgr.log(5, "%s isSuccess=%s", "000", Boolean.valueOf(z));
            if (!z) {
                if (209 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "700", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (210 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "701", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (207 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "702", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (211 == i) {
                    LogMgr.log(2, "%s TYPE=%d", "703", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                } else if (208 != i) {
                    if (226 == i) {
                        LogMgr.log(2, "%s TYPE=%d", "705", Integer.valueOf(i));
                    }
                    StartTask.this.onFinishDeleteCards(false, i, str);
                } else {
                    LogMgr.log(2, "%s TYPE=%d", "704", Integer.valueOf(i));
                    str = ObfuscatedMsgUtil.executionPoint();
                }
                i = 200;
                StartTask.this.onFinishDeleteCards(false, i, str);
            } else {
                int taskId = taskBase.getTaskId();
                if (taskId == 0) {
                    this.mLinkageDataList = Arrays.asList((String[]) taskBase.getResult());
                    if (this.mDeleteCardInfoList.size() != this.mLinkageDataList.size()) {
                        StartTask.this.onFinishDeleteCards(false, 200, ObfuscatedMsgUtil.executionPoint());
                    } else {
                        this.mDeleteCardInfoIterator = this.mDeleteCardInfoList.iterator();
                        this.mLinkageDataIterator = this.mLinkageDataList.iterator();
                        deleteNextCard();
                    }
                } else if (taskId == 1) {
                    deleteNextCard();
                }
            }
            LogMgr.log(5, "%s", "999");
        }
    }
}
