package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ExportJobResponse;
import com.amazonaws.services.pinpoint.model.ExportJobsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ExportJobsResponseJsonMarshaller {
    private static ExportJobsResponseJsonMarshaller instance;

    ExportJobsResponseJsonMarshaller() {
    }

    public void marshall(ExportJobsResponse exportJobsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (exportJobsResponse.getItem() != null) {
            List<ExportJobResponse> item = exportJobsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (ExportJobResponse exportJobResponse : item) {
                if (exportJobResponse != null) {
                    ExportJobResponseJsonMarshaller.getInstance().marshall(exportJobResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (exportJobsResponse.getNextToken() != null) {
            String nextToken = exportJobsResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static ExportJobsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobsResponseJsonMarshaller();
        }
        return instance;
    }
}
