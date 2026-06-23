package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetEndpointRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetEndpointRequestMarshaller implements Marshaller<Request<GetEndpointRequest>, GetEndpointRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetEndpointRequest> marshall(GetEndpointRequest getEndpointRequest) {
        if (getEndpointRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetEndpointRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getEndpointRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/endpoints/{endpoint-id}".replace("{application-id}", getEndpointRequest.getApplicationId() == null ? "" : StringUtils.fromString(getEndpointRequest.getApplicationId())).replace("{endpoint-id}", getEndpointRequest.getEndpointId() != null ? StringUtils.fromString(getEndpointRequest.getEndpointId()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
