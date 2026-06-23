package com.amazonaws.mobileconnectors.pinpoint.internal.core.system;

import android.os.Build;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileDemographic;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class AndroidDeviceDetails {
    private final String carrier;

    public String platform() {
        return EndpointProfileDemographic.ENDPOINT_PLATFORM;
    }

    public AndroidDeviceDetails() {
        this.carrier = null;
    }

    public AndroidDeviceDetails(String str) {
        this.carrier = str;
    }

    public String carrier() {
        return this.carrier;
    }

    public String platformVersion() {
        return Build.VERSION.RELEASE;
    }

    public String manufacturer() {
        return Build.MANUFACTURER;
    }

    public String model() {
        return Build.MODEL;
    }

    public Locale locale() {
        return Locale.getDefault();
    }
}
