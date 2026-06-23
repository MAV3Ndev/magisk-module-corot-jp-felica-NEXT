package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetAdmChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetAdmChannelRequestMarshaller implements Marshaller<Request<GetAdmChannelRequest>, GetAdmChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetAdmChannelRequest> marshall(GetAdmChannelRequest getAdmChannelRequest) {
        if (getAdmChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetAdmChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getAdmChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/adm".replace("{application-id}", getAdmChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(getAdmChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
