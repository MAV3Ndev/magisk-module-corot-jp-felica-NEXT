package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.PutEventsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
public class PutEventsResultJsonUnmarshaller implements Unmarshaller<PutEventsResult, JsonUnmarshallerContext> {
    private static PutEventsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public PutEventsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        PutEventsResult putEventsResult = new PutEventsResult();
        putEventsResult.setEventsResponse(EventsResponseJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
        return putEventsResult;
    }

    public static PutEventsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PutEventsResultJsonUnmarshaller();
        }
        return instance;
    }
}
