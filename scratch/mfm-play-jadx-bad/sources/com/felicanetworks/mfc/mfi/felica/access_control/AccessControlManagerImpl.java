package com.felicanetworks.mfc.mfi.felica.access_control;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.os.Bundle;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlFacade;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.AccessRightChecker;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Permit;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.MessageDigest;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes3.dex */
public class AccessControlManagerImpl implements AccessControlManager, AccessControlFacade.OnFinishListener {
    private static final int MAX_SIGNER_SIZE = 100;
    private AccessControlFacade mFacade;
    private String mChipIssuerId = null;
    private AccessController mAccessController = null;
    private boolean mSucceeded = false;
    private Permit mPermit = null;
    private Context mContext = null;
    private String mErrorMessage = null;
    private int mErrorType = 4;
    private volatile boolean mIsInitialized = false;

    public AccessControlManagerImpl() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public void init(Context context) {
        LogMgr.log(4, "%s", "000");
        this.mAccessController = null;
        this.mErrorMessage = null;
        this.mErrorType = 4;
        this.mSucceeded = false;
        this.mPermit = null;
        this.mContext = context;
        this.mFacade = null;
        if (context == null) {
            LogMgr.log(6, "%s", "001");
            return;
        }
        String chipIssuerId = AccessConfig.getChipIssuerId();
        this.mChipIssuerId = chipIssuerId;
        if (chipIssuerId == null) {
            LogMgr.log(6, "%s", "002");
            return;
        }
        Property.sChipIssuerId = chipIssuerId;
        Property.setCareerIdentifyCode(AccessConfig.getCareerIdentifyCode());
        Property.setFelicaReaderWriterSupported(AccessConfig.getFelicaReaderWriterSupported());
        Property.setGpEseReaderName(AccessConfig.getGpEseReaderName());
        Property.setDeviceType(AccessConfig.getDeviceType());
        if (!Property.setChipType(AccessConfig.getChipType())) {
            LogMgr.log(1, "800 unknown chip type.");
            return;
        }
        Property.setMobileDeviceInformation(AccessConfig.getMobileDeviceInformation());
        String skuUrl = AccessConfig.getSkuUrl();
        String skuKeyValue = AccessConfig.getSkuKeyValue();
        if ((skuUrl == null && skuKeyValue != null) || (skuUrl != null && skuKeyValue == null)) {
            LogMgr.log(1, "801 One of SkuUrl and SkuKeyValue does not exist.");
            return;
        }
        Property.setSkuUrl(skuUrl);
        Property.setSkuKeyValue(skuKeyValue);
        Property.setChipFunctionIdentificationInfoInfo(AccessConfig.getChipFunctionIdentificationInfo());
        Property.setDeviceIdentificationInfo(AccessConfig.getDeviceIdentificationInfo());
        this.mIsInitialized = true;
        Property.setTopLevelSdType(AccessConfig.getTopLevelSdType());
        Property.setTopLevelSdAid(AccessConfig.getTopLevelSdAid());
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public boolean startAccessControl(String callerPackageName, int pid, int uid) {
        LogMgr.log(4, "%s callerPackageName=%s pid=%d uid=%d", "000", callerPackageName, Integer.valueOf(pid), Integer.valueOf(uid));
        if (this.mContext == null) {
            LogMgr.log(6, "%s", "001");
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        if (this.mChipIssuerId == null) {
            LogMgr.log(6, "%s", "002");
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        if (!this.mIsInitialized) {
            LogMgr.log(6, "003");
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        AccessControlFacade accessControlFacade = new AccessControlFacade();
        this.mFacade = accessControlFacade;
        try {
            accessControlFacade.start(this, this.mChipIssuerId);
            this.mFacade.checkVersionUp();
            if (!this.mSucceeded) {
                LogMgr.log(1, "%s failed to check version up.", "801");
                this.mFacade.finish();
                return false;
            }
            try {
                JSONArray callerSignerHash = getCallerSignerHash(uid);
                if (callerSignerHash == null) {
                    LogMgr.log(1, "%s callerSigInfo is null.", "803");
                    this.mErrorType = 1;
                    this.mErrorMessage = null;
                    this.mFacade.finish();
                    return false;
                }
                if (callerSignerHash.length() == 0) {
                    LogMgr.log(2, "%s callerSigInfo.length is 0.", "700");
                    FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString(LogSender.EXTRA_KEY_DATE, Long.toString(System.currentTimeMillis()));
                    bundle.putString(LogSender.EXTRA_KEY_MODEL, Build.MODEL);
                    bundle.putString(LogSender.EXTRA_KEY_API_LEVEL, Integer.toString(Build.VERSION.SDK_INT));
                    bundle.putString(LogSender.EXTRA_KEY_MFIC_APP_VER, felicaAdapter.getString(R.string.mfi_client_version));
                    bundle.putString(LogSender.EXTRA_KEY_APP_PKG, Integer.toHexString(callerPackageName.hashCode()));
                    LogSender.send(felicaAdapter, LogSender.EXTRA_VALUE_EVENT_NAME_APP_HASH_NULL, LogSender.EXTRA_VALUE_LOG_TYPE_DUMP, bundle);
                }
                this.mFacade.getPermit(callerSignerHash, callerPackageName);
                this.mFacade.finish();
                if (this.mSucceeded) {
                    AccessRightChecker accessRightChecker = new AccessRightChecker();
                    accessRightChecker.setUp(this.mPermit);
                    this.mAccessController = accessRightChecker;
                    LogMgr.log(4, "%s", "999");
                    return true;
                }
                LogMgr.log(1, "%s failed to get permit.", "804");
                return false;
            } catch (Exception e) {
                LogMgr.log(1, "%s failed to get caller signer hash from PackageManager. msg=%s", "802", e.getMessage());
                this.mErrorType = 1;
                this.mErrorMessage = null;
                this.mFacade.finish();
                return false;
            }
        } catch (MfiClientException e2) {
            LogMgr.log(1, "%s failed to start AccessControlFacade. msg=%s", "800", e2.getMessage());
            this.mErrorType = 1;
            this.mErrorMessage = e2.getMessage();
            this.mFacade.finish();
            return false;
        }
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public void stopAccessControl() {
        LogMgr.log(4, "%s", "000");
        AccessControlFacade accessControlFacade = this.mFacade;
        if (accessControlFacade != null) {
            accessControlFacade.stop();
        }
        if (this.mAccessController != null) {
            this.mAccessController = null;
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public AccessController getAccessController() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return this.mAccessController;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public int getErrorType() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return this.mErrorType;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager
    public String getErrorMessage() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return this.mErrorMessage;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlFacade.OnFinishListener
    public void onGetPermitFinished(boolean isSuccess, Permit permit, int errType, String errMsg) {
        LogMgr.log(4, "%s", "000");
        this.mSucceeded = isSuccess;
        if (!isSuccess) {
            this.mErrorType = errType;
            this.mErrorMessage = errMsg;
        } else {
            this.mPermit = permit;
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessControlFacade.OnFinishListener
    public void onVersionUpCheckFinished(boolean isSuccess, boolean needsUpdate, int errType, String errMsg) {
        LogMgr.log(4, "%s", "000");
        if (!isSuccess) {
            this.mSucceeded = false;
            if (errType == 104) {
                this.mErrorType = 2;
                this.mErrorMessage = AccessControlManager.ERROR_MSG_INTERRUPTED;
            } else if (errType == 203) {
                this.mErrorType = 3;
                this.mErrorMessage = AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR;
            } else {
                this.mErrorType = 1;
                this.mErrorMessage = null;
            }
        } else {
            this.mSucceeded = true;
            if (needsUpdate) {
                this.mSucceeded = false;
                this.mErrorType = 100;
                this.mErrorMessage = null;
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    private JSONArray getCallerSignerHash(int uid) throws Exception {
        PackageInfo packageInfo;
        Signature[] signingCertificateHistory;
        LogMgr.log(4, "%s", "000");
        JSONArray jSONArray = new JSONArray();
        Context context = this.mContext;
        if (context == null) {
            throw new Exception();
        }
        PackageManager packageManager = context.getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(uid);
        if (packagesForUid != null && packagesForUid.length > 0) {
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    signingCertificateHistory = packageManager.getPackageInfo(packagesForUid[0], 64).signatures;
                } else {
                    if (Build.VERSION.SDK_INT >= 33) {
                        packageInfo = packageManager.getPackageInfo(packagesForUid[0], PackageManager.PackageInfoFlags.of(134217728L));
                    } else {
                        packageInfo = packageManager.getPackageInfo(packagesForUid[0], 134217728);
                    }
                    SigningInfo signingInfo = packageInfo.signingInfo;
                    if (signingInfo.hasMultipleSigners()) {
                        signingCertificateHistory = signingInfo.getApkContentsSigners();
                    } else {
                        signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                    }
                }
                if (signingCertificateHistory.length > 100) {
                    throw new Exception();
                }
                MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                synchronized (messageDigest) {
                    for (Signature signature : signingCertificateHistory) {
                        messageDigest.update(signature.toByteArray());
                        byte[] bArrDigest = messageDigest.digest();
                        messageDigest.reset();
                        jSONArray.put(Base64Util.encode(bArrDigest));
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            LogMgr.log(4, "%s", "999");
            return jSONArray;
        }
        throw new Exception();
    }
}
