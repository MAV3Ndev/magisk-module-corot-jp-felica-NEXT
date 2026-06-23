package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.NumberValidateRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class NumberValidateRequestJsonMarshaller {
    private static NumberValidateRequestJsonMarshaller instance;

    NumberValidateRequestJsonMarshaller() {
    }

    public void marshall(NumberValidateRequest numberValidateRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (numberValidateRequest.getIsoCountryCode() != null) {
            String isoCountryCode = numberValidateRequest.getIsoCountryCode();
            awsJsonWriter.name("IsoCountryCode");
            awsJsonWriter.value(isoCountryCode);
        }
        if (numberValidateRequest.getPhoneNumber() != null) {
            String phoneNumber = numberValidateRequest.getPhoneNumber();
            awsJsonWriter.name("PhoneNumber");
            awsJsonWriter.value(phoneNumber);
        }
        awsJsonWriter.endObject();
    }

    public static NumberValidateRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new NumberValidateRequestJsonMarshaller();
        }
        return instance;
    }
}
