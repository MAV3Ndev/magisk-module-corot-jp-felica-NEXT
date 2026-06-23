package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.ClientInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.RegisterDeviceProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.RegisterEventProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.RegisterTagProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.RenewDeviceProtocol;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.messenger.Messenger;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class PushManager {
    private static final String KEY_ACCEPT = "accept";
    private static final String KEY_API_LEVEL = "api_level";
    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_ISSUER = "issuer";
    private static final String KEY_UID = "uid";
    private static final String PM_UNINITIALIZE_ERROR = "PushManager is not initialized.";
    private static final String PUSH_RECEIVE_STATUS_OFF = "N";
    private static final String PUSH_RECEIVE_STATUS_ON = "Y";
    private static final String TAG_ACCEPT_OFF = "off";
    private static final String TAG_ACCEPT_ON = "on";
    private static final String USER_ID_UNKNOWN = "UNKNOWN";
    private static final PushManager instance = new PushManager();
    private static ModelContext _modelContext = null;
    private Context context = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private Exception asyncException = null;
    private String token = null;
    private NetworkExpert _networkExpert = null;
    private RegisterClientStatus registerClientStatus = RegisterClientStatus.NONE;

    private enum RegisterClientStatus {
        NONE,
        INITIALIZE,
        INPROGRESS,
        COMPLETE,
        FAILURE
    }

    public static PushManager getInstance(ModelContext modelContext) {
        _modelContext = modelContext;
        return instance;
    }

    private PushManager() {
    }

    public PushManager initialize(Context context) {
        this.context = context;
        this.registerClientStatus = RegisterClientStatus.INITIALIZE;
        return this;
    }

    public PushManager register(String str) throws Exception {
        if (this.context == null) {
            throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
        }
        this.asyncException = null;
        this.token = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            Messenger.fetchToken(this.context, new Messenger.FetchTokenListener() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.PushManager.1
                @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
                public void onFetchedToken(String str2) {
                    PushManager.this.token = str2;
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
                public void onFailed(Exception exc) {
                    PushManager.this.asyncException = exc;
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            if (this.asyncException != null) {
                throw this.asyncException;
            }
            registerClient(this.token);
            return this;
        } catch (Exception e) {
            LogUtil.warning(e);
            this.registerClientStatus = RegisterClientStatus.FAILURE;
            this.latch.countDown();
            throw e;
        }
    }

    public synchronized void registerClient(String str) throws Exception {
        try {
            try {
                this.registerClientStatus = RegisterClientStatus.INPROGRESS;
                if (this.context == null) {
                    throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
                }
                NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this.context);
                if (str != null) {
                    String deviceToken = noticeDataManager.getDeviceToken();
                    if (deviceToken.equals("0")) {
                        createClient(str);
                    } else if (!str.equals(deviceToken)) {
                        updateClient(str);
                    }
                }
                this.registerClientStatus = RegisterClientStatus.COMPLETE;
            } catch (Exception e) {
                this.registerClientStatus = RegisterClientStatus.FAILURE;
                LogUtil.warning(e);
                throw e;
            }
        } finally {
            this.latch.countDown();
        }
    }

    private void createClient(String str) throws Exception {
        try {
            NetworkExpert networkExpert = new NetworkExpert(_modelContext);
            this._networkExpert = networkExpert;
            RegisterDeviceProtocol registerDeviceProtocol = networkExpert.getRegisterDeviceProtocol();
            RegisterDeviceProtocol.Result result = registerDeviceProtocol.parse(this._networkExpert.connect(registerDeviceProtocol.create(new RegisterDeviceProtocol.Parameter(str))));
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this.context);
            ClientInfo clientInfo = noticeDataManager.getClientInfo();
            if (result.id != null && result.code != null && (!result.id.equals(clientInfo.id) || !result.code.equals(clientInfo.code))) {
                noticeDataManager.setClientInfo(result.id, result.code);
            }
            if (result.registrationId == null || result.registrationId.equals(clientInfo.token)) {
                return;
            }
            noticeDataManager.setDeviceToken(result.registrationId);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    private void updateClient(String str) throws Exception {
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this.context);
            ClientInfo clientInfo = noticeDataManager.getClientInfo();
            NetworkExpert networkExpert = new NetworkExpert(_modelContext);
            this._networkExpert = networkExpert;
            RenewDeviceProtocol renewDeviceProtocol = networkExpert.getRenewDeviceProtocol();
            RenewDeviceProtocol.Result result = renewDeviceProtocol.parse(this._networkExpert.connect(renewDeviceProtocol.create(new RenewDeviceProtocol.Parameter(clientInfo.id, clientInfo.code, str))));
            if (result.id != null && result.code != null && (!result.id.equals(clientInfo.id) || !result.code.equals(clientInfo.code))) {
                noticeDataManager.setClientInfo(result.id, result.code);
            }
            if (result.registrationId == null || result.registrationId.equals(clientInfo.token)) {
                return;
            }
            noticeDataManager.setDeviceToken(result.registrationId);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    private boolean waitRegister() {
        if (this.registerClientStatus == RegisterClientStatus.NONE || this.registerClientStatus == RegisterClientStatus.FAILURE) {
            return false;
        }
        waitClientRegistration();
        return this.registerClientStatus != RegisterClientStatus.FAILURE;
    }

    public void setEvent(String str) throws Exception {
        try {
            if (str == null) {
                throw new IllegalArgumentException("Input value is null.");
            }
            if (this.context == null) {
                throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
            }
            if (waitRegister()) {
                ClientInfo clientInfo = NoticeDataManager.getInstance(this.context).getClientInfo();
                NetworkExpert networkExpert = new NetworkExpert(_modelContext);
                this._networkExpert = networkExpert;
                RegisterEventProtocol registerEventProtocol = networkExpert.getRegisterEventProtocol();
                registerEventProtocol.parse(this._networkExpert.connect(registerEventProtocol.create(new RegisterEventProtocol.Parameter(clientInfo.id, clientInfo.code, str, this.context))));
            }
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    public synchronized void setTag(TagInfo tagInfo) throws Exception {
        try {
            try {
                if (tagInfo == null) {
                    throw new IllegalArgumentException("Input value is null.");
                }
                if (this.context == null) {
                    throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
                }
                if (waitRegister()) {
                    NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this.context);
                    if (tagInfo.uid != null) {
                        tagInfo.set(KEY_UID, setTagConnect(KEY_UID, tagInfo.uid));
                    }
                    if (tagInfo.app_version != null) {
                        tagInfo.set(KEY_APP_VERSION, setTagConnect(KEY_APP_VERSION, tagInfo.app_version));
                    }
                    if (tagInfo.accept != null) {
                        String tagConnect = setTagConnect(KEY_ACCEPT, tagInfo.accept);
                        String str = null;
                        if (tagConnect != null) {
                            if (tagConnect.equals(TAG_ACCEPT_ON)) {
                                str = "Y";
                            } else if (tagConnect.equals(TAG_ACCEPT_OFF)) {
                                str = "N";
                            }
                        }
                        tagInfo.set(KEY_ACCEPT, str);
                    }
                    if (tagInfo.api_level != null) {
                        tagInfo.set("api_level", setTagConnect("api_level", tagInfo.api_level));
                    }
                    if (tagInfo.issuer != null) {
                        tagInfo.set(KEY_ISSUER, setTagConnect(KEY_ISSUER, tagInfo.issuer));
                    }
                    noticeDataManager.setTagInfo(tagInfo);
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public String setTagConnect(String str, String str2) throws Exception {
        RegisterTagProtocol.Result result;
        try {
            String str3 = NoticeDataManager.getInstance(this.context).getTagInfo().get(str);
            if (KEY_ACCEPT.equals(str)) {
                if ("Y".equals(str3)) {
                    str3 = TAG_ACCEPT_ON;
                } else if ("N".equals(str3)) {
                    str3 = TAG_ACCEPT_OFF;
                }
            }
            if (str3.equals(str2)) {
                result = null;
            } else {
                ClientInfo clientInfo = NoticeDataManager.getInstance(this.context).getClientInfo();
                NetworkExpert networkExpert = new NetworkExpert(_modelContext);
                this._networkExpert = networkExpert;
                RegisterTagProtocol registerTagProtocol = networkExpert.getRegisterTagProtocol();
                result = registerTagProtocol.parse(this._networkExpert.connect(registerTagProtocol.create(new RegisterTagProtocol.Parameter(clientInfo.id, clientInfo.code, str, str2))));
            }
            if (result == null) {
                return null;
            }
            return result.value;
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    public void setDeviceTags() throws Exception {
        String id;
        String str;
        String str2;
        String strValueOf;
        String str3;
        String strValueOf2;
        try {
            if (this.context == null) {
                throw new IllegalStateException(PM_UNINITIALIZE_ERROR);
            }
            try {
                id = AdvertisingIdClient.getAdvertisingIdInfo(this.context).getId();
            } catch (Exception unused) {
                id = USER_ID_UNKNOWN;
            }
            String str4 = id;
            try {
                PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 128);
                if (Build.VERSION.SDK_INT >= 28) {
                    strValueOf2 = String.valueOf(packageInfo.getLongVersionCode());
                } else {
                    strValueOf2 = String.valueOf(packageInfo.versionCode);
                }
                str = strValueOf2;
            } catch (Exception unused2) {
                str = null;
            }
            try {
                str2 = NoticeDataManager.getInstance(this.context).getPushReceiveStatus().equals("Y") ? TAG_ACCEPT_ON : TAG_ACCEPT_OFF;
            } catch (Exception unused3) {
                str2 = null;
            }
            try {
                strValueOf = String.valueOf(Build.VERSION.SDK_INT);
            } catch (Exception unused4) {
                strValueOf = null;
            }
            try {
                str3 = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
            } catch (Exception unused5) {
                str3 = null;
            }
            setTag(new TagInfo(str4, str, str2, strValueOf, str3));
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
