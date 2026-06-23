package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class NumberValidateResponse implements Serializable {
    private String carrier;
    private String city;
    private String cleansedPhoneNumberE164;
    private String cleansedPhoneNumberNational;
    private String country;
    private String countryCodeIso2;
    private String countryCodeNumeric;
    private String county;
    private String originalCountryCodeIso2;
    private String originalPhoneNumber;
    private String phoneType;
    private Integer phoneTypeCode;
    private String timezone;
    private String zipCode;

    public String getCarrier() {
        return this.carrier;
    }

    public void setCarrier(String str) {
        this.carrier = str;
    }

    public NumberValidateResponse withCarrier(String str) {
        this.carrier = str;
        return this;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public NumberValidateResponse withCity(String str) {
        this.city = str;
        return this;
    }

    public String getCleansedPhoneNumberE164() {
        return this.cleansedPhoneNumberE164;
    }

    public void setCleansedPhoneNumberE164(String str) {
        this.cleansedPhoneNumberE164 = str;
    }

    public NumberValidateResponse withCleansedPhoneNumberE164(String str) {
        this.cleansedPhoneNumberE164 = str;
        return this;
    }

    public String getCleansedPhoneNumberNational() {
        return this.cleansedPhoneNumberNational;
    }

    public void setCleansedPhoneNumberNational(String str) {
        this.cleansedPhoneNumberNational = str;
    }

    public NumberValidateResponse withCleansedPhoneNumberNational(String str) {
        this.cleansedPhoneNumberNational = str;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public NumberValidateResponse withCountry(String str) {
        this.country = str;
        return this;
    }

    public String getCountryCodeIso2() {
        return this.countryCodeIso2;
    }

    public void setCountryCodeIso2(String str) {
        this.countryCodeIso2 = str;
    }

    public NumberValidateResponse withCountryCodeIso2(String str) {
        this.countryCodeIso2 = str;
        return this;
    }

    public String getCountryCodeNumeric() {
        return this.countryCodeNumeric;
    }

    public void setCountryCodeNumeric(String str) {
        this.countryCodeNumeric = str;
    }

    public NumberValidateResponse withCountryCodeNumeric(String str) {
        this.countryCodeNumeric = str;
        return this;
    }

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String str) {
        this.county = str;
    }

    public NumberValidateResponse withCounty(String str) {
        this.county = str;
        return this;
    }

    public String getOriginalCountryCodeIso2() {
        return this.originalCountryCodeIso2;
    }

    public void setOriginalCountryCodeIso2(String str) {
        this.originalCountryCodeIso2 = str;
    }

    public NumberValidateResponse withOriginalCountryCodeIso2(String str) {
        this.originalCountryCodeIso2 = str;
        return this;
    }

    public String getOriginalPhoneNumber() {
        return this.originalPhoneNumber;
    }

    public void setOriginalPhoneNumber(String str) {
        this.originalPhoneNumber = str;
    }

    public NumberValidateResponse withOriginalPhoneNumber(String str) {
        this.originalPhoneNumber = str;
        return this;
    }

    public String getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(String str) {
        this.phoneType = str;
    }

    public NumberValidateResponse withPhoneType(String str) {
        this.phoneType = str;
        return this;
    }

    public Integer getPhoneTypeCode() {
        return this.phoneTypeCode;
    }

    public void setPhoneTypeCode(Integer num) {
        this.phoneTypeCode = num;
    }

    public NumberValidateResponse withPhoneTypeCode(Integer num) {
        this.phoneTypeCode = num;
        return this;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public NumberValidateResponse withTimezone(String str) {
        this.timezone = str;
        return this;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String str) {
        this.zipCode = str;
    }

    public NumberValidateResponse withZipCode(String str) {
        this.zipCode = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCarrier() != null) {
            sb.append("Carrier: " + getCarrier() + ",");
        }
        if (getCity() != null) {
            sb.append("City: " + getCity() + ",");
        }
        if (getCleansedPhoneNumberE164() != null) {
            sb.append("CleansedPhoneNumberE164: " + getCleansedPhoneNumberE164() + ",");
        }
        if (getCleansedPhoneNumberNational() != null) {
            sb.append("CleansedPhoneNumberNational: " + getCleansedPhoneNumberNational() + ",");
        }
        if (getCountry() != null) {
            sb.append("Country: " + getCountry() + ",");
        }
        if (getCountryCodeIso2() != null) {
            sb.append("CountryCodeIso2: " + getCountryCodeIso2() + ",");
        }
        if (getCountryCodeNumeric() != null) {
            sb.append("CountryCodeNumeric: " + getCountryCodeNumeric() + ",");
        }
        if (getCounty() != null) {
            sb.append("County: " + getCounty() + ",");
        }
        if (getOriginalCountryCodeIso2() != null) {
            sb.append("OriginalCountryCodeIso2: " + getOriginalCountryCodeIso2() + ",");
        }
        if (getOriginalPhoneNumber() != null) {
            sb.append("OriginalPhoneNumber: " + getOriginalPhoneNumber() + ",");
        }
        if (getPhoneType() != null) {
            sb.append("PhoneType: " + getPhoneType() + ",");
        }
        if (getPhoneTypeCode() != null) {
            sb.append("PhoneTypeCode: " + getPhoneTypeCode() + ",");
        }
        if (getTimezone() != null) {
            sb.append("Timezone: " + getTimezone() + ",");
        }
        if (getZipCode() != null) {
            sb.append("ZipCode: " + getZipCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((getCarrier() == null ? 0 : getCarrier().hashCode()) + 31) * 31) + (getCity() == null ? 0 : getCity().hashCode())) * 31) + (getCleansedPhoneNumberE164() == null ? 0 : getCleansedPhoneNumberE164().hashCode())) * 31) + (getCleansedPhoneNumberNational() == null ? 0 : getCleansedPhoneNumberNational().hashCode())) * 31) + (getCountry() == null ? 0 : getCountry().hashCode())) * 31) + (getCountryCodeIso2() == null ? 0 : getCountryCodeIso2().hashCode())) * 31) + (getCountryCodeNumeric() == null ? 0 : getCountryCodeNumeric().hashCode())) * 31) + (getCounty() == null ? 0 : getCounty().hashCode())) * 31) + (getOriginalCountryCodeIso2() == null ? 0 : getOriginalCountryCodeIso2().hashCode())) * 31) + (getOriginalPhoneNumber() == null ? 0 : getOriginalPhoneNumber().hashCode())) * 31) + (getPhoneType() == null ? 0 : getPhoneType().hashCode())) * 31) + (getPhoneTypeCode() == null ? 0 : getPhoneTypeCode().hashCode())) * 31) + (getTimezone() == null ? 0 : getTimezone().hashCode())) * 31) + (getZipCode() != null ? getZipCode().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NumberValidateResponse)) {
            return false;
        }
        NumberValidateResponse numberValidateResponse = (NumberValidateResponse) obj;
        if ((numberValidateResponse.getCarrier() == null) ^ (getCarrier() == null)) {
            return false;
        }
        if (numberValidateResponse.getCarrier() != null && !numberValidateResponse.getCarrier().equals(getCarrier())) {
            return false;
        }
        if ((numberValidateResponse.getCity() == null) ^ (getCity() == null)) {
            return false;
        }
        if (numberValidateResponse.getCity() != null && !numberValidateResponse.getCity().equals(getCity())) {
            return false;
        }
        if ((numberValidateResponse.getCleansedPhoneNumberE164() == null) ^ (getCleansedPhoneNumberE164() == null)) {
            return false;
        }
        if (numberValidateResponse.getCleansedPhoneNumberE164() != null && !numberValidateResponse.getCleansedPhoneNumberE164().equals(getCleansedPhoneNumberE164())) {
            return false;
        }
        if ((numberValidateResponse.getCleansedPhoneNumberNational() == null) ^ (getCleansedPhoneNumberNational() == null)) {
            return false;
        }
        if (numberValidateResponse.getCleansedPhoneNumberNational() != null && !numberValidateResponse.getCleansedPhoneNumberNational().equals(getCleansedPhoneNumberNational())) {
            return false;
        }
        if ((numberValidateResponse.getCountry() == null) ^ (getCountry() == null)) {
            return false;
        }
        if (numberValidateResponse.getCountry() != null && !numberValidateResponse.getCountry().equals(getCountry())) {
            return false;
        }
        if ((numberValidateResponse.getCountryCodeIso2() == null) ^ (getCountryCodeIso2() == null)) {
            return false;
        }
        if (numberValidateResponse.getCountryCodeIso2() != null && !numberValidateResponse.getCountryCodeIso2().equals(getCountryCodeIso2())) {
            return false;
        }
        if ((numberValidateResponse.getCountryCodeNumeric() == null) ^ (getCountryCodeNumeric() == null)) {
            return false;
        }
        if (numberValidateResponse.getCountryCodeNumeric() != null && !numberValidateResponse.getCountryCodeNumeric().equals(getCountryCodeNumeric())) {
            return false;
        }
        if ((numberValidateResponse.getCounty() == null) ^ (getCounty() == null)) {
            return false;
        }
        if (numberValidateResponse.getCounty() != null && !numberValidateResponse.getCounty().equals(getCounty())) {
            return false;
        }
        if ((numberValidateResponse.getOriginalCountryCodeIso2() == null) ^ (getOriginalCountryCodeIso2() == null)) {
            return false;
        }
        if (numberValidateResponse.getOriginalCountryCodeIso2() != null && !numberValidateResponse.getOriginalCountryCodeIso2().equals(getOriginalCountryCodeIso2())) {
            return false;
        }
        if ((numberValidateResponse.getOriginalPhoneNumber() == null) ^ (getOriginalPhoneNumber() == null)) {
            return false;
        }
        if (numberValidateResponse.getOriginalPhoneNumber() != null && !numberValidateResponse.getOriginalPhoneNumber().equals(getOriginalPhoneNumber())) {
            return false;
        }
        if ((numberValidateResponse.getPhoneType() == null) ^ (getPhoneType() == null)) {
            return false;
        }
        if (numberValidateResponse.getPhoneType() != null && !numberValidateResponse.getPhoneType().equals(getPhoneType())) {
            return false;
        }
        if ((numberValidateResponse.getPhoneTypeCode() == null) ^ (getPhoneTypeCode() == null)) {
            return false;
        }
        if (numberValidateResponse.getPhoneTypeCode() != null && !numberValidateResponse.getPhoneTypeCode().equals(getPhoneTypeCode())) {
            return false;
        }
        if ((numberValidateResponse.getTimezone() == null) ^ (getTimezone() == null)) {
            return false;
        }
        if (numberValidateResponse.getTimezone() != null && !numberValidateResponse.getTimezone().equals(getTimezone())) {
            return false;
        }
        if ((numberValidateResponse.getZipCode() == null) ^ (getZipCode() == null)) {
            return false;
        }
        return numberValidateResponse.getZipCode() == null || numberValidateResponse.getZipCode().equals(getZipCode());
    }
}
