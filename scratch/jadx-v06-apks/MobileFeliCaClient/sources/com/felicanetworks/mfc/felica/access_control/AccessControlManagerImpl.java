package com.felicanetworks.mfc.felica.access_control;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import com.felicanetworks.mfw.i.cmn.ResUtil;
import com.felicanetworks.mfw.i.cmn.SysException;
import com.felicanetworks.mfw.i.fbl.BizResOptr;
import com.felicanetworks.mfw.i.fbl.PermitOptr;
import com.felicanetworks.mfw.i.fbl.PermitOptrListener;
import com.felicanetworks.mfw.i.fbl.Property;
import java.security.MessageDigest;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class AccessControlManagerImpl implements AccessControlManager, PermitOptrListener {
    private static final int MAX_SIGNER_SIZE = 100;
    private PermitOptr mOptr;
    private String mChipIssuerId = null;
    private String mCareerIdentifyCode = null;
    private AccessController mAccessController = null;
    private boolean mSucceeded = false;
    private Context mContext = null;
    private String mErrorMessage = null;
    private int mErrorType = 4;

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public void init(Context context) {
        this.mAccessController = null;
        this.mErrorMessage = null;
        this.mErrorType = 4;
        this.mSucceeded = false;
        this.mContext = context;
        this.mOptr = null;
        if (context == null) {
            return;
        }
        this.mChipIssuerId = AccessConfig.getChipIssuerId();
        if (this.mChipIssuerId == null) {
            return;
        }
        this.mCareerIdentifyCode = AccessConfig.getCareerIdentifyCode();
        if (this.mCareerIdentifyCode == null) {
            return;
        }
        try {
            Property.sApplicationVersion = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
            Property.sFileDir = this.mContext.getCacheDir();
            String str = this.mChipIssuerId;
            Property.sChipIssuerId = str;
            Property.sUserAgent = AccessConfig.createUserAgent(context, str);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public boolean startAccessControl(String[] strArr, int i, int i2) throws Throwable {
        if (this.mContext == null) {
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        if (strArr == null || strArr.length == 0) {
            return true;
        }
        if (Property.sApplicationVersion == null || Property.sFileDir == null || Property.sChipIssuerId == null || Property.sUserAgent == null || this.mCareerIdentifyCode == null || this.mChipIssuerId == null) {
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        try {
            BizResOptr bizResOptr = BizResOptr.getInstance();
            synchronized (bizResOptr) {
                bizResOptr.init();
            }
        } catch (SysException e) {
            if (e.getCauseClass() != ResUtil.class || ((e.getCauseMethod() != "readArea" && e.getCauseMethod() != "writeToArea") || e.getMessage() != ResUtil.FILE_CREATION_FAIL)) {
                this.mErrorType = 1;
                this.mErrorMessage = null;
                return false;
            }
        } catch (Exception unused) {
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
        try {
            Vector<byte[]> callerSignerHash = getCallerSignerHash(i2);
            if (callerSignerHash == null) {
                this.mErrorType = 1;
                this.mErrorMessage = null;
                return false;
            }
            this.mOptr = new PermitOptr();
            this.mOptr.setListener(this);
            for (String str : strArr) {
                try {
                    this.mOptr.startVerification(str, callerSignerHash);
                    if (this.mSucceeded || getErrorType() == 2 || getErrorType() == 3) {
                        break;
                    }
                } catch (Exception e2) {
                    this.mErrorType = 1;
                    this.mErrorMessage = e2.getMessage();
                    return false;
                }
            }
            if (!this.mSucceeded) {
                return false;
            }
            com.felicanetworks.mfw.i.fbl.AccessRightChecker accessRightChecker = new com.felicanetworks.mfw.i.fbl.AccessRightChecker();
            accessRightChecker.setUp(this.mOptr.getPermit());
            this.mAccessController = accessRightChecker;
            return true;
        } catch (Exception unused2) {
            this.mErrorType = 1;
            this.mErrorMessage = null;
            return false;
        }
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public void stopAccessControl() {
        PermitOptr permitOptr = this.mOptr;
        if (permitOptr != null) {
            permitOptr.stopVerification();
        }
        if (this.mAccessController != null) {
            this.mAccessController = null;
        }
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public AccessController getAccessController() {
        return this.mAccessController;
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public int getErrorType() {
        return this.mErrorType;
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessControlManager
    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    @Override // com.felicanetworks.mfw.i.fbl.PermitOptrListener
    public void ntfyVerificationResult(int i, String str) {
        if (i == 0) {
            this.mSucceeded = true;
            return;
        }
        if (i == 900) {
            this.mErrorType = 2;
            this.mErrorMessage = "Interrupted.";
        } else if (i == 8001 || i == 8011 || i == 8021) {
            this.mErrorType = 3;
            this.mErrorMessage = AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR;
        }
    }

    private Vector<byte[]> getCallerSignerHash(int i) throws Exception {
        Signature[] signingCertificateHistory;
        Vector<byte[]> vector = new Vector<>();
        Context context = this.mContext;
        if (context == null) {
            throw new Exception();
        }
        PackageManager packageManager = context.getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length > 0) {
            try {
                SigningInfo signingInfo = packageManager.getPackageInfo(packagesForUid[0], 134217728).signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
                if (signingCertificateHistory.length + 0 > 100) {
                    throw new Exception();
                }
                MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                synchronized (messageDigest) {
                    for (Signature signature : signingCertificateHistory) {
                        messageDigest.update(signature.toByteArray());
                        byte[] bArrDigest = messageDigest.digest();
                        messageDigest.reset();
                        vector.add(bArrDigest);
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            return vector;
        }
        throw new Exception();
    }
}
