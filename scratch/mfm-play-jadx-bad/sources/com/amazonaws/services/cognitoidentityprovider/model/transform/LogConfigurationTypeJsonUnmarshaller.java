package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.LogConfigurationType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class LogConfigurationTypeJsonUnmarshaller implements Unmarshaller<LogConfigurationType, JsonUnmarshallerContext> {
    private static LogConfigurationTypeJsonUnmarshaller instance;

    LogConfigurationTypeJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public LogConfigurationType unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        LogConfigurationType logConfigurationType = new LogConfigurationType();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("LogLevel")) {
                logConfigurationType.setLogLevel(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EventSource")) {
                logConfigurationType.setEventSource(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CloudWatchLogsConfiguration")) {
                logConfigurationType.setCloudWatchLogsConfiguration(CloudWatchLogsConfigurationTypeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return logConfigurationType;
    }

    public static LogConfigurationTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new LogConfigurationTypeJsonUnmarshaller();
        }
        return instance;
    }
}
