package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionsRequestMarshaller implements Marshaller<Request<GetSegmentVersionsRequest>, GetSegmentVersionsRequest> {
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
            defaultRequest.addParameter(Protocol.CLIENT_TOKEN_KEY, StringUtils.fromString(getSegmentVersionsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace2);
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
