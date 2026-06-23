package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.pinpoint.model.MethodNotAllowedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class MethodNotAllowedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public MethodNotAllowedExceptionUnmarshaller() {
        super(MethodNotAllowedException.class);
    }

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller
    public boolean match(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        return jsonErrorResponse.getErrorCode().equals("MethodNotAllowedException");
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.JsonErrorUnmarshaller, com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        MethodNotAllowedException methodNotAllowedException = (MethodNotAllowedException) super.unmarshall(jsonErrorResponse);
        methodNotAllowedException.setErrorCode("MethodNotAllowedException");
        methodNotAllowedException.setRequestID(String.valueOf(jsonErrorResponse.get("RequestID")));
        return methodNotAllowedException;
    }
}
