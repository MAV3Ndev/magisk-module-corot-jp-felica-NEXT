package com.amazonaws.services.pinpointanalytics.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.pinpointanalytics.model.Event;
import com.amazonaws.services.pinpointanalytics.model.PutEventsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.google.common.net.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
public class PutEventsRequestMarshaller implements Marshaller<Request<PutEventsRequest>, PutEventsRequest> {
    /* JADX DEBUG: Method merged with bridge method: marshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Marshaller
    public Request<PutEventsRequest> marshall(PutEventsRequest putEventsRequest) {
        if (putEventsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(PutEventsRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(putEventsRequest, "AmazonPinpointAnalytics");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        if (putEventsRequest.getClientContext() != null) {
            defaultRequest.addHeader("x-amz-Client-Context", StringUtils.fromString(putEventsRequest.getClientContext()));
        }
        if (putEventsRequest.getClientContextEncoding() != null) {
            defaultRequest.addHeader("x-amz-Client-Context-Encoding", StringUtils.fromString(putEventsRequest.getClientContextEncoding()));
        }
        defaultRequest.setResourcePath("/2014-06-05/events");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream, 8192);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(gZIPOutputStream, StringUtils.UTF8);
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(outputStreamWriter);
            jsonWriter.beginObject();
            if (putEventsRequest.getEvents() != null) {
                List<Event> events = putEventsRequest.getEvents();
                jsonWriter.name("events");
                jsonWriter.beginArray();
                for (Event event : events) {
                    if (event != null) {
                        EventJsonMarshaller.getInstance().marshall(event, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            jsonWriter.endObject();
            jsonWriter.flush();
            gZIPOutputStream.finish();
            outputStreamWriter.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            defaultRequest.setContent(new ByteArrayInputStream(byteArray));
            defaultRequest.addHeader("Content-Length", Integer.toString(byteArray.length));
            defaultRequest.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip");
            if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
                defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.0");
            }
            return defaultRequest;
        } catch (Throwable th) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + th.getMessage(), th);
        }
    }
}
