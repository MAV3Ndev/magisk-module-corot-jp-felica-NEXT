package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.services.pinpoint.model.PublicEndpoint;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class PublicEndpointJsonMarshaller {
    private static PublicEndpointJsonMarshaller instance;

    PublicEndpointJsonMarshaller() {
    }

    public void marshall(PublicEndpoint publicEndpoint, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (publicEndpoint.getAddress() != null) {
            String address = publicEndpoint.getAddress();
            awsJsonWriter.name("Address");
            awsJsonWriter.value(address);
        }
        if (publicEndpoint.getAttributes() != null) {
            Map<String, List<String>> attributes = publicEndpoint.getAttributes();
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
        if (publicEndpoint.getChannelType() != null) {
            String channelType = publicEndpoint.getChannelType();
            awsJsonWriter.name("ChannelType");
            awsJsonWriter.value(channelType);
        }
        if (publicEndpoint.getDemographic() != null) {
            EndpointDemographic demographic = publicEndpoint.getDemographic();
            awsJsonWriter.name("Demographic");
            EndpointDemographicJsonMarshaller.getInstance().marshall(demographic, awsJsonWriter);
        }
        if (publicEndpoint.getEffectiveDate() != null) {
            String effectiveDate = publicEndpoint.getEffectiveDate();
            awsJsonWriter.name("EffectiveDate");
            awsJsonWriter.value(effectiveDate);
        }
        if (publicEndpoint.getEndpointStatus() != null) {
            String endpointStatus = publicEndpoint.getEndpointStatus();
            awsJsonWriter.name("EndpointStatus");
            awsJsonWriter.value(endpointStatus);
        }
        if (publicEndpoint.getLocation() != null) {
            EndpointLocation location = publicEndpoint.getLocation();
            awsJsonWriter.name("Location");
            EndpointLocationJsonMarshaller.getInstance().marshall(location, awsJsonWriter);
        }
        if (publicEndpoint.getMetrics() != null) {
            Map<String, Double> metrics = publicEndpoint.getMetrics();
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
        if (publicEndpoint.getOptOut() != null) {
            String optOut = publicEndpoint.getOptOut();
            awsJsonWriter.name("OptOut");
            awsJsonWriter.value(optOut);
        }
        if (publicEndpoint.getRequestId() != null) {
            String requestId = publicEndpoint.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (publicEndpoint.getUser() != null) {
            EndpointUser user = publicEndpoint.getUser();
            awsJsonWriter.name("User");
            EndpointUserJsonMarshaller.getInstance().marshall(user, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static PublicEndpointJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new PublicEndpointJsonMarshaller();
        }
        return instance;
    }
}
