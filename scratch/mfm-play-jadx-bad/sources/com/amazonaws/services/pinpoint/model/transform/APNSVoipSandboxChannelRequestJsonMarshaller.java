package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSVoipSandboxChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSVoipSandboxChannelRequestJsonMarshaller {
    private static APNSVoipSandboxChannelRequestJsonMarshaller instance;

    APNSVoipSandboxChannelRequestJsonMarshaller() {
    }

    public void marshall(APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSVoipSandboxChannelRequest.getBundleId() != null) {
            String bundleId = aPNSVoipSandboxChannelRequest.getBundleId();
            awsJsonWriter.name("BundleId");
            awsJsonWriter.value(bundleId);
        }
        if (aPNSVoipSandboxChannelRequest.getCertificate() != null) {
            String certificate = aPNSVoipSandboxChannelRequest.getCertificate();
            awsJsonWriter.name("Certificate");
            awsJsonWriter.value(certificate);
        }
        if (aPNSVoipSandboxChannelRequest.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSVoipSandboxChannelRequest.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSVoipSandboxChannelRequest.getEnabled() != null) {
            Boolean enabled = aPNSVoipSandboxChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSVoipSandboxChannelRequest.getPrivateKey() != null) {
            String privateKey = aPNSVoipSandboxChannelRequest.getPrivateKey();
            awsJsonWriter.name("PrivateKey");
            awsJsonWriter.value(privateKey);
        }
        if (aPNSVoipSandboxChannelRequest.getTeamId() != null) {
            String teamId = aPNSVoipSandboxChannelRequest.getTeamId();
            awsJsonWriter.name("TeamId");
            awsJsonWriter.value(teamId);
        }
        if (aPNSVoipSandboxChannelRequest.getTokenKey() != null) {
            String tokenKey = aPNSVoipSandboxChannelRequest.getTokenKey();
            awsJsonWriter.name("TokenKey");
            awsJsonWriter.value(tokenKey);
        }
        if (aPNSVoipSandboxChannelRequest.getTokenKeyId() != null) {
            String tokenKeyId = aPNSVoipSandboxChannelRequest.getTokenKeyId();
            awsJsonWriter.name("TokenKeyId");
            awsJsonWriter.value(tokenKeyId);
        }
        awsJsonWriter.endObject();
    }

    public static APNSVoipSandboxChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSVoipSandboxChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
