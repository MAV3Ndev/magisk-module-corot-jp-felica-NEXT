package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ActivityResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ActivityResponseJsonMarshaller {
    private static ActivityResponseJsonMarshaller instance;

    ActivityResponseJsonMarshaller() {
    }

    public void marshall(ActivityResponse activityResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (activityResponse.getApplicationId() != null) {
            String applicationId = activityResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (activityResponse.getCampaignId() != null) {
            String campaignId = activityResponse.getCampaignId();
            awsJsonWriter.name("CampaignId");
            awsJsonWriter.value(campaignId);
        }
        if (activityResponse.getEnd() != null) {
            String end = activityResponse.getEnd();
            awsJsonWriter.name("End");
            awsJsonWriter.value(end);
        }
        if (activityResponse.getId() != null) {
            String id = activityResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (activityResponse.getResult() != null) {
            String result = activityResponse.getResult();
            awsJsonWriter.name("Result");
            awsJsonWriter.value(result);
        }
        if (activityResponse.getScheduledStart() != null) {
            String scheduledStart = activityResponse.getScheduledStart();
            awsJsonWriter.name("ScheduledStart");
            awsJsonWriter.value(scheduledStart);
        }
        if (activityResponse.getStart() != null) {
            String start = activityResponse.getStart();
            awsJsonWriter.name("Start");
            awsJsonWriter.value(start);
        }
        if (activityResponse.getState() != null) {
            String state = activityResponse.getState();
            awsJsonWriter.name("State");
            awsJsonWriter.value(state);
        }
        if (activityResponse.getSuccessfulEndpointCount() != null) {
            Integer successfulEndpointCount = activityResponse.getSuccessfulEndpointCount();
            awsJsonWriter.name("SuccessfulEndpointCount");
            awsJsonWriter.value(successfulEndpointCount);
        }
        if (activityResponse.getTimezonesCompletedCount() != null) {
            Integer timezonesCompletedCount = activityResponse.getTimezonesCompletedCount();
            awsJsonWriter.name("TimezonesCompletedCount");
            awsJsonWriter.value(timezonesCompletedCount);
        }
        if (activityResponse.getTimezonesTotalCount() != null) {
            Integer timezonesTotalCount = activityResponse.getTimezonesTotalCount();
            awsJsonWriter.name("TimezonesTotalCount");
            awsJsonWriter.value(timezonesTotalCount);
        }
        if (activityResponse.getTotalEndpointCount() != null) {
            Integer totalEndpointCount = activityResponse.getTotalEndpointCount();
            awsJsonWriter.name("TotalEndpointCount");
            awsJsonWriter.value(totalEndpointCount);
        }
        if (activityResponse.getTreatmentId() != null) {
            String treatmentId = activityResponse.getTreatmentId();
            awsJsonWriter.name("TreatmentId");
            awsJsonWriter.value(treatmentId);
        }
        awsJsonWriter.endObject();
    }

    public static ActivityResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ActivityResponseJsonMarshaller();
        }
        return instance;
    }
}
