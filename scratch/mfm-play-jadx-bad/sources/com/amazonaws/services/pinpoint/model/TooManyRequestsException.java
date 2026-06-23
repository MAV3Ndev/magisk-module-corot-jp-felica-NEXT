package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonServiceException;

/* JADX INFO: loaded from: classes.dex */
public class TooManyRequestsException extends AmazonServiceException {
    private static final long serialVersionUID = 1;
    private String requestID;

    public TooManyRequestsException(String str) {
        super(str);
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String str) {
        this.requestID = str;
    }
}
