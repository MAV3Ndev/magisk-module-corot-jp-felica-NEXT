package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.APNSMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class APNSMessageJsonMarshaller {
    private static APNSMessageJsonMarshaller instance;

    APNSMessageJsonMarshaller() {
    }

    public void marshall(APNSMessage aPNSMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSMessage.getAction() != null) {
            String action = aPNSMessage.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (aPNSMessage.getBadge() != null) {
            Integer badge = aPNSMessage.getBadge();
            awsJsonWriter.name("Badge");
            awsJsonWriter.value(badge);
        }
        if (aPNSMessage.getBody() != null) {
            String body = aPNSMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (aPNSMessage.getCategory() != null) {
            String category = aPNSMessage.getCategory();
            awsJsonWriter.name("Category");
            awsJsonWriter.value(category);
        }
        if (aPNSMessage.getCollapseId() != null) {
            String collapseId = aPNSMessage.getCollapseId();
            awsJsonWriter.name("CollapseId");
            awsJsonWriter.value(collapseId);
        }
        if (aPNSMessage.getData() != null) {
            Map<String, String> data = aPNSMessage.getData();
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
        if (aPNSMessage.getMediaUrl() != null) {
            String mediaUrl = aPNSMessage.getMediaUrl();
            awsJsonWriter.name("MediaUrl");
            awsJsonWriter.value(mediaUrl);
        }
        if (aPNSMessage.getPreferredAuthenticationMethod() != null) {
            String preferredAuthenticationMethod = aPNSMessage.getPreferredAuthenticationMethod();
            awsJsonWriter.name("PreferredAuthenticationMethod");
            awsJsonWriter.value(preferredAuthenticationMethod);
        }
        if (aPNSMessage.getPriority() != null) {
            String priority = aPNSMessage.getPriority();
            awsJsonWriter.name("Priority");
            awsJsonWriter.value(priority);
        }
        if (aPNSMessage.getRawContent() != null) {
            String rawContent = aPNSMessage.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (aPNSMessage.getSilentPush() != null) {
            Boolean silentPush = aPNSMessage.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (aPNSMessage.getSound() != null) {
            String sound = aPNSMessage.getSound();
            awsJsonWriter.name("Sound");
            awsJsonWriter.value(sound);
        }
        if (aPNSMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = aPNSMessage.getSubstitutions();
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
        if (aPNSMessage.getThreadId() != null) {
            String threadId = aPNSMessage.getThreadId();
            awsJsonWriter.name("ThreadId");
            awsJsonWriter.value(threadId);
        }
        if (aPNSMessage.getTimeToLive() != null) {
            Integer timeToLive = aPNSMessage.getTimeToLive();
            awsJsonWriter.name("TimeToLive");
            awsJsonWriter.value(timeToLive);
        }
        if (aPNSMessage.getTitle() != null) {
            String title = aPNSMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (aPNSMessage.getUrl() != null) {
            String url = aPNSMessage.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static APNSMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSMessageJsonMarshaller();
        }
        return instance;
    }
}
