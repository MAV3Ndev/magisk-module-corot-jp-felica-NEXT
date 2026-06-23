package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.DefaultPushNotificationMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class DefaultPushNotificationMessageJsonMarshaller {
    private static DefaultPushNotificationMessageJsonMarshaller instance;

    DefaultPushNotificationMessageJsonMarshaller() {
    }

    public void marshall(DefaultPushNotificationMessage defaultPushNotificationMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (defaultPushNotificationMessage.getAction() != null) {
            String action = defaultPushNotificationMessage.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (defaultPushNotificationMessage.getBody() != null) {
            String body = defaultPushNotificationMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (defaultPushNotificationMessage.getData() != null) {
            Map<String, String> data = defaultPushNotificationMessage.getData();
            awsJsonWriter.name("Data");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.value(value);
                }
            }
            awsJsonWriter.endObject();
        }
        if (defaultPushNotificationMessage.getSilentPush() != null) {
            Boolean silentPush = defaultPushNotificationMessage.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (defaultPushNotificationMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = defaultPushNotificationMessage.getSubstitutions();
            awsJsonWriter.name("Substitutions");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry2 : substitutions.entrySet()) {
                List<String> value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.beginArray();
                    for (String str : value2) {
                        if (str != null) {
                            awsJsonWriter.value(str);
                        }
                    }
                    awsJsonWriter.endArray();
                }
            }
            awsJsonWriter.endObject();
        }
        if (defaultPushNotificationMessage.getTitle() != null) {
            String title = defaultPushNotificationMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (defaultPushNotificationMessage.getUrl() != null) {
            String url = defaultPushNotificationMessage.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static DefaultPushNotificationMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new DefaultPushNotificationMessageJsonMarshaller();
        }
        return instance;
    }
}
