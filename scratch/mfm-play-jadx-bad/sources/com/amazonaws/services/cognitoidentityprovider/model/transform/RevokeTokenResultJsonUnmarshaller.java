package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.RevokeTokenResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class RevokeTokenResultJsonUnmarshaller implements Unmarshaller<RevokeTokenResult, JsonUnmarshallerContext> {
    private static RevokeTokenResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public RevokeTokenResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new RevokeTokenResult();
    }

    public static RevokeTokenResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RevokeTokenResultJsonUnmarshaller();
        }
        return instance;
    }
}
