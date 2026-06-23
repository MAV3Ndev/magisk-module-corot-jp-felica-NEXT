package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.ADMChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ADMChannelResponseJsonMarshaller {
    private static ADMChannelResponseJsonMarshaller instance;

    ADMChannelResponseJsonMarshaller() {
    }

    public void marshall(ADMChannelResponse aDMChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aDMChannelResponse.getApplicationId() != null) {
            String applicationId = aDMChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (aDMChannelResponse.getCreationDate() != null) {
            String creationDate = aDMChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (aDMChannelResponse.getEnabled() != null) {
            Boolean enabled = aDMChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aDMChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = aDMChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (aDMChannelResponse.getId() != null) {
            String id = aDMChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (aDMChannelResponse.getIsArchived() != null) {
            Boolean isArchived = aDMChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (aDMChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = aDMChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (aDMChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = aDMChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (aDMChannelResponse.getPlatform() != null) {
            String platform = aDMChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (aDMChannelResponse.getVersion() != null) {
            Integer version = aDMChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static ADMChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ADMChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
