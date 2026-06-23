package com.amazonaws.mobileconnectors.pinpoint.internal.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.SessionClient;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.configuration.AndroidPreferencesConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.http.SDKInfoHandler;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.idresolver.SharedPrefsUniqueIdService;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidSystem;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.SDKInfo;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient;
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClient;
import com.amazonaws.services.pinpointanalytics.AmazonPinpointAnalyticsClient;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PinpointContext implements Serializable {
    private AnalyticsClient analyticsClient;
    private final AmazonPinpointAnalyticsClient analyticsServiceClient;
    private final Context applicationContext;
    private final AndroidPreferencesConfiguration configuration;
    private NotificationClient notificationClient;
    private final PinpointConfiguration pinpointConfiguration;
    private final AmazonPinpointClient pinpointServiceClient;
    private final SDKInfo sdkInfo;
    private SessionClient sessionClient;
    private final AndroidSystem system;
    private TargetingClient targetingClient;
    private String uniqueId;
    private final SharedPrefsUniqueIdService uniqueIdService;

    public PinpointContext() {
        this.configuration = null;
        this.pinpointConfiguration = null;
        this.sdkInfo = null;
        this.uniqueIdService = null;
        this.system = null;
        this.analyticsServiceClient = null;
        this.pinpointServiceClient = null;
        this.applicationContext = null;
        this.analyticsClient = null;
        this.targetingClient = null;
        this.sessionClient = null;
        this.notificationClient = null;
    }

    public PinpointContext(AmazonPinpointAnalyticsClient amazonPinpointAnalyticsClient, AmazonPinpointClient amazonPinpointClient, Context context, String str, SDKInfo sDKInfo, PinpointConfiguration pinpointConfiguration) {
        this.sdkInfo = sDKInfo;
        this.pinpointConfiguration = pinpointConfiguration;
        this.system = new AndroidSystem(context, str);
        SharedPrefsUniqueIdService sharedPrefsUniqueIdService = new SharedPrefsUniqueIdService(str, context);
        this.uniqueIdService = sharedPrefsUniqueIdService;
        this.uniqueId = sharedPrefsUniqueIdService.getUniqueId(this);
        this.analyticsServiceClient = amazonPinpointAnalyticsClient;
        this.pinpointServiceClient = amazonPinpointClient;
        this.applicationContext = context;
        this.configuration = AndroidPreferencesConfiguration.newInstance(this);
        amazonPinpointAnalyticsClient.addRequestHandler(new SDKInfoHandler(sDKInfo));
        amazonPinpointClient.addRequestHandler(new SDKInfoHandler(sDKInfo));
    }

    public NotificationClient getNotificationClient() {
        return this.notificationClient;
    }

    public void setNotificationClient(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public AnalyticsClient getAnalyticsClient() {
        return this.analyticsClient;
    }

    public void setAnalyticsClient(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public SessionClient getSessionClient() {
        return this.sessionClient;
    }

    public void setSessionClient(SessionClient sessionClient) {
        this.sessionClient = sessionClient;
    }

    public TargetingClient getTargetingClient() {
        return this.targetingClient;
    }

    public void setTargetingClient(TargetingClient targetingClient) {
        this.targetingClient = targetingClient;
    }

    public AndroidPreferencesConfiguration getConfiguration() {
        return this.configuration;
    }

    public PinpointConfiguration getPinpointConfiguration() {
        return this.pinpointConfiguration;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public SDKInfo getSDKInfo() {
        return this.sdkInfo;
    }

    public AndroidSystem getSystem() {
        return this.system;
    }

    public AmazonPinpointAnalyticsClient getAnalyticsServiceClient() {
        return this.analyticsServiceClient;
    }

    public AmazonPinpoint getPinpointServiceClient() {
        return this.pinpointServiceClient;
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public String getNetworkType() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.applicationContext.getSystemService("connectivity")).getActiveNetworkInfo();
            return (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || !activeNetworkInfo.isAvailable() || activeNetworkInfo.getTypeName() == null) ? "Unknown" : activeNetworkInfo.getTypeName();
        } catch (Exception unused) {
            return "Unknown";
        }
    }
}
