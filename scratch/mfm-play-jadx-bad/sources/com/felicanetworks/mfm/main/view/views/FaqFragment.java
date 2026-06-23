package com.felicanetworks.mfm.main.view.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.view.SecureWebViewClient;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FaqDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes3.dex */
public class FaqFragment extends BaseFragment {
    private WebView mWebView = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_07_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_faq, container, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof FaqDrawStructure)) {
                FaqDrawStructure faqDrawStructure = (FaqDrawStructure) structure;
                WebView webView = (WebView) viewInflate.findViewById(R.id.wv_faq_content_web);
                this.mWebView = webView;
                webView.setWebViewClient(new FaqWebViewClient());
                WebSettings settings = this.mWebView.getSettings();
                if (settings != null) {
                    settings.setGeolocationEnabled(false);
                    settings.setJavaScriptEnabled(true);
                    if (Build.VERSION.SDK_INT < 26) {
                        settings.setSaveFormData(false);
                    }
                    settings.setLoadWithOverviewMode(true);
                    settings.setUseWideViewPort(true);
                    settings.setUserAgentString(Information.userAgent());
                }
                String contentUrl = faqDrawStructure.getContentUrl();
                if (contentUrl != null && !contentUrl.isEmpty()) {
                    this.mWebView.getSettings().setCacheMode(2);
                    this.mWebView.loadUrl(contentUrl);
                }
                adjustViewToNavigationBar(viewInflate.findViewById(R.id.sv_faq), true);
                return viewInflate;
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = getView();
        if (view != null) {
            adjustViewToNavigationBar(view.findViewById(R.id.sv_faq), true);
        }
    }

    public boolean goBack() {
        WebView webView = this.mWebView;
        if (webView == null || !webView.canGoBack()) {
            return false;
        }
        this.mWebView.goBack();
        return true;
    }

    public CustomDialogFragment showNetworkErrorDialog(int resId) {
        CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(resId);
        if (customDialogFragmentShowWarningDialog != null) {
            customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.1
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    try {
                        Structure structure = FaqFragment.this.getStructure();
                        if (!(structure instanceof CloseDrawStructure)) {
                            return true;
                        }
                        ((CloseDrawStructure) structure).actClose();
                        return true;
                    } catch (Exception e) {
                        FaqFragment.this.notifyException(e);
                        return true;
                    }
                }
            });
        }
        return customDialogFragmentShowWarningDialog;
    }

    protected class FaqWebViewClient extends SecureWebViewClient {
        private static final int TIMEOUT = 60000;
        private AlertDialog mAuthRequestDialog;
        private Timer mTimer = new Timer(false);
        private Handler mHandler = new Handler(Looper.getMainLooper());

        protected FaqWebViewClient() {
        }

        @Override // com.felicanetworks.mfm.main.policy.view.SecureWebViewClient
        protected void onLaunch(Intent intent) {
            if (intent != null) {
                try {
                    try {
                        FaqFragment.this.startActivity(intent);
                    } catch (Exception e) {
                        FaqFragment.this.notifyException(e);
                    }
                } catch (Exception unused) {
                    FaqFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.view.SecureWebViewClient
        protected void onIncorrect(final SecureWebViewClient.IncorrectType type) {
            if (type != null) {
                try {
                    int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType[type.ordinal()];
                    if (i == 1) {
                        FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_ssl_access);
                        FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ssl_access");
                    } else {
                        if (i != 2) {
                            return;
                        }
                        FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_scheme_check);
                        FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_scheme_check");
                    }
                } catch (Exception e) {
                    FaqFragment.this.notifyException(e);
                }
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            try {
                FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_network);
                FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_network");
            } catch (Exception e) {
                FaqFragment.this.notifyException(e);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002e  */
        @Override // android.webkit.WebViewClient
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            String str;
            String str2;
            String[] httpAuthUsernamePassword;
            if (!handler.useHttpAuthUsernamePassword() || view == null) {
                str = null;
                str2 = null;
            } else {
                if (Build.VERSION.SDK_INT < 26) {
                    httpAuthUsernamePassword = view.getHttpAuthUsernamePassword(host, realm);
                } else {
                    httpAuthUsernamePassword = WebViewDatabase.getInstance(FaqFragment.this.getContext()).getHttpAuthUsernamePassword(host, realm);
                }
                if (httpAuthUsernamePassword != null && httpAuthUsernamePassword.length == 2) {
                    str = httpAuthUsernamePassword[0];
                    str2 = httpAuthUsernamePassword[1];
                }
            }
            if (str != null && str2 != null) {
                handler.proceed(str, str2);
            } else {
                showHttpAuthRequestDialog(handler, host, realm);
            }
        }

        private void showHttpAuthRequestDialog(final HttpAuthHandler handler, final String host, final String realm) {
            final View viewInflate = LayoutInflater.from(FaqFragment.this.getContext()).inflate(R.layout.debug_dialog_http_authentication, (ViewGroup) null);
            AlertDialog.Builder builder = new AlertDialog.Builder(FaqFragment.this.getContext());
            builder.setView(viewInflate);
            builder.setTitle(FaqFragment.this.getString(R.string.dlg_title_basic_auth, host, realm));
            builder.setPositiveButton(R.string.button_text_login, new DialogInterface.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    String string = ((EditText) viewInflate.findViewById(R.id.username_edit)).getText().toString();
                    String string2 = ((EditText) viewInflate.findViewById(R.id.password_edit)).getText().toString();
                    if (Build.VERSION.SDK_INT < 26) {
                        FaqFragment.this.mWebView.setHttpAuthUsernamePassword(host, realm, string, string2);
                    } else {
                        WebViewDatabase.getInstance(FaqFragment.this.getContext()).setHttpAuthUsernamePassword(host, realm, string, string2);
                    }
                    handler.proceed(string, string2);
                    dialog.dismiss();
                    FaqWebViewClient.this.mAuthRequestDialog = null;
                    FaqWebViewClient.this.mTimer.schedule(new TimeOutTimerTask(), 60000L);
                }
            });
            builder.setNegativeButton(R.string.button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        handler.cancel();
                        FaqFragment.this.mWebView.stopLoading();
                        if (!FaqFragment.this.goBack()) {
                            Structure structure = FaqFragment.this.getStructure();
                            if (structure instanceof CloseDrawStructure) {
                                ((CloseDrawStructure) structure).actClose();
                            }
                        }
                        dialog.dismiss();
                        FaqWebViewClient.this.mAuthRequestDialog = null;
                    } catch (Exception e) {
                        FaqFragment.this.notifyException(e);
                    }
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.3
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialog) {
                    try {
                        handler.cancel();
                        FaqFragment.this.mWebView.stopLoading();
                        if (!FaqFragment.this.goBack()) {
                            Structure structure = FaqFragment.this.getStructure();
                            if (structure instanceof CloseDrawStructure) {
                                ((CloseDrawStructure) structure).actClose();
                            }
                        }
                        FaqWebViewClient.this.mAuthRequestDialog = null;
                    } catch (Exception e) {
                        FaqFragment.this.notifyException(e);
                    }
                }
            });
            AlertDialog alertDialogCreate = builder.create();
            this.mAuthRequestDialog = alertDialogCreate;
            alertDialogCreate.setCanceledOnTouchOutside(false);
            this.mAuthRequestDialog.show();
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            this.mTimer.cancel();
            this.mTimer = new Timer(false);
        }

        private class TimeOutTimerTask extends TimerTask {
            private TimeOutTimerTask() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                FaqWebViewClient.this.mHandler.post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.TimeOutTimerTask.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FaqFragment.this.mWebView.stopLoading();
                        Toast.makeText(FaqFragment.this.getActivity(), R.string.warning_load_error, 1).show();
                        FaqWebViewClient.this.mTimer.cancel();
                        FaqWebViewClient.this.mTimer = new Timer(false);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.FaqFragment$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType;

        static {
            int[] iArr = new int[SecureWebViewClient.IncorrectType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType = iArr;
            try {
                iArr[SecureWebViewClient.IncorrectType.SSL_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType[SecureWebViewClient.IncorrectType.UNSUPPORTED_DOMAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
