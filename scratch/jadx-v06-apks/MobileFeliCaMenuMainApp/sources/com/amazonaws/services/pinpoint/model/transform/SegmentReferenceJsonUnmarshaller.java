package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.SegmentReference;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentReferenceJsonUnmarshaller implements Unmarshaller<SegmentReference, JsonUnmarshallerContext> {
    private static SegmentReferenceJsonUnmarshaller instance;

    SegmentReferenceJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentReference unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentReference segmentReference = new SegmentReference();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(JsonDocumentFields.POLICY_ID)) {
                segmentReference.setId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(JsonDocumentFields.VERSION)) {
                segmentReference.setVersion(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentReference;
    }

    public static SegmentReferenceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentReferenceJsonUnmarshaller();
        }
        return instance;
    }
}
