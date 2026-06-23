package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RawEmail;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class RawEmailJsonUnmarshaller implements Unmarshaller<RawEmail, JsonUnmarshallerContext> {
    private static RawEmailJsonUnmarshaller instance;

    RawEmailJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public RawEmail unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        RawEmail rawEmail = new RawEmail();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Data")) {
                rawEmail.setData(SimpleTypeJsonUnmarshallers.ByteBufferJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return rawEmail;
    }

    public static RawEmailJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RawEmailJsonUnmarshaller();
        }
        return instance;
    }
}
