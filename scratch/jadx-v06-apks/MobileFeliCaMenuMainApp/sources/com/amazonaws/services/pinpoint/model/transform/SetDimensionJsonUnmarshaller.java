package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SetDimension;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SetDimensionJsonUnmarshaller implements Unmarshaller<SetDimension, JsonUnmarshallerContext> {
    private static SetDimensionJsonUnmarshaller instance;

    SetDimensionJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SetDimension unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SetDimension setDimension = new SetDimension();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("DimensionType")) {
                setDimension.setDimensionType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Values")) {
                setDimension.setValues(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return setDimension;
    }

    public static SetDimensionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SetDimensionJsonUnmarshaller();
        }
        return instance;
    }
}
