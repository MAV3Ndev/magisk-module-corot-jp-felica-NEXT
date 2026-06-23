package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.Service;
import android.os.Bundle;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.services.pinpoint.model.ChannelType;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NotificationClient {
    public static final String ADM_INTENT_ACTION = "com.amazon.device.messaging.intent.RECEIVE";
    public static final String BAIDU_INTENT_ACTION = "com.amazonaws.intent.baidu.NOTIFICATION_OPEN";
    protected static final String CAMPAIGN_ACTIVITY_ID_ATTRIBUTE_KEY = "campaign_activity_id";
    protected static final String CAMPAIGN_ACTIVITY_ID_PUSH_KEY = "pinpoint.campaign.campaign_activity_id";
    protected static final String CAMPAIGN_ID_ATTRIBUTE_KEY = "campaign_id";
    protected static final String CAMPAIGN_ID_PUSH_KEY = "pinpoint.campaign.campaign_id";
    protected static final String CAMPAIGN_PUSH_KEY_PREFIX = "pinpoint.campaign.";
    protected static final String CAMPAIGN_TREATMENT_ID_ATTRIBUTE_KEY = "treatment_id";
    protected static final String CAMPAIGN_TREATMENT_ID_PUSH_KEY = "pinpoint.campaign.treatment_id";
    public static final String FCM_INTENT_ACTION = "com.amazonaws.intent.fcm.NOTIFICATION_OPEN";
    public static final String GCM_INTENT_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";
    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
    protected static final String PINPOINT_PUSH_KEY_PREFIX = "pinpoint.";
    NotificationClientBase notificationClientBase;

    @Deprecated
    public enum CampaignPushResult {
        NOT_HANDLED,
        POSTED_NOTIFICATION,
        APP_IN_FOREGROUND,
        OPTED_OUT,
        NOTIFICATION_OPENED,
        SILENT
    }

    public enum PushResult {
        NOT_HANDLED,
        POSTED_NOTIFICATION,
        APP_IN_FOREGROUND,
        OPTED_OUT,
        NOTIFICATION_OPENED,
        SILENT
    }

    public static NotificationClient createClient(PinpointContext pinpointContext, ChannelType channelType) {
        return new NotificationClient(NotificationClientBase.createClient(pinpointContext, channelType));
    }

    @Deprecated
    public NotificationClient(PinpointContext pinpointContext) {
        this.notificationClientBase = NotificationClientBase.createClient(pinpointContext, ChannelType.GCM);
    }

    NotificationClient(NotificationClientBase notificationClientBase) {
        this.notificationClientBase = notificationClientBase;
    }

    @Deprecated
    public void addGCMTokenRegisteredHandler(GCMTokenRegisteredHandler gCMTokenRegisteredHandler) {
        if (gCMTokenRegisteredHandler == null) {
            throw new IllegalArgumentException("GCMTokenRegisteredHandler cannot be null.");
        }
        this.notificationClientBase.addDeviceTokenRegisteredHandler(gCMTokenRegisteredHandler);
    }

    @Deprecated
    public void removeGCMTokenRegisteredHandler(GCMTokenRegisteredHandler gCMTokenRegisteredHandler) {
        this.notificationClientBase.removeDeviceTokenRegisteredHandler(gCMTokenRegisteredHandler);
    }

    @Deprecated
    public void registerGCMDeviceToken(String str) {
        this.notificationClientBase.registerDeviceToken(str);
    }

    @Deprecated
    public String getGCMDeviceToken() {
        return this.notificationClientBase.getDeviceToken();
    }

    @Deprecated
    public CampaignPushResult handleFCMCampaignPush(String str, Map<String, String> map) {
        return handleCampaignPush(NotificationDetails.builder().from(str).mapData(map).intentAction(FCM_INTENT_ACTION).build());
    }

    @Deprecated
    public CampaignPushResult handleGCMCampaignPush(String str, Bundle bundle, Class<? extends Service> cls) {
        return handleCampaignPush(NotificationDetails.builder().from(str).bundle(bundle).serviceClass(cls).intentAction(GCM_INTENT_ACTION).build());
    }

    public boolean areAppNotificationsEnabled() {
        return this.notificationClientBase.areAppNotificationsEnabled();
    }

    public final void addDeviceTokenRegisteredHandler(DeviceTokenRegisteredHandler deviceTokenRegisteredHandler) {
        this.notificationClientBase.addDeviceTokenRegisteredHandler(deviceTokenRegisteredHandler);
    }

    public final void removeDeviceTokenRegisteredHandler(DeviceTokenRegisteredHandler deviceTokenRegisteredHandler) {
        this.notificationClientBase.removeDeviceTokenRegisteredHandler(deviceTokenRegisteredHandler);
    }

    public final void registerDeviceToken(String str) {
        this.notificationClientBase.registerDeviceToken(str);
    }

    public final void registerDeviceToken(String str, String str2) {
        this.notificationClientBase.registerDeviceToken(str, str2);
    }

    public final String getDeviceToken() {
        return this.notificationClientBase.getDeviceToken();
    }

    public String getChannelType() {
        return this.notificationClientBase.getChannelType();
    }

    public PushResult handleNotificationReceived(NotificationDetails notificationDetails) {
        return this.notificationClientBase.handleNotificationReceived(notificationDetails);
    }

    @Deprecated
    public CampaignPushResult handleCampaignPush(NotificationDetails notificationDetails) {
        return CampaignPushResult.valueOf(this.notificationClientBase.handleNotificationReceived(notificationDetails).toString());
    }

    PushResult handleNotificationOpen(Map<String, String> map, Bundle bundle) {
        return this.notificationClientBase.handleNotificationOpen(map, bundle);
    }

    int getNotificationRequestId(String str, String str2) {
        return this.notificationClientBase.getNotificationRequestId(str, str2);
    }
}
