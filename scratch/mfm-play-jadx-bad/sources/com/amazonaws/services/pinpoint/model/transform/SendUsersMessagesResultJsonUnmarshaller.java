package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SendUsersMessagesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class SendUsersMessagesResultJsonUnmarshaller implements Unmarshaller<SendUsersMessagesResult, JsonUnmarshallerContext> {
    private static SendUsersMessagesResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SendUsersMessagesResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new SendUsersMessagesResult();
    }

    public static SendUsersMessagesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SendUsersMessagesResultJsonUnmarshaller();
        }
        return instance;
    }
}
