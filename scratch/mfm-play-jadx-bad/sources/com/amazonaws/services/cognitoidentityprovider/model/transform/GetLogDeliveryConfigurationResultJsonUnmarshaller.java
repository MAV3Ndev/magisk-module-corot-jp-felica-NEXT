package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.GetLogDeliveryConfigurationResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
public class GetLogDeliveryConfigurationResultJsonUnmarshaller implements Unmarshaller<GetLogDeliveryConfigurationResult, JsonUnmarshallerContext> {
    private static GetLogDeliveryConfigurationResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetLogDeliveryConfigurationResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        GetLogDeliveryConfigurationResult getLogDeliveryConfigurationResult = new GetLogDeliveryConfigurationResult();
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("LogDeliveryConfiguration")) {
                getLogDeliveryConfigurationResult.setLogDeliveryConfiguration(LogDeliveryConfigurationTypeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getLogDeliveryConfigurationResult;
    }

    public static GetLogDeliveryConfigurationResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetLogDeliveryConfigurationResultJsonUnmarshaller();
        }
        return instance;
    }
}
