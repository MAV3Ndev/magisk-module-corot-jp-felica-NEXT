package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.GCMChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class GCMChannelResponseJsonMarshaller {
    private static GCMChannelResponseJsonMarshaller instance;

    GCMChannelResponseJsonMarshaller() {
    }

    public void marshall(GCMChannelResponse gCMChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (gCMChannelResponse.getApplicationId() != null) {
            String applicationId = gCMChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (gCMChannelResponse.getCreationDate() != null) {
            String creationDate = gCMChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (gCMChannelResponse.getCredential() != null) {
            String credential = gCMChannelResponse.getCredential();
            awsJsonWriter.name("Credential");
            awsJsonWriter.value(credential);
        }
        if (gCMChannelResponse.getEnabled() != null) {
            Boolean enabled = gCMChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (gCMChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = gCMChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (gCMChannelResponse.getId() != null) {
            String id = gCMChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (gCMChannelResponse.getIsArchived() != null) {
            Boolean isArchived = gCMChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (gCMChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = gCMChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (gCMChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = gCMChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (gCMChannelResponse.getPlatform() != null) {
            String platform = gCMChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (gCMChannelResponse.getVersion() != null) {
            Integer version = gCMChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static GCMChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GCMChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
