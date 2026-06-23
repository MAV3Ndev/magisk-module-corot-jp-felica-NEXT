package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DefaultMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class DefaultMessageJsonUnmarshaller implements Unmarshaller<DefaultMessage, JsonUnmarshallerContext> {
    private static DefaultMessageJsonUnmarshaller instance;

    DefaultMessageJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DefaultMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        DefaultMessage defaultMessage = new DefaultMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Body")) {
                defaultMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                defaultMessage.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return defaultMessage;
    }

    public static DefaultMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DefaultMessageJsonUnmarshaller();
        }
        return instance;
    }
}
