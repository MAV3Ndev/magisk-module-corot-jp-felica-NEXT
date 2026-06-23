package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteSegmentRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteSegmentRequestMarshaller implements Marshaller<Request<DeleteSegmentRequest>, DeleteSegmentRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteSegmentRequest> marshall(DeleteSegmentRequest deleteSegmentRequest) {
        if (deleteSegmentRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteSegmentRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteSegmentRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/segments/{segment-id}".replace("{application-id}", deleteSegmentRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteSegmentRequest.getApplicationId())).replace("{segment-id}", deleteSegmentRequest.getSegmentId() != null ? StringUtils.fromString(deleteSegmentRequest.getSegmentId()) : ""));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
