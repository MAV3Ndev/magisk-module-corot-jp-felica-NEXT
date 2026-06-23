package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.services.cognitoidentityprovider.model.InvalidSmsRoleAccessPolicyException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class InvalidSmsRoleAccessPolicyExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidSmsRoleAccessPolicyExceptionUnmarshaller() {
        super(InvalidSmsRoleAccessPolicyException.class);
    }

    @Override // com.amazonaws.transform.JsonErrorUnmarshaller
    public boolean match(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        return jsonErrorResponse.getErrorCode().equals("InvalidSmsRoleAccessPolicyException");
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.JsonErrorUnmarshaller, com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(JsonErrorResponseHandler.JsonErrorResponse jsonErrorResponse) throws Exception {
        InvalidSmsRoleAccessPolicyException invalidSmsRoleAccessPolicyException = (InvalidSmsRoleAccessPolicyException) super.unmarshall(jsonErrorResponse);
        invalidSmsRoleAccessPolicyException.setErrorCode("InvalidSmsRoleAccessPolicyException");
        return invalidSmsRoleAccessPolicyException;
    }
}
