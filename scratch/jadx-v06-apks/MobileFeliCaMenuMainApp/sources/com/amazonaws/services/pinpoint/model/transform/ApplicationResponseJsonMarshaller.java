package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ApplicationResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ApplicationResponseJsonMarshaller {
    private static ApplicationResponseJsonMarshaller instance;

    ApplicationResponseJsonMarshaller() {
    }

    public void marshall(ApplicationResponse applicationResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (applicationResponse.getId() != null) {
            String id = applicationResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (applicationResponse.getName() != null) {
            String name = applicationResponse.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        awsJsonWriter.endObject();
    }

    public static ApplicationResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationResponseJsonMarshaller();
        }
        return instance;
    }
}
