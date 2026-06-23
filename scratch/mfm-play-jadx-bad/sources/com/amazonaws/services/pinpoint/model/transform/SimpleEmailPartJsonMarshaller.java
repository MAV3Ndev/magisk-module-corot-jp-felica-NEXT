package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SimpleEmailPart;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SimpleEmailPartJsonMarshaller {
    private static SimpleEmailPartJsonMarshaller instance;

    SimpleEmailPartJsonMarshaller() {
    }

    public void marshall(SimpleEmailPart simpleEmailPart, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (simpleEmailPart.getCharset() != null) {
            String charset = simpleEmailPart.getCharset();
            awsJsonWriter.name("Charset");
            awsJsonWriter.value(charset);
        }
        if (simpleEmailPart.getData() != null) {
            String data = simpleEmailPart.getData();
            awsJsonWriter.name("Data");
            awsJsonWriter.value(data);
        }
        awsJsonWriter.endObject();
    }

    public static SimpleEmailPartJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SimpleEmailPartJsonMarshaller();
        }
        return instance;
    }
}
