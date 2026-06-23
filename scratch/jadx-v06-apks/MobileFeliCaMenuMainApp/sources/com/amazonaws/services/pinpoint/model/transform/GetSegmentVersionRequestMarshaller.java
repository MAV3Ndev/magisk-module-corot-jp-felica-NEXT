package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionRequestMarshaller implements Marshaller<Request<GetSegmentVersionRequest>, GetSegmentVersionRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSegmentVersionRequest> marshall(GetSegmentVersionRequest getSegmentVersionRequest) {
        if (getSegmentVersionRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSegmentVersionRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSegmentVersionRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/segments/{segment-id}/versions/{version}".replace("{application-id}", getSegmentVersionRequest.getApplicationId() == null ? "" : StringUtils.fromString(getSegmentVersionRequest.getApplicationId())).replace("{segment-id}", getSegmentVersionRequest.getSegmentId() == null ? "" : StringUtils.fromString(getSegmentVersionRequest.getSegmentId())).replace("{version}", getSegmentVersionRequest.getVersion() != null ? StringUtils.fromString(getSegmentVersionRequest.getVersion()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
