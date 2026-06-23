package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.EmailChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EmailChannelResponseJsonMarshaller {
    private static EmailChannelResponseJsonMarshaller instance;

    EmailChannelResponseJsonMarshaller() {
    }

    public void marshall(EmailChannelResponse emailChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (emailChannelResponse.getApplicationId() != null) {
            String applicationId = emailChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (emailChannelResponse.getConfigurationSet() != null) {
            String configurationSet = emailChannelResponse.getConfigurationSet();
            awsJsonWriter.name("ConfigurationSet");
            awsJsonWriter.value(configurationSet);
        }
        if (emailChannelResponse.getCreationDate() != null) {
            String creationDate = emailChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (emailChannelResponse.getEnabled() != null) {
            Boolean enabled = emailChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (emailChannelResponse.getFromAddress() != null) {
            String fromAddress = emailChannelResponse.getFromAddress();
            awsJsonWriter.name("FromAddress");
            awsJsonWriter.value(fromAddress);
        }
        if (emailChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = emailChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (emailChannelResponse.getId() != null) {
            String id = emailChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (emailChannelResponse.getIdentity() != null) {
            String identity = emailChannelResponse.getIdentity();
            awsJsonWriter.name("Identity");
            awsJsonWriter.value(identity);
        }
        if (emailChannelResponse.getIsArchived() != null) {
            Boolean isArchived = emailChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (emailChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = emailChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (emailChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = emailChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (emailChannelResponse.getMessagesPerSecond() != null) {
            Integer messagesPerSecond = emailChannelResponse.getMessagesPerSecond();
            awsJsonWriter.name("MessagesPerSecond");
            awsJsonWriter.value(messagesPerSecond);
        }
        if (emailChannelResponse.getPlatform() != null) {
            String platform = emailChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (emailChannelResponse.getRoleArn() != null) {
            String roleArn = emailChannelResponse.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (emailChannelResponse.getVersion() != null) {
            Integer version = emailChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static EmailChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EmailChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
