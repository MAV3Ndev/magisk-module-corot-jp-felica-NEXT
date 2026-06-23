package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteEndpointRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEndpointRequestMarshaller implements Marshaller<Request<DeleteEndpointRequest>, DeleteEndpointRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteEndpointRequest> marshall(DeleteEndpointRequest deleteEndpointRequest) {
        if (deleteEndpointRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteEndpointRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteEndpointRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/endpoints/{endpoint-id}".replace("{application-id}", deleteEndpointRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteEndpointRequest.getApplicationId())).replace("{endpoint-id}", deleteEndpointRequest.getEndpointId() != null ? StringUtils.fromString(deleteEndpointRequest.getEndpointId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
