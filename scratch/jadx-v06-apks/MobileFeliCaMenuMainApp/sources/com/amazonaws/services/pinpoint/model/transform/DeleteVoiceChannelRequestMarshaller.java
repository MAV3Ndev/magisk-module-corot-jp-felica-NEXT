package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.DeleteVoiceChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class DeleteVoiceChannelRequestMarshaller implements Marshaller<Request<DeleteVoiceChannelRequest>, DeleteVoiceChannelRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<DeleteVoiceChannelRequest> marshall(DeleteVoiceChannelRequest deleteVoiceChannelRequest) {
        if (deleteVoiceChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteVoiceChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteVoiceChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.DELETE);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/voice".replace("{application-id}", deleteVoiceChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(deleteVoiceChannelRequest.getApplicationId())));
        if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
            defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
        }
        return defaultRequest;
    }
}
