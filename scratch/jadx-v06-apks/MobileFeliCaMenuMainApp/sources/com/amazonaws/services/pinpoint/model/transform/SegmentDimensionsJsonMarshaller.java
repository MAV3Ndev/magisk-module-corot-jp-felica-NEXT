package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.http.HttpHeader;
import com.amazonaws.services.pinpoint.model.AttributeDimension;
import com.amazonaws.services.pinpoint.model.MetricDimension;
import com.amazonaws.services.pinpoint.model.SegmentBehaviors;
import com.amazonaws.services.pinpoint.model.SegmentDemographics;
import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.services.pinpoint.model.SegmentLocation;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class SegmentDimensionsJsonMarshaller {
    private static SegmentDimensionsJsonMarshaller instance;

    SegmentDimensionsJsonMarshaller() {
    }

    public void marshall(SegmentDimensions segmentDimensions, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentDimensions.getAttributes() != null) {
            Map<String, AttributeDimension> attributes = segmentDimensions.getAttributes();
            awsJsonWriter.name("Attributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, AttributeDimension> entry : attributes.entrySet()) {
                AttributeDimension value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    AttributeDimensionJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (segmentDimensions.getBehavior() != null) {
            SegmentBehaviors behavior = segmentDimensions.getBehavior();
            awsJsonWriter.name("Behavior");
            SegmentBehaviorsJsonMarshaller.getInstance().marshall(behavior, awsJsonWriter);
        }
        if (segmentDimensions.getDemographic() != null) {
            SegmentDemographics demographic = segmentDimensions.getDemographic();
            awsJsonWriter.name("Demographic");
            SegmentDemographicsJsonMarshaller.getInstance().marshall(demographic, awsJsonWriter);
        }
        if (segmentDimensions.getLocation() != null) {
            SegmentLocation location = segmentDimensions.getLocation();
            awsJsonWriter.name(HttpHeader.LOCATION);
            SegmentLocationJsonMarshaller.getInstance().marshall(location, awsJsonWriter);
        }
        if (segmentDimensions.getMetrics() != null) {
            Map<String, MetricDimension> metrics = segmentDimensions.getMetrics();
            awsJsonWriter.name("Metrics");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, MetricDimension> entry2 : metrics.entrySet()) {
                MetricDimension value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    MetricDimensionJsonMarshaller.getInstance().marshall(value2, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (segmentDimensions.getUserAttributes() != null) {
            Map<String, AttributeDimension> userAttributes = segmentDimensions.getUserAttributes();
            awsJsonWriter.name("UserAttributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, AttributeDimension> entry3 : userAttributes.entrySet()) {
                AttributeDimension value3 = entry3.getValue();
                if (value3 != null) {
                    awsJsonWriter.name(entry3.getKey());
                    AttributeDimensionJsonMarshaller.getInstance().marshall(value3, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static SegmentDimensionsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentDimensionsJsonMarshaller();
        }
        return instance;
    }
}
