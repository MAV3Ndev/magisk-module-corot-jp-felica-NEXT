package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.APNSVoipSandboxChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipSandboxChannelResponseJsonMarshaller {
    private static APNSVoipSandboxChannelResponseJsonMarshaller instance;

    APNSVoipSandboxChannelResponseJsonMarshaller() {
    }

    public void marshall(APNSVoipSandboxChannelResponse aPNSVoipSandboxChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSVoipSandboxChannelResponse.getApplicationId() != null) {
            String applicationId = aPNSVoipSandboxChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (aPNSVoipSandboxChannelResponse.getCreationDate() != null) {
            String creationDate = aPNSVoipSandboxChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (aPNSVoipSandboxChannelResponse.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSVoipSandboxChannelResponse.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSVoipSandboxChannelResponse.getEnabled() != null) {
            Boolean enabled = aPNSVoipSandboxChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSVoipSandboxChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = aPNSVoipSandboxChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (aPNSVoipSandboxChannelResponse.getHasTokenKey() != null) {
            Boolean hasTokenKey = aPNSVoipSandboxChannelResponse.getHasTokenKey();
            awsJsonWriter.name("HasTokenKey");
            awsJsonWriter.value(hasTokenKey.booleanValue());
        }
        if (aPNSVoipSandboxChannelResponse.getId() != null) {
            String id = aPNSVoipSandboxChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (aPNSVoipSandboxChannelResponse.getIsArchived() != null) {
            Boolean isArchived = aPNSVoipSandboxChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (aPNSVoipSandboxChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = aPNSVoipSandboxChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (aPNSVoipSandboxChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = aPNSVoipSandboxChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (aPNSVoipSandboxChannelResponse.getPlatform() != null) {
            String platform = aPNSVoipSandboxChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (aPNSVoipSandboxChannelResponse.getVersion() != null) {
            Integer version = aPNSVoipSandboxChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static APNSVoipSandboxChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipSandboxChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
