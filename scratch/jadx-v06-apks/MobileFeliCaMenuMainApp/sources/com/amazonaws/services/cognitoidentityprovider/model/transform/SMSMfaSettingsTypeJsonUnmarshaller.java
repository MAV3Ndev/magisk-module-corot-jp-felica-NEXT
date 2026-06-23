package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.SMSMfaSettingsType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SMSMfaSettingsTypeJsonUnmarshaller implements Unmarshaller<SMSMfaSettingsType, JsonUnmarshallerContext> {
    private static SMSMfaSettingsTypeJsonUnmarshaller instance;

    SMSMfaSettingsTypeJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SMSMfaSettingsType unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SMSMfaSettingsType sMSMfaSettingsType = new SMSMfaSettingsType();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Enabled")) {
                sMSMfaSettingsType.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PreferredMfa")) {
                sMSMfaSettingsType.setPreferredMfa(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sMSMfaSettingsType;
    }

    public static SMSMfaSettingsTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SMSMfaSettingsTypeJsonUnmarshaller();
        }
        return instance;
    }
}
