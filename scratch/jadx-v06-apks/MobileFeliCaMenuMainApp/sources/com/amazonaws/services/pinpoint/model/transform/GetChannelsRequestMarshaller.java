package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetChannelsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetChannelsRequestMarshaller implements Marshaller<Request<GetChannelsRequest>, GetChannelsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetChannelsRequest> marshall(GetChannelsRequest getChannelsRequest) {
        if (getChannelsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetChannelsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getChannelsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels".replace("{application-id}", getChannelsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getChannelsRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
