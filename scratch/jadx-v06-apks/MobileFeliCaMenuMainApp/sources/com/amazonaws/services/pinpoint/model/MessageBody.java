package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MessageBody implements Serializable {
    private String message;
    private String requestID;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public MessageBody withMessage(String str) {
        this.message = str;
        return this;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String str) {
        this.requestID = str;
    }

    public MessageBody withRequestID(String str) {
        this.requestID = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessage() != null) {
            sb.append("Message: " + getMessage() + ",");
        }
        if (getRequestID() != null) {
            sb.append("RequestID: " + getRequestID());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getMessage() == null ? 0 : getMessage().hashCode()) + 31) * 31) + (getRequestID() != null ? getRequestID().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MessageBody)) {
            return false;
        }
        MessageBody messageBody = (MessageBody) obj;
        if ((messageBody.getMessage() == null) ^ (getMessage() == null)) {
            return false;
        }
        if (messageBody.getMessage() != null && !messageBody.getMessage().equals(getMessage())) {
            return false;
        }
        if ((messageBody.getRequestID() == null) ^ (getRequestID() == null)) {
            return false;
        }
        return messageBody.getRequestID() == null || messageBody.getRequestID().equals(getRequestID());
    }
}
