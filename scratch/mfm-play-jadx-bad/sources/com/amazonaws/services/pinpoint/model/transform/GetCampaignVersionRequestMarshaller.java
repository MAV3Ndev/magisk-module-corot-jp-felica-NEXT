package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionRequestMarshaller implements Marshaller<Request<GetCampaignVersionRequest>, GetCampaignVersionRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignVersionRequest> marshall(GetCampaignVersionRequest getCampaignVersionRequest) {
        if (getCampaignVersionRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignVersionRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignVersionRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/campaigns/{campaign-id}/versions/{version}".replace("{application-id}", getCampaignVersionRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignVersionRequest.getApplicationId())).replace("{campaign-id}", getCampaignVersionRequest.getCampaignId() == null ? "" : StringUtils.fromString(getCampaignVersionRequest.getCampaignId())).replace("{version}", getCampaignVersionRequest.getVersion() != null ? StringUtils.fromString(getCampaignVersionRequest.getVersion()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
