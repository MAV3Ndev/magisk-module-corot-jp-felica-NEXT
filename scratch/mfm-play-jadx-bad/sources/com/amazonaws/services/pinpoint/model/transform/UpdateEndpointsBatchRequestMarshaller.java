package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.UpdateEndpointsBatchRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointsBatchRequestMarshaller implements Marshaller<Request<UpdateEndpointsBatchRequest>, UpdateEndpointsBatchRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<UpdateEndpointsBatchRequest> marshall(UpdateEndpointsBatchRequest updateEndpointsBatchRequest) {
        if (updateEndpointsBatchRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateEndpointsBatchRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(updateEndpointsBatchRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.PUT);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/endpoints".replace("{application-id}", updateEndpointsBatchRequest.getApplicationId() == null ? "" : StringUtils.fromString(updateEndpointsBatchRequest.getApplicationId())));
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            if (updateEndpointsBatchRequest.getEndpointBatchRequest() != null) {
                EndpointBatchRequestJsonMarshaller.getInstance().marshall(updateEndpointsBatchRequest.getEndpointBatchRequest(), jsonWriter);
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
