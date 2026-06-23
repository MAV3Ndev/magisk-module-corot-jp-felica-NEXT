package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.services.pinpoint.model.WriteTreatmentResource;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class WriteTreatmentResourceJsonMarshaller {
    private static WriteTreatmentResourceJsonMarshaller instance;

    WriteTreatmentResourceJsonMarshaller() {
    }

    public void marshall(WriteTreatmentResource writeTreatmentResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (writeTreatmentResource.getMessageConfiguration() != null) {
            MessageConfiguration messageConfiguration = writeTreatmentResource.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            MessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (writeTreatmentResource.getSchedule() != null) {
            Schedule schedule = writeTreatmentResource.getSchedule();
            awsJsonWriter.name("Schedule");
            ScheduleJsonMarshaller.getInstance().marshall(schedule, awsJsonWriter);
        }
        if (writeTreatmentResource.getSizePercent() != null) {
            Integer sizePercent = writeTreatmentResource.getSizePercent();
            awsJsonWriter.name("SizePercent");
            awsJsonWriter.value(sizePercent);
        }
        if (writeTreatmentResource.getTreatmentDescription() != null) {
            String treatmentDescription = writeTreatmentResource.getTreatmentDescription();
            awsJsonWriter.name("TreatmentDescription");
            awsJsonWriter.value(treatmentDescription);
        }
        if (writeTreatmentResource.getTreatmentName() != null) {
            String treatmentName = writeTreatmentResource.getTreatmentName();
            awsJsonWriter.name("TreatmentName");
            awsJsonWriter.value(treatmentName);
        }
        awsJsonWriter.endObject();
    }

    public static WriteTreatmentResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new WriteTreatmentResourceJsonMarshaller();
        }
        return instance;
    }
}
