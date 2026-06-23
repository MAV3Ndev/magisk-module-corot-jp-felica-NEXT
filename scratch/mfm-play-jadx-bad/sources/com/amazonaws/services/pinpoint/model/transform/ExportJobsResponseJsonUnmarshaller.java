package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ExportJobsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ExportJobsResponseJsonUnmarshaller implements Unmarshaller<ExportJobsResponse, JsonUnmarshallerContext> {
    private static ExportJobsResponseJsonUnmarshaller instance;

    ExportJobsResponseJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ExportJobsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ExportJobsResponse exportJobsResponse = new ExportJobsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Item")) {
                exportJobsResponse.setItem(new ListUnmarshaller(ExportJobResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("NextToken")) {
                exportJobsResponse.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return exportJobsResponse;
    }

    public static ExportJobsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
