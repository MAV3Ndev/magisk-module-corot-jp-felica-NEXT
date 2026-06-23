package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionsRequestMarshaller implements Marshaller<Request<GetSegmentVersionsRequest>, GetSegmentVersionsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentVersionsRequest> marshall(GetSegmentVersionsRequest getSegmentVersionsRequest) {
        if (getSegmentVersionsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentVersionsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentVersionsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/segments/{segment-id}/versions".replace("{application-id}", getSegmentVersionsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentVersionsRequest.getApplicationId()));
        if (getSegmentVersionsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getSegmentVersionsRequest.getPageSize()));
        }
        String strReplace2 = strReplace.replace("{segment-id}", getSegmentVersionsRequest.getSegmentId() != null ? StringUtils.fromString(getSegmentVersionsRequest.getSegmentId()) : "");
        if (getSegmentVersionsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getSegmentVersionsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace2);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
