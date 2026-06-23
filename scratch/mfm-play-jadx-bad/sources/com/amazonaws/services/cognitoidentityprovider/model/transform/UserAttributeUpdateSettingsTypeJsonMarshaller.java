package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.UserAttributeUpdateSettingsType;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class UserAttributeUpdateSettingsTypeJsonMarshaller {
    private static UserAttributeUpdateSettingsTypeJsonMarshaller instance;

    UserAttributeUpdateSettingsTypeJsonMarshaller() {
    }

    public void marshall(UserAttributeUpdateSettingsType userAttributeUpdateSettingsType, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (userAttributeUpdateSettingsType.getAttributesRequireVerificationBeforeUpdate() != null) {
            List<String> attributesRequireVerificationBeforeUpdate = userAttributeUpdateSettingsType.getAttributesRequireVerificationBeforeUpdate();
            awsJsonWriter.name("AttributesRequireVerificationBeforeUpdate");
            awsJsonWriter.beginArray();
            for (String str : attributesRequireVerificationBeforeUpdate) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static UserAttributeUpdateSettingsTypeJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new UserAttributeUpdateSettingsTypeJsonMarshaller();
        }
        return instance;
    }
}
