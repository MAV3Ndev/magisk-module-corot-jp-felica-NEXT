package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.GCMMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class GCMMessageJsonMarshaller {
    private static GCMMessageJsonMarshaller instance;

    GCMMessageJsonMarshaller() {
    }

    public void marshall(GCMMessage gCMMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (gCMMessage.getAction() != null) {
            String action = gCMMessage.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (gCMMessage.getBody() != null) {
            String body = gCMMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (gCMMessage.getCollapseKey() != null) {
            String collapseKey = gCMMessage.getCollapseKey();
            awsJsonWriter.name("CollapseKey");
            awsJsonWriter.value(collapseKey);
        }
        if (gCMMessage.getData() != null) {
            Map<String, String> data = gCMMessage.getData();
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
        if (gCMMessage.getIconReference() != null) {
            String iconReference = gCMMessage.getIconReference();
            awsJsonWriter.name("IconReference");
            awsJsonWriter.value(iconReference);
        }
        if (gCMMessage.getImageIconUrl() != null) {
            String imageIconUrl = gCMMessage.getImageIconUrl();
            awsJsonWriter.name("ImageIconUrl");
            awsJsonWriter.value(imageIconUrl);
        }
        if (gCMMessage.getImageUrl() != null) {
            String imageUrl = gCMMessage.getImageUrl();
            awsJsonWriter.name("ImageUrl");
            awsJsonWriter.value(imageUrl);
        }
        if (gCMMessage.getPriority() != null) {
            String priority = gCMMessage.getPriority();
            awsJsonWriter.name("Priority");
            awsJsonWriter.value(priority);
        }
        if (gCMMessage.getRawContent() != null) {
            String rawContent = gCMMessage.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (gCMMessage.getRestrictedPackageName() != null) {
            String restrictedPackageName = gCMMessage.getRestrictedPackageName();
            awsJsonWriter.name("RestrictedPackageName");
            awsJsonWriter.value(restrictedPackageName);
        }
        if (gCMMessage.getSilentPush() != null) {
            Boolean silentPush = gCMMessage.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (gCMMessage.getSmallImageIconUrl() != null) {
            String smallImageIconUrl = gCMMessage.getSmallImageIconUrl();
            awsJsonWriter.name("SmallImageIconUrl");
            awsJsonWriter.value(smallImageIconUrl);
        }
        if (gCMMessage.getSound() != null) {
            String sound = gCMMessage.getSound();
            awsJsonWriter.name("Sound");
            awsJsonWriter.value(sound);
        }
        if (gCMMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = gCMMessage.getSubstitutions();
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
        if (gCMMessage.getTimeToLive() != null) {
            Integer timeToLive = gCMMessage.getTimeToLive();
            awsJsonWriter.name("TimeToLive");
            awsJsonWriter.value(timeToLive);
        }
        if (gCMMessage.getTitle() != null) {
            String title = gCMMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (gCMMessage.getUrl() != null) {
            String url = gCMMessage.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static GCMMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GCMMessageJsonMarshaller();
        }
        return instance;
    }
}
