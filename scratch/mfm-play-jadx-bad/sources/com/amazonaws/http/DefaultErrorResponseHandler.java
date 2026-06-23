package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.XpathUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes.dex */
public class DefaultErrorResponseHandler implements HttpResponseHandler<AmazonServiceException> {
    private static final Log log = LogFactory.getLog((Class<?>) DefaultErrorResponseHandler.class);
    private List<Unmarshaller<AmazonServiceException, Node>> unmarshallerList;

    @Override // com.amazonaws.http.HttpResponseHandler
    public boolean needsConnectionLeftOpen() {
        return false;
    }

    public DefaultErrorResponseHandler(List<Unmarshaller<AmazonServiceException, Node>> list) {
        this.unmarshallerList = list;
    }

    /* JADX DEBUG: Method merged with bridge method: handle(Lcom/amazonaws/http/HttpResponse;)Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.amazonaws.http.HttpResponseHandler
    public AmazonServiceException handle(HttpResponse httpResponse) throws Exception {
        try {
            String string = IOUtils.toString(httpResponse.getContent());
            try {
                Document documentDocumentFrom = XpathUtils.documentFrom(string);
                Iterator<Unmarshaller<AmazonServiceException, Node>> it = this.unmarshallerList.iterator();
                while (it.hasNext()) {
                    AmazonServiceException amazonServiceExceptionUnmarshall = it.next().unmarshall(documentDocumentFrom);
                    if (amazonServiceExceptionUnmarshall != null) {
                        amazonServiceExceptionUnmarshall.setStatusCode(httpResponse.getStatusCode());
                        return amazonServiceExceptionUnmarshall;
                    }
                }
                throw new AmazonClientException("Unable to unmarshall error response from service");
            } catch (Exception e) {
                return newAmazonServiceException(String.format("Unable to unmarshall error response (%s)", string), httpResponse, e);
            }
        } catch (IOException e2) {
            Log log2 = log;
            if (log2.isDebugEnabled()) {
                log2.debug("Failed in reading the error response", e2);
            }
            return newAmazonServiceException("Unable to unmarshall error response", httpResponse, e2);
        }
    }

    private AmazonServiceException newAmazonServiceException(String str, HttpResponse httpResponse, Exception exc) {
        AmazonServiceException amazonServiceException = new AmazonServiceException(str, exc);
        int statusCode = httpResponse.getStatusCode();
        amazonServiceException.setErrorCode(statusCode + " " + httpResponse.getStatusText());
        amazonServiceException.setErrorType(AmazonServiceException.ErrorType.Unknown);
        amazonServiceException.setStatusCode(statusCode);
        return amazonServiceException;
    }
}
