package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.NumberValidateResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class NumberValidateResponseJsonUnmarshaller implements Unmarshaller<NumberValidateResponse, JsonUnmarshallerContext> {
    private static NumberValidateResponseJsonUnmarshaller instance;

    NumberValidateResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public NumberValidateResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        NumberValidateResponse numberValidateResponse = new NumberValidateResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(DataRecordKey.NETWORK_OPERATOR)) {
                numberValidateResponse.setCarrier(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("City")) {
                numberValidateResponse.setCity(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CleansedPhoneNumberE164")) {
                numberValidateResponse.setCleansedPhoneNumberE164(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CleansedPhoneNumberNational")) {
                numberValidateResponse.setCleansedPhoneNumberNational(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Country")) {
                numberValidateResponse.setCountry(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CountryCodeIso2")) {
                numberValidateResponse.setCountryCodeIso2(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CountryCodeNumeric")) {
                numberValidateResponse.setCountryCodeNumeric(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("County")) {
                numberValidateResponse.setCounty(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("OriginalCountryCodeIso2")) {
                numberValidateResponse.setOriginalCountryCodeIso2(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("OriginalPhoneNumber")) {
                numberValidateResponse.setOriginalPhoneNumber(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(DataRecordKey.PHONE_TYPE)) {
                numberValidateResponse.setPhoneType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PhoneTypeCode")) {
                numberValidateResponse.setPhoneTypeCode(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Timezone")) {
                numberValidateResponse.setTimezone(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ZipCode")) {
                numberValidateResponse.setZipCode(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return numberValidateResponse;
    }

    public static NumberValidateResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new NumberValidateResponseJsonUnmarshaller();
        }
        return instance;
    }
}
