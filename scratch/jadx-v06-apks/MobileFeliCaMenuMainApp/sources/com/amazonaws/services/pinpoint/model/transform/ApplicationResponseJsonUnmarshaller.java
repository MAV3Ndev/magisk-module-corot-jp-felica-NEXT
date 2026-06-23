package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.ApplicationResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ApplicationResponseJsonUnmarshaller implements Unmarshaller<ApplicationResponse, JsonUnmarshallerContext> {
    private static ApplicationResponseJsonUnmarshaller instance;

    ApplicationResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public ApplicationResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ApplicationResponse applicationResponse = new ApplicationResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(JsonDocumentFields.POLICY_ID)) {
                applicationResponse.setId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Name")) {
                applicationResponse.setName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return applicationResponse;
    }

    public static ApplicationResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationResponseJsonUnmarshaller();
        }
        return instance;
    }
}
