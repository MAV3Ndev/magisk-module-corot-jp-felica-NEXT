package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.DeleteUserAttributesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserAttributesResultJsonUnmarshaller implements Unmarshaller<DeleteUserAttributesResult, JsonUnmarshallerContext> {
    private static DeleteUserAttributesResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteUserAttributesResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteUserAttributesResult();
    }

    public static DeleteUserAttributesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteUserAttributesResultJsonUnmarshaller();
        }
        return instance;
    }
}
