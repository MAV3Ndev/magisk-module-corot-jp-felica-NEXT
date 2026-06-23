package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteApnsSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsSandboxChannelRequestMarshaller implements Marshaller<Request<DeleteApnsSandboxChannelRequest>, DeleteApnsSandboxChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteApnsSandboxChannelRequest> marshall(DeleteApnsSandboxChannelRequest deleteApnsSandboxChannelRequest) {
        if (deleteApnsSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteApnsSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteApnsSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_sandbox".replace("{application-id}", deleteApnsSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteApnsSandboxChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
