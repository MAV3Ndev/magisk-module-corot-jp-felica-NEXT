package com.felicanetworks.mfm.main.policy.view;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class SecureWebViewClient extends WebViewClient {
    private List<String> _whiteDomainList = new ArrayList();

    public enum IncorrectType {
        UNSUPPORTED_DOMAIN,
        SSL_ERROR
    }

    protected abstract void onIncorrect(IncorrectType incorrectType);

    protected abstract void onLaunch(Intent intent);

    private enum ExternalScheme {
        BROWSER("browser", "http"),
        BROWSERS("browsers", "https"),
        OTHER("", "");

        private String replace;
        private String scheme;

        ExternalScheme(String str, String str2) {
            this.scheme = str;
            this.replace = str2;
        }

        static ExternalScheme resolve(String str) {
            ExternalScheme externalScheme = OTHER;
            for (ExternalScheme externalScheme2 : values()) {
                if (externalScheme2.scheme.equals(str)) {
                    return externalScheme2;
                }
            }
            return externalScheme;
        }
    }

    public SecureWebViewClient() {
        this._whiteDomainList.addAll(Arrays.asList((String[]) Sg.getValue(Sg.Key.SETTING_WHITE_DOMAIN_LIST)));
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return shouldOverrideUrlLoadingProcessing(str);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return shouldOverrideUrlLoadingProcessing(webResourceRequest.getUrl().toString());
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.cancel();
        onIncorrect(IncorrectType.SSL_ERROR);
    }

    private boolean shouldOverrideUrlLoadingProcessing(String str) {
        Uri uri = Uri.parse(str);
        if (str.startsWith("mailto:")) {
            onLaunch(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            return true;
        }
        ExternalScheme externalSchemeResolve = ExternalScheme.resolve(uri.getScheme());
        if (ExternalScheme.OTHER != externalSchemeResolve) {
            if (this._whiteDomainList.contains(uri.getHost())) {
                onLaunch(new Intent("android.intent.action.VIEW", Uri.parse(str.replaceFirst(externalSchemeResolve.scheme, externalSchemeResolve.replace))));
            } else {
                onIncorrect(IncorrectType.UNSUPPORTED_DOMAIN);
            }
            return true;
        }
        if ((!str.startsWith("http://") && !str.startsWith("https://")) || this._whiteDomainList.contains(uri.getHost())) {
            return false;
        }
        onIncorrect(IncorrectType.UNSUPPORTED_DOMAIN);
        return true;
    }
}
