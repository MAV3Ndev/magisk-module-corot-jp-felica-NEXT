package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AttributesResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class AttributesResourceJsonUnmarshaller implements Unmarshaller<AttributesResource, JsonUnmarshallerContext> {
    private static AttributesResourceJsonUnmarshaller instance;

    AttributesResourceJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public AttributesResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        AttributesResource attributesResource = new AttributesResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApplicationId")) {
                attributesResource.setApplicationId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("AttributeType")) {
                attributesResource.setAttributeType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Attributes")) {
                attributesResource.setAttributes(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return attributesResource;
    }

    public static AttributesResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AttributesResourceJsonUnmarshaller();
        }
        return instance;
    }
}
