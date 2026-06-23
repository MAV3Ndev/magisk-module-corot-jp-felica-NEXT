package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteEventStream;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class WriteEventStreamJsonUnmarshaller implements Unmarshaller<WriteEventStream, JsonUnmarshallerContext> {
    private static WriteEventStreamJsonUnmarshaller instance;

    WriteEventStreamJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public WriteEventStream unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        WriteEventStream writeEventStream = new WriteEventStream();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("DestinationStreamArn")) {
                writeEventStream.setDestinationStreamArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RoleArn")) {
                writeEventStream.setRoleArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeEventStream;
    }

    public static WriteEventStreamJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteEventStreamJsonUnmarshaller();
        }
        return instance;
    }
}
