package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.QuietTime;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class QuietTimeJsonUnmarshaller implements Unmarshaller<QuietTime, JsonUnmarshallerContext> {
    private static QuietTimeJsonUnmarshaller instance;

    QuietTimeJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public QuietTime unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        QuietTime quietTime = new QuietTime();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("End")) {
                quietTime.setEnd(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Start")) {
                quietTime.setStart(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return quietTime;
    }

    public static QuietTimeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new QuietTimeJsonUnmarshaller();
        }
        return instance;
    }
}
