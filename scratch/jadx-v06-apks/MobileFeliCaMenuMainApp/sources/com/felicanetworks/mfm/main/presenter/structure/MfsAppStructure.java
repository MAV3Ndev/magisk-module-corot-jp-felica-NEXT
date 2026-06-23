package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.pkg.SpecAppSignatures;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.agent.TermsOfServiceFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class MfsAppStructure extends ExternalAppStructure {
    private static final String EXTRAS_DISPLAY_TOS = "com.felicanetworks.mfs.tos";
    private static final String MFM_APP_ID = "com.felicanetworks.mfs.ai";
    public static final int RESULT_CODE_SETTINGAPP_CANCELED = 0;
    public static final int RESULT_CODE_SETTINGAPP_FELICA_ALREADYINIT = 2;
    public static final int RESULT_CODE_SETTINGAPP_FELICA_CANCELED = 0;
    public static final int RESULT_CODE_SETTINGAPP_FELICA_SUCCESS = 1;
    public static final int RESULT_CODE_SETTINGAPP_NOERROR_ALREADYINIT = 2;
    public static final int RESULT_CODE_SETTINGAPP_NOERROR_SUCCESS = 1;
    public static final String RESULT_KEY_SETTINGAPP_FELICA = "com.felicanetworks.mfs.result.felica";
    private TermsOfServiceFuncAgent _tosAgent;

    public MfsAppStructure(TermsOfServiceFuncAgent termsOfServiceFuncAgent) {
        super(StructureType.MFS_APP);
        this._tosAgent = termsOfServiceFuncAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.ExternalAppStructure
    public Intent getDefaultIntent() {
        Intent intent = new Intent();
        try {
            intent.setClassName((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_MFS), (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_CLS_MFS));
            intent.putExtra(MFM_APP_ID, (String) Sg.getValue(Sg.Key.SETTING_MFM_ID));
            intent.putExtra(EXTRAS_DISPLAY_TOS, this._tosAgent.getDisplayTos());
            return intent;
        } catch (Exception e) {
            LogUtil.warning(e);
            return null;
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int i, Intent intent) {
        try {
            if (intent != null) {
                int intExtra = intent.getIntExtra(RESULT_KEY_SETTINGAPP_FELICA, -1);
                if (intExtra == 0) {
                    StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_INCOMPLETE, this, new Object[0]);
                } else if (intExtra == 1) {
                    StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_COMPLETE, this, new Object[0]);
                } else if (intExtra == 2) {
                    StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_ALREADY_INIT, this, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EV_RESULT_OTHERS, this, new Object[0]);
                }
            } else if (i == 0) {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_INCOMPLETE, this, new Object[0]);
            } else if (i == 1) {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_COMPLETE, this, new Object[0]);
            } else if (i == 2) {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_FELICA_SETTING_ALREADY_INIT, this, new Object[0]);
            } else {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_OTHERS, this, new Object[0]);
            }
        } catch (Exception e) {
            MfmException mfmException = new MfmException(getClass(), 1, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, mfmException);
        }
    }

    public boolean confirmExistMfs(PackageManager packageManager) {
        int applicationEnabledSetting;
        try {
            applicationEnabledSetting = packageManager.getApplicationEnabledSetting((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_MFS));
        } catch (IllegalArgumentException e) {
            StateController.postStateEvent(StateMachine.Event.EP_MFS_UNINSTALLED, this, e);
        }
        if (applicationEnabledSetting != 1 && applicationEnabledSetting != 0) {
            StateController.postStateEvent(StateMachine.Event.EP_MFS_DISABLED, this, new MfmException(getClass(), 2, "MFS_DISABLED"), 5);
            return false;
        }
        return true;
    }

    public boolean confirmSignMfs(PackageManager packageManager) {
        PackageInfo packageInfo;
        Signature[] apkContentsSigners;
        byte[] bArrHexStringToBin;
        MessageDigest messageDigest;
        Iterator it;
        try {
            String str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_MFS);
            ArrayList arrayList = new ArrayList();
            if (Build.VERSION.SDK_INT >= 28) {
                packageInfo = packageManager.getPackageInfo(str, 134217728);
            } else {
                packageInfo = packageManager.getPackageInfo(str, 64);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                if (!packageInfo.signingInfo.hasMultipleSigners()) {
                    apkContentsSigners = packageInfo.signingInfo.getSigningCertificateHistory();
                } else {
                    apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
                }
            } else {
                apkContentsSigners = packageInfo.signatures;
            }
            if (apkContentsSigners != null) {
                for (Signature signature : apkContentsSigners) {
                    arrayList.add(signature.toByteArray());
                }
            }
            bArrHexStringToBin = CommonUtil.hexStringToBin(SpecAppSignatures.findSignatures(str).get(0).toUpperCase(Locale.US));
            messageDigest = MessageDigest.getInstance("SHA-256");
            it = arrayList.iterator();
        } catch (PackageManager.NameNotFoundException e) {
            StateController.postStateEvent(StateMachine.Event.EP_MFS_UNINSTALLED, this, e);
        } catch (NoSuchAlgorithmException unused) {
            StateController.postStateEvent(StateMachine.Event.EP_MFS_SIGNATURE_UNMATCHED, this, new MfmException(getClass(), 3, "MFS_SIGNATURE_UNMATCHED"), 6);
            return false;
        }
        while (it.hasNext()) {
            messageDigest.update((byte[]) it.next());
            if (!Arrays.equals(bArrHexStringToBin, messageDigest.digest())) {
                StateController.postStateEvent(StateMachine.Event.EP_MFS_SIGNATURE_UNMATCHED, this, new MfmException(getClass(), 3, "MFS_SIGNATURE_UNMATCHED"), 6);
                return false;
            }
            return true;
        }
        return true;
    }
}
