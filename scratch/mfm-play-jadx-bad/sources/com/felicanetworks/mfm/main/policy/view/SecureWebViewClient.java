package com.felicanetworks.mfm.main.policy.view;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.core.net.MailTo;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SecureWebViewClient extends WebViewClient {
    private List<String> _whiteDomainList = new ArrayList();

    public enum IncorrectType {
        UNSUPPORTED_DOMAIN,
        SSL_ERROR
    }

    protected abstract void onIncorrect(IncorrectType type);

    protected abstract void onLaunch(Intent intent);

    private enum ExternalScheme {
        BROWSER("browser", "http"),
        BROWSERS("browsers", "https"),
        OTHER("", "");

        private String replace;
        private String scheme;

        ExternalScheme(String scheme, String replace) {
            this.scheme = scheme;
            this.replace = replace;
        }

        static ExternalScheme resolve(String scheme) {
            ExternalScheme externalScheme = OTHER;
            for (ExternalScheme externalScheme2 : values()) {
                if (externalScheme2.scheme.equals(scheme)) {
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
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return shouldOverrideUrlLoadingProcessing(url);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoadingProcessing(request.getUrl().toString());
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
        onIncorrect(IncorrectType.SSL_ERROR);
    }

    private boolean shouldOverrideUrlLoadingProcessing(String url) {
        Uri uri = Uri.parse(url);
        if (url.startsWith(MailTo.MAILTO_SCHEME)) {
            onLaunch(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            return true;
        }
        ExternalScheme externalSchemeResolve = ExternalScheme.resolve(uri.getScheme());
        if (ExternalScheme.OTHER != externalSchemeResolve) {
            if (this._whiteDomainList.contains(uri.getHost())) {
                onLaunch(new Intent("android.intent.action.VIEW", Uri.parse(url.replaceFirst(externalSchemeResolve.scheme, externalSchemeResolve.replace))));
            } else {
                onIncorrect(IncorrectType.UNSUPPORTED_DOMAIN);
            }
            return true;
        }
        if ((!url.startsWith("http://") && !url.startsWith("https://")) || this._whiteDomainList.contains(uri.getHost())) {
            return false;
        }
        onIncorrect(IncorrectType.UNSUPPORTED_DOMAIN);
        return true;
    }
}
