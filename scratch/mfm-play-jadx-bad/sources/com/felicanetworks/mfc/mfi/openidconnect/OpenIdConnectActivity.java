package com.felicanetworks.mfc.mfi.openidconnect;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseIntArray;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
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
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class OpenIdConnectActivity extends FragmentActivity {
    private static final SparseIntArray ERR_TYPE_MAP;
    private static final HashMap<String, String> ISSUER_MAP;
    private static final int RESULT_AGREEMENT_NOT_ACCEPT_ERROR = 5;
    private static final int RESULT_INVALID_CODE_ERROR = 3;
    private static final int RESULT_OPSRV_REQUIRED_LIB_UNAVAILABLE = 7;
    private static final int RESULT_OPSRV_RESULT_ERROR = 8;
    private static final int RESULT_SKIPPED = 10;
    private static final int RESULT_SUCCESS = 1;
    private static final int RESULT_UNKNOWN_ERROR = 2;
    private static final int RESULT_USED_BY_OTHER_APP = 4;
    private static boolean mActivityStarted;
    private static boolean mMfiStarted;
    private final ActivityResultCallback<ActivityResult> mActivityResultCallback;
    private final ActivityResultLauncher<IntentSenderRequest> mLauncher;
    private Context mContext = null;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private OpenIdConnectFragment mConfirmFragment = null;
    private OpenIdConnectFragment mSignInFragment = null;
    private OnConfirmListener mConfirmListener = new OnConfirmListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.1
        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnConfirmListener
        public void onConfirm(int confirm) {
            LogMgr.log(4, "000 confirm = " + confirm);
            if (confirm == 0) {
                LogMgr.log(7, "001");
                OpenIdConnectSharedPreferences.agreeCooperateAccountAll(OpenIdConnectActivity.this.mContext);
                OpenIdConnectActivity openIdConnectActivity = OpenIdConnectActivity.this;
                openIdConnectActivity.switchFragment(openIdConnectActivity.mSignInFragment);
            } else if (confirm == 8) {
                LogMgr.log(7, "002");
                OpenIdConnectActivity.this.setErrorResultAndFinish(10, "Indicates that the user skipped sign in.");
                OpenIdConnectActivity.this.finish();
            } else if (confirm == 4) {
                LogMgr.log(7, "003");
                OpenIdConnectActivity.this.setErrorResultAndFinish(5, "Agreement is not accepted.");
                OpenIdConnectActivity.this.finish();
            } else {
                LogMgr.log(7, "004");
                OpenIdConnectActivity.this.setErrorResultAndFinish(2, "Indicates that an unknown error has occurred");
                OpenIdConnectActivity.this.finish();
            }
            LogMgr.log(4, "999");
        }
    };
    private OnSignInListener mSignInListener = new OnSignInListener() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.2
        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnSignInListener
        public void onSignInSuccess(String authCode, String issuer, String name) {
            LogMgr.log(5, "000");
            PreAccountCache preAccountCache = PreAccountCache.getInstance();
            preAccountCache.updateAccountInfoCache(issuer, name);
            Intent intent = new Intent();
            intent.putExtra("code", preAccountCache.getAccountCodeCache());
            OpenIdConnectActivity.this.setResult(1, intent);
            OpenIdConnectActivity.this.finish();
            LogMgr.log(5, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.OnSignInListener
        public void onError(String issuer, int type, String msg) {
            LogMgr.log(5, "000");
            OpenIdConnectActivity.this.setErrorResultAndFinish(OpenIdConnectActivity.ERR_TYPE_MAP.indexOfKey(type) >= 0 ? OpenIdConnectActivity.ERR_TYPE_MAP.get(type) : 200, issuer, msg);
            LogMgr.log(5, "999");
        }
    };

    public interface OnConfirmListener extends EventListener {
        void onConfirm(int confirm);
    }

    public interface OnSignInListener extends EventListener {
        void onError(String issuer, int type, String msg);

        void onSignInSuccess(String authCode, String issuer, String name);
    }

    public OpenIdConnectActivity() {
        ActivityResultCallback<ActivityResult> activityResultCallback = new ActivityResultCallback<ActivityResult>() { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.4
            /* JADX DEBUG: Method merged with bridge method: onActivityResult(Ljava/lang/Object;)V */
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult result) throws Throwable {
                LogMgr.performanceIn("UI", "OpenIdConnectActivity", "onActivityResult");
                LogMgr.log(4, "000 ResultCode=" + result.getResultCode());
                if (result.getResultCode() == -1) {
                    LogMgr.log(6, "001");
                    ((GoogleSignInFragment) OpenIdConnectActivity.this.mSignInFragment).handleSignInResult(result);
                } else {
                    LogMgr.log(6, "002");
                    OpenIdConnectActivity.this.setErrorResultAndFinish(8, OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE, ObfuscatedMsgUtil.executionPoint());
                }
                LogMgr.log(4, "999");
                LogMgr.performanceOut("UI", "OpenIdConnectActivity", "onActivityResult");
            }
        };
        this.mActivityResultCallback = activityResultCallback;
        this.mLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), activityResultCallback);
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        ERR_TYPE_MAP = sparseIntArray;
        sparseIntArray.put(1, 2);
        sparseIntArray.put(2, 8);
        sparseIntArray.put(6, 7);
        HashMap<String, String> map = new HashMap<>();
        ISSUER_MAP = map;
        map.put("Google", OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE);
        mActivityStarted = false;
        mMfiStarted = false;
    }

    public static boolean setMfiStarted(boolean started) {
        synchronized (OpenIdConnectActivity.class) {
            if (started) {
                if (mActivityStarted || mMfiStarted) {
                    return false;
                }
            }
            mMfiStarted = started;
            return true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        LogMgr.performanceIn("UI", "OpenIdConnectActivity", "onCreate");
        LogMgr.log(4, "000");
        super.onCreate(savedInstanceState);
        boolean z = true;
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(z) { // from class: com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity.3
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                LogMgr.log(4, "000");
                OpenIdConnectActivity.this.onBackPressed();
                LogMgr.log(4, "999");
            }
        });
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
            accountIssuerCache.equals(OpenIdConnectConst.ACCOUNT_ISSUER_GOOGLE);
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
        LogMgr.performanceOut("UI", "OpenIdConnectActivity", "onCreate");
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
        LogMgr.performanceIn("UI", "OpenIdConnectActivity", "onDestroy");
        LogMgr.log(4, "000");
        super.onDestroy();
        setActivityStarted(false);
        LogMgr.log(4, "999");
        LogMgr.performanceOut("UI", "OpenIdConnectActivity", "onDestroy");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        LogMgr.log(4, "000");
        super.onConfigurationChanged(newConfig);
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

    private boolean initGoogleFragment() {
        LogMgr.log(6, "000");
        try {
            this.mConfirmFragment = new ConfirmGoogleAccountFragment(PreAccountCache.getInstance().getLayoutType());
            this.mSignInFragment = new GoogleSignInFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(GoogleSignInFragment.KEY_SHOULD_SKIP_AGREEMENT_PAGE, PreAccountCache.getInstance().shouldSkipAgreementPage());
            this.mSignInFragment.setArguments(bundle);
            this.mConfirmFragment.setListener(this.mConfirmListener);
            this.mSignInFragment.setListener(this.mSignInListener);
            this.mSignInFragment.setActivityResultLauncher(this.mLauncher);
            LogMgr.log(6, "999");
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

    private String getIssuer(String issuer) {
        HashMap<String, String> map = ISSUER_MAP;
        if (!map.containsValue(issuer)) {
            return issuer;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(issuer)) {
                return entry.getKey();
            }
        }
        return issuer;
    }

    private String getOpErrMsg(int type, String issuer, String msg) {
        return 2 != type ? 7 == type ? String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_MSG_ONLY, msg) : String.format(MfiClientCallbackConst.MSG_FMT_OP_ERROR_WITH_MSG, issuer, msg) : msg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorResultAndFinish(int type, String issuer, String msg) {
        String opErrMsg = getOpErrMsg(type, getIssuer(issuer), msg);
        Intent intent = new Intent();
        intent.putExtra(OpenIdConnectConst.EXTRA_ERROR_MSG, opErrMsg);
        LogMgr.exLogOnActivityResult(type);
        setResult(type, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorResultAndFinish(int type, String msg) {
        Intent intent = new Intent();
        intent.putExtra(OpenIdConnectConst.EXTRA_ERROR_MSG, msg);
        LogMgr.exLogOnActivityResult(type);
        setResult(type, intent);
        finish();
    }

    private boolean setActivityStarted(boolean activityStarted) {
        LogMgr.log(6, "000");
        synchronized (OpenIdConnectActivity.class) {
            if (activityStarted) {
                if (mActivityStarted || mMfiStarted) {
                    LogMgr.log(6, "998");
                    return false;
                }
            }
            mActivityStarted = activityStarted;
            LogMgr.log(6, "999");
            return true;
        }
    }
}
