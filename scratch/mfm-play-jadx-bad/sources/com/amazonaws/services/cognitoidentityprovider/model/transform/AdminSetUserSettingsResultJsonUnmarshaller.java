package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.AdminSetUserSettingsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class AdminSetUserSettingsResultJsonUnmarshaller implements Unmarshaller<AdminSetUserSettingsResult, JsonUnmarshallerContext> {
    private static AdminSetUserSettingsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public AdminSetUserSettingsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new AdminSetUserSettingsResult();
    }

    public static AdminSetUserSettingsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AdminSetUserSettingsResultJsonUnmarshaller();
        }
        return instance;
    }
}
