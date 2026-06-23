package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteApnsChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsChannelRequestMarshaller implements Marshaller<Request<DeleteApnsChannelRequest>, DeleteApnsChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteApnsChannelRequest> marshall(DeleteApnsChannelRequest deleteApnsChannelRequest) {
        if (deleteApnsChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteApnsChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteApnsChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns".replace("{application-id}", deleteApnsChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteApnsChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
