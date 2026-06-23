package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentRequestMarshaller implements Marshaller<Request<GetSegmentRequest>, GetSegmentRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentRequest> marshall(GetSegmentRequest getSegmentRequest) {
        if (getSegmentRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/segments/{segment-id}".replace("{application-id}", getSegmentRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentRequest.getApplicationId())).replace("{segment-id}", getSegmentRequest.getSegmentId() != null ? StringUtils.fromString(getSegmentRequest.getSegmentId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
