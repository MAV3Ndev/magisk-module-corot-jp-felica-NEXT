package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.APNSSandboxChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSSandboxChannelResponseJsonMarshaller {
    private static APNSSandboxChannelResponseJsonMarshaller instance;

    APNSSandboxChannelResponseJsonMarshaller() {
    }

    public void marshall(APNSSandboxChannelResponse aPNSSandboxChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSSandboxChannelResponse.getApplicationId() != null) {
            String applicationId = aPNSSandboxChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (aPNSSandboxChannelResponse.getCreationDate() != null) {
            String creationDate = aPNSSandboxChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (aPNSSandboxChannelResponse.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSSandboxChannelResponse.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSSandboxChannelResponse.getEnabled() != null) {
            Boolean enabled = aPNSSandboxChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSSandboxChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = aPNSSandboxChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (aPNSSandboxChannelResponse.getHasTokenKey() != null) {
            Boolean hasTokenKey = aPNSSandboxChannelResponse.getHasTokenKey();
            awsJsonWriter.name("HasTokenKey");
            awsJsonWriter.value(hasTokenKey.booleanValue());
        }
        if (aPNSSandboxChannelResponse.getId() != null) {
            String id = aPNSSandboxChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (aPNSSandboxChannelResponse.getIsArchived() != null) {
            Boolean isArchived = aPNSSandboxChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (aPNSSandboxChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = aPNSSandboxChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (aPNSSandboxChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = aPNSSandboxChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (aPNSSandboxChannelResponse.getPlatform() != null) {
            String platform = aPNSSandboxChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (aPNSSandboxChannelResponse.getVersion() != null) {
            Integer version = aPNSSandboxChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static APNSSandboxChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSSandboxChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
