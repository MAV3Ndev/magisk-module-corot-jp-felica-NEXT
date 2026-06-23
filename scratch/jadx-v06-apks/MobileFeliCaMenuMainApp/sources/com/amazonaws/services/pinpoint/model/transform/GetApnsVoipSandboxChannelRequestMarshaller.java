package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApnsVoipSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsVoipSandboxChannelRequestMarshaller implements Marshaller<Request<GetApnsVoipSandboxChannelRequest>, GetApnsVoipSandboxChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApnsVoipSandboxChannelRequest> marshall(GetApnsVoipSandboxChannelRequest getApnsVoipSandboxChannelRequest) {
        if (getApnsVoipSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApnsVoipSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApnsVoipSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip_sandbox".replace("{application-id}", getApnsVoipSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApnsVoipSandboxChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
