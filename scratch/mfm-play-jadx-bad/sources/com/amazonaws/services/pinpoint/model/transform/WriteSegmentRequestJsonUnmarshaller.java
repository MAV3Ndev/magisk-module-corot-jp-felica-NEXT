package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteSegmentRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class WriteSegmentRequestJsonUnmarshaller implements Unmarshaller<WriteSegmentRequest, JsonUnmarshallerContext> {
    private static WriteSegmentRequestJsonUnmarshaller instance;

    WriteSegmentRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public WriteSegmentRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        WriteSegmentRequest writeSegmentRequest = new WriteSegmentRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Dimensions")) {
                writeSegmentRequest.setDimensions(SegmentDimensionsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Name")) {
                writeSegmentRequest.setName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentGroups")) {
                writeSegmentRequest.setSegmentGroups(SegmentGroupListJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeSegmentRequest;
    }

    public static WriteSegmentRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteSegmentRequestJsonUnmarshaller();
        }
        return instance;
    }
}
