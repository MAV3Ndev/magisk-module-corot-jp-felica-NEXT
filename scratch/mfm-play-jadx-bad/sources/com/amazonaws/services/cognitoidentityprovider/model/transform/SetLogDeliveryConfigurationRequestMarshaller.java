package com.amazonaws.services.cognitoidentityprovider.model.transform;

import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentityprovider.model.LogConfigurationType;
import com.amazonaws.services.cognitoidentityprovider.model.SetLogDeliveryConfigurationRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SetLogDeliveryConfigurationRequestMarshaller implements Marshaller<Request<SetLogDeliveryConfigurationRequest>, SetLogDeliveryConfigurationRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<SetLogDeliveryConfigurationRequest> marshall(SetLogDeliveryConfigurationRequest setLogDeliveryConfigurationRequest) {
        if (setLogDeliveryConfigurationRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(SetLogDeliveryConfigurationRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(setLogDeliveryConfigurationRequest, "AmazonCognitoIdentityProvider");
        defaultRequest.addHeader("X-Amz-Target", "AWSCognitoIdentityProviderService.SetLogDeliveryConfiguration");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath(DomExceptionUtils.SEPARATOR);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (setLogDeliveryConfigurationRequest.getUserPoolId() != null) {
                String userPoolId = setLogDeliveryConfigurationRequest.getUserPoolId();
                jsonWriter.name("UserPoolId");
                jsonWriter.value(userPoolId);
            }
            if (setLogDeliveryConfigurationRequest.getLogConfigurations() != null) {
                List<LogConfigurationType> logConfigurations = setLogDeliveryConfigurationRequest.getLogConfigurations();
                jsonWriter.name("LogConfigurations");
                jsonWriter.beginArray();
                for (LogConfigurationType logConfigurationType : logConfigurations) {
                    if (logConfigurationType != null) {
                        LogConfigurationTypeJsonMarshaller.getInstance().marshall(logConfigurationType, jsonWriter);
                    }
                }
                jsonWriter.endArray();
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
