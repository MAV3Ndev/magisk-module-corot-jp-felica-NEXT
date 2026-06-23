package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.APNSChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSChannelResponseJsonMarshaller {
    private static APNSChannelResponseJsonMarshaller instance;

    APNSChannelResponseJsonMarshaller() {
    }

    public void marshall(APNSChannelResponse aPNSChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSChannelResponse.getApplicationId() != null) {
            String applicationId = aPNSChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (aPNSChannelResponse.getCreationDate() != null) {
            String creationDate = aPNSChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (aPNSChannelResponse.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSChannelResponse.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSChannelResponse.getEnabled() != null) {
            Boolean enabled = aPNSChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = aPNSChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (aPNSChannelResponse.getHasTokenKey() != null) {
            Boolean hasTokenKey = aPNSChannelResponse.getHasTokenKey();
            awsJsonWriter.name("HasTokenKey");
            awsJsonWriter.value(hasTokenKey.booleanValue());
        }
        if (aPNSChannelResponse.getId() != null) {
            String id = aPNSChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (aPNSChannelResponse.getIsArchived() != null) {
            Boolean isArchived = aPNSChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (aPNSChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = aPNSChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (aPNSChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = aPNSChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (aPNSChannelResponse.getPlatform() != null) {
            String platform = aPNSChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (aPNSChannelResponse.getVersion() != null) {
            Integer version = aPNSChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static APNSChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
