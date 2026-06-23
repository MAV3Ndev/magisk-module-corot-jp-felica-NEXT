package com.amazonaws.mobileconnectors.pinpoint;

import android.content.Context;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.AppLevelOptOutProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.pinpoint.model.ChannelType;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PinpointConfiguration {
    private String appId;
    private AppLevelOptOutProvider appLevelOptOutProvider;
    private ChannelType channelType;
    private ClientConfiguration clientConfiguration;
    private Context context;
    private AWSCredentialsProvider credentialsProvider;
    private boolean enableEvents;
    private boolean enableTargeting;
    private ExecutorService executor;
    private PinpointCallback<PinpointManager> initCompletionCallback;
    private Regions region;
    private boolean shouldPostNotificationsInForeground;

    public PinpointConfiguration(Context context, String str, Regions regions, ChannelType channelType, AWSCredentialsProvider aWSCredentialsProvider) {
        this.enableEvents = true;
        this.enableTargeting = true;
        this.shouldPostNotificationsInForeground = false;
        this.clientConfiguration = new ClientConfiguration();
        this.context = context;
        this.appId = str;
        this.credentialsProvider = aWSCredentialsProvider;
        this.region = regions;
        this.channelType = channelType;
    }

    @Deprecated
    public PinpointConfiguration(Context context, String str, Regions regions, AWSCredentialsProvider aWSCredentialsProvider) {
        this.enableEvents = true;
        this.enableTargeting = true;
        this.shouldPostNotificationsInForeground = false;
        this.clientConfiguration = new ClientConfiguration();
        this.context = context;
        this.appId = str;
        this.credentialsProvider = aWSCredentialsProvider;
        this.region = regions;
        this.channelType = ChannelType.GCM;
    }

    public PinpointConfiguration(Context context, AWSCredentialsProvider aWSCredentialsProvider, AWSConfiguration aWSConfiguration) {
        this.enableEvents = true;
        this.enableTargeting = true;
        this.shouldPostNotificationsInForeground = false;
        this.clientConfiguration = new ClientConfiguration();
        this.context = context;
        try {
            JSONObject jSONObjectOptJsonObject = aWSConfiguration.optJsonObject("PinpointAnalytics");
            this.appId = jSONObjectOptJsonObject.getString("AppId");
            this.channelType = convertToChannelType(jSONObjectOptJsonObject.optString("ChannelType"));
            this.region = Regions.fromName(jSONObjectOptJsonObject.getString("Region"));
            String userAgent = aWSConfiguration.getUserAgent();
            String userAgent2 = this.clientConfiguration.getUserAgent();
            if (userAgent2 == null) {
                userAgent2 = "";
            }
            if (userAgent != null) {
                this.clientConfiguration.setUserAgent(userAgent2.trim() + " " + userAgent);
            }
            this.credentialsProvider = aWSCredentialsProvider;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read AppId or Region from AWSConfiguration please check your setup or awsconfiguration.json file", e);
        }
    }

    private ChannelType convertToChannelType(String str) {
        if (str.isEmpty()) {
            return ChannelType.GCM;
        }
        return ChannelType.fromValue(str);
    }

    public PinpointConfiguration withClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = new ClientConfiguration(clientConfiguration);
        return this;
    }

    public ClientConfiguration getClientConfiguration() {
        return this.clientConfiguration;
    }

    public PinpointConfiguration withAllowsEventCollection(boolean z) {
        this.enableEvents = z;
        return this;
    }

    public boolean getEnableEvents() {
        return this.enableEvents;
    }

    public boolean getEnableTargeting() {
        return this.enableTargeting;
    }

    public PinpointConfiguration withEnablePinpoint(boolean z) {
        this.enableTargeting = z;
        return this;
    }

    public AWSCredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

    public PinpointConfiguration withCredentialsProvider(AWSCredentialsProvider aWSCredentialsProvider) {
        this.credentialsProvider = aWSCredentialsProvider;
        return this;
    }

    public Context getAppContext() {
        return this.context;
    }

    public PinpointConfiguration withAppContext(Context context) {
        this.context = context;
        return this;
    }

    public String getAppId() {
        return this.appId;
    }

    public PinpointConfiguration withAppId(String str) {
        this.appId = str;
        return this;
    }

    public Regions getRegion() {
        return this.region;
    }

    public PinpointConfiguration withRegion(Regions regions) {
        this.region = regions;
        return this;
    }

    public PinpointCallback<PinpointManager> getInitCompletionCallback() {
        return this.initCompletionCallback;
    }

    public PinpointConfiguration withInitCompletionCallback(PinpointCallback<PinpointManager> pinpointCallback) {
        this.initCompletionCallback = pinpointCallback;
        return this;
    }

    public AppLevelOptOutProvider getAppLevelOptOutProvider() {
        return this.appLevelOptOutProvider;
    }

    public PinpointConfiguration withAppLevelOptOutProvider(AppLevelOptOutProvider appLevelOptOutProvider) {
        this.appLevelOptOutProvider = appLevelOptOutProvider;
        return this;
    }

    public ChannelType getChannelType() {
        return this.channelType;
    }

    public PinpointConfiguration withChannelType(ChannelType channelType) {
        this.channelType = channelType;
        return this;
    }

    public PinpointConfiguration withPostNotificationsInForeground(boolean z) {
        this.shouldPostNotificationsInForeground = z;
        return this;
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    public PinpointConfiguration withExecutor(ExecutorService executorService) {
        this.executor = executorService;
        return this;
    }

    public boolean getShouldPostNotificationsInForeground() {
        return this.shouldPostNotificationsInForeground;
    }
}
