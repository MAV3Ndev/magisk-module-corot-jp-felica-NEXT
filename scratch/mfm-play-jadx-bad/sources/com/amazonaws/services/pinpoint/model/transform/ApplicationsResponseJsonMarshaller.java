package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ApplicationResponse;
import com.amazonaws.services.pinpoint.model.ApplicationsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ApplicationsResponseJsonMarshaller {
    private static ApplicationsResponseJsonMarshaller instance;

    ApplicationsResponseJsonMarshaller() {
    }

    public void marshall(ApplicationsResponse applicationsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (applicationsResponse.getItem() != null) {
            List<ApplicationResponse> item = applicationsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (ApplicationResponse applicationResponse : item) {
                if (applicationResponse != null) {
                    ApplicationResponseJsonMarshaller.getInstance().marshall(applicationResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (applicationsResponse.getNextToken() != null) {
            String nextToken = applicationsResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static ApplicationsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationsResponseJsonMarshaller();
        }
        return instance;
    }
}
