package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetGcmChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetGcmChannelRequestMarshaller implements Marshaller<Request<GetGcmChannelRequest>, GetGcmChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetGcmChannelRequest> marshall(GetGcmChannelRequest getGcmChannelRequest) {
        if (getGcmChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetGcmChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getGcmChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/gcm".replace("{application-id}", getGcmChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getGcmChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
