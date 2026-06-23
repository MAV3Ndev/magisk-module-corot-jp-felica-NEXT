package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentResponse;
import com.amazonaws.services.pinpoint.model.SegmentsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SegmentsResponseJsonMarshaller {
    private static SegmentsResponseJsonMarshaller instance;

    SegmentsResponseJsonMarshaller() {
    }

    public void marshall(SegmentsResponse segmentsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentsResponse.getItem() != null) {
            List<SegmentResponse> item = segmentsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (SegmentResponse segmentResponse : item) {
                if (segmentResponse != null) {
                    SegmentResponseJsonMarshaller.getInstance().marshall(segmentResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (segmentsResponse.getNextToken() != null) {
            String nextToken = segmentsResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentsResponseJsonMarshaller();
        }
        return instance;
    }
}
