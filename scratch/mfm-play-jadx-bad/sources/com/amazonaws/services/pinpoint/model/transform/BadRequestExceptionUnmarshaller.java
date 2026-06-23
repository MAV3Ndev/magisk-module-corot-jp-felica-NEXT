package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.pinpoint.model.BadRequestException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class BadRequestExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public BadRequestExceptionUnmarshaller() {
        super(BadRequestException.class);
    }

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller
    public boolean match(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        return jsonErrorResponse.getErrorCode().equals("BadRequestException");
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.JsonErrorUnmarshaller, com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        BadRequestException badRequestException = (BadRequestException) super.unmarshall(jsonErrorResponse);
        badRequestException.setErrorCode("BadRequestException");
        badRequestException.setRequestID(String.valueOf(jsonErrorResponse.get("RequestID")));
        return badRequestException;
    }
}
