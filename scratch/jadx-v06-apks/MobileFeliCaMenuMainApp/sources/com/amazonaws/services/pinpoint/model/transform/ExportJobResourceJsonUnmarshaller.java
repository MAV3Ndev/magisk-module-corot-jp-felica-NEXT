package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ExportJobResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ExportJobResourceJsonUnmarshaller implements Unmarshaller<ExportJobResource, JsonUnmarshallerContext> {
    private static ExportJobResourceJsonUnmarshaller instance;

    ExportJobResourceJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public ExportJobResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ExportJobResource exportJobResource = new ExportJobResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("RoleArn")) {
                exportJobResource.setRoleArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("S3UrlPrefix")) {
                exportJobResource.setS3UrlPrefix(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentId")) {
                exportJobResource.setSegmentId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentVersion")) {
                exportJobResource.setSegmentVersion(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return exportJobResource;
    }

    public static ExportJobResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobResourceJsonUnmarshaller();
        }
        return instance;
    }
}
