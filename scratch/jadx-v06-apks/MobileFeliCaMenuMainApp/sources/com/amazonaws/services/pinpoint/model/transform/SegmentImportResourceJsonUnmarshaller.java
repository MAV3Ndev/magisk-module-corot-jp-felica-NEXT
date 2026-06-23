package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentImportResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentImportResourceJsonUnmarshaller implements Unmarshaller<SegmentImportResource, JsonUnmarshallerContext> {
    private static SegmentImportResourceJsonUnmarshaller instance;

    SegmentImportResourceJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentImportResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentImportResource segmentImportResource = new SegmentImportResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ChannelCounts")) {
                segmentImportResource.setChannelCounts(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ExternalId")) {
                segmentImportResource.setExternalId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Format")) {
                segmentImportResource.setFormat(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RoleArn")) {
                segmentImportResource.setRoleArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("S3Url")) {
                segmentImportResource.setS3Url(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Size")) {
                segmentImportResource.setSize(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentImportResource;
    }

    public static SegmentImportResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentImportResourceJsonUnmarshaller();
        }
        return instance;
    }
}
