package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.CampaignState;
import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.services.pinpoint.model.TreatmentResource;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class TreatmentResourceJsonMarshaller {
    private static TreatmentResourceJsonMarshaller instance;

    TreatmentResourceJsonMarshaller() {
    }

    public void marshall(TreatmentResource treatmentResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (treatmentResource.getId() != null) {
            String id = treatmentResource.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (treatmentResource.getMessageConfiguration() != null) {
            MessageConfiguration messageConfiguration = treatmentResource.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            MessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (treatmentResource.getSchedule() != null) {
            Schedule schedule = treatmentResource.getSchedule();
            awsJsonWriter.name("Schedule");
            ScheduleJsonMarshaller.getInstance().marshall(schedule, awsJsonWriter);
        }
        if (treatmentResource.getSizePercent() != null) {
            Integer sizePercent = treatmentResource.getSizePercent();
            awsJsonWriter.name("SizePercent");
            awsJsonWriter.value(sizePercent);
        }
        if (treatmentResource.getState() != null) {
            CampaignState state = treatmentResource.getState();
            awsJsonWriter.name("State");
            CampaignStateJsonMarshaller.getInstance().marshall(state, awsJsonWriter);
        }
        if (treatmentResource.getTreatmentDescription() != null) {
            String treatmentDescription = treatmentResource.getTreatmentDescription();
            awsJsonWriter.name("TreatmentDescription");
            awsJsonWriter.value(treatmentDescription);
        }
        if (treatmentResource.getTreatmentName() != null) {
            String treatmentName = treatmentResource.getTreatmentName();
            awsJsonWriter.name("TreatmentName");
            awsJsonWriter.value(treatmentName);
        }
        awsJsonWriter.endObject();
    }

    public static TreatmentResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new TreatmentResourceJsonMarshaller();
        }
        return instance;
    }
}
