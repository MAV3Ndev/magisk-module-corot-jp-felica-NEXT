package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.DeleteUserPoolDomainResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserPoolDomainResultJsonUnmarshaller implements Unmarshaller<DeleteUserPoolDomainResult, JsonUnmarshallerContext> {
    private static DeleteUserPoolDomainResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteUserPoolDomainResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteUserPoolDomainResult();
    }

    public static DeleteUserPoolDomainResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteUserPoolDomainResultJsonUnmarshaller();
        }
        return instance;
    }
}
