package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ApplicationsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ApplicationsResponseJsonUnmarshaller implements Unmarshaller<ApplicationsResponse, JsonUnmarshallerContext> {
    private static ApplicationsResponseJsonUnmarshaller instance;

    ApplicationsResponseJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ApplicationsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ApplicationsResponse applicationsResponse = new ApplicationsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Item")) {
                applicationsResponse.setItem(new ListUnmarshaller(ApplicationResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("NextToken")) {
                applicationsResponse.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return applicationsResponse;
    }

    public static ApplicationsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
