package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetImportJobRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobRequestMarshaller implements Marshaller<Request<GetImportJobRequest>, GetImportJobRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetImportJobRequest> marshall(GetImportJobRequest getImportJobRequest) {
        if (getImportJobRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetImportJobRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getImportJobRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/jobs/import/{job-id}".replace("{application-id}", getImportJobRequest.getApplicationId() == null ? "" : StringUtils.fromString(getImportJobRequest.getApplicationId())).replace("{job-id}", getImportJobRequest.getJobId() != null ? StringUtils.fromString(getImportJobRequest.getJobId()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
