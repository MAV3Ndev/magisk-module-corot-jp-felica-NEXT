package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteApplicationSettingsRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class WriteApplicationSettingsRequestJsonUnmarshaller implements Unmarshaller<WriteApplicationSettingsRequest, JsonUnmarshallerContext> {
    private static WriteApplicationSettingsRequestJsonUnmarshaller instance;

    WriteApplicationSettingsRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public WriteApplicationSettingsRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        WriteApplicationSettingsRequest writeApplicationSettingsRequest = new WriteApplicationSettingsRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("CampaignHook")) {
                writeApplicationSettingsRequest.setCampaignHook(CampaignHookJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CloudWatchMetricsEnabled")) {
                writeApplicationSettingsRequest.setCloudWatchMetricsEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Limits")) {
                writeApplicationSettingsRequest.setLimits(CampaignLimitsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("QuietTime")) {
                writeApplicationSettingsRequest.setQuietTime(QuietTimeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeApplicationSettingsRequest;
    }

    public static WriteApplicationSettingsRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteApplicationSettingsRequestJsonUnmarshaller();
        }
        return instance;
    }
}
