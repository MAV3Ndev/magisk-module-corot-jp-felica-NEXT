package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class NumberValidateRequest implements Serializable {
    private String isoCountryCode;
    private String phoneNumber;

    public String getIsoCountryCode() {
        return this.isoCountryCode;
    }

    public void setIsoCountryCode(String str) {
        this.isoCountryCode = str;
    }

    public NumberValidateRequest withIsoCountryCode(String str) {
        this.isoCountryCode = str;
        return this;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public NumberValidateRequest withPhoneNumber(String str) {
        this.phoneNumber = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIsoCountryCode() != null) {
            sb.append("IsoCountryCode: " + getIsoCountryCode() + ",");
        }
        if (getPhoneNumber() != null) {
            sb.append("PhoneNumber: " + getPhoneNumber());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getIsoCountryCode() == null ? 0 : getIsoCountryCode().hashCode()) + 31) * 31) + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NumberValidateRequest)) {
            return false;
        }
        NumberValidateRequest numberValidateRequest = (NumberValidateRequest) obj;
        if ((numberValidateRequest.getIsoCountryCode() == null) ^ (getIsoCountryCode() == null)) {
            return false;
        }
        if (numberValidateRequest.getIsoCountryCode() != null && !numberValidateRequest.getIsoCountryCode().equals(getIsoCountryCode())) {
            return false;
        }
        if ((numberValidateRequest.getPhoneNumber() == null) ^ (getPhoneNumber() == null)) {
            return false;
        }
        return numberValidateRequest.getPhoneNumber() == null || numberValidateRequest.getPhoneNumber().equals(getPhoneNumber());
    }
}
