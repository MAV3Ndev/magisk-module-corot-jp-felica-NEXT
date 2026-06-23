package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import com.google.common.net.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class HttpRequestFactory {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public HttpRequest createHttpRequest(Request<?> request, ClientConfiguration clientConfiguration, ExecutionContext executionContext) {
        String strAppendUri;
        URI endpoint = request.getEndpoint();
        boolean z = true;
        if (request.getEncodedUriResourcePath() != null) {
            strAppendUri = HttpUtils.appendUriEncoded(endpoint.toString(), request.getEncodedUriResourcePath());
        } else {
            strAppendUri = HttpUtils.appendUri(endpoint.toString(), request.getResourcePath(), true);
        }
        String strEncodeParameters = HttpUtils.encodeParameters(request);
        HttpMethodName httpMethod = request.getHttpMethod();
        boolean z2 = request.getContent() != null;
        if (httpMethod == HttpMethodName.POST && !z2) {
            z = false;
        }
        if (strEncodeParameters != null && z) {
            strAppendUri = strAppendUri + "?" + strEncodeParameters;
        }
        HashMap map = new HashMap();
        configureHeaders(map, request, executionContext, clientConfiguration);
        InputStream content = request.getContent();
        if (httpMethod == HttpMethodName.PATCH) {
            httpMethod = HttpMethodName.POST;
            map.put("X-HTTP-Method-Override", HttpMethodName.PATCH.toString());
        }
        if (httpMethod == HttpMethodName.POST && request.getContent() == null && strEncodeParameters != null) {
            byte[] bytes = strEncodeParameters.getBytes(StringUtils.UTF8);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            map.put("Content-Length", String.valueOf(bytes.length));
            content = byteArrayInputStream;
        }
        if (clientConfiguration.isEnableGzip() && map.get(HttpHeaders.ACCEPT_ENCODING) == null) {
            map.put(HttpHeaders.ACCEPT_ENCODING, "gzip");
        } else {
            map.put(HttpHeaders.ACCEPT_ENCODING, "identity");
        }
        HttpRequest httpRequest = new HttpRequest(httpMethod.toString(), URI.create(strAppendUri), map, content);
        httpRequest.setStreaming(request.isStreaming());
        return httpRequest;
    }

    private void configureHeaders(Map<String, String> map, Request<?> request, ExecutionContext executionContext, ClientConfiguration clientConfiguration) {
        URI endpoint = request.getEndpoint();
        String host = endpoint.getHost();
        if (HttpUtils.isUsingNonDefaultPort(endpoint)) {
            host = host + ":" + endpoint.getPort();
        }
        map.put("Host", host);
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        if (map.get("Content-Type") == null || map.get("Content-Type").isEmpty()) {
            map.put("Content-Type", "application/x-www-form-urlencoded; charset=" + StringUtils.lowerCase("UTF-8"));
        }
        if (executionContext == null || executionContext.getContextUserAgent() == null) {
            return;
        }
        map.put("User-Agent", createUserAgentString(clientConfiguration, executionContext.getContextUserAgent()));
    }

    private String createUserAgentString(ClientConfiguration clientConfiguration, String str) {
        if (clientConfiguration.getUserAgent().contains(str)) {
            return clientConfiguration.getUserAgent();
        }
        return clientConfiguration.getUserAgent() + " " + str;
    }
}
