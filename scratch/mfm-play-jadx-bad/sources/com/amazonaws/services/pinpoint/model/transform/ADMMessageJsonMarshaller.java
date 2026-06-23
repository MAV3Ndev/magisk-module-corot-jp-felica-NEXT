package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ADMMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ADMMessageJsonMarshaller {
    private static ADMMessageJsonMarshaller instance;

    ADMMessageJsonMarshaller() {
    }

    public void marshall(ADMMessage aDMMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aDMMessage.getAction() != null) {
            String action = aDMMessage.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (aDMMessage.getBody() != null) {
            String body = aDMMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (aDMMessage.getConsolidationKey() != null) {
            String consolidationKey = aDMMessage.getConsolidationKey();
            awsJsonWriter.name("ConsolidationKey");
            awsJsonWriter.value(consolidationKey);
        }
        if (aDMMessage.getData() != null) {
            Map<String, String> data = aDMMessage.getData();
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
        if (aDMMessage.getExpiresAfter() != null) {
            String expiresAfter = aDMMessage.getExpiresAfter();
            awsJsonWriter.name("ExpiresAfter");
            awsJsonWriter.value(expiresAfter);
        }
        if (aDMMessage.getIconReference() != null) {
            String iconReference = aDMMessage.getIconReference();
            awsJsonWriter.name("IconReference");
            awsJsonWriter.value(iconReference);
        }
        if (aDMMessage.getImageIconUrl() != null) {
            String imageIconUrl = aDMMessage.getImageIconUrl();
            awsJsonWriter.name("ImageIconUrl");
            awsJsonWriter.value(imageIconUrl);
        }
        if (aDMMessage.getImageUrl() != null) {
            String imageUrl = aDMMessage.getImageUrl();
            awsJsonWriter.name("ImageUrl");
            awsJsonWriter.value(imageUrl);
        }
        if (aDMMessage.getMD5() != null) {
            String md5 = aDMMessage.getMD5();
            awsJsonWriter.name("MD5");
            awsJsonWriter.value(md5);
        }
        if (aDMMessage.getRawContent() != null) {
            String rawContent = aDMMessage.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (aDMMessage.getSilentPush() != null) {
            Boolean silentPush = aDMMessage.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (aDMMessage.getSmallImageIconUrl() != null) {
            String smallImageIconUrl = aDMMessage.getSmallImageIconUrl();
            awsJsonWriter.name("SmallImageIconUrl");
            awsJsonWriter.value(smallImageIconUrl);
        }
        if (aDMMessage.getSound() != null) {
            String sound = aDMMessage.getSound();
            awsJsonWriter.name("Sound");
            awsJsonWriter.value(sound);
        }
        if (aDMMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = aDMMessage.getSubstitutions();
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
        if (aDMMessage.getTitle() != null) {
            String title = aDMMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (aDMMessage.getUrl() != null) {
            String url = aDMMessage.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static ADMMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ADMMessageJsonMarshaller();
        }
        return instance;
    }
}
