package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PhoneNumberValidateResult implements Serializable {
    private NumberValidateResponse numberValidateResponse;

    public NumberValidateResponse getNumberValidateResponse() {
        return this.numberValidateResponse;
    }

    public void setNumberValidateResponse(NumberValidateResponse numberValidateResponse) {
        this.numberValidateResponse = numberValidateResponse;
    }

    public PhoneNumberValidateResult withNumberValidateResponse(NumberValidateResponse numberValidateResponse) {
        this.numberValidateResponse = numberValidateResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getNumberValidateResponse() != null) {
            sb.append("NumberValidateResponse: " + getNumberValidateResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getNumberValidateResponse() == null ? 0 : getNumberValidateResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhoneNumberValidateResult)) {
            return false;
        }
        PhoneNumberValidateResult phoneNumberValidateResult = (PhoneNumberValidateResult) obj;
        if ((phoneNumberValidateResult.getNumberValidateResponse() == null) ^ (getNumberValidateResponse() == null)) {
            return false;
        }
        return phoneNumberValidateResult.getNumberValidateResponse() == null || phoneNumberValidateResult.getNumberValidateResponse().equals(getNumberValidateResponse());
    }
}
