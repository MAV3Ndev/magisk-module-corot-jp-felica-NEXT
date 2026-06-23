package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ExportJobResource;
import com.amazonaws.services.pinpoint.model.ExportJobResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ExportJobResponseJsonMarshaller {
    private static ExportJobResponseJsonMarshaller instance;

    ExportJobResponseJsonMarshaller() {
    }

    public void marshall(ExportJobResponse exportJobResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (exportJobResponse.getApplicationId() != null) {
            String applicationId = exportJobResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (exportJobResponse.getCompletedPieces() != null) {
            Integer completedPieces = exportJobResponse.getCompletedPieces();
            awsJsonWriter.name("CompletedPieces");
            awsJsonWriter.value(completedPieces);
        }
        if (exportJobResponse.getCompletionDate() != null) {
            String completionDate = exportJobResponse.getCompletionDate();
            awsJsonWriter.name("CompletionDate");
            awsJsonWriter.value(completionDate);
        }
        if (exportJobResponse.getCreationDate() != null) {
            String creationDate = exportJobResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (exportJobResponse.getDefinition() != null) {
            ExportJobResource definition = exportJobResponse.getDefinition();
            awsJsonWriter.name("Definition");
            ExportJobResourceJsonMarshaller.getInstance().marshall(definition, awsJsonWriter);
        }
        if (exportJobResponse.getFailedPieces() != null) {
            Integer failedPieces = exportJobResponse.getFailedPieces();
            awsJsonWriter.name("FailedPieces");
            awsJsonWriter.value(failedPieces);
        }
        if (exportJobResponse.getFailures() != null) {
            List<String> failures = exportJobResponse.getFailures();
            awsJsonWriter.name("Failures");
            awsJsonWriter.beginArray();
            for (String str : failures) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        if (exportJobResponse.getId() != null) {
            String id = exportJobResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (exportJobResponse.getJobStatus() != null) {
            String jobStatus = exportJobResponse.getJobStatus();
            awsJsonWriter.name("JobStatus");
            awsJsonWriter.value(jobStatus);
        }
        if (exportJobResponse.getTotalFailures() != null) {
            Integer totalFailures = exportJobResponse.getTotalFailures();
            awsJsonWriter.name("TotalFailures");
            awsJsonWriter.value(totalFailures);
        }
        if (exportJobResponse.getTotalPieces() != null) {
            Integer totalPieces = exportJobResponse.getTotalPieces();
            awsJsonWriter.name("TotalPieces");
            awsJsonWriter.value(totalPieces);
        }
        if (exportJobResponse.getTotalProcessed() != null) {
            Integer totalProcessed = exportJobResponse.getTotalProcessed();
            awsJsonWriter.name("TotalProcessed");
            awsJsonWriter.value(totalProcessed);
        }
        if (exportJobResponse.getType() != null) {
            String type = exportJobResponse.getType();
            awsJsonWriter.name("Type");
            awsJsonWriter.value(type);
        }
        awsJsonWriter.endObject();
    }

    public static ExportJobResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobResponseJsonMarshaller();
        }
        return instance;
    }
}
