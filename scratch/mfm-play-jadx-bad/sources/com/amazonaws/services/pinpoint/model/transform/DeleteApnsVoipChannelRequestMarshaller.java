package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipChannelRequestMarshaller implements Marshaller<Request<DeleteApnsVoipChannelRequest>, DeleteApnsVoipChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteApnsVoipChannelRequest> marshall(DeleteApnsVoipChannelRequest deleteApnsVoipChannelRequest) {
        if (deleteApnsVoipChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteApnsVoipChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteApnsVoipChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip".replace("{application-id}", deleteApnsVoipChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteApnsVoipChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
