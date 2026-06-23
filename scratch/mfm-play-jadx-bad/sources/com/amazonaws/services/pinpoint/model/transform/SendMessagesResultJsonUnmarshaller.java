package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SendMessagesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class SendMessagesResultJsonUnmarshaller implements Unmarshaller<SendMessagesResult, JsonUnmarshallerContext> {
    private static SendMessagesResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SendMessagesResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new SendMessagesResult();
    }

    public static SendMessagesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SendMessagesResultJsonUnmarshaller();
        }
        return instance;
    }
}
