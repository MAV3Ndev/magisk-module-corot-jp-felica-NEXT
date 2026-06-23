package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSVoipSandboxChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipSandboxChannelRequestJsonUnmarshaller implements Unmarshaller<APNSVoipSandboxChannelRequest, JsonUnmarshallerContext> {
    private static APNSVoipSandboxChannelRequestJsonUnmarshaller instance;

    APNSVoipSandboxChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public APNSVoipSandboxChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest = new APNSVoipSandboxChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("BundleId")) {
                aPNSVoipSandboxChannelRequest.setBundleId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Certificate")) {
                aPNSVoipSandboxChannelRequest.setCertificate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultAuthenticationMethod")) {
                aPNSVoipSandboxChannelRequest.setDefaultAuthenticationMethod(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                aPNSVoipSandboxChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("PrivateKey")) {
                aPNSVoipSandboxChannelRequest.setPrivateKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TeamId")) {
                aPNSVoipSandboxChannelRequest.setTeamId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKey")) {
                aPNSVoipSandboxChannelRequest.setTokenKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TokenKeyId")) {
                aPNSVoipSandboxChannelRequest.setTokenKeyId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return aPNSVoipSandboxChannelRequest;
    }

    public static APNSVoipSandboxChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipSandboxChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
