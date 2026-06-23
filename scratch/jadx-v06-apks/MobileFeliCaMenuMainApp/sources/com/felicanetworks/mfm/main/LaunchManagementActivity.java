package com.felicanetworks.mfm.main;

import android.content.Intent;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class LaunchManagementActivity extends BaseActivity {
    private static final int CARD_ID_LENGTH = 63;
    private static final String EXTRA_KEY_CARD_ID = "cid";
    private static final String EXTRA_KEY_DIRECT_TYPE = "com.felicanetworks.mfm.main.DIRECT_TYPE";
    private static final String EXTRA_KEY_SERVICE_ID = "serviceId";
    private static final int SERVICE_ID_LENGTH = 8;

    public enum DirectType {
        CARD_DETAIL,
        UNKNOWN
    }

    public static boolean isDirectLaunch(Intent intent) {
        return intent.hasExtra(EXTRA_KEY_DIRECT_TYPE);
    }

    public static DirectType findDirectType(Intent intent) {
        Serializable serializableExtra = intent.getSerializableExtra(EXTRA_KEY_DIRECT_TYPE);
        if (!(serializableExtra instanceof DirectType)) {
            return DirectType.UNKNOWN;
        }
        return (DirectType) serializableExtra;
    }

    public static String findServiceId(Intent intent) {
        return intent.getStringExtra(EXTRA_KEY_SERVICE_ID);
    }

    public static String findCardId(Intent intent) {
        return intent.getStringExtra(EXTRA_KEY_CARD_ID);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x007c A[Catch: all -> 0x0080, Exception -> 0x0088, TRY_LEAVE, TryCatch #3 {Exception -> 0x0088, all -> 0x0080, blocks: (B:2:0x0000, B:6:0x000d, B:8:0x0013, B:10:0x0029, B:12:0x0046, B:14:0x004c, B:16:0x0054, B:18:0x005d, B:20:0x0065, B:21:0x006c, B:22:0x0070, B:23:0x0074, B:25:0x0078, B:26:0x007c), top: B:38:0x0000, inners: #2 }] */
    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onCreate(android.os.Bundle r6) {
        /*
            r5 = this;
            android.content.Intent r0 = r5.getIntent()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r0 != 0) goto Ld
            r5.finish()
            super.onCreate(r6)
            return
        Ld:
            android.net.Uri r0 = r0.getData()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r0 == 0) goto L88
            java.lang.String r1 = r0.getPath()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            java.lang.String r2 = "serviceId"
            java.lang.String r2 = r0.getQueryParameter(r2)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            java.lang.String r3 = "cid"
            java.lang.String r0 = r0.getQueryParameter(r3)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r3 != 0) goto L7c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            r3.<init>()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            java.lang.String r4 = "/"
            r3.append(r4)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            java.lang.Class<com.felicanetworks.mfm.main.view.activity.CardDetailActivity> r4 = com.felicanetworks.mfm.main.view.activity.CardDetailActivity.class
            java.lang.String r4 = r4.getSimpleName()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            r3.append(r4)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            boolean r1 = r1.equals(r3)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r1 == 0) goto L7c
            boolean r1 = android.text.TextUtils.isEmpty(r2)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r1 != 0) goto L74
            int r1 = r2.length()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            r3 = 8
            if (r1 != r3) goto L74
            com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r2)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            if (r1 != 0) goto L70
            int r1 = r0.length()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            r3 = 63
            if (r1 != r3) goto L6c
            com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r0)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            r5.directScreenTransitionCardDetail(r2, r0)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L6c:
            r5.screenTransitionServiceList()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L70:
            r5.directScreenTransitionCardDetail(r2, r0)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L74:
            r5.screenTransitionServiceList()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException -> L78 java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L78:
            r5.screenTransitionServiceList()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L7c:
            r5.screenTransitionServiceList()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L88
            goto L88
        L80:
            r0 = move-exception
            r5.finish()
            super.onCreate(r6)
            throw r0
        L88:
            r5.finish()
            super.onCreate(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.LaunchManagementActivity.onCreate(android.os.Bundle):void");
    }

    private void directScreenTransitionCardDetail(String str, String str2) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_DIRECT_TYPE, DirectType.CARD_DETAIL);
        intent.setClass(this, ServiceListActivity.class);
        intent.putExtra(EXTRA_KEY_SERVICE_ID, str);
        intent.putExtra(EXTRA_KEY_CARD_ID, str2);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    private void screenTransitionServiceList() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_DIRECT_TYPE, DirectType.UNKNOWN);
        intent.setClass(this, ServiceListActivity.class);
        intent.addCategory("android.intent.category.INFO");
        intent.setFlags(268468224);
        startActivity(intent);
    }
}
