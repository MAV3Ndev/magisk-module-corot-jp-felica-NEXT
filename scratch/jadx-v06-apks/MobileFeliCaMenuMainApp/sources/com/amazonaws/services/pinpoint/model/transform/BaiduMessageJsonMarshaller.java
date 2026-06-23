package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.BaiduMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class BaiduMessageJsonMarshaller {
    private static BaiduMessageJsonMarshaller instance;

    BaiduMessageJsonMarshaller() {
    }

    public void marshall(BaiduMessage baiduMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (baiduMessage.getAction() != null) {
            String action = baiduMessage.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (baiduMessage.getBody() != null) {
            String body = baiduMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (baiduMessage.getData() != null) {
            Map<String, String> data = baiduMessage.getData();
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
        if (baiduMessage.getIconReference() != null) {
            String iconReference = baiduMessage.getIconReference();
            awsJsonWriter.name("IconReference");
            awsJsonWriter.value(iconReference);
        }
        if (baiduMessage.getImageIconUrl() != null) {
            String imageIconUrl = baiduMessage.getImageIconUrl();
            awsJsonWriter.name("ImageIconUrl");
            awsJsonWriter.value(imageIconUrl);
        }
        if (baiduMessage.getImageUrl() != null) {
            String imageUrl = baiduMessage.getImageUrl();
            awsJsonWriter.name("ImageUrl");
            awsJsonWriter.value(imageUrl);
        }
        if (baiduMessage.getRawContent() != null) {
            String rawContent = baiduMessage.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (baiduMessage.getSilentPush() != null) {
            Boolean silentPush = baiduMessage.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (baiduMessage.getSmallImageIconUrl() != null) {
            String smallImageIconUrl = baiduMessage.getSmallImageIconUrl();
            awsJsonWriter.name("SmallImageIconUrl");
            awsJsonWriter.value(smallImageIconUrl);
        }
        if (baiduMessage.getSound() != null) {
            String sound = baiduMessage.getSound();
            awsJsonWriter.name("Sound");
            awsJsonWriter.value(sound);
        }
        if (baiduMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = baiduMessage.getSubstitutions();
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
        if (baiduMessage.getTimeToLive() != null) {
            Integer timeToLive = baiduMessage.getTimeToLive();
            awsJsonWriter.name("TimeToLive");
            awsJsonWriter.value(timeToLive);
        }
        if (baiduMessage.getTitle() != null) {
            String title = baiduMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (baiduMessage.getUrl() != null) {
            String url = baiduMessage.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static BaiduMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new BaiduMessageJsonMarshaller();
        }
        return instance;
    }
}
