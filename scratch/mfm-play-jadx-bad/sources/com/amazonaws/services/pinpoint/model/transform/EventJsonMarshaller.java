package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.Event;
import com.amazonaws.services.pinpoint.model.Session;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventJsonMarshaller {
    private static EventJsonMarshaller instance;

    EventJsonMarshaller() {
    }

    public void marshall(Event event, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (event.getAppPackageName() != null) {
            String appPackageName = event.getAppPackageName();
            awsJsonWriter.name("AppPackageName");
            awsJsonWriter.value(appPackageName);
        }
        if (event.getAppTitle() != null) {
            String appTitle = event.getAppTitle();
            awsJsonWriter.name("AppTitle");
            awsJsonWriter.value(appTitle);
        }
        if (event.getAppVersionCode() != null) {
            String appVersionCode = event.getAppVersionCode();
            awsJsonWriter.name("AppVersionCode");
            awsJsonWriter.value(appVersionCode);
        }
        if (event.getAttributes() != null) {
            Map<String, String> attributes = event.getAttributes();
            awsJsonWriter.name("Attributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.value(value);
                }
            }
            awsJsonWriter.endObject();
        }
        if (event.getClientSdkVersion() != null) {
            String clientSdkVersion = event.getClientSdkVersion();
            awsJsonWriter.name("ClientSdkVersion");
            awsJsonWriter.value(clientSdkVersion);
        }
        if (event.getEventType() != null) {
            String eventType = event.getEventType();
            awsJsonWriter.name("EventType");
            awsJsonWriter.value(eventType);
        }
        if (event.getMetrics() != null) {
            Map<String, Double> metrics = event.getMetrics();
            awsJsonWriter.name("Metrics");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Double> entry2 : metrics.entrySet()) {
                Double value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.value(value2);
                }
            }
            awsJsonWriter.endObject();
        }
        if (event.getSdkName() != null) {
            String sdkName = event.getSdkName();
            awsJsonWriter.name("SdkName");
            awsJsonWriter.value(sdkName);
        }
        if (event.getSession() != null) {
            Session session = event.getSession();
            awsJsonWriter.name("Session");
            SessionJsonMarshaller.getInstance().marshall(session, awsJsonWriter);
        }
        if (event.getTimestamp() != null) {
            String timestamp = event.getTimestamp();
            awsJsonWriter.name("Timestamp");
            awsJsonWriter.value(timestamp);
        }
        awsJsonWriter.endObject();
    }

    public static EventJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventJsonMarshaller();
        }
        return instance;
    }
}
