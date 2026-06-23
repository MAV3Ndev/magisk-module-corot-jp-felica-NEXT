package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.RiskExceptionConfigurationType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class RiskExceptionConfigurationTypeJsonUnmarshaller implements Unmarshaller<RiskExceptionConfigurationType, JsonUnmarshallerContext> {
    private static RiskExceptionConfigurationTypeJsonUnmarshaller instance;

    RiskExceptionConfigurationTypeJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public RiskExceptionConfigurationType unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        RiskExceptionConfigurationType riskExceptionConfigurationType = new RiskExceptionConfigurationType();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BlockedIPRangeList")) {
                riskExceptionConfigurationType.setBlockedIPRangeList(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SkippedIPRangeList")) {
                riskExceptionConfigurationType.setSkippedIPRangeList(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return riskExceptionConfigurationType;
    }

    public static RiskExceptionConfigurationTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RiskExceptionConfigurationTypeJsonUnmarshaller();
        }
        return instance;
    }
}
