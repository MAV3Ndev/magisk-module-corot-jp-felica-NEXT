package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SMSChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SMSChannelRequestJsonMarshaller {
    private static SMSChannelRequestJsonMarshaller instance;

    SMSChannelRequestJsonMarshaller() {
    }

    public void marshall(SMSChannelRequest sMSChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (sMSChannelRequest.getEnabled() != null) {
            Boolean enabled = sMSChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (sMSChannelRequest.getSenderId() != null) {
            String senderId = sMSChannelRequest.getSenderId();
            awsJsonWriter.name("SenderId");
            awsJsonWriter.value(senderId);
        }
        if (sMSChannelRequest.getShortCode() != null) {
            String shortCode = sMSChannelRequest.getShortCode();
            awsJsonWriter.name("ShortCode");
            awsJsonWriter.value(shortCode);
        }
        awsJsonWriter.endObject();
    }

    public static SMSChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SMSChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
