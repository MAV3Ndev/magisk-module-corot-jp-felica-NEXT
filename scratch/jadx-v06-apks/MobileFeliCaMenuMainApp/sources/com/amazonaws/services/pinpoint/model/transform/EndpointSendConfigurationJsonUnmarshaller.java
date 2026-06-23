package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointSendConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EndpointSendConfigurationJsonUnmarshaller implements Unmarshaller<EndpointSendConfiguration, JsonUnmarshallerContext> {
    private static EndpointSendConfigurationJsonUnmarshaller instance;

    EndpointSendConfigurationJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EndpointSendConfiguration unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EndpointSendConfiguration endpointSendConfiguration = new EndpointSendConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BodyOverride")) {
                endpointSendConfiguration.setBodyOverride(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Context")) {
                endpointSendConfiguration.setContext(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RawContent")) {
                endpointSendConfiguration.setRawContent(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                endpointSendConfiguration.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TitleOverride")) {
                endpointSendConfiguration.setTitleOverride(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return endpointSendConfiguration;
    }

    public static EndpointSendConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointSendConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
