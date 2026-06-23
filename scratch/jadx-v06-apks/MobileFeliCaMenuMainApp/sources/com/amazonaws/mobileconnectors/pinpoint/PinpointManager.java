package com.amazonaws.mobileconnectors.pinpoint;

import android.content.Context;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.SessionClient;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.SDKInfo;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.amazonaws.mobileconnectors.pinpoint.internal.validate.EncodingValidator;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.DeviceTokenRegisteredHandler;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.PinpointNotificationReceiver;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.pinpoint.AmazonPinpointClient;
import com.amazonaws.services.pinpoint.model.ChannelType;
import com.amazonaws.services.pinpointanalytics.AmazonPinpointAnalyticsClient;
import com.amazonaws.util.VersionInfoUtils;

/* JADX INFO: loaded from: classes.dex */
public class PinpointManager {
    private static final EncodingValidator ENCODING_VALIDATOR;
    private static final SDKInfo SDK_INFO;
    private static final String SDK_NAME = "aws-sdk-android";
    private static final String SDK_VERSION;
    private static final Log log;
    private final AnalyticsClient analyticsClient;
    private final NotificationClient notificationClient;
    private final PinpointContext pinpointContext;
    private final SessionClient sessionClient;
    private final TargetingClient targetingClient;

    static {
        String version = VersionInfoUtils.getVersion();
        SDK_VERSION = version;
        SDK_INFO = new SDKInfo(SDK_NAME, version);
        log = LogFactory.getLog((Class<?>) PinpointManager.class);
        ENCODING_VALIDATOR = new EncodingValidator(StringUtil.UTF_8);
    }

    public PinpointManager(PinpointConfiguration pinpointConfiguration) {
        try {
            Preconditions.checkNotNull(pinpointConfiguration, "The config provided must not be null");
            AWSCredentialsProvider credentialsProvider = pinpointConfiguration.getCredentialsProvider();
            Context appContext = pinpointConfiguration.getAppContext();
            String appId = pinpointConfiguration.getAppId();
            Regions region = pinpointConfiguration.getRegion();
            ChannelType channelType = pinpointConfiguration.getChannelType();
            PinpointCallback<PinpointManager> initCompletionCallback = pinpointConfiguration.getInitCompletionCallback();
            Preconditions.checkNotNull(credentialsProvider, "The credentialsProvider provided must not be null");
            Preconditions.checkNotNull(appContext, "The application pinpointContext provided must not be null");
            Preconditions.checkNotNull(appId, "The app ID specified must not be null");
            AmazonPinpointAnalyticsClient amazonPinpointAnalyticsClient = new AmazonPinpointAnalyticsClient(credentialsProvider, pinpointConfiguration.getClientConfiguration());
            AmazonPinpointClient amazonPinpointClient = new AmazonPinpointClient(credentialsProvider, pinpointConfiguration.getClientConfiguration());
            ENCODING_VALIDATOR.validate();
            PinpointContext pinpointContext = new PinpointContext(amazonPinpointAnalyticsClient, amazonPinpointClient, appContext, appId, SDK_INFO, pinpointConfiguration);
            this.pinpointContext = pinpointContext;
            NotificationClient notificationClientCreateClient = NotificationClient.createClient(pinpointContext, channelType);
            this.notificationClient = notificationClientCreateClient;
            this.pinpointContext.setNotificationClient(notificationClientCreateClient);
            PinpointNotificationReceiver.setNotificationClient(this.notificationClient);
            if (pinpointConfiguration.getEnableEvents()) {
                AnalyticsClient analyticsClient = new AnalyticsClient(this.pinpointContext);
                this.analyticsClient = analyticsClient;
                this.pinpointContext.setAnalyticsClient(analyticsClient);
                SessionClient sessionClient = new SessionClient(this.pinpointContext);
                this.sessionClient = sessionClient;
                this.pinpointContext.setSessionClient(sessionClient);
            } else {
                this.analyticsClient = null;
                this.sessionClient = null;
            }
            if (pinpointConfiguration.getEnableTargeting()) {
                if (pinpointConfiguration.getExecutor() != null) {
                    this.targetingClient = new TargetingClient(this.pinpointContext, pinpointConfiguration.getExecutor());
                } else {
                    this.targetingClient = new TargetingClient(this.pinpointContext);
                }
                this.pinpointContext.setTargetingClient(this.targetingClient);
                this.notificationClient.addDeviceTokenRegisteredHandler(new DeviceTokenRegisteredHandler() { // from class: com.amazonaws.mobileconnectors.pinpoint.PinpointManager.1
                    @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.DeviceTokenRegisteredHandler
                    public void tokenRegistered(String str) {
                        PinpointManager.this.targetingClient.updateEndpointProfile();
                    }
                });
            } else {
                this.targetingClient = null;
            }
            if (initCompletionCallback != null) {
                initCompletionCallback.onComplete(this);
            }
            if (region != null && !"us-east-1".equals(region.getName())) {
                this.pinpointContext.getPinpointServiceClient().setEndpoint(String.format("pinpoint.%s.amazonaws.com", region.getName()));
            }
            log.debug(String.format("Pinpoint SDK(%s) initialization successfully completed", SDK_VERSION));
        } catch (RuntimeException e) {
            log.debug("Cannot initialize Pinpoint SDK", e);
            throw new AmazonClientException(e.getLocalizedMessage());
        }
    }

    public PinpointContext getPinpointContext() {
        return this.pinpointContext;
    }

    public AnalyticsClient getAnalyticsClient() {
        return this.analyticsClient;
    }

    public TargetingClient getTargetingClient() {
        return this.targetingClient;
    }

    public SessionClient getSessionClient() {
        return this.sessionClient;
    }

    public NotificationClient getNotificationClient() {
        return this.notificationClient;
    }
}
