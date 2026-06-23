package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetEmailChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetEmailChannelRequestMarshaller implements Marshaller<Request<GetEmailChannelRequest>, GetEmailChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetEmailChannelRequest> marshall(GetEmailChannelRequest getEmailChannelRequest) {
        if (getEmailChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetEmailChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getEmailChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/email".replace("{application-id}", getEmailChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getEmailChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
