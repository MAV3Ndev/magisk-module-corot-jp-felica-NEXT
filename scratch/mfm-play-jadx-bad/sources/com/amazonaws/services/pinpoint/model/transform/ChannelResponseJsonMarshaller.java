package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ChannelResponseJsonMarshaller {
    private static ChannelResponseJsonMarshaller instance;

    ChannelResponseJsonMarshaller() {
    }

    public void marshall(ChannelResponse channelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (channelResponse.getApplicationId() != null) {
            String applicationId = channelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (channelResponse.getCreationDate() != null) {
            String creationDate = channelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (channelResponse.getEnabled() != null) {
            Boolean enabled = channelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (channelResponse.getHasCredential() != null) {
            Boolean hasCredential = channelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (channelResponse.getId() != null) {
            String id = channelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (channelResponse.getIsArchived() != null) {
            Boolean isArchived = channelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (channelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = channelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (channelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = channelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (channelResponse.getVersion() != null) {
            Integer version = channelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static ChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
