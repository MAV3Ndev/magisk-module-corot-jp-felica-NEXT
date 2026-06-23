package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ApplicationSettingsResource;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ApplicationSettingsResourceJsonUnmarshaller implements Unmarshaller<ApplicationSettingsResource, JsonUnmarshallerContext> {
    private static ApplicationSettingsResourceJsonUnmarshaller instance;

    ApplicationSettingsResourceJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ApplicationSettingsResource unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ApplicationSettingsResource applicationSettingsResource = new ApplicationSettingsResource();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApplicationId")) {
                applicationSettingsResource.setApplicationId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CampaignHook")) {
                applicationSettingsResource.setCampaignHook(CampaignHookJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LastModifiedDate")) {
                applicationSettingsResource.setLastModifiedDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Limits")) {
                applicationSettingsResource.setLimits(CampaignLimitsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("QuietTime")) {
                applicationSettingsResource.setQuietTime(QuietTimeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return applicationSettingsResource;
    }

    public static ApplicationSettingsResourceJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationSettingsResourceJsonUnmarshaller();
        }
        return instance;
    }
}
