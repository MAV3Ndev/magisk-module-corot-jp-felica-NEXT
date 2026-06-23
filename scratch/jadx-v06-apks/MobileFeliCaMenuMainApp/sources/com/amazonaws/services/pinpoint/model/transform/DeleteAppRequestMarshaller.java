package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteAppRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteAppRequestMarshaller implements Marshaller<Request<DeleteAppRequest>, DeleteAppRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteAppRequest> marshall(DeleteAppRequest deleteAppRequest) {
        if (deleteAppRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteAppRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteAppRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}".replace("{application-id}", deleteAppRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteAppRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
