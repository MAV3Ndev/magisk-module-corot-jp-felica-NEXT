package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteAdmChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteAdmChannelRequestMarshaller implements Marshaller<Request<DeleteAdmChannelRequest>, DeleteAdmChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteAdmChannelRequest> marshall(DeleteAdmChannelRequest deleteAdmChannelRequest) {
        if (deleteAdmChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteAdmChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteAdmChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/adm".replace("{application-id}", deleteAdmChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteAdmChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
