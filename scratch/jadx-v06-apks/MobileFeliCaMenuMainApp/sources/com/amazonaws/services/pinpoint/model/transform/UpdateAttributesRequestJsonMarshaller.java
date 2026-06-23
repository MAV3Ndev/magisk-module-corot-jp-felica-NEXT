package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateAttributesRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class UpdateAttributesRequestJsonMarshaller {
    private static UpdateAttributesRequestJsonMarshaller instance;

    UpdateAttributesRequestJsonMarshaller() {
    }

    public void marshall(UpdateAttributesRequest updateAttributesRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (updateAttributesRequest.getBlacklist() != null) {
            List<String> blacklist = updateAttributesRequest.getBlacklist();
            awsJsonWriter.name("Blacklist");
            awsJsonWriter.beginArray();
            for (String str : blacklist) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static UpdateAttributesRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateAttributesRequestJsonMarshaller();
        }
        return instance;
    }
}
