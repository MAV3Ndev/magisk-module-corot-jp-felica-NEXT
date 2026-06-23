package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EndpointUserJsonMarshaller {
    private static EndpointUserJsonMarshaller instance;

    EndpointUserJsonMarshaller() {
    }

    public void marshall(EndpointUser endpointUser, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointUser.getUserAttributes() != null) {
            Map<String, List<String>> userAttributes = endpointUser.getUserAttributes();
            awsJsonWriter.name("UserAttributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry : userAttributes.entrySet()) {
                List<String> value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.beginArray();
                    for (String str : value) {
                        if (str != null) {
                            awsJsonWriter.value(str);
                        }
                    }
                    awsJsonWriter.endArray();
                }
            }
            awsJsonWriter.endObject();
        }
        if (endpointUser.getUserId() != null) {
            String userId = endpointUser.getUserId();
            awsJsonWriter.name("UserId");
            awsJsonWriter.value(userId);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointUserJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointUserJsonMarshaller();
        }
        return instance;
    }
}
