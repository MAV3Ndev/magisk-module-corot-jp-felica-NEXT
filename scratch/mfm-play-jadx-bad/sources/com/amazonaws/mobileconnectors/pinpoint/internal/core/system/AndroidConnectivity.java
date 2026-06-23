package com.amazonaws.mobileconnectors.pinpoint.internal.core.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;

/* JADX INFO: loaded from: classes.dex */
public class AndroidConnectivity {
    private static final Log log = LogFactory.getLog((Class<?>) AndroidConnectivity.class);
    private Context context;
    protected boolean hasMobile;
    protected boolean hasWifi;
    protected boolean inAirplaneMode;

    public AndroidConnectivity() {
    }

    public AndroidConnectivity(Context context) {
        this.context = context;
    }

    public boolean isConnected() {
        determineAvailability();
        return hasWifi() || hasWAN();
    }

    public boolean hasWifi() {
        return this.hasWifi;
    }

    public boolean hasWAN() {
        return this.hasMobile && !this.inAirplaneMode;
    }

    private void determineAvailability() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        this.inAirplaneMode = Settings.System.getInt(this.context.getContentResolver(), "airplane_mode_on", 0) != 0;
        Log log2 = log;
        log2.info("Airplane mode: " + this.inAirplaneMode);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        this.hasWifi = false;
        this.hasMobile = connectivityManager != null;
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnectedOrConnecting()) {
                int type = activeNetworkInfo.getType();
                this.hasWifi = type == 1 || type == 6;
                this.hasMobile = type == 0 || type == 4 || type == 5 || type == 2 || type == 3;
            } else {
                this.hasMobile = false;
            }
        }
        log2.info(String.format("Device Connectivity (%s)", this.hasWifi ? "On Wifi" : this.hasMobile ? "On Mobile" : "No network connectivity"));
    }
}
