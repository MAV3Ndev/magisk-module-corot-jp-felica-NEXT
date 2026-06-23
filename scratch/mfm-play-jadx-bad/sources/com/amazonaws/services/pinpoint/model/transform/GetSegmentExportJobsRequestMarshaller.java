package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentExportJobsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentExportJobsRequestMarshaller implements Marshaller<Request<GetSegmentExportJobsRequest>, GetSegmentExportJobsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentExportJobsRequest> marshall(GetSegmentExportJobsRequest getSegmentExportJobsRequest) {
        if (getSegmentExportJobsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentExportJobsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentExportJobsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/segments/{segment-id}/jobs/export".replace("{application-id}", getSegmentExportJobsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentExportJobsRequest.getApplicationId()));
        if (getSegmentExportJobsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getSegmentExportJobsRequest.getPageSize()));
        }
        String strReplace2 = strReplace.replace("{segment-id}", getSegmentExportJobsRequest.getSegmentId() != null ? StringUtils.fromString(getSegmentExportJobsRequest.getSegmentId()) : "");
        if (getSegmentExportJobsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getSegmentExportJobsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace2);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
