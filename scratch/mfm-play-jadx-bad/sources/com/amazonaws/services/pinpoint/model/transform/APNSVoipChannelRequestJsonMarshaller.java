package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSVoipChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipChannelRequestJsonMarshaller {
    private static APNSVoipChannelRequestJsonMarshaller instance;

    APNSVoipChannelRequestJsonMarshaller() {
    }

    public void marshall(APNSVoipChannelRequest aPNSVoipChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSVoipChannelRequest.getBundleId() != null) {
            String bundleId = aPNSVoipChannelRequest.getBundleId();
            awsJsonWriter.name("BundleId");
            awsJsonWriter.value(bundleId);
        }
        if (aPNSVoipChannelRequest.getCertificate() != null) {
            String certificate = aPNSVoipChannelRequest.getCertificate();
            awsJsonWriter.name("Certificate");
            awsJsonWriter.value(certificate);
        }
        if (aPNSVoipChannelRequest.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSVoipChannelRequest.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSVoipChannelRequest.getEnabled() != null) {
            Boolean enabled = aPNSVoipChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSVoipChannelRequest.getPrivateKey() != null) {
            String privateKey = aPNSVoipChannelRequest.getPrivateKey();
            awsJsonWriter.name("PrivateKey");
            awsJsonWriter.value(privateKey);
        }
        if (aPNSVoipChannelRequest.getTeamId() != null) {
            String teamId = aPNSVoipChannelRequest.getTeamId();
            awsJsonWriter.name("TeamId");
            awsJsonWriter.value(teamId);
        }
        if (aPNSVoipChannelRequest.getTokenKey() != null) {
            String tokenKey = aPNSVoipChannelRequest.getTokenKey();
            awsJsonWriter.name("TokenKey");
            awsJsonWriter.value(tokenKey);
        }
        if (aPNSVoipChannelRequest.getTokenKeyId() != null) {
            String tokenKeyId = aPNSVoipChannelRequest.getTokenKeyId();
            awsJsonWriter.name("TokenKeyId");
            awsJsonWriter.value(tokenKeyId);
        }
        awsJsonWriter.endObject();
    }

    public static APNSVoipChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
