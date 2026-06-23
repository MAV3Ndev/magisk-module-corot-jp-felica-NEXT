package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetExportJobsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobsRequestMarshaller implements Marshaller<Request<GetExportJobsRequest>, GetExportJobsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetExportJobsRequest> marshall(GetExportJobsRequest getExportJobsRequest) {
        if (getExportJobsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetExportJobsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getExportJobsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/jobs/export".replace("{application-id}", getExportJobsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getExportJobsRequest.getApplicationId()));
        if (getExportJobsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getExportJobsRequest.getPageSize()));
        }
        if (getExportJobsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getExportJobsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
