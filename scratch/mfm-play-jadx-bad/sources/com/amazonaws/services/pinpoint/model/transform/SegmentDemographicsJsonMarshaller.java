package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.SegmentDemographics;
import com.amazonaws.services.pinpoint.model.SetDimension;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SegmentDemographicsJsonMarshaller {
    private static SegmentDemographicsJsonMarshaller instance;

    SegmentDemographicsJsonMarshaller() {
    }

    public void marshall(SegmentDemographics segmentDemographics, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentDemographics.getAppVersion() != null) {
            SetDimension appVersion = segmentDemographics.getAppVersion();
            awsJsonWriter.name("AppVersion");
            SetDimensionJsonMarshaller.getInstance().marshall(appVersion, awsJsonWriter);
        }
        if (segmentDemographics.getChannel() != null) {
            SetDimension channel = segmentDemographics.getChannel();
            awsJsonWriter.name("Channel");
            SetDimensionJsonMarshaller.getInstance().marshall(channel, awsJsonWriter);
        }
        if (segmentDemographics.getDeviceType() != null) {
            SetDimension deviceType = segmentDemographics.getDeviceType();
            awsJsonWriter.name("DeviceType");
            SetDimensionJsonMarshaller.getInstance().marshall(deviceType, awsJsonWriter);
        }
        if (segmentDemographics.getMake() != null) {
            SetDimension make = segmentDemographics.getMake();
            awsJsonWriter.name("Make");
            SetDimensionJsonMarshaller.getInstance().marshall(make, awsJsonWriter);
        }
        if (segmentDemographics.getModel() != null) {
            SetDimension model = segmentDemographics.getModel();
            awsJsonWriter.name("Model");
            SetDimensionJsonMarshaller.getInstance().marshall(model, awsJsonWriter);
        }
        if (segmentDemographics.getPlatform() != null) {
            SetDimension platform = segmentDemographics.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            SetDimensionJsonMarshaller.getInstance().marshall(platform, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentDemographicsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentDemographicsJsonMarshaller();
        }
        return instance;
    }
}
