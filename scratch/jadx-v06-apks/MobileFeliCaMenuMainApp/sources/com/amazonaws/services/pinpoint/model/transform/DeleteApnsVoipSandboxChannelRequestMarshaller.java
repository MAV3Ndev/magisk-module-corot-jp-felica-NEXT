package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipSandboxChannelRequestMarshaller implements Marshaller<Request<DeleteApnsVoipSandboxChannelRequest>, DeleteApnsVoipSandboxChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteApnsVoipSandboxChannelRequest> marshall(DeleteApnsVoipSandboxChannelRequest deleteApnsVoipSandboxChannelRequest) {
        if (deleteApnsVoipSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteApnsVoipSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteApnsVoipSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip_sandbox".replace("{application-id}", deleteApnsVoipSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteApnsVoipSandboxChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
