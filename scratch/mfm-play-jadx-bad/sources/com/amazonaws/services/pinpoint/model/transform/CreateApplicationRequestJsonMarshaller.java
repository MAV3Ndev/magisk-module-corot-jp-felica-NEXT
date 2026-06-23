package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateApplicationRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CreateApplicationRequestJsonMarshaller {
    private static CreateApplicationRequestJsonMarshaller instance;

    CreateApplicationRequestJsonMarshaller() {
    }

    public void marshall(CreateApplicationRequest createApplicationRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (createApplicationRequest.getName() != null) {
            String name = createApplicationRequest.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        awsJsonWriter.endObject();
    }

    public static CreateApplicationRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CreateApplicationRequestJsonMarshaller();
        }
        return instance;
    }
}
