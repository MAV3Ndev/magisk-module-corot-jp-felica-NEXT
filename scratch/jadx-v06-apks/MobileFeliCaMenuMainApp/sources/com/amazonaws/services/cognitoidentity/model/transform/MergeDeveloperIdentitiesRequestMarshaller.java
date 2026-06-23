package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.MergeDeveloperIdentitiesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class MergeDeveloperIdentitiesRequestMarshaller implements Marshaller<Request<MergeDeveloperIdentitiesRequest>, MergeDeveloperIdentitiesRequest> {
    @Override // com.amazonaws.transform.Marshaller
    public Request<MergeDeveloperIdentitiesRequest> marshall(MergeDeveloperIdentitiesRequest mergeDeveloperIdentitiesRequest) {
        if (mergeDeveloperIdentitiesRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(MergeDeveloperIdentitiesRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(mergeDeveloperIdentitiesRequest, "AmazonCognitoIdentity");
        defaultRequest.addHeader("X-Amz-Target", "AWSCognitoIdentityService.MergeDeveloperIdentities");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath("/");
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (mergeDeveloperIdentitiesRequest.getSourceUserIdentifier() != null) {
                String sourceUserIdentifier = mergeDeveloperIdentitiesRequest.getSourceUserIdentifier();
                jsonWriter.name("SourceUserIdentifier");
                jsonWriter.value(sourceUserIdentifier);
            }
            if (mergeDeveloperIdentitiesRequest.getDestinationUserIdentifier() != null) {
                String destinationUserIdentifier = mergeDeveloperIdentitiesRequest.getDestinationUserIdentifier();
                jsonWriter.name("DestinationUserIdentifier");
                jsonWriter.value(destinationUserIdentifier);
            }
            if (mergeDeveloperIdentitiesRequest.getDeveloperProviderName() != null) {
                String developerProviderName = mergeDeveloperIdentitiesRequest.getDeveloperProviderName();
                jsonWriter.name("DeveloperProviderName");
                jsonWriter.value(developerProviderName);
            }
            if (mergeDeveloperIdentitiesRequest.getIdentityPoolId() != null) {
                String identityPoolId = mergeDeveloperIdentitiesRequest.getIdentityPoolId();
                jsonWriter.name("IdentityPoolId");
                jsonWriter.value(identityPoolId);
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
