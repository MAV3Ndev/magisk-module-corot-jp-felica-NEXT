package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetUserEndpointsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetUserEndpointsRequestMarshaller implements Marshaller<Request<GetUserEndpointsRequest>, GetUserEndpointsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetUserEndpointsRequest> marshall(GetUserEndpointsRequest getUserEndpointsRequest) {
        if (getUserEndpointsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetUserEndpointsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getUserEndpointsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/users/{user-id}".replace("{application-id}", getUserEndpointsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getUserEndpointsRequest.getApplicationId())).replace("{user-id}", getUserEndpointsRequest.getUserId() != null ? StringUtils.fromString(getUserEndpointsRequest.getUserId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
