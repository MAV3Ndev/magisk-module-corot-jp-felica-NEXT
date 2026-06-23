package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonServiceException;

/* JADX INFO: loaded from: classes.dex */
public class NotFoundException extends AmazonServiceException {
    private static final long serialVersionUID = 1;
    private String requestID;

    public NotFoundException(String str) {
        super(str);
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String str) {
        this.requestID = str;
    }
}
