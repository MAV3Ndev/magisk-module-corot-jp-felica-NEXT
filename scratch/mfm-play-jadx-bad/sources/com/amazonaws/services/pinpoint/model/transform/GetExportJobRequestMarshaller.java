package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetExportJobRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobRequestMarshaller implements Marshaller<Request<GetExportJobRequest>, GetExportJobRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetExportJobRequest> marshall(GetExportJobRequest getExportJobRequest) {
        if (getExportJobRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetExportJobRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getExportJobRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/jobs/export/{job-id}".replace("{application-id}", getExportJobRequest.getApplicationId() == null ? "" : StringUtils.fromString(getExportJobRequest.getApplicationId())).replace("{job-id}", getExportJobRequest.getJobId() != null ? StringUtils.fromString(getExportJobRequest.getJobId()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
