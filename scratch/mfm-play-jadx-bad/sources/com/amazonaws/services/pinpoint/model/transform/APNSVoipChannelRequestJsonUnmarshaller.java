package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSVoipChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipChannelRequestJsonUnmarshaller implements Unmarshaller<APNSVoipChannelRequest, JsonUnmarshallerContext> {
    private static APNSVoipChannelRequestJsonUnmarshaller instance;

    APNSVoipChannelRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public APNSVoipChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        APNSVoipChannelRequest aPNSVoipChannelRequest = new APNSVoipChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BundleId")) {
                aPNSVoipChannelRequest.setBundleId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Certificate")) {
                aPNSVoipChannelRequest.setCertificate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultAuthenticationMethod")) {
                aPNSVoipChannelRequest.setDefaultAuthenticationMethod(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                aPNSVoipChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PrivateKey")) {
                aPNSVoipChannelRequest.setPrivateKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TeamId")) {
                aPNSVoipChannelRequest.setTeamId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKey")) {
                aPNSVoipChannelRequest.setTokenKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKeyId")) {
                aPNSVoipChannelRequest.setTokenKeyId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return aPNSVoipChannelRequest;
    }

    public static APNSVoipChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
