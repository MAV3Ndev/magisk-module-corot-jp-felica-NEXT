package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PhoneNumberValidateRequest extends AmazonWebServiceRequest implements Serializable {
    private NumberValidateRequest numberValidateRequest;

    public NumberValidateRequest getNumberValidateRequest() {
        return this.numberValidateRequest;
    }

    public void setNumberValidateRequest(NumberValidateRequest numberValidateRequest) {
        this.numberValidateRequest = numberValidateRequest;
    }

    public PhoneNumberValidateRequest withNumberValidateRequest(NumberValidateRequest numberValidateRequest) {
        this.numberValidateRequest = numberValidateRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getNumberValidateRequest() != null) {
            sb.append("NumberValidateRequest: " + getNumberValidateRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getNumberValidateRequest() == null ? 0 : getNumberValidateRequest().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhoneNumberValidateRequest)) {
            return false;
        }
        PhoneNumberValidateRequest phoneNumberValidateRequest = (PhoneNumberValidateRequest) obj;
        if ((phoneNumberValidateRequest.getNumberValidateRequest() == null) ^ (getNumberValidateRequest() == null)) {
            return false;
        }
        return phoneNumberValidateRequest.getNumberValidateRequest() == null || phoneNumberValidateRequest.getNumberValidateRequest().equals(getNumberValidateRequest());
    }
}
