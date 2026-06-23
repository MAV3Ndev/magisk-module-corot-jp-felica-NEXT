package com.amazonaws.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.util.XpathUtils;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public class StandardErrorUnmarshaller extends AbstractErrorUnmarshaller<Node> {
    public StandardErrorUnmarshaller() {
    }

    protected StandardErrorUnmarshaller(Class<? extends AmazonServiceException> cls) {
        super(cls);
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public AmazonServiceException unmarshall(Node node) throws Exception {
        String errorCode = parseErrorCode(node);
        String strAsString = XpathUtils.asString("ErrorResponse/Error/Type", node);
        String strAsString2 = XpathUtils.asString("ErrorResponse/RequestId", node);
        AmazonServiceException amazonServiceExceptionNewException = newException(XpathUtils.asString("ErrorResponse/Error/Message", node));
        amazonServiceExceptionNewException.setErrorCode(errorCode);
        amazonServiceExceptionNewException.setRequestId(strAsString2);
        if (strAsString == null) {
            amazonServiceExceptionNewException.setErrorType(AmazonServiceException.ErrorType.Unknown);
            return amazonServiceExceptionNewException;
        }
        if ("Receiver".equalsIgnoreCase(strAsString)) {
            amazonServiceExceptionNewException.setErrorType(AmazonServiceException.ErrorType.Service);
            return amazonServiceExceptionNewException;
        }
        if ("Sender".equalsIgnoreCase(strAsString)) {
            amazonServiceExceptionNewException.setErrorType(AmazonServiceException.ErrorType.Client);
        }
        return amazonServiceExceptionNewException;
    }

    public String parseErrorCode(Node node) throws Exception {
        return XpathUtils.asString("ErrorResponse/Error/Code", node);
    }

    public String getErrorPropertyPath(String str) {
        return "ErrorResponse/Error/" + str;
    }
}
