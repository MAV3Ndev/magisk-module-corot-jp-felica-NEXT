package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ActivitiesResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ActivitiesResponseJsonUnmarshaller implements Unmarshaller<ActivitiesResponse, JsonUnmarshallerContext> {
    private static ActivitiesResponseJsonUnmarshaller instance;

    ActivitiesResponseJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ActivitiesResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ActivitiesResponse activitiesResponse = new ActivitiesResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Item")) {
                activitiesResponse.setItem(new ListUnmarshaller(ActivityResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("NextToken")) {
                activitiesResponse.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return activitiesResponse;
    }

    public static ActivitiesResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ActivitiesResponseJsonUnmarshaller();
        }
        return instance;
    }
}
