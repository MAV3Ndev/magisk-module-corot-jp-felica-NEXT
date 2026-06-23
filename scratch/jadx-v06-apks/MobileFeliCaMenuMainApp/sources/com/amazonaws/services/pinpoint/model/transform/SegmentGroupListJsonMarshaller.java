package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentGroup;
import com.amazonaws.services.pinpoint.model.SegmentGroupList;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SegmentGroupListJsonMarshaller {
    private static SegmentGroupListJsonMarshaller instance;

    SegmentGroupListJsonMarshaller() {
    }

    public void marshall(SegmentGroupList segmentGroupList, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentGroupList.getGroups() != null) {
            List<SegmentGroup> groups = segmentGroupList.getGroups();
            awsJsonWriter.name("Groups");
            awsJsonWriter.beginArray();
            for (SegmentGroup segmentGroup : groups) {
                if (segmentGroup != null) {
                    SegmentGroupJsonMarshaller.getInstance().marshall(segmentGroup, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (segmentGroupList.getInclude() != null) {
            String include = segmentGroupList.getInclude();
            awsJsonWriter.name("Include");
            awsJsonWriter.value(include);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentGroupListJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentGroupListJsonMarshaller();
        }
        return instance;
    }
}
