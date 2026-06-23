package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetImportJobsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobsRequestMarshaller implements Marshaller<Request<GetImportJobsRequest>, GetImportJobsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetImportJobsRequest> marshall(GetImportJobsRequest getImportJobsRequest) {
        if (getImportJobsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetImportJobsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getImportJobsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/jobs/import".replace("{application-id}", getImportJobsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getImportJobsRequest.getApplicationId()));
        if (getImportJobsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getImportJobsRequest.getPageSize()));
        }
        if (getImportJobsRequest.getToken() != null) {
            defaultRequest.addParameter(Protocol.CLIENT_TOKEN_KEY, StringUtils.fromString(getImportJobsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
