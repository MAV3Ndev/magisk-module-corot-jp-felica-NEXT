package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignRequestMarshaller implements Marshaller<Request<GetCampaignRequest>, GetCampaignRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignRequest> marshall(GetCampaignRequest getCampaignRequest) {
        if (getCampaignRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/campaigns/{campaign-id}".replace("{application-id}", getCampaignRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignRequest.getApplicationId())).replace("{campaign-id}", getCampaignRequest.getCampaignId() != null ? StringUtils.fromString(getCampaignRequest.getCampaignId()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
