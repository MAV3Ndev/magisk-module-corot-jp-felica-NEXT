package com.felicanetworks.mfm.main.view.views;

import android.content.DialogInterface;
import android.content.Intent;
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

/* JADX INFO: loaded from: classes.dex */
public class FaqFragment extends BaseFragment {
    private WebView mWebView = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_07_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_faq, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
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
            return viewInflate;
        }
        return viewInflate;
    }

    public boolean goBack() {
        WebView webView = this.mWebView;
        if (webView == null || !webView.canGoBack()) {
            return false;
        }
        this.mWebView.goBack();
        return true;
    }

    public CustomDialogFragment showNetworkErrorDialog(int i) {
        CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(i);
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
        protected void onIncorrect(SecureWebViewClient.IncorrectType incorrectType) {
            if (incorrectType != null) {
                try {
                    int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType[incorrectType.ordinal()];
                    if (i == 1) {
                        FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_ssl_access);
                        FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ssl_access");
                    } else if (i == 2) {
                        FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_scheme_check);
                        FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_scheme_check");
                    }
                } catch (Exception e) {
                    FaqFragment.this.notifyException(e);
                }
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            try {
                FaqFragment.this.showNetworkErrorDialog(R.string.dlg_warning_network);
                FaqFragment.this.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_network");
            } catch (Exception e) {
                FaqFragment.this.notifyException(e);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            onReceivedError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
        @Override // android.webkit.WebViewClient
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onReceivedHttpAuthRequest(android.webkit.WebView r4, android.webkit.HttpAuthHandler r5, java.lang.String r6, java.lang.String r7) {
            /*
                r3 = this;
                boolean r0 = r5.useHttpAuthUsernamePassword()
                r1 = 0
                if (r0 == 0) goto L2f
                if (r4 == 0) goto L2f
                int r0 = android.os.Build.VERSION.SDK_INT
                r2 = 26
                if (r0 >= r2) goto L14
                java.lang.String[] r4 = r4.getHttpAuthUsernamePassword(r6, r7)
                goto L22
            L14:
                com.felicanetworks.mfm.main.view.views.FaqFragment r4 = com.felicanetworks.mfm.main.view.views.FaqFragment.this
                android.content.Context r4 = r4.getContext()
                android.webkit.WebViewDatabase r4 = android.webkit.WebViewDatabase.getInstance(r4)
                java.lang.String[] r4 = r4.getHttpAuthUsernamePassword(r6, r7)
            L22:
                if (r4 == 0) goto L2f
                int r0 = r4.length
                r2 = 2
                if (r0 != r2) goto L2f
                r0 = 0
                r1 = r4[r0]
                r0 = 1
                r4 = r4[r0]
                goto L30
            L2f:
                r4 = r1
            L30:
                if (r1 == 0) goto L38
                if (r4 == 0) goto L38
                r5.proceed(r1, r4)
                goto L3b
            L38:
                r3.showHttpAuthRequestDialog(r5, r6, r7)
            L3b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.onReceivedHttpAuthRequest(android.webkit.WebView, android.webkit.HttpAuthHandler, java.lang.String, java.lang.String):void");
        }

        private void showHttpAuthRequestDialog(final HttpAuthHandler httpAuthHandler, final String str, final String str2) {
            final View viewInflate = LayoutInflater.from(FaqFragment.this.getContext()).inflate(R.layout.debug_dialog_http_authentication, (ViewGroup) null);
            AlertDialog.Builder builder = new AlertDialog.Builder(FaqFragment.this.getContext());
            builder.setView(viewInflate);
            builder.setTitle(FaqFragment.this.getString(R.string.dlg_title_basic_auth, str, str2));
            builder.setPositiveButton(R.string.button_text_login, new DialogInterface.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    String string = ((EditText) viewInflate.findViewById(R.id.username_edit)).getText().toString();
                    String string2 = ((EditText) viewInflate.findViewById(R.id.password_edit)).getText().toString();
                    if (Build.VERSION.SDK_INT < 26) {
                        FaqFragment.this.mWebView.setHttpAuthUsernamePassword(str, str2, string, string2);
                    } else {
                        WebViewDatabase.getInstance(FaqFragment.this.getContext()).setHttpAuthUsernamePassword(str, str2, string, string2);
                    }
                    httpAuthHandler.proceed(string, string2);
                    dialogInterface.dismiss();
                    FaqWebViewClient.this.mAuthRequestDialog = null;
                    FaqWebViewClient.this.mTimer.schedule(new TimeOutTimerTask(), 60000L);
                }
            });
            builder.setNegativeButton(R.string.button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        httpAuthHandler.cancel();
                        FaqFragment.this.mWebView.stopLoading();
                        if (!FaqFragment.this.goBack()) {
                            Structure structure = FaqFragment.this.getStructure();
                            if (structure instanceof CloseDrawStructure) {
                                ((CloseDrawStructure) structure).actClose();
                            }
                        }
                        dialogInterface.dismiss();
                        FaqWebViewClient.this.mAuthRequestDialog = null;
                    } catch (Exception e) {
                        FaqFragment.this.notifyException(e);
                    }
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.felicanetworks.mfm.main.view.views.FaqFragment.FaqWebViewClient.3
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    try {
                        httpAuthHandler.cancel();
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
        public void onPageFinished(WebView webView, String str) {
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
