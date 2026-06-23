package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.CloudWatchLogsConfigurationType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CloudWatchLogsConfigurationTypeJsonUnmarshaller implements Unmarshaller<CloudWatchLogsConfigurationType, JsonUnmarshallerContext> {
    private static CloudWatchLogsConfigurationTypeJsonUnmarshaller instance;

    CloudWatchLogsConfigurationTypeJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CloudWatchLogsConfigurationType unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CloudWatchLogsConfigurationType cloudWatchLogsConfigurationType = new CloudWatchLogsConfigurationType();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("LogGroupArn")) {
                cloudWatchLogsConfigurationType.setLogGroupArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return cloudWatchLogsConfigurationType;
    }

    public static CloudWatchLogsConfigurationTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CloudWatchLogsConfigurationTypeJsonUnmarshaller();
        }
        return instance;
    }
}
