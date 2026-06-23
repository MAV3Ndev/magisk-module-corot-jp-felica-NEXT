package com.felicanetworks.semc.permit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.sws.json.SpAppInfoJsonArray;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class SignerHashChecker {
    private static final int MAX_SIGNER_SIZE = 100;

    public static SignerHashCheckResult checkSignerHash(Context context, String str) throws JSONException {
        LogMgr.log(6, "000 packageName=[" + str + "]");
        SignerHashCheckResult signerHashCheckResult = new SignerHashCheckResult();
        if (context == null) {
            LogMgr.log(2, "700 context= is null");
            signerHashCheckResult.mResult = false;
            signerHashCheckResult.mErrorInfo = new ErrorInfo();
            signerHashCheckResult.mErrorInfo.mErrorType = 900;
            signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint();
            return signerHashCheckResult;
        }
        try {
            ArrayList<String> callerSignerHash = getCallerSignerHash(context, str);
            if (callerSignerHash == null) {
                LogMgr.log(2, "703 callerSigInfo is null.");
                signerHashCheckResult.mResult = false;
                signerHashCheckResult.mErrorInfo = new ErrorInfo();
                signerHashCheckResult.mErrorInfo.mErrorType = 900;
                signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint();
                return signerHashCheckResult;
            }
            try {
                ArrayList<SpAppInfo> spAppInfoList = new SpAppInfoJsonArray(new SharedPrefsUtil(context).readSpAppInfoList()).getSpAppInfoList(str);
                if (spAppInfoList == null) {
                    LogMgr.log(2, "707 No app cert hash list data. Not permitted.");
                    signerHashCheckResult.mResult = false;
                    signerHashCheckResult.mErrorInfo = new ErrorInfo();
                    signerHashCheckResult.mErrorInfo.mErrorType = 100;
                    signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint();
                    return signerHashCheckResult;
                }
                try {
                    for (String str2 : callerSignerHash) {
                        for (SpAppInfo spAppInfo : spAppInfoList) {
                            if (str2.equals(spAppInfo.mSigningCertHash.toUpperCase())) {
                                LogMgr.log(8, "003 Hash comparison success.");
                                signerHashCheckResult.mResult = true;
                                signerHashCheckResult.mSpAppInfo = spAppInfo;
                                return signerHashCheckResult;
                            }
                        }
                    }
                    LogMgr.log(2, "709 Does not matched any hash data. Not permitted.");
                    signerHashCheckResult.mResult = false;
                    signerHashCheckResult.mErrorInfo = new ErrorInfo();
                    signerHashCheckResult.mErrorInfo.mErrorType = 100;
                    signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint();
                    return signerHashCheckResult;
                } catch (Exception e) {
                    LogMgr.log(2, "708 Hash comparison failed.");
                    LogMgr.printStackTrace(9, e);
                    signerHashCheckResult.mResult = false;
                    signerHashCheckResult.mErrorInfo = new ErrorInfo();
                    signerHashCheckResult.mErrorInfo.mErrorType = 900;
                    signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint(e);
                    return signerHashCheckResult;
                }
            } catch (SemClientException e2) {
                LogMgr.log(2, "704 SemClientException occurred.");
                signerHashCheckResult.mResult = false;
                signerHashCheckResult.mErrorInfo = new ErrorInfo();
                signerHashCheckResult.mErrorInfo.mErrorType = e2.getErrorCode();
                signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint(e2);
                return signerHashCheckResult;
            } catch (JSONException e3) {
                LogMgr.log(2, "705 JSONException occurred.");
                throw e3;
            } catch (Exception e4) {
                LogMgr.log(2, "706 Failed to get app cert hash.");
                signerHashCheckResult.mResult = false;
                signerHashCheckResult.mErrorInfo = new ErrorInfo();
                signerHashCheckResult.mErrorInfo.mErrorType = 900;
                signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint(e4);
                return signerHashCheckResult;
            }
        } catch (Exception e5) {
            LogMgr.log(2, "702 failed to get caller signer hash from PackageManager. msg=" + e5.getMessage());
            signerHashCheckResult.mResult = false;
            signerHashCheckResult.mErrorInfo = new ErrorInfo();
            signerHashCheckResult.mErrorInfo.mErrorType = 900;
            signerHashCheckResult.mErrorInfo.mErrorMessage = ObfuscatedMsgUtil.executionPoint(e5);
            return signerHashCheckResult;
        }
    }

    private static ArrayList<String> getCallerSignerHash(Context context, String str) throws SemClientException {
        PackageInfo packageInfo;
        Signature[] signingCertificateHistory;
        LogMgr.log(6, "000 callerPackageName=[" + str + "]");
        ArrayList<String> arrayList = new ArrayList<>();
        if (context == null) {
            LogMgr.log(2, "700 context= is null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT < 28) {
                signingCertificateHistory = packageManager.getPackageInfo(str, 64).signatures;
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    LogMgr.log(9, "001 called PackageManager.getPackageInfo for 33 and over");
                    packageInfo = packageManager.getPackageInfo(str, PackageManager.PackageInfoFlags.of(134217728L));
                } else {
                    LogMgr.log(9, "002 called PackageManager.getPackageInfo for less than 33");
                    packageInfo = packageManager.getPackageInfo(str, 134217728);
                }
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
            }
            if (signingCertificateHistory.length > 100) {
                LogMgr.log(8, "003 context= > MAX_SIGNER_SIZE.");
                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            synchronized (messageDigest) {
                for (Signature signature : signingCertificateHistory) {
                    messageDigest.update(signature.toByteArray());
                    byte[] bArrDigest = messageDigest.digest();
                    messageDigest.reset();
                    StringBuilder sb = new StringBuilder();
                    for (byte b : bArrDigest) {
                        sb.append(String.format("%02X", Byte.valueOf(b)));
                    }
                    arrayList.add(sb.toString());
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogMgr.log(8, "NameNotFoundException occurred. e=" + e.getMessage());
        } catch (NoSuchAlgorithmException e2) {
            LogMgr.log(2, "701 NoSuchAlgorithmException");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e2));
        }
        LogMgr.log(6, "999");
        return arrayList;
    }
}
