package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class NotificationDetails {
    private Bundle bundle;
    private String from;
    private String intentAction;
    private String notificationChannelId;
    private Class<?> targetClass;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String str) {
        this.from = str;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public void setTargetClass(Class<?> cls) {
        this.targetClass = cls;
    }

    public String getIntentAction() {
        return this.intentAction;
    }

    public void setIntentAction(String str) {
        this.intentAction = str;
    }

    public String getNotificationChannelId() {
        return this.notificationChannelId;
    }

    public void setNotificationChannelId(String str) {
        this.notificationChannelId = str;
    }

    public static NotificationDetailsBuilder builder() {
        return new NotificationDetailsBuilder();
    }

    public static class NotificationDetailsBuilder {
        private Bundle bundle;
        private String from;
        private Intent intent;
        private String intentAction;
        private Map<String, String> mapData;
        private String message;
        private String notificationChannelId;

        @Deprecated
        public NotificationDetailsBuilder serviceClass(Class<? extends Service> cls) {
            return this;
        }

        public NotificationDetailsBuilder from(String str) {
            this.from = str;
            return this;
        }

        public NotificationDetailsBuilder bundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        public NotificationDetailsBuilder intentAction(String str) {
            this.intentAction = str;
            return this;
        }

        public NotificationDetailsBuilder mapData(Map<String, String> map) {
            this.mapData = map;
            return this;
        }

        public NotificationDetailsBuilder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public NotificationDetailsBuilder message(String str) {
            this.message = str;
            return this;
        }

        public NotificationDetailsBuilder notificationChannelId(String str) {
            this.notificationChannelId = str;
            return this;
        }

        public NotificationDetails build() {
            NotificationDetails notificationDetails = new NotificationDetails();
            if (NotificationClient.GCM_INTENT_ACTION.equals(this.intentAction)) {
                notificationDetails.setFrom(this.from);
                notificationDetails.setBundle(this.bundle);
                notificationDetails.setTargetClass(PinpointNotificationActivity.class);
                notificationDetails.setIntentAction(this.intentAction);
                notificationDetails.setNotificationChannelId(this.notificationChannelId);
            }
            if (NotificationClient.FCM_INTENT_ACTION.equals(this.intentAction)) {
                if (this.mapData != null) {
                    Bundle bundle = new Bundle();
                    for (Map.Entry<String, String> entry : this.mapData.entrySet()) {
                        bundle.putString(entry.getKey(), entry.getValue());
                    }
                    notificationDetails.setBundle(bundle);
                }
                notificationDetails.setFrom(this.from);
                notificationDetails.setTargetClass(PinpointNotificationActivity.class);
                notificationDetails.setIntentAction(this.intentAction);
                notificationDetails.setNotificationChannelId(this.notificationChannelId);
            }
            if (NotificationClient.ADM_INTENT_ACTION.equals(this.intentAction)) {
                Intent intent = this.intent;
                if (intent != null) {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        notificationDetails.setFrom(extras.getString("from"));
                    }
                    notificationDetails.setBundle(extras);
                }
                notificationDetails.setTargetClass(PinpointNotificationActivity.class);
                notificationDetails.setIntentAction(this.intentAction);
            }
            if (NotificationClient.BAIDU_INTENT_ACTION.equals(this.intentAction)) {
                try {
                    if (!StringUtil.isNullOrEmpty(this.message)) {
                        JSONObject jSONObject = new JSONObject(this.message);
                        String strOptString = jSONObject.optString("from", null);
                        Bundle bundle2 = new Bundle();
                        Iterator<String> itKeys = jSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            bundle2.putString(next, jSONObject.getString(next));
                        }
                        notificationDetails.setFrom(strOptString);
                        notificationDetails.setBundle(bundle2);
                    }
                    notificationDetails.setTargetClass(PinpointNotificationActivity.class);
                    notificationDetails.setIntentAction(this.intentAction);
                    return notificationDetails;
                } catch (JSONException e) {
                    NotificationClientBase.log.error("Unable to parse JSON message: " + e, e);
                }
            }
            return notificationDetails;
        }
    }
}
