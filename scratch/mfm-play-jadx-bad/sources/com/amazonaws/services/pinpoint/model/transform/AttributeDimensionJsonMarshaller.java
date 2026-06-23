package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AttributeDimension;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class AttributeDimensionJsonMarshaller {
    private static AttributeDimensionJsonMarshaller instance;

    AttributeDimensionJsonMarshaller() {
    }

    public void marshall(AttributeDimension attributeDimension, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (attributeDimension.getAttributeType() != null) {
            String attributeType = attributeDimension.getAttributeType();
            awsJsonWriter.name("AttributeType");
            awsJsonWriter.value(attributeType);
        }
        if (attributeDimension.getValues() != null) {
            List<String> values = attributeDimension.getValues();
            awsJsonWriter.name("Values");
            awsJsonWriter.beginArray();
            for (String str : values) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static AttributeDimensionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeDimensionJsonMarshaller();
        }
        return instance;
    }
}
