package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.NumberValidateResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class NumberValidateResponseJsonMarshaller {
    private static NumberValidateResponseJsonMarshaller instance;

    NumberValidateResponseJsonMarshaller() {
    }

    public void marshall(NumberValidateResponse numberValidateResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (numberValidateResponse.getCarrier() != null) {
            String carrier = numberValidateResponse.getCarrier();
            awsJsonWriter.name(DataRecordKey.NETWORK_OPERATOR);
            awsJsonWriter.value(carrier);
        }
        if (numberValidateResponse.getCity() != null) {
            String city = numberValidateResponse.getCity();
            awsJsonWriter.name("City");
            awsJsonWriter.value(city);
        }
        if (numberValidateResponse.getCleansedPhoneNumberE164() != null) {
            String cleansedPhoneNumberE164 = numberValidateResponse.getCleansedPhoneNumberE164();
            awsJsonWriter.name("CleansedPhoneNumberE164");
            awsJsonWriter.value(cleansedPhoneNumberE164);
        }
        if (numberValidateResponse.getCleansedPhoneNumberNational() != null) {
            String cleansedPhoneNumberNational = numberValidateResponse.getCleansedPhoneNumberNational();
            awsJsonWriter.name("CleansedPhoneNumberNational");
            awsJsonWriter.value(cleansedPhoneNumberNational);
        }
        if (numberValidateResponse.getCountry() != null) {
            String country = numberValidateResponse.getCountry();
            awsJsonWriter.name("Country");
            awsJsonWriter.value(country);
        }
        if (numberValidateResponse.getCountryCodeIso2() != null) {
            String countryCodeIso2 = numberValidateResponse.getCountryCodeIso2();
            awsJsonWriter.name("CountryCodeIso2");
            awsJsonWriter.value(countryCodeIso2);
        }
        if (numberValidateResponse.getCountryCodeNumeric() != null) {
            String countryCodeNumeric = numberValidateResponse.getCountryCodeNumeric();
            awsJsonWriter.name("CountryCodeNumeric");
            awsJsonWriter.value(countryCodeNumeric);
        }
        if (numberValidateResponse.getCounty() != null) {
            String county = numberValidateResponse.getCounty();
            awsJsonWriter.name("County");
            awsJsonWriter.value(county);
        }
        if (numberValidateResponse.getOriginalCountryCodeIso2() != null) {
            String originalCountryCodeIso2 = numberValidateResponse.getOriginalCountryCodeIso2();
            awsJsonWriter.name("OriginalCountryCodeIso2");
            awsJsonWriter.value(originalCountryCodeIso2);
        }
        if (numberValidateResponse.getOriginalPhoneNumber() != null) {
            String originalPhoneNumber = numberValidateResponse.getOriginalPhoneNumber();
            awsJsonWriter.name("OriginalPhoneNumber");
            awsJsonWriter.value(originalPhoneNumber);
        }
        if (numberValidateResponse.getPhoneType() != null) {
            String phoneType = numberValidateResponse.getPhoneType();
            awsJsonWriter.name(DataRecordKey.PHONE_TYPE);
            awsJsonWriter.value(phoneType);
        }
        if (numberValidateResponse.getPhoneTypeCode() != null) {
            Integer phoneTypeCode = numberValidateResponse.getPhoneTypeCode();
            awsJsonWriter.name("PhoneTypeCode");
            awsJsonWriter.value(phoneTypeCode);
        }
        if (numberValidateResponse.getTimezone() != null) {
            String timezone = numberValidateResponse.getTimezone();
            awsJsonWriter.name("Timezone");
            awsJsonWriter.value(timezone);
        }
        if (numberValidateResponse.getZipCode() != null) {
            String zipCode = numberValidateResponse.getZipCode();
            awsJsonWriter.name("ZipCode");
            awsJsonWriter.value(zipCode);
        }
        awsJsonWriter.endObject();
    }

    public static NumberValidateResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new NumberValidateResponseJsonMarshaller();
        }
        return instance;
    }
}
