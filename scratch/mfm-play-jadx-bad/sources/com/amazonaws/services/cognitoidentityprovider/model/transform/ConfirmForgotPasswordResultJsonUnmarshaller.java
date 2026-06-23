package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.ConfirmForgotPasswordResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class ConfirmForgotPasswordResultJsonUnmarshaller implements Unmarshaller<ConfirmForgotPasswordResult, JsonUnmarshallerContext> {
    private static ConfirmForgotPasswordResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ConfirmForgotPasswordResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new ConfirmForgotPasswordResult();
    }

    public static ConfirmForgotPasswordResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ConfirmForgotPasswordResultJsonUnmarshaller();
        }
        return instance;
    }
}
