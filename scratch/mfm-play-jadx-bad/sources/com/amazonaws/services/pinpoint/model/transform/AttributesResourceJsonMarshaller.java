package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AttributesResource;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class AttributesResourceJsonMarshaller {
    private static AttributesResourceJsonMarshaller instance;

    AttributesResourceJsonMarshaller() {
    }

    public void marshall(AttributesResource attributesResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (attributesResource.getApplicationId() != null) {
            String applicationId = attributesResource.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (attributesResource.getAttributeType() != null) {
            String attributeType = attributesResource.getAttributeType();
            awsJsonWriter.name("AttributeType");
            awsJsonWriter.value(attributeType);
        }
        if (attributesResource.getAttributes() != null) {
            List<String> attributes = attributesResource.getAttributes();
            awsJsonWriter.name("Attributes");
            awsJsonWriter.beginArray();
            for (String str : attributes) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static AttributesResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AttributesResourceJsonMarshaller();
        }
        return instance;
    }
}
