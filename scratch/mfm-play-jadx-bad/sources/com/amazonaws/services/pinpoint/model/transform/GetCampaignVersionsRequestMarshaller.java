package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionsRequestMarshaller implements Marshaller<Request<GetCampaignVersionsRequest>, GetCampaignVersionsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignVersionsRequest> marshall(GetCampaignVersionsRequest getCampaignVersionsRequest) {
        if (getCampaignVersionsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignVersionsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignVersionsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/campaigns/{campaign-id}/versions".replace("{application-id}", getCampaignVersionsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignVersionsRequest.getApplicationId())).replace("{campaign-id}", getCampaignVersionsRequest.getCampaignId() != null ? StringUtils.fromString(getCampaignVersionsRequest.getCampaignId()) : "");
        if (getCampaignVersionsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getCampaignVersionsRequest.getPageSize()));
        }
        if (getCampaignVersionsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getCampaignVersionsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
