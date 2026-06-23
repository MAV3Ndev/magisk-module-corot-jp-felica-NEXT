package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ImportJobsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ImportJobsResponseJsonUnmarshaller implements Unmarshaller<ImportJobsResponse, JsonUnmarshallerContext> {
    private static ImportJobsResponseJsonUnmarshaller instance;

    ImportJobsResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public ImportJobsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ImportJobsResponse importJobsResponse = new ImportJobsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Item")) {
                importJobsResponse.setItem(new ListUnmarshaller(ImportJobResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("NextToken")) {
                importJobsResponse.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return importJobsResponse;
    }

    public static ImportJobsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ImportJobsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
