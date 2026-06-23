package com.felicanetworks.mfc.mfi.openidconnect;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseIntArray;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.PreAccountCache;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment;
import com.felicanetworks.mfc.mfi.openidconnect.google.GoogleSignInFragment;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.SignatureUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OpenIdConnectActivity extends FragmentActivity {
    private static final SparseIntArray ERR_TYPE_MAP;
    private static final HashMap<String, String> ISSUER_MAP;
    private static final int RESULT_AGREEMENT_NOT_ACCEPT_ERROR = 5;
    private static final int RESULT_HTTP_COMMUNICATION_ERROR = 9;
    private static final int RESULT_INVALID_CODE_ERROR = 3;
    private static final int RESULT_OPSRV_ACCOUNT_ERROR = 6;
    private static final int RESULT_OPSRV_REQUIRED_LIB_UNAVAILABLE = 7;
    private static final int RESULT_OPSRV_RESULT_ERROR = 8;
    private static final int RESULT_SKIPPED = 10;
    private static final int RESULT_SUCCESS = 1;
    private static final int RESULT_UNKNOWN_ERROR = 2;
    private static final int RESULT_USED_BY_OTHER_APP = 4;
    private static boolean mActivityStarted;
    private static boolean mMfiStarted;
    private Context mContext = null;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private OpenIdConnectFragment mConfirmFragment = null;
    private OpenIdConnectFragment mSignInFragment = null;
    private OnConfirmListener mConfirmListener = new OnConfirmListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.1
        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnConfirmListener
        public void onConfirm(int i) {
            if (i == 0) {
                OpenIdConnectSharedPreferences.agreeCooperateAccountAll(OpenIdConnectActivity.this.mContext);
                OpenIdConnectActivity openIdConnectActivity = OpenIdConnectActivity.this;
                openIdConnectActivity.switchFragment(openIdConnectActivity.mSignInFragment);
            } else if (i == 8) {
                OpenIdConnectActivity.this.setErrorResultAndFinish(10, "Indicates that the user skipped sign-in.");
                OpenIdConnectActivity.this.finish();
            } else if (i == 4) {
                OpenIdConnectActivity.this.setErrorResultAndFinish(5, "Agreement is not accepted.");
                OpenIdConnectActivity.this.finish();
            } else {
                OpenIdConnectActivity.this.setErrorResultAndFinish(2, "Indicates that an unknown error has occurred");
                OpenIdConnectActivity.this.finish();
            }
        }
    };
    private OnSignInListener mSignInListener = new OnSignInListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.2
        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnSignInListener
        public void onSignInSuccess(String str, String str2, String str3) {
            PreAccountCache preAccountCache = PreAccountCache.getInstance();
            preAccountCache.updateAccountInfoCache(str2, str3);
            Intent intent = new Intent();
            intent.putExtra("code", preAccountCache.getAccountCodeCache());
            OpenIdConnectActivity.this.setResult(1, intent);
            OpenIdConnectActivity.this.finish();
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnSignInListener
        public void onSignInFailed(String str, int i, String str2) {
            if (str.hashCode() == -376862683) {
                str.equals("https://accounts.google.com");
            }
            int i2 = 5 == i ? 6 : 7 == i ? 9 : 8;
            OpenIdConnectActivity openIdConnectActivity = OpenIdConnectActivity.this;
            openIdConnectActivity.setErrorResultAndFinish(i2, str, openIdConnectActivity.createErrMsg(i, str2));
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnSignInListener
        public void onError(String str, int i, String str2) {
            OpenIdConnectActivity.this.setErrorResultAndFinish(OpenIdConnectActivity.ERR_TYPE_MAP.indexOfKey(i) >= 0 ? OpenIdConnectActivity.ERR_TYPE_MAP.get(i) : 200, str, str2);
        }
    };

    public interface OnConfirmListener extends EventListener {
        void onConfirm(int i);
    }

    public interface OnSignInListener extends EventListener {
        void onError(String str, int i, String str2);

        void onSignInFailed(String str, int i, String str2);

        void onSignInSuccess(String str, String str2, String str3);
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        ERR_TYPE_MAP = sparseIntArray;
        sparseIntArray.put(1, 2);
        ERR_TYPE_MAP.put(2, 8);
        ERR_TYPE_MAP.put(3, 6);
        ERR_TYPE_MAP.put(5, 9);
        ERR_TYPE_MAP.put(6, 7);
        HashMap<String, String> map = new HashMap<>();
        ISSUER_MAP = map;
        map.put("Google", "https://accounts.google.com");
        mActivityStarted = false;
        mMfiStarted = false;
    }

    public static boolean setMfiStarted(boolean z) {
        synchronized (OpenIdConnectActivity.class) {
            if (z) {
                if (mActivityStarted || mMfiStarted) {
                    return false;
                }
            }
            mMfiStarted = z;
            return true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onCreate");
        LogMgr.log(4, "000");
        super.onCreate(bundle);
        boolean z = true;
        if (!setActivityStarted(true)) {
            setErrorResultAndFinish(4, "Already started by other app.");
            finish();
            return;
        }
        this.mContext = getApplicationContext();
        PreAccountCache preAccountCache = PreAccountCache.getInstance();
        int intExtra = getIntent().getIntExtra("code", 0);
        if (intExtra == 0 || intExtra != preAccountCache.getAccountCodeCache()) {
            try {
                preAccountCache.clearLoginData();
            } catch (MfiClientException e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(7, e);
            }
            setErrorResultAndFinish(3, "Code is invalid.");
            finish();
            return;
        }
        if (1 != preAccountCache.getLayoutType() || preAccountCache.shouldSkipAgreementPage()) {
            String callingPackage = getCallingPackage();
            if (callingPackage == null || !callingPackage.equals(preAccountCache.getAppIdentifiableInfo())) {
                LogMgr.log(2, "701 CallingPackage is invalid.");
                setErrorResultAndFinish(2, ObfuscatedMsgUtil.executionPoint());
                finish();
                return;
            } else {
                LogMgr.log(6, "001 CallingPackage is valid.");
                if (!SignatureUtil.checkAppCertHashBase64(this, preAccountCache.getAppCallerInfo(), callingPackage)) {
                    LogMgr.log(2, "702 Calling app signers is invalid.");
                    setErrorResultAndFinish(2, ObfuscatedMsgUtil.executionPoint());
                    finish();
                    return;
                }
                LogMgr.log(6, "002 Calling app signers is valid.");
            }
        }
        String accountIssuerCache = preAccountCache.getAccountIssuerCache();
        if (accountIssuerCache != null && accountIssuerCache.hashCode() == -376862683) {
            accountIssuerCache.equals("https://accounts.google.com");
        }
        boolean zInitGoogleFragment = initGoogleFragment();
        if (!zInitGoogleFragment) {
            finish();
            return;
        }
        setContentView(R.layout.mfi_openidconnect_activity);
        if (!OpenIdConnectSharedPreferences.isAgreeCooperateAccountV2(this) && !preAccountCache.shouldSkipAgreementPage()) {
            z = false;
        }
        if (z) {
            switchFragment(this.mSignInFragment);
        } else {
            switchFragment(this.mConfirmFragment);
        }
        LogMgr.log(4, "999");
        LogMgr.performanceOut(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onCreate");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        LogMgr.log(4, "000");
        super.onStart();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        LogMgr.log(4, "000");
        super.onResume();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        LogMgr.log(4, "000");
        super.onPause();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        LogMgr.log(4, "000");
        super.onStop();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onDestroy");
        LogMgr.log(4, "000");
        super.onDestroy();
        setActivityStarted(false);
        LogMgr.log(4, "999");
        LogMgr.performanceOut(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onDestroy");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LogMgr.log(4, "000");
        super.onConfigurationChanged(configuration);
        LogMgr.log(4, "999");
    }

    @Override // android.app.Activity
    public void finish() {
        LogMgr.log(4, "000");
        setActivityStarted(false);
        finishAndRemoveTask();
        LogMgr.log(4, "999");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        OpenIdConnectFragment openIdConnectFragment = (OpenIdConnectFragment) this.mFragmentManager.findFragmentById(R.id.openid_connect_container);
        if (openIdConnectFragment != null) {
            openIdConnectFragment.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onActivityResult");
        LogMgr.log(4, "000 RequestCode=" + i + ", ResultCode=" + i2);
        if (i == 1) {
            ((GoogleSignInFragment) this.mSignInFragment).handleSignInResult(intent);
        }
        LogMgr.log(4, "999");
        LogMgr.performanceOut(LogMgr.PERFORMANCE_UI, "OpenIdConnectActivity", "onActivityResult");
    }

    private boolean initGoogleFragment() {
        try {
            this.mConfirmFragment = new ConfirmGoogleAccountFragment(PreAccountCache.getInstance().getLayoutType());
            this.mSignInFragment = new GoogleSignInFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(GoogleSignInFragment.KEY_SHOULD_SKIP_AGREEMENT_PAGE, PreAccountCache.getInstance().shouldSkipAgreementPage());
            this.mSignInFragment.setArguments(bundle);
            this.mConfirmFragment.setListener(this.mConfirmListener);
            this.mSignInFragment.setListener(this.mSignInListener);
            return true;
        } catch (IllegalArgumentException unused) {
            setErrorResultAndFinish(2, "Indicates that an unknown error has occurred");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.openid_connect_container, fragment);
        fragmentTransactionBeginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String createErrMsg(int i, String str) {
        return String.format(Locale.getDefault(), OpenIdConnectConst.ERROR_MSG_FMT_INVALID_STATUS_CODE, Integer.valueOf(i), str);
    }

    private String getIssuer(String str) {
        if (!ISSUER_MAP.containsValue(str)) {
            return str;
        }
        for (Map.Entry<String, String> entry : ISSUER_MAP.entrySet()) {
            if (entry.getValue().equals(str)) {
                return entry.getKey();
            }
        }
        return str;
    }

    private String getOpErrMsg(int i, String str, String str2) {
        if (2 == i) {
            return str2;
        }
        if (6 == i) {
            return String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR, str);
        }
        if (7 == i) {
            return String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_MSG_ONLY, str2);
        }
        return String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_WITH_MSG, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorResultAndFinish(int i, String str, String str2) {
        String opErrMsg = getOpErrMsg(i, getIssuer(str), str2);
        Intent intent = new Intent();
        intent.putExtra(OpenIdConnectConst.EXTRA_ERROR_MSG, opErrMsg);
        LogMgr.exLogOnActivityResult(i);
        setResult(i, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorResultAndFinish(int i, String str) {
        Intent intent = new Intent();
        intent.putExtra(OpenIdConnectConst.EXTRA_ERROR_MSG, str);
        LogMgr.exLogOnActivityResult(i);
        setResult(i, intent);
        finish();
    }

    private boolean setActivityStarted(boolean z) {
        synchronized (OpenIdConnectActivity.class) {
            if (z) {
                if (mActivityStarted || mMfiStarted) {
                    return false;
                }
            }
            mActivityStarted = z;
            return true;
        }
    }
}
