package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignHook;
import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.services.pinpoint.model.WriteCampaignRequest;
import com.amazonaws.services.pinpoint.model.WriteTreatmentResource;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class WriteCampaignRequestJsonMarshaller {
    private static WriteCampaignRequestJsonMarshaller instance;

    WriteCampaignRequestJsonMarshaller() {
    }

    public void marshall(WriteCampaignRequest writeCampaignRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (writeCampaignRequest.getAdditionalTreatments() != null) {
            List<WriteTreatmentResource> additionalTreatments = writeCampaignRequest.getAdditionalTreatments();
            awsJsonWriter.name("AdditionalTreatments");
            awsJsonWriter.beginArray();
            for (WriteTreatmentResource writeTreatmentResource : additionalTreatments) {
                if (writeTreatmentResource != null) {
                    WriteTreatmentResourceJsonMarshaller.getInstance().marshall(writeTreatmentResource, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (writeCampaignRequest.getDescription() != null) {
            String description = writeCampaignRequest.getDescription();
            awsJsonWriter.name("Description");
            awsJsonWriter.value(description);
        }
        if (writeCampaignRequest.getHoldoutPercent() != null) {
            Integer holdoutPercent = writeCampaignRequest.getHoldoutPercent();
            awsJsonWriter.name("HoldoutPercent");
            awsJsonWriter.value(holdoutPercent);
        }
        if (writeCampaignRequest.getHook() != null) {
            CampaignHook hook = writeCampaignRequest.getHook();
            awsJsonWriter.name("Hook");
            CampaignHookJsonMarshaller.getInstance().marshall(hook, awsJsonWriter);
        }
        if (writeCampaignRequest.getIsPaused() != null) {
            Boolean isPaused = writeCampaignRequest.getIsPaused();
            awsJsonWriter.name("IsPaused");
            awsJsonWriter.value(isPaused.booleanValue());
        }
        if (writeCampaignRequest.getLimits() != null) {
            CampaignLimits limits = writeCampaignRequest.getLimits();
            awsJsonWriter.name("Limits");
            CampaignLimitsJsonMarshaller.getInstance().marshall(limits, awsJsonWriter);
        }
        if (writeCampaignRequest.getMessageConfiguration() != null) {
            MessageConfiguration messageConfiguration = writeCampaignRequest.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            MessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (writeCampaignRequest.getName() != null) {
            String name = writeCampaignRequest.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        if (writeCampaignRequest.getSchedule() != null) {
            Schedule schedule = writeCampaignRequest.getSchedule();
            awsJsonWriter.name("Schedule");
            ScheduleJsonMarshaller.getInstance().marshall(schedule, awsJsonWriter);
        }
        if (writeCampaignRequest.getSegmentId() != null) {
            String segmentId = writeCampaignRequest.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (writeCampaignRequest.getSegmentVersion() != null) {
            Integer segmentVersion = writeCampaignRequest.getSegmentVersion();
            awsJsonWriter.name("SegmentVersion");
            awsJsonWriter.value(segmentVersion);
        }
        if (writeCampaignRequest.getTreatmentDescription() != null) {
            String treatmentDescription = writeCampaignRequest.getTreatmentDescription();
            awsJsonWriter.name("TreatmentDescription");
            awsJsonWriter.value(treatmentDescription);
        }
        if (writeCampaignRequest.getTreatmentName() != null) {
            String treatmentName = writeCampaignRequest.getTreatmentName();
            awsJsonWriter.name("TreatmentName");
            awsJsonWriter.value(treatmentName);
        }
        awsJsonWriter.endObject();
    }

    public static WriteCampaignRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new WriteCampaignRequestJsonMarshaller();
        }
        return instance;
    }
}
