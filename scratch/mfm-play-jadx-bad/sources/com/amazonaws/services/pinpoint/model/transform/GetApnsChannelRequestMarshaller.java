package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApnsChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsChannelRequestMarshaller implements Marshaller<Request<GetApnsChannelRequest>, GetApnsChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApnsChannelRequest> marshall(GetApnsChannelRequest getApnsChannelRequest) {
        if (getApnsChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApnsChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApnsChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns".replace("{application-id}", getApnsChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApnsChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
