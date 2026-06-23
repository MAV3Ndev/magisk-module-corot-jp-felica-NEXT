package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSSandboxChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSSandboxChannelRequestJsonMarshaller {
    private static APNSSandboxChannelRequestJsonMarshaller instance;

    APNSSandboxChannelRequestJsonMarshaller() {
    }

    public void marshall(APNSSandboxChannelRequest aPNSSandboxChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSSandboxChannelRequest.getBundleId() != null) {
            String bundleId = aPNSSandboxChannelRequest.getBundleId();
            awsJsonWriter.name("BundleId");
            awsJsonWriter.value(bundleId);
        }
        if (aPNSSandboxChannelRequest.getCertificate() != null) {
            String certificate = aPNSSandboxChannelRequest.getCertificate();
            awsJsonWriter.name("Certificate");
            awsJsonWriter.value(certificate);
        }
        if (aPNSSandboxChannelRequest.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSSandboxChannelRequest.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSSandboxChannelRequest.getEnabled() != null) {
            Boolean enabled = aPNSSandboxChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSSandboxChannelRequest.getPrivateKey() != null) {
            String privateKey = aPNSSandboxChannelRequest.getPrivateKey();
            awsJsonWriter.name("PrivateKey");
            awsJsonWriter.value(privateKey);
        }
        if (aPNSSandboxChannelRequest.getTeamId() != null) {
            String teamId = aPNSSandboxChannelRequest.getTeamId();
            awsJsonWriter.name("TeamId");
            awsJsonWriter.value(teamId);
        }
        if (aPNSSandboxChannelRequest.getTokenKey() != null) {
            String tokenKey = aPNSSandboxChannelRequest.getTokenKey();
            awsJsonWriter.name("TokenKey");
            awsJsonWriter.value(tokenKey);
        }
        if (aPNSSandboxChannelRequest.getTokenKeyId() != null) {
            String tokenKeyId = aPNSSandboxChannelRequest.getTokenKeyId();
            awsJsonWriter.name("TokenKeyId");
            awsJsonWriter.value(tokenKeyId);
        }
        awsJsonWriter.endObject();
    }

    public static APNSSandboxChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSSandboxChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
