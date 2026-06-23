package com.amazonaws.services.pinpointanalytics.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.pinpointanalytics.model.BadRequestException;
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

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller, com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        BadRequestException badRequestException = (BadRequestException) super.unmarshall(jsonErrorResponse);
        badRequestException.setErrorCode("BadRequestException");
        return badRequestException;
    }
}
