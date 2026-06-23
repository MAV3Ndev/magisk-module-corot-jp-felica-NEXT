package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.SegmentResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentResponseJsonUnmarshaller implements Unmarshaller<SegmentResponse, JsonUnmarshallerContext> {
    private static SegmentResponseJsonUnmarshaller instance;

    SegmentResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentResponse segmentResponse = new SegmentResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApplicationId")) {
                segmentResponse.setApplicationId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CreationDate")) {
                segmentResponse.setCreationDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Dimensions")) {
                segmentResponse.setDimensions(SegmentDimensionsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(JsonDocumentFields.POLICY_ID)) {
                segmentResponse.setId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ImportDefinition")) {
                segmentResponse.setImportDefinition(SegmentImportResourceJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LastModifiedDate")) {
                segmentResponse.setLastModifiedDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Name")) {
                segmentResponse.setName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentGroups")) {
                segmentResponse.setSegmentGroups(SegmentGroupListJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentType")) {
                segmentResponse.setSegmentType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(JsonDocumentFields.VERSION)) {
                segmentResponse.setVersion(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentResponse;
    }

    public static SegmentResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentResponseJsonUnmarshaller();
        }
        return instance;
    }
}
