package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentImportJobsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentImportJobsRequestMarshaller implements Marshaller<Request<GetSegmentImportJobsRequest>, GetSegmentImportJobsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentImportJobsRequest> marshall(GetSegmentImportJobsRequest getSegmentImportJobsRequest) {
        if (getSegmentImportJobsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentImportJobsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentImportJobsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/segments/{segment-id}/jobs/import".replace("{application-id}", getSegmentImportJobsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentImportJobsRequest.getApplicationId()));
        if (getSegmentImportJobsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getSegmentImportJobsRequest.getPageSize()));
        }
        String strReplace2 = strReplace.replace("{segment-id}", getSegmentImportJobsRequest.getSegmentId() != null ? StringUtils.fromString(getSegmentImportJobsRequest.getSegmentId()) : "");
        if (getSegmentImportJobsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getSegmentImportJobsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace2);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
