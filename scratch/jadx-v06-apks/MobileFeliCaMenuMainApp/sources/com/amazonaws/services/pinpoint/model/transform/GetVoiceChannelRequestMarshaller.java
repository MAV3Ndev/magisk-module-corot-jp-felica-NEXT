package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetVoiceChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetVoiceChannelRequestMarshaller implements Marshaller<Request<GetVoiceChannelRequest>, GetVoiceChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetVoiceChannelRequest> marshall(GetVoiceChannelRequest getVoiceChannelRequest) {
        if (getVoiceChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetVoiceChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getVoiceChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/voice".replace("{application-id}", getVoiceChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getVoiceChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
