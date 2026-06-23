package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.ProvidedContext;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
class ProvidedContextStaxUnmarshaller implements Unmarshaller<ProvidedContext, StaxUnmarshallerContext> {
    private static ProvidedContextStaxUnmarshaller instance;

    ProvidedContextStaxUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ProvidedContext unmarshall(StaxUnmarshallerContext staxUnmarshallerContext) throws Exception {
        ProvidedContext providedContext = new ProvidedContext();
        int currentDepth = staxUnmarshallerContext.getCurrentDepth();
        int i = currentDepth + 1;
        if (staxUnmarshallerContext.isStartOfDocument()) {
            i = currentDepth + 3;
        }
        while (true) {
            int iNextEvent = staxUnmarshallerContext.nextEvent();
            if (iNextEvent == 1) {
                break;
            }
            if (iNextEvent == 2) {
                if (staxUnmarshallerContext.testExpression("ProviderArn", i)) {
                    providedContext.setProviderArn(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(staxUnmarshallerContext));
                } else if (staxUnmarshallerContext.testExpression("ContextAssertion", i)) {
                    providedContext.setContextAssertion(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(staxUnmarshallerContext));
                }
            } else if (iNextEvent == 3 && staxUnmarshallerContext.getCurrentDepth() < currentDepth) {
                break;
            }
        }
        return providedContext;
    }

    public static ProvidedContextStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ProvidedContextStaxUnmarshaller();
        }
        return instance;
    }
}
