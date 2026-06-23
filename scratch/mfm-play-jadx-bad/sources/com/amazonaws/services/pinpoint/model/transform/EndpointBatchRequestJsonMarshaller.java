package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointBatchItem;
import com.amazonaws.services.pinpoint.model.EndpointBatchRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class EndpointBatchRequestJsonMarshaller {
    private static EndpointBatchRequestJsonMarshaller instance;

    EndpointBatchRequestJsonMarshaller() {
    }

    public void marshall(EndpointBatchRequest endpointBatchRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointBatchRequest.getItem() != null) {
            List<EndpointBatchItem> item = endpointBatchRequest.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (EndpointBatchItem endpointBatchItem : item) {
                if (endpointBatchItem != null) {
                    EndpointBatchItemJsonMarshaller.getInstance().marshall(endpointBatchItem, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static EndpointBatchRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointBatchRequestJsonMarshaller();
        }
        return instance;
    }
}
