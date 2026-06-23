package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApnsSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsSandboxChannelRequestMarshaller implements Marshaller<Request<GetApnsSandboxChannelRequest>, GetApnsSandboxChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApnsSandboxChannelRequest> marshall(GetApnsSandboxChannelRequest getApnsSandboxChannelRequest) {
        if (getApnsSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApnsSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApnsSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_sandbox".replace("{application-id}", getApnsSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApnsSandboxChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
