package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSmsChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSmsChannelRequestMarshaller implements Marshaller<Request<GetSmsChannelRequest>, GetSmsChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSmsChannelRequest> marshall(GetSmsChannelRequest getSmsChannelRequest) {
        if (getSmsChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSmsChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSmsChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/sms".replace("{application-id}", getSmsChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSmsChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
