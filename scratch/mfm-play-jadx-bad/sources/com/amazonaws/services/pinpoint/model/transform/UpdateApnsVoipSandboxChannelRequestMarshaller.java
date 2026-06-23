package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.UpdateApnsVoipSandboxChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipSandboxChannelRequestMarshaller implements Marshaller<Request<UpdateApnsVoipSandboxChannelRequest>, UpdateApnsVoipSandboxChannelRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<UpdateApnsVoipSandboxChannelRequest> marshall(UpdateApnsVoipSandboxChannelRequest updateApnsVoipSandboxChannelRequest) {
        if (updateApnsVoipSandboxChannelRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateApnsVoipSandboxChannelRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(updateApnsVoipSandboxChannelRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.PUT);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/channels/apns_voip_sandbox".replace("{application-id}", updateApnsVoipSandboxChannelRequest.getApplicationId() == null ? "" : StringUtils.fromString(updateApnsVoipSandboxChannelRequest.getApplicationId())));
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            if (updateApnsVoipSandboxChannelRequest.getAPNSVoipSandboxChannelRequest() != null) {
                APNSVoipSandboxChannelRequestJsonMarshaller.getInstance().marshall(updateApnsVoipSandboxChannelRequest.getAPNSVoipSandboxChannelRequest(), jsonWriter);
            }
            jsonWriter.close();
            String string = stringWriter.toString();
            byte[] bytes = string.getBytes(StringUtils.UTF8);
            defaultRequest.setContent(new StringInputStream(string));
            defaultRequest.addHeader("Content-Length", Integer.toString(bytes.length));
            if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
                defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.1");
            }
            return defaultRequest;
        } catch (Throwable th) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + th.getMessage(), th);
        }
    }
}
