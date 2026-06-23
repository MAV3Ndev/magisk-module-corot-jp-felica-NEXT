package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.GetApplicationSettingsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class GetApplicationSettingsRequestMarshaller implements Marshaller<Request<GetApplicationSettingsRequest>, GetApplicationSettingsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetApplicationSettingsRequest> marshall(GetApplicationSettingsRequest getApplicationSettingsRequest) {
        if (getApplicationSettingsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetApplicationSettingsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getApplicationSettingsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.GET);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/settings".replace("{application-id}", getApplicationSettingsRequest.getApplicationId() == null ? "" : StringUtils.fromString(getApplicationSettingsRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
