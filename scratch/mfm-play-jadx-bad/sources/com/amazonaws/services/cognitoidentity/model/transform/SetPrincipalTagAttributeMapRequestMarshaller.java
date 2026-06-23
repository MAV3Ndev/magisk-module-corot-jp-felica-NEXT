package com.amazonaws.services.cognitoidentity.model.transform;

import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.SetPrincipalTagAttributeMapRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SetPrincipalTagAttributeMapRequestMarshaller implements Marshaller<Request<SetPrincipalTagAttributeMapRequest>, SetPrincipalTagAttributeMapRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<SetPrincipalTagAttributeMapRequest> marshall(SetPrincipalTagAttributeMapRequest setPrincipalTagAttributeMapRequest) {
        if (setPrincipalTagAttributeMapRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(SetPrincipalTagAttributeMapRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(setPrincipalTagAttributeMapRequest, "AmazonCognitoIdentity");
        defaultRequest.addHeader("X-Amz-Target", "AWSCognitoIdentityService.SetPrincipalTagAttributeMap");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath(DomExceptionUtils.SEPARATOR);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (setPrincipalTagAttributeMapRequest.getIdentityPoolId() != null) {
                String identityPoolId = setPrincipalTagAttributeMapRequest.getIdentityPoolId();
                jsonWriter.name("IdentityPoolId");
                jsonWriter.value(identityPoolId);
            }
            if (setPrincipalTagAttributeMapRequest.getIdentityProviderName() != null) {
                String identityProviderName = setPrincipalTagAttributeMapRequest.getIdentityProviderName();
                jsonWriter.name("IdentityProviderName");
                jsonWriter.value(identityProviderName);
            }
            if (setPrincipalTagAttributeMapRequest.getUseDefaults() != null) {
                Boolean useDefaults = setPrincipalTagAttributeMapRequest.getUseDefaults();
                jsonWriter.name("UseDefaults");
                jsonWriter.value(useDefaults.booleanValue());
            }
            if (setPrincipalTagAttributeMapRequest.getPrincipalTags() != null) {
                Map<String, String> principalTags = setPrincipalTagAttributeMapRequest.getPrincipalTags();
                jsonWriter.name("PrincipalTags");
                jsonWriter.beginObject();
                for (Map.Entry<String, String> entry : principalTags.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        jsonWriter.name(entry.getKey());
                        jsonWriter.value(value);
                    }
                }
                jsonWriter.endObject();
            }
            jsonWriter.endObject();
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
