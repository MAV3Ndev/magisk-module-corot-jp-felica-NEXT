package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.NumberValidateRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class NumberValidateRequestJsonUnmarshaller implements Unmarshaller<NumberValidateRequest, JsonUnmarshallerContext> {
    private static NumberValidateRequestJsonUnmarshaller instance;

    NumberValidateRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public NumberValidateRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        NumberValidateRequest numberValidateRequest = new NumberValidateRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("IsoCountryCode")) {
                numberValidateRequest.setIsoCountryCode(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PhoneNumber")) {
                numberValidateRequest.setPhoneNumber(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return numberValidateRequest;
    }

    public static NumberValidateRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new NumberValidateRequestJsonUnmarshaller();
        }
        return instance;
    }
}
