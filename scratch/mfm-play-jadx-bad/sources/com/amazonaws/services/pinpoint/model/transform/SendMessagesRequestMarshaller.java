package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.SendMessagesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class SendMessagesRequestMarshaller implements Marshaller<Request<SendMessagesRequest>, SendMessagesRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<SendMessagesRequest> marshall(SendMessagesRequest sendMessagesRequest) {
        if (sendMessagesRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(SendMessagesRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(sendMessagesRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/messages".replace("{application-id}", sendMessagesRequest.getApplicationId() == null ? "" : StringUtils.fromString(sendMessagesRequest.getApplicationId())));
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            if (sendMessagesRequest.getMessageRequest() != null) {
                MessageRequestJsonMarshaller.getInstance().marshall(sendMessagesRequest.getMessageRequest(), jsonWriter);
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
