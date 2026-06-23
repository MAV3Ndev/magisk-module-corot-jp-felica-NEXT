package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentityprovider.model.GetSigningCertificateRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class GetSigningCertificateRequestMarshaller implements Marshaller<Request<GetSigningCertificateRequest>, GetSigningCertificateRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<GetSigningCertificateRequest> marshall(GetSigningCertificateRequest getSigningCertificateRequest) {
        if (getSigningCertificateRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSigningCertificateRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(getSigningCertificateRequest, "AmazonCognitoIdentityProvider");
        defaultRequest.addHeader("X-Amz-Target", "AWSCognitoIdentityProviderService.GetSigningCertificate");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath("/");
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (getSigningCertificateRequest.getUserPoolId() != null) {
                String userPoolId = getSigningCertificateRequest.getUserPoolId();
                jsonWriter.name("UserPoolId");
                jsonWriter.value(userPoolId);
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
