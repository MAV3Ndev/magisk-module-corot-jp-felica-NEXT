package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSSandboxChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class APNSSandboxChannelRequestJsonUnmarshaller implements Unmarshaller<APNSSandboxChannelRequest, JsonUnmarshallerContext> {
    private static APNSSandboxChannelRequestJsonUnmarshaller instance;

    APNSSandboxChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public APNSSandboxChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        APNSSandboxChannelRequest aPNSSandboxChannelRequest = new APNSSandboxChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BundleId")) {
                aPNSSandboxChannelRequest.setBundleId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Certificate")) {
                aPNSSandboxChannelRequest.setCertificate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultAuthenticationMethod")) {
                aPNSSandboxChannelRequest.setDefaultAuthenticationMethod(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                aPNSSandboxChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PrivateKey")) {
                aPNSSandboxChannelRequest.setPrivateKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TeamId")) {
                aPNSSandboxChannelRequest.setTeamId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKey")) {
                aPNSSandboxChannelRequest.setTokenKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKeyId")) {
                aPNSSandboxChannelRequest.setTokenKeyId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return aPNSSandboxChannelRequest;
    }

    public static APNSSandboxChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new APNSSandboxChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
