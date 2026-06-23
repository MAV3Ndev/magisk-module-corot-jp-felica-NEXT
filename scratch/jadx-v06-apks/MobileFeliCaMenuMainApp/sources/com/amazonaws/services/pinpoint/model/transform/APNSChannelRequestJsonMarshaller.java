package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.APNSChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class APNSChannelRequestJsonMarshaller {
    private static APNSChannelRequestJsonMarshaller instance;

    APNSChannelRequestJsonMarshaller() {
    }

    public void marshall(APNSChannelRequest aPNSChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aPNSChannelRequest.getBundleId() != null) {
            String bundleId = aPNSChannelRequest.getBundleId();
            awsJsonWriter.name("BundleId");
            awsJsonWriter.value(bundleId);
        }
        if (aPNSChannelRequest.getCertificate() != null) {
            String certificate = aPNSChannelRequest.getCertificate();
            awsJsonWriter.name("Certificate");
            awsJsonWriter.value(certificate);
        }
        if (aPNSChannelRequest.getDefaultAuthenticationMethod() != null) {
            String defaultAuthenticationMethod = aPNSChannelRequest.getDefaultAuthenticationMethod();
            awsJsonWriter.name("DefaultAuthenticationMethod");
            awsJsonWriter.value(defaultAuthenticationMethod);
        }
        if (aPNSChannelRequest.getEnabled() != null) {
            Boolean enabled = aPNSChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (aPNSChannelRequest.getPrivateKey() != null) {
            String privateKey = aPNSChannelRequest.getPrivateKey();
            awsJsonWriter.name("PrivateKey");
            awsJsonWriter.value(privateKey);
        }
        if (aPNSChannelRequest.getTeamId() != null) {
            String teamId = aPNSChannelRequest.getTeamId();
            awsJsonWriter.name("TeamId");
            awsJsonWriter.value(teamId);
        }
        if (aPNSChannelRequest.getTokenKey() != null) {
            String tokenKey = aPNSChannelRequest.getTokenKey();
            awsJsonWriter.name("TokenKey");
            awsJsonWriter.value(tokenKey);
        }
        if (aPNSChannelRequest.getTokenKeyId() != null) {
            String tokenKeyId = aPNSChannelRequest.getTokenKeyId();
            awsJsonWriter.name("TokenKeyId");
            awsJsonWriter.value(tokenKeyId);
        }
        awsJsonWriter.endObject();
    }

    public static APNSChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new APNSChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
