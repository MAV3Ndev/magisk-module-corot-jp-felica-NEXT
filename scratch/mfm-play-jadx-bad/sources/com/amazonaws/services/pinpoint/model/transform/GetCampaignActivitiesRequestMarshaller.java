package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignActivitiesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignActivitiesRequestMarshaller implements Marshaller<Request<GetCampaignActivitiesRequest>, GetCampaignActivitiesRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignActivitiesRequest> marshall(GetCampaignActivitiesRequest getCampaignActivitiesRequest) {
        if (getCampaignActivitiesRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignActivitiesRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignActivitiesRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/campaigns/{campaign-id}/activities".replace("{application-id}", getCampaignActivitiesRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignActivitiesRequest.getApplicationId())).replace("{campaign-id}", getCampaignActivitiesRequest.getCampaignId() != null ? StringUtils.fromString(getCampaignActivitiesRequest.getCampaignId()) : "");
        if (getCampaignActivitiesRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getCampaignActivitiesRequest.getPageSize()));
        }
        if (getCampaignActivitiesRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getCampaignActivitiesRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
