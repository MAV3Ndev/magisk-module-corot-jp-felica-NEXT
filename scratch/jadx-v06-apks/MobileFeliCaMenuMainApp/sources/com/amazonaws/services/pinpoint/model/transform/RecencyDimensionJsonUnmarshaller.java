package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RecencyDimension;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class RecencyDimensionJsonUnmarshaller implements Unmarshaller<RecencyDimension, JsonUnmarshallerContext> {
    private static RecencyDimensionJsonUnmarshaller instance;

    RecencyDimensionJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public RecencyDimension unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        RecencyDimension recencyDimension = new RecencyDimension();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Duration")) {
                recencyDimension.setDuration(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RecencyType")) {
                recencyDimension.setRecencyType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return recencyDimension;
    }

    public static RecencyDimensionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RecencyDimensionJsonUnmarshaller();
        }
        return instance;
    }
}
