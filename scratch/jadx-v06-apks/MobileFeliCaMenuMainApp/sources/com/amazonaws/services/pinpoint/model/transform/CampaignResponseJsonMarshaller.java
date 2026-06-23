package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.CampaignHook;
import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.services.pinpoint.model.CampaignResponse;
import com.amazonaws.services.pinpoint.model.CampaignState;
import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.services.pinpoint.model.TreatmentResource;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class CampaignResponseJsonMarshaller {
    private static CampaignResponseJsonMarshaller instance;

    CampaignResponseJsonMarshaller() {
    }

    public void marshall(CampaignResponse campaignResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignResponse.getAdditionalTreatments() != null) {
            List<TreatmentResource> additionalTreatments = campaignResponse.getAdditionalTreatments();
            awsJsonWriter.name("AdditionalTreatments");
            awsJsonWriter.beginArray();
            for (TreatmentResource treatmentResource : additionalTreatments) {
                if (treatmentResource != null) {
                    TreatmentResourceJsonMarshaller.getInstance().marshall(treatmentResource, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (campaignResponse.getApplicationId() != null) {
            String applicationId = campaignResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (campaignResponse.getCreationDate() != null) {
            String creationDate = campaignResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (campaignResponse.getDefaultState() != null) {
            CampaignState defaultState = campaignResponse.getDefaultState();
            awsJsonWriter.name("DefaultState");
            CampaignStateJsonMarshaller.getInstance().marshall(defaultState, awsJsonWriter);
        }
        if (campaignResponse.getDescription() != null) {
            String description = campaignResponse.getDescription();
            awsJsonWriter.name("Description");
            awsJsonWriter.value(description);
        }
        if (campaignResponse.getHoldoutPercent() != null) {
            Integer holdoutPercent = campaignResponse.getHoldoutPercent();
            awsJsonWriter.name("HoldoutPercent");
            awsJsonWriter.value(holdoutPercent);
        }
        if (campaignResponse.getHook() != null) {
            CampaignHook hook = campaignResponse.getHook();
            awsJsonWriter.name("Hook");
            CampaignHookJsonMarshaller.getInstance().marshall(hook, awsJsonWriter);
        }
        if (campaignResponse.getId() != null) {
            String id = campaignResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (campaignResponse.getIsPaused() != null) {
            Boolean isPaused = campaignResponse.getIsPaused();
            awsJsonWriter.name("IsPaused");
            awsJsonWriter.value(isPaused.booleanValue());
        }
        if (campaignResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = campaignResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (campaignResponse.getLimits() != null) {
            CampaignLimits limits = campaignResponse.getLimits();
            awsJsonWriter.name("Limits");
            CampaignLimitsJsonMarshaller.getInstance().marshall(limits, awsJsonWriter);
        }
        if (campaignResponse.getMessageConfiguration() != null) {
            MessageConfiguration messageConfiguration = campaignResponse.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            MessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (campaignResponse.getName() != null) {
            String name = campaignResponse.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        if (campaignResponse.getSchedule() != null) {
            Schedule schedule = campaignResponse.getSchedule();
            awsJsonWriter.name("Schedule");
            ScheduleJsonMarshaller.getInstance().marshall(schedule, awsJsonWriter);
        }
        if (campaignResponse.getSegmentId() != null) {
            String segmentId = campaignResponse.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (campaignResponse.getSegmentVersion() != null) {
            Integer segmentVersion = campaignResponse.getSegmentVersion();
            awsJsonWriter.name("SegmentVersion");
            awsJsonWriter.value(segmentVersion);
        }
        if (campaignResponse.getState() != null) {
            CampaignState state = campaignResponse.getState();
            awsJsonWriter.name("State");
            CampaignStateJsonMarshaller.getInstance().marshall(state, awsJsonWriter);
        }
        if (campaignResponse.getTreatmentDescription() != null) {
            String treatmentDescription = campaignResponse.getTreatmentDescription();
            awsJsonWriter.name("TreatmentDescription");
            awsJsonWriter.value(treatmentDescription);
        }
        if (campaignResponse.getTreatmentName() != null) {
            String treatmentName = campaignResponse.getTreatmentName();
            awsJsonWriter.name("TreatmentName");
            awsJsonWriter.value(treatmentName);
        }
        if (campaignResponse.getVersion() != null) {
            Integer version = campaignResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignResponseJsonMarshaller();
        }
        return instance;
    }
}
