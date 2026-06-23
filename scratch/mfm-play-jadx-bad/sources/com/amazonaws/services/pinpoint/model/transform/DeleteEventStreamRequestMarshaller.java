package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteEventStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEventStreamRequestMarshaller implements Marshaller<Request<DeleteEventStreamRequest>, DeleteEventStreamRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteEventStreamRequest> marshall(DeleteEventStreamRequest deleteEventStreamRequest) {
        if (deleteEventStreamRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteEventStreamRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteEventStreamRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/eventstream".replace("{application-id}", deleteEventStreamRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteEventStreamRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
