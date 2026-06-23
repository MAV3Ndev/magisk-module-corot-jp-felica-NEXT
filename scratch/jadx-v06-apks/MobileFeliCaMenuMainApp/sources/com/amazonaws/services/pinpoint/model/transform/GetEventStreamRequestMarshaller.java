package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetEventStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetEventStreamRequestMarshaller implements Marshaller<Request<GetEventStreamRequest>, GetEventStreamRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetEventStreamRequest> marshall(GetEventStreamRequest getEventStreamRequest) {
        if (getEventStreamRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetEventStreamRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getEventStreamRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/eventstream".replace("{application-id}", getEventStreamRequest.getApplicationId() == null ? "" : StringUtils.fromString(getEventStreamRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
