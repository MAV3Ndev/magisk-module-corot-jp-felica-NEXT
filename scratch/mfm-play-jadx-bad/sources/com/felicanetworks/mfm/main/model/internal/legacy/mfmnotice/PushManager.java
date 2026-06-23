package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.messenger.Messenger;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class PushManager {
    private static final String DISTRIBUTION_GROUP_DEFAULT = "0";
    private static final String KEY_ACCEPT = "accept";
    private static final String KEY_API_LEVEL = "api_level";
    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_DISTRIBUTION_GROUP = "distribution_group";
    private static final String KEY_ISSUER = "issuer";
    private static final String PM_UNINITIALIZE_ERROR = "PushManager is not initialized.";
    private static final String PUSH_RECEIVE_STATUS_OFF = "N";
    private static final String PUSH_RECEIVE_STATUS_ON = "Y";
    private static final String TAG_ACCEPT_OFF = "off";
    private static final String TAG_ACCEPT_ON = "on";
    private static final PushManager instance = new PushManager();
    private Context context = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private Exception asyncException = null;
    private String token = null;
    private RegisterClientStatus registerClientStatus = RegisterClientStatus.NONE;

    private enum RegisterClientStatus {
        NONE,
        INITIALIZE,
        INPROGRESS,
        COMPLETE,
        FAILURE
    }

    public static PushManager getInstance() {
        return instance;
    }

    private PushManager() {
    }

    public PushManager initialize(Context context) {
        this.context = context;
        this.registerClientStatus = RegisterClientStatus.INITIALIZE;
        return this;
    }

    public void register(final String senderId) throws Exception {
        Context context = this.context;
        if (context == null) {
            throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
        }
        this.asyncException = null;
        this.token = null;
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(context);
            if (!noticeDataManager.setupPreference()) {
                throw new IllegalStateException("Preference initialization error");
            }
            if (!TextUtils.equals(noticeDataManager.getDeviceToken(), "0")) {
                this.registerClientStatus = RegisterClientStatus.COMPLETE;
                this.latch.countDown();
                return;
            }
            FirebaseAnalytics.getInstance(this.context).setAnalyticsCollectionEnabled(true);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            Messenger.fetchToken(this.context, new Messenger.FetchTokenListener() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.PushManager.1
                @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
                public void onFetchedToken(String t) {
                    PushManager.this.token = t;
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
                public void onFailed(Exception exception) {
                    PushManager.this.asyncException = exception;
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            Exception exc = this.asyncException;
            if (exc != null) {
                throw exc;
            }
            registerClient(this.token);
        } catch (Exception e) {
            LogUtil.warning(e);
            this.registerClientStatus = RegisterClientStatus.FAILURE;
            this.latch.countDown();
            throw e;
        }
    }

    public synchronized void registerClient(final String registrationId) {
        this.registerClientStatus = RegisterClientStatus.INPROGRESS;
        Context context = this.context;
        if (context == null) {
            throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
        }
        NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(context);
        if (registrationId != null) {
            String deviceToken = noticeDataManager.getDeviceToken();
            if (deviceToken.equals("0") || !registrationId.equals(deviceToken)) {
                noticeDataManager.setDeviceToken(registrationId);
            }
        }
        this.registerClientStatus = RegisterClientStatus.COMPLETE;
        this.latch.countDown();
    }

    private boolean waitRegister() {
        if (this.registerClientStatus == RegisterClientStatus.NONE || this.registerClientStatus == RegisterClientStatus.FAILURE) {
            return false;
        }
        waitClientRegistration();
        return this.registerClientStatus != RegisterClientStatus.FAILURE;
    }

    public synchronized void setTag(final TagInfo ti) throws Exception {
        String str;
        try {
            try {
                if (ti == null) {
                    throw new IllegalArgumentException("Input value is null.");
                }
                if (this.context == null) {
                    throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
                }
                if (waitRegister()) {
                    NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this.context);
                    FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this.context);
                    if (ti.app_version != null && !noticeDataManager.getPreferenceDataKeyAppVersion().equals(ti.app_version)) {
                        firebaseAnalytics.setUserProperty(KEY_APP_VERSION, ti.app_version);
                    }
                    if (ti.accept != null && !noticeDataManager.getPreferenceDataKeyAccept().equals(ti.accept)) {
                        if ("Y".equals(ti.accept)) {
                            str = "on";
                        } else {
                            str = "N".equals(ti.accept) ? "off" : null;
                        }
                        firebaseAnalytics.setUserProperty(KEY_ACCEPT, str);
                    }
                    if (ti.api_level != null && !noticeDataManager.getPreferenceDataKeyApiLevel().equals(ti.api_level)) {
                        firebaseAnalytics.setUserProperty("api_level", ti.api_level);
                    }
                    if (ti.issuer != null && !noticeDataManager.getPreferenceDataKeyIssuer().equals(ti.issuer)) {
                        firebaseAnalytics.setUserProperty(KEY_ISSUER, ti.issuer);
                    }
                    if (ti.distribution_group != null && !noticeDataManager.getPreferenceDataKeyDistributionGroup().equals(ti.distribution_group)) {
                        firebaseAnalytics.setUserProperty(KEY_DISTRIBUTION_GROUP, ti.distribution_group);
                    }
                    noticeDataManager.setTagInfo(ti);
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void setDeviceTags() throws Exception {
        String str;
        String pushReceiveStatus;
        String strValueOf;
        String str2;
        PackageInfo packageInfo;
        String strValueOf2;
        try {
            if (this.context == null) {
                throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
            }
            String strValueOf3 = null;
            try {
                if (Build.VERSION.SDK_INT >= 33) {
                    packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), PackageManager.PackageInfoFlags.of(128L));
                } else {
                    packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 128);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    strValueOf2 = String.valueOf(packageInfo.getLongVersionCode());
                } else {
                    strValueOf2 = String.valueOf(packageInfo.versionCode);
                }
                str = strValueOf2;
            } catch (Exception unused) {
                str = null;
            }
            try {
                pushReceiveStatus = NoticeDataManager.getInstance(this.context).getPushReceiveStatus();
            } catch (Exception unused2) {
                pushReceiveStatus = null;
            }
            try {
                strValueOf = String.valueOf(Build.VERSION.SDK_INT);
            } catch (Exception unused3) {
                strValueOf = null;
            }
            try {
                str2 = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
            } catch (Exception unused4) {
                str2 = null;
            }
            try {
                if (NoticeDataManager.getInstance(this.context).getPreferenceDataKeyDistributionGroup().equals("0")) {
                    strValueOf3 = String.valueOf(new Random().nextInt(100) + 1);
                }
            } catch (Exception unused5) {
            }
            setTag(new TagInfo(str, pushReceiveStatus, strValueOf, str2, strValueOf3));
        } catch (Exception e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    public void setRegisterState(Map<String, MyServiceInfo.RegisterState> registerStateMap) throws Exception {
        try {
            if (registerStateMap == null) {
                throw new IllegalArgumentException("Input value is null.");
            }
            if (this.context == null) {
                throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
            }
            if (waitRegister()) {
                FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this.context);
                for (Map.Entry<String, MyServiceInfo.RegisterState> entry : registerStateMap.entrySet()) {
                    MyServiceInfo.RegisterState value = entry.getValue();
                    if (value != null) {
                        if (value == MyServiceInfo.RegisterState.NONE) {
                            value = MyServiceInfo.RegisterState.NO_REGISTER;
                        }
                        firebaseAnalytics.setUserProperty(entry.getKey(), String.valueOf(value.ordinal()));
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    private void waitClientRegistration() {
        try {
            this.latch.await();
        } catch (InterruptedException unused) {
        }
    }
}
