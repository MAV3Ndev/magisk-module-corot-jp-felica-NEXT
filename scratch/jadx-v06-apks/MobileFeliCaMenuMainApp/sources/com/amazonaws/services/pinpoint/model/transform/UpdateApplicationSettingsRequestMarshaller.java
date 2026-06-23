package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpoint.model.UpdateApplicationSettingsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApplicationSettingsRequestMarshaller implements Marshaller<Request<UpdateApplicationSettingsRequest>, UpdateApplicationSettingsRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<UpdateApplicationSettingsRequest> marshall(UpdateApplicationSettingsRequest updateApplicationSettingsRequest) {
        if (updateApplicationSettingsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateApplicationSettingsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(updateApplicationSettingsRequest, "AmazonPinpoint");
        defaultRequest.setHttpMethod(HttpMethodName.PUT);
        defaultRequest.setResourcePath("/v1/apps/{application-id}/settings".replace("{application-id}", updateApplicationSettingsRequest.getApplicationId() == null ? "" : StringUtils.fromString(updateApplicationSettingsRequest.getApplicationId())));
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            if (updateApplicationSettingsRequest.getWriteApplicationSettingsRequest() != null) {
                WriteApplicationSettingsRequestJsonMarshaller.getInstance().marshall(updateApplicationSettingsRequest.getWriteApplicationSettingsRequest(), jsonWriter);
            }
            jsonWriter.close();
            String string = stringWriter.toString();
            byte[] bytes = string.getBytes(StringUtils.UTF8);
            defaultRequest.setContent(new StringInputStream(string));
            defaultRequest.addHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(bytes.length));
            if (!defaultRequest.getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
                defaultRequest.addHeader(HttpHeader.CONTENT_TYPE, "application/x-amz-json-1.1");
            }
            return defaultRequest;
        } catch (Throwable th) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + th.getMessage(), th);
        }
    }
}
