package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetAppsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetAppsRequestMarshaller implements Marshaller<Request<GetAppsRequest>, GetAppsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
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
            defaultRequest.addParameter("token", StringUtils.fromString(getAppsRequest.getToken()));
        }
        defaultRequest.setResourcePath("/v1/apps");
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
