package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.APNSChannelResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class APNSChannelResponseJsonUnmarshaller implements Unmarshaller<APNSChannelResponse, JsonUnmarshallerContext> {
    private static APNSChannelResponseJsonUnmarshaller instance;

    APNSChannelResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public APNSChannelResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        APNSChannelResponse aPNSChannelResponse = new APNSChannelResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApplicationId")) {
                aPNSChannelResponse.setApplicationId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("CreationDate")) {
                aPNSChannelResponse.setCreationDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultAuthenticationMethod")) {
                aPNSChannelResponse.setDefaultAuthenticationMethod(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                aPNSChannelResponse.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("HasCredential")) {
                aPNSChannelResponse.setHasCredential(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("HasTokenKey")) {
                aPNSChannelResponse.setHasTokenKey(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(JsonDocumentFields.POLICY_ID)) {
                aPNSChannelResponse.setId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("IsArchived")) {
                aPNSChannelResponse.setIsArchived(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LastModifiedBy")) {
                aPNSChannelResponse.setLastModifiedBy(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LastModifiedDate")) {
                aPNSChannelResponse.setLastModifiedDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(DataRecordKey.PLATFORM)) {
                aPNSChannelResponse.setPlatform(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(JsonDocumentFields.VERSION)) {
                aPNSChannelResponse.setVersion(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return aPNSChannelResponse;
    }

    public static APNSChannelResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new APNSChannelResponseJsonUnmarshaller();
        }
        return instance;
    }
}
