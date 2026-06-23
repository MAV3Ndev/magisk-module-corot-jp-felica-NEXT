package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApnsVoipChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsVoipChannelRequestMarshaller implements Marshaller<Request<GetApnsVoipChannelRequest>, GetApnsVoipChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApnsVoipChannelRequest> marshall(GetApnsVoipChannelRequest getApnsVoipChannelRequest) {
        if (getApnsVoipChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApnsVoipChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApnsVoipChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip".replace("{application-id}", getApnsVoipChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApnsVoipChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
