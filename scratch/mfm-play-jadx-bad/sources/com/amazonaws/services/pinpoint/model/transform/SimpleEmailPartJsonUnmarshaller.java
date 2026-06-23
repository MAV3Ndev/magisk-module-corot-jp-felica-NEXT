package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SimpleEmailPart;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SimpleEmailPartJsonUnmarshaller implements Unmarshaller<SimpleEmailPart, JsonUnmarshallerContext> {
    private static SimpleEmailPartJsonUnmarshaller instance;

    SimpleEmailPartJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SimpleEmailPart unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SimpleEmailPart simpleEmailPart = new SimpleEmailPart();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Charset")) {
                simpleEmailPart.setCharset(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Data")) {
                simpleEmailPart.setData(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return simpleEmailPart;
    }

    public static SimpleEmailPartJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SimpleEmailPartJsonUnmarshaller();
        }
        return instance;
    }
}
