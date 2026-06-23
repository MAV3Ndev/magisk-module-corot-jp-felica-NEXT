package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.SMSChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SMSChannelResponseJsonMarshaller {
    private static SMSChannelResponseJsonMarshaller instance;

    SMSChannelResponseJsonMarshaller() {
    }

    public void marshall(SMSChannelResponse sMSChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (sMSChannelResponse.getApplicationId() != null) {
            String applicationId = sMSChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (sMSChannelResponse.getCreationDate() != null) {
            String creationDate = sMSChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (sMSChannelResponse.getEnabled() != null) {
            Boolean enabled = sMSChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (sMSChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = sMSChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (sMSChannelResponse.getId() != null) {
            String id = sMSChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (sMSChannelResponse.getIsArchived() != null) {
            Boolean isArchived = sMSChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (sMSChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = sMSChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (sMSChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = sMSChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (sMSChannelResponse.getPlatform() != null) {
            String platform = sMSChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (sMSChannelResponse.getPromotionalMessagesPerSecond() != null) {
            Integer promotionalMessagesPerSecond = sMSChannelResponse.getPromotionalMessagesPerSecond();
            awsJsonWriter.name("PromotionalMessagesPerSecond");
            awsJsonWriter.value(promotionalMessagesPerSecond);
        }
        if (sMSChannelResponse.getSenderId() != null) {
            String senderId = sMSChannelResponse.getSenderId();
            awsJsonWriter.name("SenderId");
            awsJsonWriter.value(senderId);
        }
        if (sMSChannelResponse.getShortCode() != null) {
            String shortCode = sMSChannelResponse.getShortCode();
            awsJsonWriter.name("ShortCode");
            awsJsonWriter.value(shortCode);
        }
        if (sMSChannelResponse.getTransactionalMessagesPerSecond() != null) {
            Integer transactionalMessagesPerSecond = sMSChannelResponse.getTransactionalMessagesPerSecond();
            awsJsonWriter.name("TransactionalMessagesPerSecond");
            awsJsonWriter.value(transactionalMessagesPerSecond);
        }
        if (sMSChannelResponse.getVersion() != null) {
            Integer version = sMSChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static SMSChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SMSChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
