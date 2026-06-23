package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RecencyDimension;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class RecencyDimensionJsonMarshaller {
    private static RecencyDimensionJsonMarshaller instance;

    RecencyDimensionJsonMarshaller() {
    }

    public void marshall(RecencyDimension recencyDimension, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (recencyDimension.getDuration() != null) {
            String duration = recencyDimension.getDuration();
            awsJsonWriter.name("Duration");
            awsJsonWriter.value(duration);
        }
        if (recencyDimension.getRecencyType() != null) {
            String recencyType = recencyDimension.getRecencyType();
            awsJsonWriter.name("RecencyType");
            awsJsonWriter.value(recencyType);
        }
        awsJsonWriter.endObject();
    }

    public static RecencyDimensionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new RecencyDimensionJsonMarshaller();
        }
        return instance;
    }
}
