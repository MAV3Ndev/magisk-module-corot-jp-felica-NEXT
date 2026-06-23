package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.GetIdentityProviderByIdentifierResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
public class GetIdentityProviderByIdentifierResultJsonUnmarshaller implements Unmarshaller<GetIdentityProviderByIdentifierResult, JsonUnmarshallerContext> {
    private static GetIdentityProviderByIdentifierResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetIdentityProviderByIdentifierResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        GetIdentityProviderByIdentifierResult getIdentityProviderByIdentifierResult = new GetIdentityProviderByIdentifierResult();
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("IdentityProvider")) {
                getIdentityProviderByIdentifierResult.setIdentityProvider(IdentityProviderTypeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getIdentityProviderByIdentifierResult;
    }

    public static GetIdentityProviderByIdentifierResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetIdentityProviderByIdentifierResultJsonUnmarshaller();
        }
        return instance;
    }
}
