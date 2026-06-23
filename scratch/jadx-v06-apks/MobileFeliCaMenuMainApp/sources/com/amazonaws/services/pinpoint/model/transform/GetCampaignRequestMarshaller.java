package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignRequestMarshaller implements Marshaller<Request<GetCampaignRequest>, GetCampaignRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignRequest> marshall(GetCampaignRequest getCampaignRequest) {
        if (getCampaignRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/campaigns/{campaign-id}".replace("{application-id}", getCampaignRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignRequest.getApplicationId())).replace("{campaign-id}", getCampaignRequest.getCampaignId() != null ? StringUtils.fromString(getCampaignRequest.getCampaignId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
