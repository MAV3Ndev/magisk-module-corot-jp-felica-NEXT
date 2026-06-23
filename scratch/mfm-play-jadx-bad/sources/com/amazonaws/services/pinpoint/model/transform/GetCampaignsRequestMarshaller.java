package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignsRequestMarshaller implements Marshaller<Request<GetCampaignsRequest>, GetCampaignsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetCampaignsRequest> marshall(GetCampaignsRequest getCampaignsRequest) {
        if (getCampaignsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCampaignsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getCampaignsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        String strReplace = "/v1/apps/{application-id}/campaigns".replace("{application-id}", getCampaignsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getCampaignsRequest.getApplicationId()));
        if (getCampaignsRequest.getPageSize() != null) {
            defaultRequest.addParameter("page-size", StringUtils.fromString(getCampaignsRequest.getPageSize()));
        }
        if (getCampaignsRequest.getToken() != null) {
            defaultRequest.addParameter("token", StringUtils.fromString(getCampaignsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
