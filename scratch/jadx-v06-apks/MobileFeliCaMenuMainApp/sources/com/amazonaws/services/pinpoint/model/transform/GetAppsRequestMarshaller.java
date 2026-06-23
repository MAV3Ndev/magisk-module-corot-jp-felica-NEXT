package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetAppsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;

/* JADX INFO: loaded from: classes.dex */
public class GetAppsRequestMarshaller implements Marshaller<Request<GetAppsRequest>, GetAppsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetAppsRequest> marshall(GetAppsRequest getAppsRequest) {
        if (getAppsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetAppsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getAppsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        if (getAppsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getAppsRequest.getPageSize()));
        }
        if (getAppsRequest.getToken() != null) {
            defaultRequest.addParameter(Protocol.CLIENT_TOKEN_KEY, StringUtils.fromString(getAppsRequest.getToken()));
        }
        defaultRequest.setResourcePath("/v1/apps");
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
