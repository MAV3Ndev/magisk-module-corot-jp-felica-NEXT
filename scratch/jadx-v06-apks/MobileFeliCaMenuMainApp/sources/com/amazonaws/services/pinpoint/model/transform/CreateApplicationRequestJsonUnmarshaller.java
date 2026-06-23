package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateApplicationRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CreateApplicationRequestJsonUnmarshaller implements Unmarshaller<CreateApplicationRequest, JsonUnmarshallerContext> {
    private static CreateApplicationRequestJsonUnmarshaller instance;

    CreateApplicationRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public CreateApplicationRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Name")) {
                createApplicationRequest.setName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return createApplicationRequest;
    }

    public static CreateApplicationRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateApplicationRequestJsonUnmarshaller();
        }
        return instance;
    }
}
