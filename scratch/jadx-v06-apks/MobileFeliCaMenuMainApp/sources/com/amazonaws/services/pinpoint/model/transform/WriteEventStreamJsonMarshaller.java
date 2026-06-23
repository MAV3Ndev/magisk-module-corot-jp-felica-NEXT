package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteEventStream;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class WriteEventStreamJsonMarshaller {
    private static WriteEventStreamJsonMarshaller instance;

    WriteEventStreamJsonMarshaller() {
    }

    public void marshall(WriteEventStream writeEventStream, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (writeEventStream.getDestinationStreamArn() != null) {
            String destinationStreamArn = writeEventStream.getDestinationStreamArn();
            awsJsonWriter.name("DestinationStreamArn");
            awsJsonWriter.value(destinationStreamArn);
        }
        if (writeEventStream.getRoleArn() != null) {
            String roleArn = writeEventStream.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        awsJsonWriter.endObject();
    }

    public static WriteEventStreamJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new WriteEventStreamJsonMarshaller();
        }
        return instance;
    }
}
