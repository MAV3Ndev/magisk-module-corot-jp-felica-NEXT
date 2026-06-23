package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RawEmail;
import com.amazonaws.util.json.AwsJsonWriter;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RawEmailJsonMarshaller {
    private static RawEmailJsonMarshaller instance;

    RawEmailJsonMarshaller() {
    }

    public void marshall(RawEmail rawEmail, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (rawEmail.getData() != null) {
            ByteBuffer data = rawEmail.getData();
            awsJsonWriter.name("Data");
            awsJsonWriter.value(data);
        }
        awsJsonWriter.endObject();
    }

    public static RawEmailJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new RawEmailJsonMarshaller();
        }
        return instance;
    }
}
