package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteGcmChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteGcmChannelRequestMarshaller implements Marshaller<Request<DeleteGcmChannelRequest>, DeleteGcmChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteGcmChannelRequest> marshall(DeleteGcmChannelRequest deleteGcmChannelRequest) {
        if (deleteGcmChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteGcmChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteGcmChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/gcm".replace("{application-id}", deleteGcmChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteGcmChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
