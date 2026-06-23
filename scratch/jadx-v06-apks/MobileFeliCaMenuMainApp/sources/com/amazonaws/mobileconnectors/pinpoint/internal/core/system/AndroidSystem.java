package com.amazonaws.mobileconnectors.pinpoint.internal.core.system;

import android.content.Context;
import android.telephony.TelephonyManager;

/* JADX INFO: loaded from: classes.dex */
public class AndroidSystem {
    private static final String PREFERENCES_AND_FILE_MANAGER_SUFFIX = "515d6767-01b7-49e5-8273-c8d11b0f331d";
    private final AndroidAppDetails appDetails;
    private final AndroidConnectivity connectivity;
    private final AndroidDeviceDetails deviceDetails;
    private final AndroidPreferences preferences;

    public AndroidSystem() {
        this.preferences = null;
        this.connectivity = null;
        this.appDetails = null;
        this.deviceDetails = null;
    }

    public AndroidSystem(Context context, String str) {
        this.preferences = new AndroidPreferences(context, str + PREFERENCES_AND_FILE_MANAGER_SUFFIX);
        this.connectivity = new AndroidConnectivity(context);
        this.appDetails = new AndroidAppDetails(context, str);
        this.deviceDetails = new AndroidDeviceDetails(getCarrier(context));
    }

    private String getCarrier(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager.getNetworkOperatorName() != null && !telephonyManager.getNetworkOperatorName().equals("")) {
                return telephonyManager.getNetworkOperatorName();
            }
        } catch (Exception unused) {
        }
        return "Unknown";
    }

    public AndroidPreferences getPreferences() {
        return this.preferences;
    }

    public AndroidConnectivity getConnectivity() {
        return this.connectivity;
    }

    public AndroidAppDetails getAppDetails() {
        return this.appDetails;
    }

    public AndroidDeviceDetails getDeviceDetails() {
        return this.deviceDetails;
    }
}
