package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ImportJobResource;
import com.amazonaws.services.pinpoint.model.ImportJobResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ImportJobResponseJsonMarshaller {
    private static ImportJobResponseJsonMarshaller instance;

    ImportJobResponseJsonMarshaller() {
    }

    public void marshall(ImportJobResponse importJobResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (importJobResponse.getApplicationId() != null) {
            String applicationId = importJobResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (importJobResponse.getCompletedPieces() != null) {
            Integer completedPieces = importJobResponse.getCompletedPieces();
            awsJsonWriter.name("CompletedPieces");
            awsJsonWriter.value(completedPieces);
        }
        if (importJobResponse.getCompletionDate() != null) {
            String completionDate = importJobResponse.getCompletionDate();
            awsJsonWriter.name("CompletionDate");
            awsJsonWriter.value(completionDate);
        }
        if (importJobResponse.getCreationDate() != null) {
            String creationDate = importJobResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (importJobResponse.getDefinition() != null) {
            ImportJobResource definition = importJobResponse.getDefinition();
            awsJsonWriter.name("Definition");
            ImportJobResourceJsonMarshaller.getInstance().marshall(definition, awsJsonWriter);
        }
        if (importJobResponse.getFailedPieces() != null) {
            Integer failedPieces = importJobResponse.getFailedPieces();
            awsJsonWriter.name("FailedPieces");
            awsJsonWriter.value(failedPieces);
        }
        if (importJobResponse.getFailures() != null) {
            List<String> failures = importJobResponse.getFailures();
            awsJsonWriter.name("Failures");
            awsJsonWriter.beginArray();
            for (String str : failures) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        if (importJobResponse.getId() != null) {
            String id = importJobResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (importJobResponse.getJobStatus() != null) {
            String jobStatus = importJobResponse.getJobStatus();
            awsJsonWriter.name("JobStatus");
            awsJsonWriter.value(jobStatus);
        }
        if (importJobResponse.getTotalFailures() != null) {
            Integer totalFailures = importJobResponse.getTotalFailures();
            awsJsonWriter.name("TotalFailures");
            awsJsonWriter.value(totalFailures);
        }
        if (importJobResponse.getTotalPieces() != null) {
            Integer totalPieces = importJobResponse.getTotalPieces();
            awsJsonWriter.name("TotalPieces");
            awsJsonWriter.value(totalPieces);
        }
        if (importJobResponse.getTotalProcessed() != null) {
            Integer totalProcessed = importJobResponse.getTotalProcessed();
            awsJsonWriter.name("TotalProcessed");
            awsJsonWriter.value(totalProcessed);
        }
        if (importJobResponse.getType() != null) {
            String type = importJobResponse.getType();
            awsJsonWriter.name("Type");
            awsJsonWriter.value(type);
        }
        awsJsonWriter.endObject();
    }

    public static ImportJobResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ImportJobResponseJsonMarshaller();
        }
        return instance;
    }
}
