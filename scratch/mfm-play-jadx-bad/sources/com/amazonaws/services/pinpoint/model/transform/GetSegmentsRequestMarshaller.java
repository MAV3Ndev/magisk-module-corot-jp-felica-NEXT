package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentsRequestMarshaller implements Marshaller<Request<GetSegmentsRequest>, GetSegmentsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentsRequest> marshall(GetSegmentsRequest getSegmentsRequest) {
        if (getSegmentsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/segments".replace("{application-id}", getSegmentsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentsRequest.getApplicationId()));
        if (getSegmentsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getSegmentsRequest.getPageSize()));
        }
        if (getSegmentsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getSegmentsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
