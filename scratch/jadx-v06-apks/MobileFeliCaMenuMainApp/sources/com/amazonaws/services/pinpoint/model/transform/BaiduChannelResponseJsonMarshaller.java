package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.BaiduChannelResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class BaiduChannelResponseJsonMarshaller {
    private static BaiduChannelResponseJsonMarshaller instance;

    BaiduChannelResponseJsonMarshaller() {
    }

    public void marshall(BaiduChannelResponse baiduChannelResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (baiduChannelResponse.getApplicationId() != null) {
            String applicationId = baiduChannelResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (baiduChannelResponse.getCreationDate() != null) {
            String creationDate = baiduChannelResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (baiduChannelResponse.getCredential() != null) {
            String credential = baiduChannelResponse.getCredential();
            awsJsonWriter.name("Credential");
            awsJsonWriter.value(credential);
        }
        if (baiduChannelResponse.getEnabled() != null) {
            Boolean enabled = baiduChannelResponse.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (baiduChannelResponse.getHasCredential() != null) {
            Boolean hasCredential = baiduChannelResponse.getHasCredential();
            awsJsonWriter.name("HasCredential");
            awsJsonWriter.value(hasCredential.booleanValue());
        }
        if (baiduChannelResponse.getId() != null) {
            String id = baiduChannelResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (baiduChannelResponse.getIsArchived() != null) {
            Boolean isArchived = baiduChannelResponse.getIsArchived();
            awsJsonWriter.name("IsArchived");
            awsJsonWriter.value(isArchived.booleanValue());
        }
        if (baiduChannelResponse.getLastModifiedBy() != null) {
            String lastModifiedBy = baiduChannelResponse.getLastModifiedBy();
            awsJsonWriter.name("LastModifiedBy");
            awsJsonWriter.value(lastModifiedBy);
        }
        if (baiduChannelResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = baiduChannelResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (baiduChannelResponse.getPlatform() != null) {
            String platform = baiduChannelResponse.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (baiduChannelResponse.getVersion() != null) {
            Integer version = baiduChannelResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static BaiduChannelResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new BaiduChannelResponseJsonMarshaller();
        }
        return instance;
    }
}
