package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentityprovider.model.ForgetDeviceRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class ForgetDeviceRequestMarshaller implements Marshaller<Request<ForgetDeviceRequest>, ForgetDeviceRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<ForgetDeviceRequest> marshall(ForgetDeviceRequest forgetDeviceRequest) {
        if (forgetDeviceRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(ForgetDeviceRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(forgetDeviceRequest, "AmazonCognitoIdentityProvider");
        defaultRequest.addHeader("X-Amz-Target", "AWSCognitoIdentityProviderService.ForgetDevice");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath("/");
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (forgetDeviceRequest.getAccessToken() != null) {
                String accessToken = forgetDeviceRequest.getAccessToken();
                jsonWriter.name("AccessToken");
                jsonWriter.value(accessToken);
            }
            if (forgetDeviceRequest.getDeviceKey() != null) {
                String deviceKey = forgetDeviceRequest.getDeviceKey();
                jsonWriter.name("DeviceKey");
                jsonWriter.value(deviceKey);
            }
            jsonWriter.endObject();
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
