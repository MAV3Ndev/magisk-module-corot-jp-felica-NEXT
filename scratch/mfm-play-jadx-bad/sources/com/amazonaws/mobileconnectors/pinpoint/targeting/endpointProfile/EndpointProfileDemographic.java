package com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile;

import android.os.Build;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EndpointProfileDemographic implements JSONSerializable {
    public static final String ENDPOINT_PLATFORM = "ANDROID";
    private String appVersion;
    private Locale locale;
    private String make;
    private String model = Build.MODEL;
    private String timezone = TimeZone.getDefault().getID();
    private String platform = ENDPOINT_PLATFORM;
    private String platformVersion = Build.VERSION.RELEASE;

    public EndpointProfileDemographic(PinpointContext pinpointContext) {
        this.make = "";
        this.appVersion = "";
        Preconditions.checkNotNull(pinpointContext, "A valid pinpointContext must be provided");
        this.make = pinpointContext.getSystem().getDeviceDetails().manufacturer();
        this.appVersion = pinpointContext.getSystem().getAppDetails().versionName();
        this.locale = pinpointContext.getApplicationContext().getResources().getConfiguration().locale;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String str) {
        if (str == null) {
            return;
        }
        this.make = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        if (str == null) {
            return;
        }
        this.model = str;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        if (str == null) {
            return;
        }
        this.timezone = str;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        if (locale == null) {
            return;
        }
        this.locale = locale;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String str) {
        if (str == null) {
            return;
        }
        this.appVersion = str;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        if (str == null) {
            return;
        }
        this.platform = str;
    }

    public String getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(String str) {
        if (str == null) {
            return;
        }
        this.platformVersion = str;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        JSONBuilder jSONBuilder = new JSONBuilder(null);
        jSONBuilder.withAttribute("Make", getMake());
        jSONBuilder.withAttribute("Model", getModel());
        jSONBuilder.withAttribute("Timezone", getTimezone());
        jSONBuilder.withAttribute("Locale", getLocale());
        jSONBuilder.withAttribute("AppVersion", getAppVersion());
        jSONBuilder.withAttribute(DataRecordKey.PLATFORM, getPlatform());
        jSONBuilder.withAttribute("PlatformVersion", getPlatformVersion());
        return jSONBuilder.toJSONObject();
    }
}
