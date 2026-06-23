package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetCampaignsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;
import com.felicanetworks.mfm.main.model.internal.main.net.Protocol;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignsRequestMarshaller implements Marshaller<Request<GetCampaignsRequest>, GetCampaignsRequest> {
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
            defaultRequest.addParameter(Protocol.CLIENT_TOKEN_KEY, StringUtils.fromString(getCampaignsRequest.getToken()));
        }
        defaultRequest.setResourcePath(strReplace);
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
