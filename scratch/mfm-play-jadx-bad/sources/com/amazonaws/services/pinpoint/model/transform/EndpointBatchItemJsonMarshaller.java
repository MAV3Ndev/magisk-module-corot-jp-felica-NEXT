package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.EndpointBatchItem;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EndpointBatchItemJsonMarshaller {
    private static EndpointBatchItemJsonMarshaller instance;

    EndpointBatchItemJsonMarshaller() {
    }

    public void marshall(EndpointBatchItem endpointBatchItem, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointBatchItem.getAddress() != null) {
            String address = endpointBatchItem.getAddress();
            awsJsonWriter.name("Address");
            awsJsonWriter.value(address);
        }
        if (endpointBatchItem.getAttributes() != null) {
            Map<String, List<String>> attributes = endpointBatchItem.getAttributes();
            awsJsonWriter.name("Attributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
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
        if (endpointBatchItem.getChannelType() != null) {
            String channelType = endpointBatchItem.getChannelType();
            awsJsonWriter.name("ChannelType");
            awsJsonWriter.value(channelType);
        }
        if (endpointBatchItem.getDemographic() != null) {
            EndpointDemographic demographic = endpointBatchItem.getDemographic();
            awsJsonWriter.name("Demographic");
            EndpointDemographicJsonMarshaller.getInstance().marshall(demographic, awsJsonWriter);
        }
        if (endpointBatchItem.getEffectiveDate() != null) {
            String effectiveDate = endpointBatchItem.getEffectiveDate();
            awsJsonWriter.name("EffectiveDate");
            awsJsonWriter.value(effectiveDate);
        }
        if (endpointBatchItem.getEndpointStatus() != null) {
            String endpointStatus = endpointBatchItem.getEndpointStatus();
            awsJsonWriter.name("EndpointStatus");
            awsJsonWriter.value(endpointStatus);
        }
        if (endpointBatchItem.getId() != null) {
            String id = endpointBatchItem.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (endpointBatchItem.getLocation() != null) {
            EndpointLocation location = endpointBatchItem.getLocation();
            awsJsonWriter.name("Location");
            EndpointLocationJsonMarshaller.getInstance().marshall(location, awsJsonWriter);
        }
        if (endpointBatchItem.getMetrics() != null) {
            Map<String, Double> metrics = endpointBatchItem.getMetrics();
            awsJsonWriter.name("Metrics");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Double> entry2 : metrics.entrySet()) {
                Double value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.value(value2);
                }
            }
            awsJsonWriter.endObject();
        }
        if (endpointBatchItem.getOptOut() != null) {
            String optOut = endpointBatchItem.getOptOut();
            awsJsonWriter.name("OptOut");
            awsJsonWriter.value(optOut);
        }
        if (endpointBatchItem.getRequestId() != null) {
            String requestId = endpointBatchItem.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (endpointBatchItem.getUser() != null) {
            EndpointUser user = endpointBatchItem.getUser();
            awsJsonWriter.name("User");
            EndpointUserJsonMarshaller.getInstance().marshall(user, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointBatchItemJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointBatchItemJsonMarshaller();
        }
        return instance;
    }
}
