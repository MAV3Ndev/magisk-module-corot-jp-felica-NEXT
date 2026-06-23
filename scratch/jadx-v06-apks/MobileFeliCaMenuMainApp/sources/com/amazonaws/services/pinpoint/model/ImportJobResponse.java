package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ImportJobResponse implements Serializable {
    private String applicationId;
    private Integer completedPieces;
    private String completionDate;
    private String creationDate;
    private ImportJobResource definition;
    private Integer failedPieces;
    private List<String> failures;
    private String id;
    private String jobStatus;
    private Integer totalFailures;
    private Integer totalPieces;
    private Integer totalProcessed;
    private String type;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public ImportJobResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public Integer getCompletedPieces() {
        return this.completedPieces;
    }

    public void setCompletedPieces(Integer num) {
        this.completedPieces = num;
    }

    public ImportJobResponse withCompletedPieces(Integer num) {
        this.completedPieces = num;
        return this;
    }

    public String getCompletionDate() {
        return this.completionDate;
    }

    public void setCompletionDate(String str) {
        this.completionDate = str;
    }

    public ImportJobResponse withCompletionDate(String str) {
        this.completionDate = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public ImportJobResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public ImportJobResource getDefinition() {
        return this.definition;
    }

    public void setDefinition(ImportJobResource importJobResource) {
        this.definition = importJobResource;
    }

    public ImportJobResponse withDefinition(ImportJobResource importJobResource) {
        this.definition = importJobResource;
        return this;
    }

    public Integer getFailedPieces() {
        return this.failedPieces;
    }

    public void setFailedPieces(Integer num) {
        this.failedPieces = num;
    }

    public ImportJobResponse withFailedPieces(Integer num) {
        this.failedPieces = num;
        return this;
    }

    public List<String> getFailures() {
        return this.failures;
    }

    public void setFailures(Collection<String> collection) {
        if (collection == null) {
            this.failures = null;
        } else {
            this.failures = new ArrayList(collection);
        }
    }

    public ImportJobResponse withFailures(String... strArr) {
        if (getFailures() == null) {
            this.failures = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.failures.add(str);
        }
        return this;
    }

    public ImportJobResponse withFailures(Collection<String> collection) {
        setFailures(collection);
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public ImportJobResponse withId(String str) {
        this.id = str;
        return this;
    }

    public String getJobStatus() {
        return this.jobStatus;
    }

    public void setJobStatus(String str) {
        this.jobStatus = str;
    }

    public ImportJobResponse withJobStatus(String str) {
        this.jobStatus = str;
        return this;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus.toString();
    }

    public ImportJobResponse withJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus.toString();
        return this;
    }

    public Integer getTotalFailures() {
        return this.totalFailures;
    }

    public void setTotalFailures(Integer num) {
        this.totalFailures = num;
    }

    public ImportJobResponse withTotalFailures(Integer num) {
        this.totalFailures = num;
        return this;
    }

    public Integer getTotalPieces() {
        return this.totalPieces;
    }

    public void setTotalPieces(Integer num) {
        this.totalPieces = num;
    }

    public ImportJobResponse withTotalPieces(Integer num) {
        this.totalPieces = num;
        return this;
    }

    public Integer getTotalProcessed() {
        return this.totalProcessed;
    }

    public void setTotalProcessed(Integer num) {
        this.totalProcessed = num;
    }

    public ImportJobResponse withTotalProcessed(Integer num) {
        this.totalProcessed = num;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public ImportJobResponse withType(String str) {
        this.type = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCompletedPieces() != null) {
            sb.append("CompletedPieces: " + getCompletedPieces() + ",");
        }
        if (getCompletionDate() != null) {
            sb.append("CompletionDate: " + getCompletionDate() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getDefinition() != null) {
            sb.append("Definition: " + getDefinition() + ",");
        }
        if (getFailedPieces() != null) {
            sb.append("FailedPieces: " + getFailedPieces() + ",");
        }
        if (getFailures() != null) {
            sb.append("Failures: " + getFailures() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getJobStatus() != null) {
            sb.append("JobStatus: " + getJobStatus() + ",");
        }
        if (getTotalFailures() != null) {
            sb.append("TotalFailures: " + getTotalFailures() + ",");
        }
        if (getTotalPieces() != null) {
            sb.append("TotalPieces: " + getTotalPieces() + ",");
        }
        if (getTotalProcessed() != null) {
            sb.append("TotalProcessed: " + getTotalProcessed() + ",");
        }
        if (getType() != null) {
            sb.append("Type: " + getType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCompletedPieces() == null ? 0 : getCompletedPieces().hashCode())) * 31) + (getCompletionDate() == null ? 0 : getCompletionDate().hashCode())) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getDefinition() == null ? 0 : getDefinition().hashCode())) * 31) + (getFailedPieces() == null ? 0 : getFailedPieces().hashCode())) * 31) + (getFailures() == null ? 0 : getFailures().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getJobStatus() == null ? 0 : getJobStatus().hashCode())) * 31) + (getTotalFailures() == null ? 0 : getTotalFailures().hashCode())) * 31) + (getTotalPieces() == null ? 0 : getTotalPieces().hashCode())) * 31) + (getTotalProcessed() == null ? 0 : getTotalProcessed().hashCode())) * 31) + (getType() != null ? getType().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ImportJobResponse)) {
            return false;
        }
        ImportJobResponse importJobResponse = (ImportJobResponse) obj;
        if ((importJobResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (importJobResponse.getApplicationId() != null && !importJobResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((importJobResponse.getCompletedPieces() == null) ^ (getCompletedPieces() == null)) {
            return false;
        }
        if (importJobResponse.getCompletedPieces() != null && !importJobResponse.getCompletedPieces().equals(getCompletedPieces())) {
            return false;
        }
        if ((importJobResponse.getCompletionDate() == null) ^ (getCompletionDate() == null)) {
            return false;
        }
        if (importJobResponse.getCompletionDate() != null && !importJobResponse.getCompletionDate().equals(getCompletionDate())) {
            return false;
        }
        if ((importJobResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (importJobResponse.getCreationDate() != null && !importJobResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((importJobResponse.getDefinition() == null) ^ (getDefinition() == null)) {
            return false;
        }
        if (importJobResponse.getDefinition() != null && !importJobResponse.getDefinition().equals(getDefinition())) {
            return false;
        }
        if ((importJobResponse.getFailedPieces() == null) ^ (getFailedPieces() == null)) {
            return false;
        }
        if (importJobResponse.getFailedPieces() != null && !importJobResponse.getFailedPieces().equals(getFailedPieces())) {
            return false;
        }
        if ((importJobResponse.getFailures() == null) ^ (getFailures() == null)) {
            return false;
        }
        if (importJobResponse.getFailures() != null && !importJobResponse.getFailures().equals(getFailures())) {
            return false;
        }
        if ((importJobResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (importJobResponse.getId() != null && !importJobResponse.getId().equals(getId())) {
            return false;
        }
        if ((importJobResponse.getJobStatus() == null) ^ (getJobStatus() == null)) {
            return false;
        }
        if (importJobResponse.getJobStatus() != null && !importJobResponse.getJobStatus().equals(getJobStatus())) {
            return false;
        }
        if ((importJobResponse.getTotalFailures() == null) ^ (getTotalFailures() == null)) {
            return false;
        }
        if (importJobResponse.getTotalFailures() != null && !importJobResponse.getTotalFailures().equals(getTotalFailures())) {
            return false;
        }
        if ((importJobResponse.getTotalPieces() == null) ^ (getTotalPieces() == null)) {
            return false;
        }
        if (importJobResponse.getTotalPieces() != null && !importJobResponse.getTotalPieces().equals(getTotalPieces())) {
            return false;
        }
        if ((importJobResponse.getTotalProcessed() == null) ^ (getTotalProcessed() == null)) {
            return false;
        }
        if (importJobResponse.getTotalProcessed() != null && !importJobResponse.getTotalProcessed().equals(getTotalProcessed())) {
            return false;
        }
        if ((importJobResponse.getType() == null) ^ (getType() == null)) {
            return false;
        }
        return importJobResponse.getType() == null || importJobResponse.getType().equals(getType());
    }
}
