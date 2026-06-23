package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteBaiduChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteBaiduChannelRequestMarshaller implements Marshaller<Request<DeleteBaiduChannelRequest>, DeleteBaiduChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteBaiduChannelRequest> marshall(DeleteBaiduChannelRequest deleteBaiduChannelRequest) {
        if (deleteBaiduChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteBaiduChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteBaiduChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/baidu".replace("{application-id}", deleteBaiduChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteBaiduChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
            defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
