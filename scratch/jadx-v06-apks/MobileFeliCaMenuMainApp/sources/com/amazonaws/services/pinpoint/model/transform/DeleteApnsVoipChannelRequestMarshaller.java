package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipChannelRequestMarshaller implements Marshaller<Request<DeleteApnsVoipChannelRequest>, DeleteApnsVoipChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteApnsVoipChannelRequest> marshall(DeleteApnsVoipChannelRequest deleteApnsVoipChannelRequest) {
        if (deleteApnsVoipChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteApnsVoipChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteApnsVoipChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip".replace("{application-id}", deleteApnsVoipChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteApnsVoipChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
