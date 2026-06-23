package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventStream;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EventStreamJsonMarshaller {
    private static EventStreamJsonMarshaller instance;

    EventStreamJsonMarshaller() {
    }

    public void marshall(EventStream eventStream, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventStream.getApplicationId() != null) {
            String applicationId = eventStream.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (eventStream.getDestinationStreamArn() != null) {
            String destinationStreamArn = eventStream.getDestinationStreamArn();
            awsJsonWriter.name("DestinationStreamArn");
            awsJsonWriter.value(destinationStreamArn);
        }
        if (eventStream.getExternalId() != null) {
            String externalId = eventStream.getExternalId();
            awsJsonWriter.name("ExternalId");
            awsJsonWriter.value(externalId);
        }
        if (eventStream.getLastModifiedDate() != null) {
            String lastModifiedDate = eventStream.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (eventStream.getLastUpdatedBy() != null) {
            String lastUpdatedBy = eventStream.getLastUpdatedBy();
            awsJsonWriter.name("LastUpdatedBy");
            awsJsonWriter.value(lastUpdatedBy);
        }
        if (eventStream.getRoleArn() != null) {
            String roleArn = eventStream.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        awsJsonWriter.endObject();
    }

    public static EventStreamJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventStreamJsonMarshaller();
        }
        return instance;
    }
}
