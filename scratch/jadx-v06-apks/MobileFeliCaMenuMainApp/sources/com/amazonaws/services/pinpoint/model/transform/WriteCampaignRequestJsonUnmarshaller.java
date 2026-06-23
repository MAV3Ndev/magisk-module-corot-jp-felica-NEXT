package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteCampaignRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class WriteCampaignRequestJsonUnmarshaller implements Unmarshaller<WriteCampaignRequest, JsonUnmarshallerContext> {
    private static WriteCampaignRequestJsonUnmarshaller instance;

    WriteCampaignRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public WriteCampaignRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        WriteCampaignRequest writeCampaignRequest = new WriteCampaignRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("AdditionalTreatments")) {
                writeCampaignRequest.setAdditionalTreatments(new ListUnmarshaller(WriteTreatmentResourceJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Description")) {
                writeCampaignRequest.setDescription(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("HoldoutPercent")) {
                writeCampaignRequest.setHoldoutPercent(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Hook")) {
                writeCampaignRequest.setHook(CampaignHookJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("IsPaused")) {
                writeCampaignRequest.setIsPaused(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Limits")) {
                writeCampaignRequest.setLimits(CampaignLimitsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageConfiguration")) {
                writeCampaignRequest.setMessageConfiguration(MessageConfigurationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Name")) {
                writeCampaignRequest.setName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Schedule")) {
                writeCampaignRequest.setSchedule(ScheduleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentId")) {
                writeCampaignRequest.setSegmentId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SegmentVersion")) {
                writeCampaignRequest.setSegmentVersion(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentDescription")) {
                writeCampaignRequest.setTreatmentDescription(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentName")) {
                writeCampaignRequest.setTreatmentName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeCampaignRequest;
    }

    public static WriteCampaignRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteCampaignRequestJsonUnmarshaller();
        }
        return instance;
    }
}
