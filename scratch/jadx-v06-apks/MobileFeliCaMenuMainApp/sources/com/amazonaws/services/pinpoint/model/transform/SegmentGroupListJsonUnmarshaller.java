package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentGroupList;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentGroupListJsonUnmarshaller implements Unmarshaller<SegmentGroupList, JsonUnmarshallerContext> {
    private static SegmentGroupListJsonUnmarshaller instance;

    SegmentGroupListJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentGroupList unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentGroupList segmentGroupList = new SegmentGroupList();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Groups")) {
                segmentGroupList.setGroups(new ListUnmarshaller(SegmentGroupJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Include")) {
                segmentGroupList.setInclude(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentGroupList;
    }

    public static SegmentGroupListJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentGroupListJsonUnmarshaller();
        }
        return instance;
    }
}
