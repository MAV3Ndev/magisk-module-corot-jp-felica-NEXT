package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteEmailChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEmailChannelRequestMarshaller implements Marshaller<Request<DeleteEmailChannelRequest>, DeleteEmailChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteEmailChannelRequest> marshall(DeleteEmailChannelRequest deleteEmailChannelRequest) {
        if (deleteEmailChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteEmailChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteEmailChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/email".replace("{application-id}", deleteEmailChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteEmailChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
