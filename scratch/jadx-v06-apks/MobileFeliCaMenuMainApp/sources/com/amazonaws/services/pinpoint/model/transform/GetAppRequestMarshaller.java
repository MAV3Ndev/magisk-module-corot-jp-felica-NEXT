package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetAppRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetAppRequestMarshaller implements Marshaller<Request<GetAppRequest>, GetAppRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetAppRequest> marshall(GetAppRequest getAppRequest) {
        if (getAppRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetAppRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getAppRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}".replace("{application-id}", getAppRequest.getApplicationId() == null ? "" : StringUtils.fromString(getAppRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
