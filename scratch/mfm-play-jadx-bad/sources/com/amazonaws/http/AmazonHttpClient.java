package com.amazonaws.http;

import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.CredentialsRequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.TimingInfo;
import com.amazonaws.util.URIBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class AmazonHttpClient {
    private static final String HEADER_SDK_RETRY_INFO = "aws-sdk-retry";
    private static final String HEADER_SDK_TRANSACTION_ID = "aws-sdk-invocation-id";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final int HTTP_STATUS_MULTIPLE_CHOICES = 300;
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_REQ_TOO_LONG = 413;
    private static final int HTTP_STATUS_SERVICE_UNAVAILABLE = 503;
    private static final int HTTP_STATUS_TEMP_REDIRECT = 307;
    private static final long TIME_MILLISEC = 1000;
    final ClientConfiguration config;
    final HttpClient httpClient;
    private final HttpRequestFactory requestFactory;
    private final RequestMetricCollector requestMetricCollector;
    private static final Log REQUEST_LOG = LogFactory.getLog("com.amazonaws.request");
    static final Log log = LogFactory.getLog((Class<?>) AmazonHttpClient.class);

    @Deprecated
    public ResponseMetadata getResponseMetadataForRequest(AmazonWebServiceRequest amazonWebServiceRequest) {
        return null;
    }

    public AmazonHttpClient(ClientConfiguration clientConfiguration) {
        this(clientConfiguration, new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        this(clientConfiguration, new UrlHttpClient(clientConfiguration), requestMetricCollector);
    }

    public AmazonHttpClient(ClientConfiguration clientConfiguration, HttpClient httpClient) {
        this.requestFactory = new HttpRequestFactory();
        this.config = clientConfiguration;
        this.httpClient = httpClient;
        this.requestMetricCollector = null;
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration clientConfiguration, HttpClient httpClient, RequestMetricCollector requestMetricCollector) {
        this.requestFactory = new HttpRequestFactory();
        this.config = clientConfiguration;
        this.httpClient = httpClient;
        this.requestMetricCollector = requestMetricCollector;
    }

    public <T> Response<T> execute(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponseHandler<AmazonServiceException> httpResponseHandler2, ExecutionContext executionContext) throws Throwable {
        Response<T> responseExecuteHelper;
        if (request.getHostPrefix() != null) {
            try {
                URI endpoint = request.getEndpoint();
                request.setEndpoint(URIBuilder.builder(endpoint).host(request.getHostPrefix() + endpoint.getHost()).build());
            } catch (URISyntaxException e) {
                Log log2 = log;
                if (log2.isDebugEnabled()) {
                    log2.debug("Failed to prepend host prefix: " + e.getMessage(), e);
                }
            }
        }
        if (executionContext == null) {
            throw new AmazonClientException("Internal SDK Error: No execution context parameter specified.");
        }
        List<RequestHandler2> listRequestHandler2s = requestHandler2s(request, executionContext);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        try {
            responseExecuteHelper = executeHelper(request, httpResponseHandler, httpResponseHandler2, executionContext);
        } catch (AmazonClientException e2) {
            e = e2;
            responseExecuteHelper = null;
        }
        try {
            afterResponse(request, listRequestHandler2s, responseExecuteHelper, awsRequestMetrics.getTimingInfo().endTiming());
            return responseExecuteHelper;
        } catch (AmazonClientException e3) {
            e = e3;
            afterError(request, responseExecuteHelper, listRequestHandler2s, e);
            throw e;
        }
    }

    void afterError(Request<?> request, Response<?> response, List<RequestHandler2> list, AmazonClientException amazonClientException) {
        Iterator<RequestHandler2> it = list.iterator();
        while (it.hasNext()) {
            it.next().afterError(request, response, amazonClientException);
        }
    }

    <T> void afterResponse(Request<?> request, List<RequestHandler2> list, Response<T> response, TimingInfo timingInfo) {
        Iterator<RequestHandler2> it = list.iterator();
        while (it.hasNext()) {
            it.next().afterResponse(request, response);
        }
    }

    List<RequestHandler2> requestHandler2s(Request<?> request, ExecutionContext executionContext) {
        List<RequestHandler2> requestHandler2s = executionContext.getRequestHandler2s();
        if (requestHandler2s == null) {
            return Collections.EMPTY_LIST;
        }
        for (RequestHandler2 requestHandler2 : requestHandler2s) {
            if (requestHandler2 instanceof CredentialsRequestHandler) {
                ((CredentialsRequestHandler) requestHandler2).setCredentials(executionContext.getCredentials());
            }
            requestHandler2.beforeRequest(request);
        }
        return requestHandler2s;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [482=4, 483=4, 486=4] */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:164:0x0314 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:170:0x031a */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:216:0x0393 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:261:0x0455 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:331:0x0446 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0300 A[Catch: IOException -> 0x0308, TRY_LEAVE, TryCatch #9 {IOException -> 0x0308, blocks: (B:156:0x02fa, B:158:0x0300), top: B:280:0x02fa }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x03d6 A[Catch: all -> 0x0455, TRY_ENTER, TryCatch #24 {all -> 0x0455, blocks: (B:243:0x03cc, B:246:0x03d6, B:247:0x03ec, B:249:0x0429, B:260:0x0454, B:210:0x0389, B:211:0x038e), top: B:289:0x03cc }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0429 A[Catch: all -> 0x0455, TRY_LEAVE, TryCatch #24 {all -> 0x0455, blocks: (B:243:0x03cc, B:246:0x03d6, B:247:0x03ec, B:249:0x0429, B:260:0x0454, B:210:0x0389, B:211:0x038e), top: B:289:0x03cc }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x045a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0178 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0454 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:333:? A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x013d A[Catch: all -> 0x00e1, IOException -> 0x0399, Error -> 0x03a7, RuntimeException -> 0x03b2, TRY_ENTER, TRY_LEAVE, TryCatch #14 {IOException -> 0x0399, blocks: (B:44:0x0120, B:46:0x0128, B:48:0x012f, B:49:0x0134, B:50:0x0135, B:52:0x013d), top: B:285:0x0120 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0158  */
    /* JADX WARN: Type inference failed for: r0v42, types: [com.amazonaws.http.HttpRequestFactory] */
    /* JADX WARN: Type inference failed for: r24v0, types: [com.amazonaws.http.AmazonHttpClient] */
    /* JADX WARN: Type inference failed for: r2v27, types: [com.amazonaws.logging.Log] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.amazonaws.logging.Log] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.amazonaws.http.ExecutionContext] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v33 */
    /* JADX WARN: Type inference failed for: r8v37 */
    /* JADX WARN: Type inference failed for: r8v40 */
    /* JADX WARN: Type inference failed for: r8v54 */
    /* JADX WARN: Type inference failed for: r8v55 */
    /* JADX WARN: Type inference failed for: r8v56 */
    /* JADX WARN: Type inference failed for: r8v57 */
    /* JADX WARN: Type inference failed for: r8v58 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v60 */
    /* JADX WARN: Type inference failed for: r8v61 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    <T> Response<T> executeHelper(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponseHandler<AmazonServiceException> httpResponseHandler2, ExecutionContext executionContext) throws Throwable {
        ?? r8;
        Throwable th;
        int i;
        long j;
        Signer signer;
        LinkedHashMap linkedHashMap;
        long jPauseBeforeNextRetry;
        Object obj;
        AmazonClientException amazonClientException;
        Log log2;
        Signer signerByURI;
        Log log3;
        HttpRequest httpRequestCreateHttpRequest;
        HttpResponse httpResponseExecute;
        Object obj2;
        HttpResponse httpResponse;
        HttpResponse httpResponse2;
        HttpResponse httpResponse3;
        Object obj3;
        Object obj4;
        Object obj5;
        boolean z;
        Object obj6;
        StringBuilder sb;
        Object obj7;
        Object obj8;
        ExecutionContext executionContext2 = executionContext;
        AWSRequestMetrics awsRequestMetrics = executionContext2.getAwsRequestMetrics();
        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.ServiceName, request.getServiceName());
        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.ServiceEndpoint, request.getEndpoint());
        setUserAgent(request);
        request.addHeader(HEADER_SDK_TRANSACTION_ID, UUID.randomUUID().toString());
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(request.getParameters());
        HashMap map = new HashMap(request.getHeaders());
        InputStream content = request.getContent();
        if (content != null && content.markSupported()) {
            content.mark(-1);
        }
        AWSCredentials credentials = executionContext2.getCredentials();
        int i2 = 0;
        long j2 = 0;
        AmazonClientException amazonClientException2 = null;
        Signer signer2 = null;
        URI uriCreate = null;
        HttpResponse httpResponse4 = null;
        HttpRequest httpRequest = null;
        boolean z2 = false;
        ?? r82 = executionContext2;
        while (true) {
            int i3 = i2 + 1;
            boolean z3 = z2;
            long j3 = j2;
            awsRequestMetrics.setCounter(AWSRequestMetrics.Field.RequestCount, i3);
            if (i3 > 1) {
                request.setParameters(linkedHashMap2);
                request.setHeaders(map);
                request.setContent(content);
            }
            if (uriCreate != null && request.getEndpoint() == null && request.getResourcePath() == null) {
                request.setEndpoint(URI.create(uriCreate.getScheme() + "://" + uriCreate.getAuthority()));
                request.setResourcePath(uriCreate.getPath());
            }
            try {
                if (i3 > 1) {
                    try {
                        try {
                            try {
                                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                try {
                                    jPauseBeforeNextRetry = pauseBeforeNextRetry(request.getOriginalRequest(), amazonClientException2, i3, this.config.getRetryPolicy());
                                    try {
                                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                        InputStream content2 = request.getContent();
                                        if (content2 != null && content2.markSupported()) {
                                            content2.reset();
                                        }
                                    } catch (IOException e) {
                                        e = e;
                                        obj = "Cannot close the response content.";
                                        j = jPauseBeforeNextRetry;
                                        signer = signer2;
                                        linkedHashMap = linkedHashMap2;
                                        i = i3;
                                        r82 = obj;
                                        try {
                                            log2 = log;
                                            if (log2.isDebugEnabled()) {
                                            }
                                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                            if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            th = th;
                                            r8 = r82;
                                            if (z3) {
                                                throw th;
                                            }
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th3) {
                                    awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RetryPauseTime);
                                    throw th3;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                r8 = "Cannot close the response content.";
                            }
                        } catch (IOException e2) {
                            e = e2;
                            r82 = "Cannot close the response content.";
                            i = i3;
                            j = j3;
                            signer = signer2;
                            linkedHashMap = linkedHashMap2;
                            log2 = log;
                            if (log2.isDebugEnabled()) {
                            }
                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                            if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                            }
                        }
                    } catch (Error e3) {
                        e = e3;
                        throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                    } catch (RuntimeException e4) {
                        e = e4;
                        throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                    }
                } else {
                    jPauseBeforeNextRetry = j3;
                }
                signer = signer2;
                try {
                    try {
                        request.addHeader(HEADER_SDK_RETRY_INFO, i2 + DomExceptionUtils.SEPARATOR + jPauseBeforeNextRetry);
                        signerByURI = signer == null ? r82.getSignerByURI(request.getEndpoint()) : signer;
                    } catch (Throwable th5) {
                        th = th5;
                        r82 = "Cannot close the response content.";
                        th = th;
                        r8 = r82;
                    }
                } catch (IOException e5) {
                    e = e5;
                }
                if (signerByURI == null || credentials == null) {
                    log3 = REQUEST_LOG;
                    if (log3.isDebugEnabled()) {
                        signer = signerByURI;
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        signer = signerByURI;
                        sb2.append("Sending Request: ");
                        sb2.append(request.toString());
                        log3.debug(sb2.toString());
                    }
                    httpRequestCreateHttpRequest = this.requestFactory.createHttpRequest(request, this.config, r82);
                    try {
                        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.HttpRequestTime);
                    } catch (IOException e6) {
                        e = e6;
                        httpRequest = httpRequestCreateHttpRequest;
                        obj = "Cannot close the response content.";
                        j = jPauseBeforeNextRetry;
                        linkedHashMap = linkedHashMap2;
                        i = i3;
                        r82 = obj;
                        log2 = log;
                        if (log2.isDebugEnabled()) {
                        }
                        awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                        amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                        if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                        }
                    }
                    try {
                        httpResponseExecute = this.httpClient.execute(httpRequestCreateHttpRequest);
                        try {
                            try {
                                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.HttpRequestTime);
                            } catch (IOException e7) {
                                e = e7;
                                httpRequest = httpRequestCreateHttpRequest;
                                obj3 = "Cannot close the response content.";
                                j = jPauseBeforeNextRetry;
                                linkedHashMap = linkedHashMap2;
                                i = i3;
                            }
                            if (isRequestSuccessful(httpResponseExecute)) {
                                httpRequest = httpRequestCreateHttpRequest;
                                linkedHashMap = linkedHashMap2;
                                try {
                                    if (isTemporaryRedirect(httpResponseExecute)) {
                                        try {
                                            String str = httpResponseExecute.getHeaders().get("Location");
                                            Log log4 = log;
                                            obj6 = "Cannot close the response content.";
                                            try {
                                                try {
                                                    sb = new StringBuilder();
                                                    j = jPauseBeforeNextRetry;
                                                } catch (IOException e8) {
                                                    e = e8;
                                                    j = jPauseBeforeNextRetry;
                                                    httpResponse4 = httpResponseExecute;
                                                    i = i3;
                                                    r82 = obj6;
                                                    log2 = log;
                                                    if (log2.isDebugEnabled()) {
                                                        log2.debug("Unable to execute HTTP request: " + e.getMessage(), e);
                                                    }
                                                    awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                                    amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                                    if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                                        throw amazonClientException;
                                                    }
                                                    resetRequestAfterError(request, e);
                                                    if (!z3 && httpResponse4 != null) {
                                                        try {
                                                            if (httpResponse4.getRawContent() != null) {
                                                                httpResponse4.getRawContent().close();
                                                            }
                                                        } catch (IOException e9) {
                                                            log.warn(r82, e9);
                                                        }
                                                    }
                                                    z2 = z3;
                                                    signer2 = signer;
                                                    j2 = j;
                                                    int i4 = i;
                                                    amazonClientException2 = amazonClientException;
                                                    i2 = i4;
                                                    r82 = executionContext;
                                                    linkedHashMap2 = linkedHashMap;
                                                }
                                                try {
                                                    sb.append("Redirecting to: ");
                                                    sb.append(str);
                                                    log4.debug(sb.toString());
                                                    uriCreate = URI.create(str);
                                                    request.setEndpoint(null);
                                                    request.setResourcePath(null);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(httpResponseExecute.getStatusCode()));
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.RedirectLocation, str);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                                    i = i3;
                                                    z2 = z3;
                                                    obj7 = obj6;
                                                    amazonClientException = null;
                                                    httpResponse = httpResponseExecute;
                                                    if (!z2 && httpResponse != null) {
                                                        try {
                                                            if (httpResponse.getRawContent() != null) {
                                                                httpResponse.getRawContent().close();
                                                            }
                                                        } catch (IOException e10) {
                                                            log.warn(obj7, e10);
                                                        }
                                                    }
                                                    httpResponse4 = httpResponse;
                                                } catch (IOException e11) {
                                                    e = e11;
                                                    httpResponse4 = httpResponseExecute;
                                                    i = i3;
                                                    r82 = obj6;
                                                    log2 = log;
                                                    if (log2.isDebugEnabled()) {
                                                    }
                                                    awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                                    amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                                    if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                                    }
                                                }
                                            } catch (Error e12) {
                                                e = e12;
                                                throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                                            } catch (RuntimeException e13) {
                                                e = e13;
                                                throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                                            } catch (Throwable th6) {
                                                th = th6;
                                                th = th;
                                                httpResponse4 = httpResponseExecute;
                                                r8 = obj6;
                                                if (z3) {
                                                }
                                            }
                                        } catch (IOException e14) {
                                            e = e14;
                                            obj6 = "Cannot close the response content.";
                                        } catch (Error e15) {
                                            e = e15;
                                            obj6 = "Cannot close the response content.";
                                        } catch (RuntimeException e16) {
                                            e = e16;
                                            obj6 = "Cannot close the response content.";
                                        } catch (Throwable th7) {
                                            th = th7;
                                            obj6 = "Cannot close the response content.";
                                        }
                                    } else {
                                        j = jPauseBeforeNextRetry;
                                        try {
                                            boolean zNeedsConnectionLeftOpen = httpResponseHandler2.needsConnectionLeftOpen();
                                            try {
                                                AmazonServiceException amazonServiceExceptionHandleErrorResponse = handleErrorResponse(request, httpResponseHandler2, httpResponseExecute);
                                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, amazonServiceExceptionHandleErrorResponse.getRequestId());
                                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSErrorCode, amazonServiceExceptionHandleErrorResponse.getErrorCode());
                                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(amazonServiceExceptionHandleErrorResponse.getStatusCode()));
                                                i = i3;
                                                obj8 = "Cannot close the response content.";
                                                httpResponse = httpResponseExecute;
                                                try {
                                                    if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonServiceExceptionHandleErrorResponse, i, this.config.getRetryPolicy())) {
                                                        throw amazonServiceExceptionHandleErrorResponse;
                                                    }
                                                    if (RetryUtils.isClockSkewError(amazonServiceExceptionHandleErrorResponse)) {
                                                        SDKGlobalConfiguration.setGlobalTimeOffset(parseClockSkewOffset(httpResponse, amazonServiceExceptionHandleErrorResponse));
                                                    }
                                                    resetRequestAfterError(request, amazonServiceExceptionHandleErrorResponse);
                                                    z2 = zNeedsConnectionLeftOpen;
                                                    amazonClientException = amazonServiceExceptionHandleErrorResponse;
                                                    obj7 = obj8;
                                                    if (!z2) {
                                                        if (httpResponse.getRawContent() != null) {
                                                        }
                                                    }
                                                    httpResponse4 = httpResponse;
                                                } catch (IOException e17) {
                                                    e = e17;
                                                    z3 = zNeedsConnectionLeftOpen;
                                                    r82 = obj8;
                                                    httpResponse4 = httpResponse;
                                                    log2 = log;
                                                    if (log2.isDebugEnabled()) {
                                                    }
                                                    awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                                    awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                                    amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                                    if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                                    }
                                                } catch (Error e18) {
                                                    e = e18;
                                                    throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                                                } catch (RuntimeException e19) {
                                                    e = e19;
                                                    throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                                                } catch (Throwable th8) {
                                                    th = th8;
                                                    th = th;
                                                    z3 = zNeedsConnectionLeftOpen;
                                                    obj4 = obj8;
                                                    httpResponse4 = httpResponse;
                                                    r8 = obj4;
                                                    if (z3) {
                                                    }
                                                }
                                            } catch (IOException e20) {
                                                e = e20;
                                                i = i3;
                                                obj8 = "Cannot close the response content.";
                                                httpResponse = httpResponseExecute;
                                            } catch (Error e21) {
                                                e = e21;
                                                httpResponse = httpResponseExecute;
                                            } catch (RuntimeException e22) {
                                                e = e22;
                                                httpResponse = httpResponseExecute;
                                            } catch (Throwable th9) {
                                                th = th9;
                                                httpResponse = httpResponseExecute;
                                                obj8 = "Cannot close the response content.";
                                            }
                                        } catch (IOException e23) {
                                            e = e23;
                                            i = i3;
                                            obj3 = "Cannot close the response content.";
                                            httpResponse = httpResponseExecute;
                                            r82 = obj3;
                                            httpResponse4 = httpResponse;
                                            log2 = log;
                                            if (log2.isDebugEnabled()) {
                                            }
                                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                            if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                            }
                                        } catch (Error e24) {
                                            e = e24;
                                            httpResponse3 = httpResponseExecute;
                                            throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                                        } catch (RuntimeException e25) {
                                            e = e25;
                                            httpResponse2 = httpResponseExecute;
                                            throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                                        } catch (Throwable th10) {
                                            th = th10;
                                            httpResponse = httpResponseExecute;
                                            obj2 = "Cannot close the response content.";
                                            th = th;
                                            obj4 = obj2;
                                            httpResponse4 = httpResponse;
                                            r8 = obj4;
                                            if (z3) {
                                            }
                                        }
                                    }
                                } catch (IOException e26) {
                                    e = e26;
                                    obj3 = "Cannot close the response content.";
                                    j = jPauseBeforeNextRetry;
                                    i = i3;
                                }
                                signer2 = signer;
                                j2 = j;
                                int i42 = i;
                                amazonClientException2 = amazonClientException;
                                i2 = i42;
                                r82 = executionContext;
                                linkedHashMap2 = linkedHashMap;
                            } else {
                                try {
                                    try {
                                        httpRequest = httpRequestCreateHttpRequest;
                                    } catch (IOException e27) {
                                        e = e27;
                                        httpRequest = httpRequestCreateHttpRequest;
                                    }
                                    try {
                                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.StatusCode, Integer.valueOf(httpResponseExecute.getStatusCode()));
                                        boolean zNeedsConnectionLeftOpen2 = httpResponseHandler.needsConnectionLeftOpen();
                                        linkedHashMap = linkedHashMap2;
                                        try {
                                            z = zNeedsConnectionLeftOpen2;
                                            try {
                                                Response<T> response = new Response<>(handleResponse(request, httpResponseHandler, httpResponseExecute, r82), httpResponseExecute);
                                                if (!z && httpResponseExecute != null) {
                                                    try {
                                                        if (httpResponseExecute.getRawContent() != null) {
                                                            httpResponseExecute.getRawContent().close();
                                                        }
                                                    } catch (IOException e28) {
                                                        log.warn("Cannot close the response content.", e28);
                                                    }
                                                }
                                                return response;
                                            } catch (IOException e29) {
                                                e = e29;
                                                r82 = "Cannot close the response content.";
                                                j = jPauseBeforeNextRetry;
                                                i = i3;
                                                z3 = z;
                                                httpResponse4 = httpResponseExecute;
                                                log2 = log;
                                                if (log2.isDebugEnabled()) {
                                                }
                                                awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                                amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                                if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                                }
                                            } catch (Error e30) {
                                                e = e30;
                                                throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                                            } catch (RuntimeException e31) {
                                                e = e31;
                                                throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                                            } catch (Throwable th11) {
                                                th = th11;
                                                th = th;
                                                obj5 = "Cannot close the response content.";
                                                z3 = z;
                                                httpResponse4 = httpResponseExecute;
                                                r8 = obj5;
                                                if (z3) {
                                                }
                                            }
                                        } catch (IOException e32) {
                                            e = e32;
                                            z = zNeedsConnectionLeftOpen2;
                                        } catch (Error e33) {
                                            e = e33;
                                        } catch (RuntimeException e34) {
                                            e = e34;
                                        } catch (Throwable th12) {
                                            th = th12;
                                            z = zNeedsConnectionLeftOpen2;
                                        }
                                    } catch (IOException e35) {
                                        e = e35;
                                        linkedHashMap = linkedHashMap2;
                                        r82 = "Cannot close the response content.";
                                        j = jPauseBeforeNextRetry;
                                        httpResponse4 = httpResponseExecute;
                                        i = i3;
                                        log2 = log;
                                        if (log2.isDebugEnabled()) {
                                        }
                                        awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                                        amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                                        if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                                        }
                                    }
                                } catch (Error e36) {
                                    e = e36;
                                    throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                                } catch (RuntimeException e37) {
                                    e = e37;
                                    throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                                } catch (Throwable th13) {
                                    th = th13;
                                    obj5 = "Cannot close the response content.";
                                }
                            }
                        } catch (Error e38) {
                            e = e38;
                            httpResponse3 = httpResponseExecute;
                        } catch (RuntimeException e39) {
                            e = e39;
                            httpResponse2 = httpResponseExecute;
                        } catch (Throwable th14) {
                            th = th14;
                            obj2 = "Cannot close the response content.";
                            httpResponse = httpResponseExecute;
                        }
                    } catch (Throwable th15) {
                        httpRequest = httpRequestCreateHttpRequest;
                        r82 = "Cannot close the response content.";
                        j = jPauseBeforeNextRetry;
                        linkedHashMap = linkedHashMap2;
                        i = i3;
                        try {
                            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.HttpRequestTime);
                            throw th15;
                        } catch (IOException e40) {
                            e = e40;
                            log2 = log;
                            if (log2.isDebugEnabled()) {
                            }
                            awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                            awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                            amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                            if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                            }
                        } catch (Error e41) {
                            e = e41;
                            throw ((Error) handleUnexpectedFailure(e, awsRequestMetrics));
                        } catch (RuntimeException e42) {
                            e = e42;
                            throw ((RuntimeException) handleUnexpectedFailure(e, awsRequestMetrics));
                        }
                    }
                } else {
                    try {
                        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestSigningTime);
                        try {
                            signerByURI.sign(request, credentials);
                            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestSigningTime);
                            log3 = REQUEST_LOG;
                            if (log3.isDebugEnabled()) {
                            }
                            httpRequestCreateHttpRequest = this.requestFactory.createHttpRequest(request, this.config, r82);
                            awsRequestMetrics.startEvent(AWSRequestMetrics.Field.HttpRequestTime);
                            httpResponseExecute = this.httpClient.execute(httpRequestCreateHttpRequest);
                            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.HttpRequestTime);
                            if (isRequestSuccessful(httpResponseExecute)) {
                            }
                        } catch (Throwable th16) {
                            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestSigningTime);
                            throw th16;
                        }
                    } catch (IOException e43) {
                        e = e43;
                        signer = signerByURI;
                        obj = "Cannot close the response content.";
                        j = jPauseBeforeNextRetry;
                        linkedHashMap = linkedHashMap2;
                        i = i3;
                        r82 = obj;
                        log2 = log;
                        if (log2.isDebugEnabled()) {
                        }
                        awsRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, e);
                        awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                        amazonClientException = new AmazonClientException("Unable to execute HTTP request: " + e.getMessage(), e);
                        if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), amazonClientException, i, this.config.getRetryPolicy())) {
                        }
                    }
                }
            } catch (Throwable th17) {
                th = th17;
                z3 = true;
            }
            if (z3 || httpResponse4 == null) {
                throw th;
            }
            try {
                if (httpResponse4.getRawContent() == null) {
                    throw th;
                }
                httpResponse4.getRawContent().close();
                throw th;
            } catch (IOException e44) {
                log.warn(r8, e44);
                throw th;
            }
        }
    }

    private <T extends Throwable> T handleUnexpectedFailure(T t, AWSRequestMetrics aWSRequestMetrics) {
        aWSRequestMetrics.incrementCounter(AWSRequestMetrics.Field.Exception);
        aWSRequestMetrics.addProperty(AWSRequestMetrics.Field.Exception, t);
        return t;
    }

    void resetRequestAfterError(Request<?> request, Exception exc) {
        if (request.getContent() == null) {
            return;
        }
        if (!request.getContent().markSupported()) {
            throw new AmazonClientException("Encountered an exception and stream is not resettable", exc);
        }
        try {
            request.getContent().reset();
        } catch (IOException unused) {
            throw new AmazonClientException("Encountered an exception and couldn't reset the stream to retry", exc);
        }
    }

    void setUserAgent(Request<?> request) {
        RequestClientOptions requestClientOptions;
        String clientMarker;
        String userAgentOverride = ClientConfiguration.DEFAULT_USER_AGENT;
        AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
        if (originalRequest != null && (requestClientOptions = originalRequest.getRequestClientOptions()) != null && (clientMarker = requestClientOptions.getClientMarker(RequestClientOptions.Marker.USER_AGENT)) != null) {
            userAgentOverride = createUserAgentString(userAgentOverride, clientMarker);
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(this.config.getUserAgent())) {
            userAgentOverride = createUserAgentString(userAgentOverride, this.config.getUserAgent());
        }
        if (this.config.getUserAgentOverride() != null) {
            userAgentOverride = this.config.getUserAgentOverride();
        }
        request.addHeader("User-Agent", userAgentOverride);
    }

    static String createUserAgentString(String str, String str2) {
        if (str.contains(str2)) {
            return str;
        }
        return str.trim() + " " + str2.trim();
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }

    private boolean shouldRetry(AmazonWebServiceRequest amazonWebServiceRequest, InputStream inputStream, AmazonClientException amazonClientException, int i, RetryPolicy retryPolicy) {
        int i2 = i - 1;
        int maxErrorRetry = this.config.getMaxErrorRetry();
        if (maxErrorRetry < 0 || !retryPolicy.isMaxErrorRetryInClientConfigHonored()) {
            maxErrorRetry = retryPolicy.getMaxErrorRetry();
        }
        if (i2 >= maxErrorRetry) {
            return false;
        }
        if (inputStream != null && !inputStream.markSupported()) {
            Log log2 = log;
            if (log2.isDebugEnabled()) {
                log2.debug("Content not repeatable");
            }
            return false;
        }
        return retryPolicy.getRetryCondition().shouldRetry(amazonWebServiceRequest, amazonClientException, i2);
    }

    private static boolean isTemporaryRedirect(HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusCode();
        String str = httpResponse.getHeaders().get("Location");
        return (statusCode != 307 || str == null || str.isEmpty()) ? false : true;
    }

    private boolean isRequestSuccessful(HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    <T> T handleResponse(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> httpResponseHandler, HttpResponse httpResponse, ExecutionContext executionContext) throws IOException {
        try {
            AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
            awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ResponseProcessingTime);
            try {
                AmazonWebServiceResponse<T> amazonWebServiceResponseHandle = httpResponseHandler.handle(httpResponse);
                if (amazonWebServiceResponseHandle == null) {
                    throw new RuntimeException("Unable to unmarshall response metadata. Response Code: " + httpResponse.getStatusCode() + ", Response Text: " + httpResponse.getStatusText());
                }
                Log log2 = REQUEST_LOG;
                if (log2.isDebugEnabled()) {
                    log2.debug("Received successful response: " + httpResponse.getStatusCode() + ", AWS Request ID: " + amazonWebServiceResponseHandle.getRequestId());
                }
                awsRequestMetrics.addProperty(AWSRequestMetrics.Field.AWSRequestID, amazonWebServiceResponseHandle.getRequestId());
                return amazonWebServiceResponseHandle.getResult();
            } finally {
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ResponseProcessingTime);
            }
        } catch (CRC32MismatchException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to unmarshall response (" + e3.getMessage() + "). Response Code: " + httpResponse.getStatusCode() + ", Response Text: " + httpResponse.getStatusText(), e3);
        }
    }

    AmazonServiceException handleErrorResponse(Request<?> request, HttpResponseHandler<AmazonServiceException> httpResponseHandler, HttpResponse httpResponse) throws IOException {
        AmazonServiceException amazonServiceException;
        int statusCode = httpResponse.getStatusCode();
        try {
            amazonServiceException = httpResponseHandler.handle(httpResponse);
            REQUEST_LOG.debug("Received error response: " + amazonServiceException.toString());
        } catch (Exception e) {
            if (statusCode == HTTP_STATUS_REQ_TOO_LONG) {
                amazonServiceException = new AmazonServiceException("Request entity too large");
                amazonServiceException.setServiceName(request.getServiceName());
                amazonServiceException.setStatusCode(HTTP_STATUS_REQ_TOO_LONG);
                amazonServiceException.setErrorType(AmazonServiceException.ErrorType.Client);
                amazonServiceException.setErrorCode("Request entity too large");
            } else if (statusCode == 503 && "Service Unavailable".equalsIgnoreCase(httpResponse.getStatusText())) {
                amazonServiceException = new AmazonServiceException("Service unavailable");
                amazonServiceException.setServiceName(request.getServiceName());
                amazonServiceException.setStatusCode(503);
                amazonServiceException.setErrorType(AmazonServiceException.ErrorType.Service);
                amazonServiceException.setErrorCode("Service unavailable");
            } else {
                if (e instanceof IOException) {
                    throw ((IOException) e);
                }
                throw new AmazonClientException("Unable to unmarshall error response (" + e.getMessage() + "). Response Code: " + statusCode + ", Response Text: " + httpResponse.getStatusText() + ", Response Headers: " + httpResponse.getHeaders(), e);
            }
        }
        amazonServiceException.setStatusCode(statusCode);
        amazonServiceException.setServiceName(request.getServiceName());
        amazonServiceException.fillInStackTrace();
        return amazonServiceException;
    }

    private long pauseBeforeNextRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i, RetryPolicy retryPolicy) {
        int i2 = i - 2;
        long jDelayBeforeNextRetry = retryPolicy.getBackoffStrategy().delayBeforeNextRetry(amazonWebServiceRequest, amazonClientException, i2);
        Log log2 = log;
        if (log2.isDebugEnabled()) {
            log2.debug("Retriable error detected, will retry in " + jDelayBeforeNextRetry + "ms, attempt number: " + i2);
        }
        try {
            Thread.sleep(jDelayBeforeNextRetry);
            return jDelayBeforeNextRetry;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AmazonClientException(e.getMessage(), e);
        }
    }

    private String getServerDateFromException(String str) {
        int iIndexOf;
        int iIndexOf2 = str.indexOf("(");
        if (str.contains(" + 15")) {
            iIndexOf = str.indexOf(" + 15");
        } else {
            iIndexOf = str.indexOf(" - 15");
        }
        return str.substring(iIndexOf2 + 1, iIndexOf);
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:13:0x0039 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:19:0x0012 */
    /* JADX DEBUG: Multi-variable search result rejected for r4v4, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r4v5, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r4v9, resolved type: java.lang.String */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020 A[Catch: RuntimeException -> 0x003b, TRY_ENTER, TRY_LEAVE, TryCatch #1 {RuntimeException -> 0x003b, blocks: (B:4:0x0014, B:9:0x0020), top: B:21:0x0014 }] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    long parseClockSkewOffset(HttpResponse httpResponse, AmazonServiceException amazonServiceException) {
        Date rFC822Date;
        Date date = new Date();
        String str = httpResponse.getHeaders().get("Date");
        try {
            if (str != 0) {
                try {
                    if (str.isEmpty()) {
                        rFC822Date = DateUtils.parseCompressedISO8601Date(getServerDateFromException(amazonServiceException.getMessage()));
                    } else {
                        rFC822Date = DateUtils.parseRFC822Date(str);
                    }
                } catch (RuntimeException e) {
                    e = e;
                    str = 0;
                    log.warn("Unable to parse clock skew offset from response: " + str, e);
                    return 0L;
                }
            }
            long time = date.getTime() - rFC822Date.getTime();
            str = 1000;
            return time / TIME_MILLISEC;
        } catch (RuntimeException e2) {
            e = e2;
        }
    }

    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }
}
