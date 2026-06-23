package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.SegmentDemographics;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentDemographicsJsonUnmarshaller implements Unmarshaller<SegmentDemographics, JsonUnmarshallerContext> {
    private static SegmentDemographicsJsonUnmarshaller instance;

    SegmentDemographicsJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentDemographics unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentDemographics segmentDemographics = new SegmentDemographics();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("AppVersion")) {
                segmentDemographics.setAppVersion(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Channel")) {
                segmentDemographics.setChannel(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DeviceType")) {
                segmentDemographics.setDeviceType(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Make")) {
                segmentDemographics.setMake(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Model")) {
                segmentDemographics.setModel(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(DataRecordKey.PLATFORM)) {
                segmentDemographics.setPlatform(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentDemographics;
    }

    public static SegmentDemographicsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentDemographicsJsonUnmarshaller();
        }
        return instance;
    }
}
