package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.WriteTreatmentResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class WriteTreatmentResourceJsonUnmarshaller implements Unmarshaller<WriteTreatmentResource, JsonUnmarshallerContext> {
    private static WriteTreatmentResourceJsonUnmarshaller instance;

    WriteTreatmentResourceJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public WriteTreatmentResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        WriteTreatmentResource writeTreatmentResource = new WriteTreatmentResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("MessageConfiguration")) {
                writeTreatmentResource.setMessageConfiguration(MessageConfigurationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Schedule")) {
                writeTreatmentResource.setSchedule(ScheduleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SizePercent")) {
                writeTreatmentResource.setSizePercent(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentDescription")) {
                writeTreatmentResource.setTreatmentDescription(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentName")) {
                writeTreatmentResource.setTreatmentName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeTreatmentResource;
    }

    public static WriteTreatmentResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteTreatmentResourceJsonUnmarshaller();
        }
        return instance;
    }
}
