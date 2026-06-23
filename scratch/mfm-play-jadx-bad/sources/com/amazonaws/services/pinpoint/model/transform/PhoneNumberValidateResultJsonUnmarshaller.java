package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.PhoneNumberValidateResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class PhoneNumberValidateResultJsonUnmarshaller implements Unmarshaller<PhoneNumberValidateResult, JsonUnmarshallerContext> {
    private static PhoneNumberValidateResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public PhoneNumberValidateResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new PhoneNumberValidateResult();
    }

    public static PhoneNumberValidateResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PhoneNumberValidateResultJsonUnmarshaller();
        }
        return instance;
    }
}
