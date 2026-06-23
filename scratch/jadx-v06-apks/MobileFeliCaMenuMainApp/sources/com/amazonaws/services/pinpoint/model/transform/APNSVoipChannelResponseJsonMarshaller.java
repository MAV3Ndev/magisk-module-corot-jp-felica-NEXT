package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.APNSVoipChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipChannelResponseJsonMarshaller {
    private static APNSVoipChannelResponseJsonMarshaller instance;

    APNSVoipChannelResponseJsonMarshaller() {
    }

    public void marshall(APNSVoipChannelResponse aPNSVoipChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSVoipChannelResponse.getApplicationId() != null) {
            String applicationId = aPNSVoipChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (aPNSVoipChannelResponse.getCreationDate() != null) {
            String creationDate = aPNSVoipChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (aPNSVoipChannelResponse.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSVoipChannelResponse.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSVoipChannelResponse.getEnabled() != null) {
            Boolean enabled = aPNSVoipChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSVoipChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = aPNSVoipChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (aPNSVoipChannelResponse.getHasTokenKey() != null) {
            Boolean hasTokenKey = aPNSVoipChannelResponse.getHasTokenKey();
            awsJsonWriter.name("HasTokenKey");
            awsJsonWriter.value(hasTokenKey.booleanValue());
        }
        if (aPNSVoipChannelResponse.getId() != null) {
            String id = aPNSVoipChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (aPNSVoipChannelResponse.getIsArchived() != null) {
            Boolean isArchived = aPNSVoipChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (aPNSVoipChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = aPNSVoipChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (aPNSVoipChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = aPNSVoipChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (aPNSVoipChannelResponse.getPlatform() != null) {
            String platform = aPNSVoipChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (aPNSVoipChannelResponse.getVersion() != null) {
            Integer version = aPNSVoipChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static APNSVoipChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
