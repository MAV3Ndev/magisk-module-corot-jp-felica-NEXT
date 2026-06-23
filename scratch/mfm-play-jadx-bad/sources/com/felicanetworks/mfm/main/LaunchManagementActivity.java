package com.felicanetworks.mfm.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
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
        Serializable serializableExtra;
        if (Build.VERSION.SDK_INT >= 33) {
            serializableExtra = intent.getSerializableExtra(EXTRA_KEY_DIRECT_TYPE, Serializable.class);
        } else {
            serializableExtra = intent.getSerializableExtra(EXTRA_KEY_DIRECT_TYPE);
        }
        if (!(serializableExtra instanceof DirectType)) {
            return DirectType.UNKNOWN;
        }
        return (DirectType) serializableExtra;
    }

    public static String findServiceId(Intent intent) {
        return intent.getStringExtra("serviceId");
    }

    public static String findCardId(Intent intent) {
        return intent.getStringExtra(EXTRA_KEY_CARD_ID);
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null) {
                    String path = data.getPath();
                    String queryParameter = data.getQueryParameter("serviceId");
                    String queryParameter2 = data.getQueryParameter(EXTRA_KEY_CARD_ID);
                    if (!TextUtils.isEmpty(path) && path.equals("/CardDetailActivity")) {
                        try {
                            if (!TextUtils.isEmpty(queryParameter) && queryParameter.length() == 8) {
                                DataCheckerUtil.checkAlphaNumberFormat(queryParameter);
                                if (!TextUtils.isEmpty(queryParameter2)) {
                                    if (queryParameter2.length() == 63) {
                                        DataCheckerUtil.checkAlphaNumberFormat(queryParameter2);
                                        directScreenTransitionCardDetail(queryParameter, queryParameter2);
                                    } else {
                                        screenTransitionServiceList();
                                    }
                                } else {
                                    directScreenTransitionCardDetail(queryParameter, queryParameter2);
                                }
                            } else {
                                screenTransitionServiceList();
                            }
                        } catch (DataCheckerException unused) {
                            screenTransitionServiceList();
                        }
                    } else {
                        screenTransitionServiceList();
                    }
                }
            } else {
                finish();
                super.onCreate(savedInstanceState);
                return;
            }
        } catch (Exception unused2) {
        } catch (Throwable th) {
            finish();
            super.onCreate(savedInstanceState);
            throw th;
        }
        finish();
        super.onCreate(savedInstanceState);
    }

    private void directScreenTransitionCardDetail(String serviceID, String cid) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_DIRECT_TYPE, DirectType.CARD_DETAIL);
        intent.setClass(this, ServiceListActivity.class);
        intent.putExtra("serviceId", serviceID);
        intent.putExtra(EXTRA_KEY_CARD_ID, cid);
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
