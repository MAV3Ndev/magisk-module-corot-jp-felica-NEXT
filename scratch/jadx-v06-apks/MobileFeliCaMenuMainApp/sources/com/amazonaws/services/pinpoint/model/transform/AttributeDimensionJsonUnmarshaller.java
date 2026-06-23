package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AttributeDimension;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class AttributeDimensionJsonUnmarshaller implements Unmarshaller<AttributeDimension, JsonUnmarshallerContext> {
    private static AttributeDimensionJsonUnmarshaller instance;

    AttributeDimensionJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public AttributeDimension unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        AttributeDimension attributeDimension = new AttributeDimension();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("AttributeType")) {
                attributeDimension.setAttributeType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Values")) {
                attributeDimension.setValues(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return attributeDimension;
    }

    public static AttributeDimensionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeDimensionJsonUnmarshaller();
        }
        return instance;
    }
}
