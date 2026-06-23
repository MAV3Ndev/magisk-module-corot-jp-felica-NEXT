package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetBaiduChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetBaiduChannelRequestMarshaller implements Marshaller<Request<GetBaiduChannelRequest>, GetBaiduChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetBaiduChannelRequest> marshall(GetBaiduChannelRequest getBaiduChannelRequest) {
        if (getBaiduChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetBaiduChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getBaiduChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/baidu".replace("{application-id}", getBaiduChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getBaiduChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
