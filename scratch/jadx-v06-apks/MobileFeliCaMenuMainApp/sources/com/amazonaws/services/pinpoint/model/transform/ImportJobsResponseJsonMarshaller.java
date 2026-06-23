package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ImportJobResponse;
import com.amazonaws.services.pinpoint.model.ImportJobsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ImportJobsResponseJsonMarshaller {
    private static ImportJobsResponseJsonMarshaller instance;

    ImportJobsResponseJsonMarshaller() {
    }

    public void marshall(ImportJobsResponse importJobsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (importJobsResponse.getItem() != null) {
            List<ImportJobResponse> item = importJobsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (ImportJobResponse importJobResponse : item) {
                if (importJobResponse != null) {
                    ImportJobResponseJsonMarshaller.getInstance().marshall(importJobResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (importJobsResponse.getNextToken() != null) {
            String nextToken = importJobsResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static ImportJobsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ImportJobsResponseJsonMarshaller();
        }
        return instance;
    }
}
