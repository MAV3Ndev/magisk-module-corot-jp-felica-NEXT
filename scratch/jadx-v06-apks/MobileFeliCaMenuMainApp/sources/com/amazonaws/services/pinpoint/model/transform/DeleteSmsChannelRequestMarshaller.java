package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteSmsChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteSmsChannelRequestMarshaller implements Marshaller<Request<DeleteSmsChannelRequest>, DeleteSmsChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteSmsChannelRequest> marshall(DeleteSmsChannelRequest deleteSmsChannelRequest) {
        if (deleteSmsChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteSmsChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteSmsChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/sms".replace("{application-id}", deleteSmsChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteSmsChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
