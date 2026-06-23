package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteUserEndpointsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserEndpointsRequestMarshaller implements Marshaller<Request<DeleteUserEndpointsRequest>, DeleteUserEndpointsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteUserEndpointsRequest> marshall(DeleteUserEndpointsRequest deleteUserEndpointsRequest) {
        if (deleteUserEndpointsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteUserEndpointsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteUserEndpointsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/users/{user-id}".replace("{application-id}", deleteUserEndpointsRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteUserEndpointsRequest.getApplicationId())).replace("{user-id}", deleteUserEndpointsRequest.getUserId() != null ? StringUtils.fromString(deleteUserEndpointsRequest.getUserId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
