package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.LogDeliveryConfigurationType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class LogDeliveryConfigurationTypeJsonUnmarshaller implements Unmarshaller<LogDeliveryConfigurationType, JsonUnmarshallerContext> {
    private static LogDeliveryConfigurationTypeJsonUnmarshaller instance;

    LogDeliveryConfigurationTypeJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public LogDeliveryConfigurationType unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        LogDeliveryConfigurationType logDeliveryConfigurationType = new LogDeliveryConfigurationType();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("UserPoolId")) {
                logDeliveryConfigurationType.setUserPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LogConfigurations")) {
                logDeliveryConfigurationType.setLogConfigurations(new ListUnmarshaller(LogConfigurationTypeJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return logDeliveryConfigurationType;
    }

    public static LogDeliveryConfigurationTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new LogDeliveryConfigurationTypeJsonUnmarshaller();
        }
        return instance;
    }
}
