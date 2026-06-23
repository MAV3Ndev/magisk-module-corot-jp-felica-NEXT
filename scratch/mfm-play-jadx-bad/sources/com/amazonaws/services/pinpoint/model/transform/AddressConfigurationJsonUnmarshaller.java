package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AddressConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class AddressConfigurationJsonUnmarshaller implements Unmarshaller<AddressConfiguration, JsonUnmarshallerContext> {
    private static AddressConfigurationJsonUnmarshaller instance;

    AddressConfigurationJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public AddressConfiguration unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        AddressConfiguration addressConfiguration = new AddressConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BodyOverride")) {
                addressConfiguration.setBodyOverride(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ChannelType")) {
                addressConfiguration.setChannelType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Context")) {
                addressConfiguration.setContext(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RawContent")) {
                addressConfiguration.setRawContent(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                addressConfiguration.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TitleOverride")) {
                addressConfiguration.setTitleOverride(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return addressConfiguration;
    }

    public static AddressConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AddressConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
