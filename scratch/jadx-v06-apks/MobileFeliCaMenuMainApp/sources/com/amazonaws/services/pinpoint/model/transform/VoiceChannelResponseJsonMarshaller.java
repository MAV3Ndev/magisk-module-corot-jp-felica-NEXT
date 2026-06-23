package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.VoiceChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class VoiceChannelResponseJsonMarshaller {
    private static VoiceChannelResponseJsonMarshaller instance;

    VoiceChannelResponseJsonMarshaller() {
    }

    public void marshall(VoiceChannelResponse voiceChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (voiceChannelResponse.getApplicationId() != null) {
            String applicationId = voiceChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (voiceChannelResponse.getCreationDate() != null) {
            String creationDate = voiceChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (voiceChannelResponse.getEnabled() != null) {
            Boolean enabled = voiceChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (voiceChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = voiceChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (voiceChannelResponse.getId() != null) {
            String id = voiceChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (voiceChannelResponse.getIsArchived() != null) {
            Boolean isArchived = voiceChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (voiceChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = voiceChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (voiceChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = voiceChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (voiceChannelResponse.getPlatform() != null) {
            String platform = voiceChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (voiceChannelResponse.getVersion() != null) {
            Integer version = voiceChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static VoiceChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new VoiceChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
