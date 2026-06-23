package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DefaultMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class DefaultMessageJsonMarshaller {
    private static DefaultMessageJsonMarshaller instance;

    DefaultMessageJsonMarshaller() {
    }

    public void marshall(DefaultMessage defaultMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (defaultMessage.getBody() != null) {
            String body = defaultMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (defaultMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = defaultMessage.getSubstitutions();
            awsJsonWriter.name("Substitutions");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry : substitutions.entrySet()) {
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
        awsJsonWriter.endObject();
    }

    public static DefaultMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new DefaultMessageJsonMarshaller();
        }
        return instance;
    }
}
