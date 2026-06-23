package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteCampaignRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteCampaignRequestMarshaller implements Marshaller<Request<DeleteCampaignRequest>, DeleteCampaignRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteCampaignRequest> marshall(DeleteCampaignRequest deleteCampaignRequest) {
        if (deleteCampaignRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteCampaignRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteCampaignRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/campaigns/{campaign-id}".replace("{application-id}", deleteCampaignRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteCampaignRequest.getApplicationId())).replace("{campaign-id}", deleteCampaignRequest.getCampaignId() != null ? StringUtils.fromString(deleteCampaignRequest.getCampaignId()) : ""));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
