package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.Message;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class MessageJsonMarshaller {
    private static MessageJsonMarshaller instance;

    MessageJsonMarshaller() {
    }

    public void marshall(Message message, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (message.getAction() != null) {
            String action = message.getAction();
            awsJsonWriter.name(JsonDocumentFields.ACTION);
            awsJsonWriter.value(action);
        }
        if (message.getBody() != null) {
            String body = message.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (message.getImageIconUrl() != null) {
            String imageIconUrl = message.getImageIconUrl();
            awsJsonWriter.name("ImageIconUrl");
            awsJsonWriter.value(imageIconUrl);
        }
        if (message.getImageSmallIconUrl() != null) {
            String imageSmallIconUrl = message.getImageSmallIconUrl();
            awsJsonWriter.name("ImageSmallIconUrl");
            awsJsonWriter.value(imageSmallIconUrl);
        }
        if (message.getImageUrl() != null) {
            String imageUrl = message.getImageUrl();
            awsJsonWriter.name("ImageUrl");
            awsJsonWriter.value(imageUrl);
        }
        if (message.getJsonBody() != null) {
            String jsonBody = message.getJsonBody();
            awsJsonWriter.name("JsonBody");
            awsJsonWriter.value(jsonBody);
        }
        if (message.getMediaUrl() != null) {
            String mediaUrl = message.getMediaUrl();
            awsJsonWriter.name("MediaUrl");
            awsJsonWriter.value(mediaUrl);
        }
        if (message.getRawContent() != null) {
            String rawContent = message.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (message.getSilentPush() != null) {
            Boolean silentPush = message.getSilentPush();
            awsJsonWriter.name("SilentPush");
            awsJsonWriter.value(silentPush.booleanValue());
        }
        if (message.getTimeToLive() != null) {
            Integer timeToLive = message.getTimeToLive();
            awsJsonWriter.name("TimeToLive");
            awsJsonWriter.value(timeToLive);
        }
        if (message.getTitle() != null) {
            String title = message.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        if (message.getUrl() != null) {
            String url = message.getUrl();
            awsJsonWriter.name("Url");
            awsJsonWriter.value(url);
        }
        awsJsonWriter.endObject();
    }

    public static MessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageJsonMarshaller();
        }
        return instance;
    }
}
