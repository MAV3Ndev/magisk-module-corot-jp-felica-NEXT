package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.RemoveAttributesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class RemoveAttributesRequestMarshaller implements Marshaller<Request<RemoveAttributesRequest>, RemoveAttributesRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<RemoveAttributesRequest> marshall(RemoveAttributesRequest removeAttributesRequest) {
        if (removeAttributesRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(RemoveAttributesRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(removeAttributesRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.PUT);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/attributes/{attribute-type}".replace("{application-id}", removeAttributesRequest.getApplicationId() == null ? "" : StringUtils.fromString(removeAttributesRequest.getApplicationId())).replace("{attribute-type}", removeAttributesRequest.getAttributeType() != null ? StringUtils.fromString(removeAttributesRequest.getAttributeType()) : ""));
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            if (removeAttributesRequest.getUpdateAttributesRequest() != null) {
                UpdateAttributesRequestJsonMarshaller.getInstance().marshall(removeAttributesRequest.getUpdateAttributesRequest(), jsonWriter);
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
