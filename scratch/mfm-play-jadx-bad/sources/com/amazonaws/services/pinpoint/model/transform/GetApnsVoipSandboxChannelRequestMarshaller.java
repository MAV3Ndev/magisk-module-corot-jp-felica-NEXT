package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApnsVoipSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsVoipSandboxChannelRequestMarshaller implements Marshaller<Request<GetApnsVoipSandboxChannelRequest>, GetApnsVoipSandboxChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApnsVoipSandboxChannelRequest> marshall(GetApnsVoipSandboxChannelRequest getApnsVoipSandboxChannelRequest) {
        if (getApnsVoipSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApnsVoipSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApnsVoipSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip_sandbox".replace("{application-id}", getApnsVoipSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApnsVoipSandboxChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
