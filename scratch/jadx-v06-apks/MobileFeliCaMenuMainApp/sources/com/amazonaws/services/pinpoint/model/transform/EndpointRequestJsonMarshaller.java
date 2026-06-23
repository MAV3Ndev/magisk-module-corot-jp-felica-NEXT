package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.http.HttpHeader;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointRequest;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EndpointRequestJsonMarshaller {
    private static EndpointRequestJsonMarshaller instance;

    EndpointRequestJsonMarshaller() {
    }

    public void marshall(EndpointRequest endpointRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointRequest.getAddress() != null) {
            String address = endpointRequest.getAddress();
            awsJsonWriter.name("Address");
            awsJsonWriter.value(address);
        }
        if (endpointRequest.getAttributes() != null) {
            Map<String, List<String>> attributes = endpointRequest.getAttributes();
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
        if (endpointRequest.getChannelType() != null) {
            String channelType = endpointRequest.getChannelType();
            awsJsonWriter.name("ChannelType");
            awsJsonWriter.value(channelType);
        }
        if (endpointRequest.getDemographic() != null) {
            EndpointDemographic demographic = endpointRequest.getDemographic();
            awsJsonWriter.name("Demographic");
            EndpointDemographicJsonMarshaller.getInstance().marshall(demographic, awsJsonWriter);
        }
        if (endpointRequest.getEffectiveDate() != null) {
            String effectiveDate = endpointRequest.getEffectiveDate();
            awsJsonWriter.name("EffectiveDate");
            awsJsonWriter.value(effectiveDate);
        }
        if (endpointRequest.getEndpointStatus() != null) {
            String endpointStatus = endpointRequest.getEndpointStatus();
            awsJsonWriter.name("EndpointStatus");
            awsJsonWriter.value(endpointStatus);
        }
        if (endpointRequest.getLocation() != null) {
            EndpointLocation location = endpointRequest.getLocation();
            awsJsonWriter.name(HttpHeader.LOCATION);
            EndpointLocationJsonMarshaller.getInstance().marshall(location, awsJsonWriter);
        }
        if (endpointRequest.getMetrics() != null) {
            Map<String, Double> metrics = endpointRequest.getMetrics();
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
        if (endpointRequest.getOptOut() != null) {
            String optOut = endpointRequest.getOptOut();
            awsJsonWriter.name("OptOut");
            awsJsonWriter.value(optOut);
        }
        if (endpointRequest.getRequestId() != null) {
            String requestId = endpointRequest.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (endpointRequest.getUser() != null) {
            EndpointUser user = endpointRequest.getUser();
            awsJsonWriter.name("User");
            EndpointUserJsonMarshaller.getInstance().marshall(user, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointRequestJsonMarshaller();
        }
        return instance;
    }
}
