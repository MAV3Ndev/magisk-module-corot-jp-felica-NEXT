package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.pinpoint.model.ForbiddenException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class ForbiddenExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ForbiddenExceptionUnmarshaller() {
        super(ForbiddenException.class);
    }

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller
    public boolean match(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        return jsonErrorResponse.getErrorCode().equals("ForbiddenException");
    }

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller, com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        ForbiddenException forbiddenException = (ForbiddenException) super.unmarshall(jsonErrorResponse);
        forbiddenException.setErrorCode("ForbiddenException");
        forbiddenException.setRequestID(String.valueOf(jsonErrorResponse.get("RequestID")));
        return forbiddenException;
    }
}
