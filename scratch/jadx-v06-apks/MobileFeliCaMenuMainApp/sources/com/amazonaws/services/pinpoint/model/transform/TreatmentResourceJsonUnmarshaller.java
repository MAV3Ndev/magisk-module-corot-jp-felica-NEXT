package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.TreatmentResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class TreatmentResourceJsonUnmarshaller implements Unmarshaller<TreatmentResource, JsonUnmarshallerContext> {
    private static TreatmentResourceJsonUnmarshaller instance;

    TreatmentResourceJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public TreatmentResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        TreatmentResource treatmentResource = new TreatmentResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(JsonDocumentFields.POLICY_ID)) {
                treatmentResource.setId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageConfiguration")) {
                treatmentResource.setMessageConfiguration(MessageConfigurationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Schedule")) {
                treatmentResource.setSchedule(ScheduleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SizePercent")) {
                treatmentResource.setSizePercent(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("State")) {
                treatmentResource.setState(CampaignStateJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentDescription")) {
                treatmentResource.setTreatmentDescription(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TreatmentName")) {
                treatmentResource.setTreatmentName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return treatmentResource;
    }

    public static TreatmentResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TreatmentResourceJsonUnmarshaller();
        }
        return instance;
    }
}
