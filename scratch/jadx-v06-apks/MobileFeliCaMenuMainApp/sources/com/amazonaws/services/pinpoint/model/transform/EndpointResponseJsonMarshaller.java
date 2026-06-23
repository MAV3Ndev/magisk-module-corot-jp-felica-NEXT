package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointResponse;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EndpointResponseJsonMarshaller {
    private static EndpointResponseJsonMarshaller instance;

    EndpointResponseJsonMarshaller() {
    }

    public void marshall(EndpointResponse endpointResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointResponse.getAddress() != null) {
            String address = endpointResponse.getAddress();
            awsJsonWriter.name("Address");
            awsJsonWriter.value(address);
        }
        if (endpointResponse.getApplicationId() != null) {
            String applicationId = endpointResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (endpointResponse.getAttributes() != null) {
            Map<String, List<String>> attributes = endpointResponse.getAttributes();
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
        if (endpointResponse.getChannelType() != null) {
            String channelType = endpointResponse.getChannelType();
            awsJsonWriter.name("ChannelType");
            awsJsonWriter.value(channelType);
        }
        if (endpointResponse.getCohortId() != null) {
            String cohortId = endpointResponse.getCohortId();
            awsJsonWriter.name("CohortId");
            awsJsonWriter.value(cohortId);
        }
        if (endpointResponse.getCreationDate() != null) {
            String creationDate = endpointResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (endpointResponse.getDemographic() != null) {
            EndpointDemographic demographic = endpointResponse.getDemographic();
            awsJsonWriter.name("Demographic");
            EndpointDemographicJsonMarshaller.getInstance().marshall(demographic, awsJsonWriter);
        }
        if (endpointResponse.getEffectiveDate() != null) {
            String effectiveDate = endpointResponse.getEffectiveDate();
            awsJsonWriter.name("EffectiveDate");
            awsJsonWriter.value(effectiveDate);
        }
        if (endpointResponse.getEndpointStatus() != null) {
            String endpointStatus = endpointResponse.getEndpointStatus();
            awsJsonWriter.name("EndpointStatus");
            awsJsonWriter.value(endpointStatus);
        }
        if (endpointResponse.getId() != null) {
            String id = endpointResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (endpointResponse.getLocation() != null) {
            EndpointLocation location = endpointResponse.getLocation();
            awsJsonWriter.name(HttpHeader.LOCATION);
            EndpointLocationJsonMarshaller.getInstance().marshall(location, awsJsonWriter);
        }
        if (endpointResponse.getMetrics() != null) {
            Map<String, Double> metrics = endpointResponse.getMetrics();
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
        if (endpointResponse.getOptOut() != null) {
            String optOut = endpointResponse.getOptOut();
            awsJsonWriter.name("OptOut");
            awsJsonWriter.value(optOut);
        }
        if (endpointResponse.getRequestId() != null) {
            String requestId = endpointResponse.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (endpointResponse.getUser() != null) {
            EndpointUser user = endpointResponse.getUser();
            awsJsonWriter.name("User");
            EndpointUserJsonMarshaller.getInstance().marshall(user, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointResponseJsonMarshaller();
        }
        return instance;
    }
}
