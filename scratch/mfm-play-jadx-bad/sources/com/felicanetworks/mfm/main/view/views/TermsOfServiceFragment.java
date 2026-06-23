package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.core.view.PointerIconCompat;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.internal.main.net.BasicAuthentication;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.policy.view.SecureWebViewClient;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.TermsOfServiceDrawStructure;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;

/* JADX INFO: loaded from: classes3.dex */
public class TermsOfServiceFragment extends BaseFragment {
    private Action action;

    public interface Action {
        void onCompleteTermsOfService();

        void onFailedLoading(MfmException e);

        void onServerError(MfmException e);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: android.content.Context */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Action) {
            this.action = (Action) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.action = null;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Structure structure = getStructure();
        if (structure instanceof TermsOfServiceDrawStructure) {
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_01_09, Integer.valueOf(((TermsOfServiceDrawStructure) structure).getTosVersion()));
        }
        return inflater.inflate(R.layout.fragment_terms_of_service, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setLayout();
        final CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = DialogFactory.createCircleProgressDialog(getContext());
        showNormalDialog(customDialogFragmentCreateCircleProgressDialog);
        final WebView webView = (WebView) view.findViewById(R.id.mfmTosWebView);
        final WebView webView2 = (WebView) view.findViewById(R.id.mfsTosWebView);
        String str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_MFM_FOR_WEBVIEW);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_DISCLAIMER_MFS);
        webView.getSettings().setUserAgentString(Information.userAgent());
        loadWebView(str, webView, new MfmTermsOfServiceWebViewClient(this, new WebViewCallback() { // from class: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.WebViewCallback
            public void onLoadFinished() {
                if (TermsOfServiceFragment.this.getContext() == null) {
                    return;
                }
                Animation animationLoadAnimation = AnimationUtils.loadAnimation(TermsOfServiceFragment.this.getContext(), android.R.anim.fade_in);
                webView.setVisibility(0);
                webView2.setVisibility(0);
                webView.startAnimation(animationLoadAnimation);
                webView2.startAnimation(animationLoadAnimation);
                customDialogFragmentCreateCircleProgressDialog.dismiss();
            }

            @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.WebViewCallback
            public void onLoadError(WebViewCallback.WebViewError error) {
                if (TermsOfServiceFragment.this.action == null) {
                    return;
                }
                customDialogFragmentCreateCircleProgressDialog.dismiss();
                int i = error.code;
                if (i == 1001 || i == 1002) {
                    TermsOfServiceFragment.this.action.onServerError(new MfmException(TermsOfServiceFragment.class, 2, "Terms of service server error. " + error.toString()));
                    return;
                }
                TermsOfServiceFragment.this.action.onFailedLoading(new MfmException(TermsOfServiceFragment.class, 1, "Terms of service loading failed. " + error.toString()));
            }
        }));
        loadWebView(str2, webView2, new TermsOfServiceWebViewClient(this, new WebViewCallback() { // from class: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.2
            @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.WebViewCallback
            void onClickLink() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_FELICA, new Object[0]);
            }
        }));
        final Button button = (Button) view.findViewById(R.id.next);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_BUTTON_NEXT, new Object[0]);
                if (TermsOfServiceFragment.this.action == null) {
                    return;
                }
                view2.setEnabled(false);
                TermsOfServiceFragment.this.action.onCompleteTermsOfService();
            }
        });
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setText((String) Sg.getValue(Sg.Key.SETTING_STRING_TERMS_OF_SERVICE_CHECKBOX));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                button.setEnabled(b);
            }
        });
    }

    private void loadWebView(String url, WebView webView, TermsOfServiceWebViewClient client) {
        webView.clearCache(true);
        webView.setWebViewClient(client);
        webView.getSettings().setCacheMode(2);
        webView.loadUrl(url);
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        setLayout();
    }

    public void setLayout() {
        int i = 8;
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 24 && getActivity().isInMultiWindowMode()) {
            i2 = 8;
            i = 0;
        }
        View view = getView();
        view.findViewById(R.id.disagreementMessage).setVisibility(i);
        view.findViewById(R.id.space1).setVisibility(i2);
        view.findViewById(R.id.mfmTosWebViewFrame).setVisibility(i2);
        view.findViewById(R.id.mfmTosWebView).setVisibility(i2);
        view.findViewById(R.id.space2).setVisibility(i2);
        view.findViewById(R.id.mfsTosWebViewFrame).setVisibility(i2);
        view.findViewById(R.id.mfsTosWebView).setVisibility(i2);
        view.findViewById(R.id.space3).setVisibility(i2);
        view.findViewById(R.id.checkbox).setVisibility(i2);
        view.findViewById(R.id.space4).setVisibility(i2);
        view.findViewById(R.id.next).setVisibility(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNetworkErrorDialog(int resId) {
        CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(resId);
        if (customDialogFragmentShowWarningDialog == null) {
            return;
        }
        customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.5
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                try {
                    Structure structure = TermsOfServiceFragment.this.getStructure();
                    if (!(structure instanceof CloseDrawStructure)) {
                        return true;
                    }
                    ((CloseDrawStructure) structure).actClose();
                    return true;
                } catch (Exception e) {
                    TermsOfServiceFragment.this.notifyException(e);
                    return true;
                }
            }
        });
    }

    private static class WebViewCallback {
        void onClickLink() {
        }

        void onLoadError(WebViewError error) {
        }

        void onLoadFinished() {
        }

        private WebViewCallback() {
        }

        static class WebViewError {
            private static final int ERROR_CODE_ILLEGAL_CONTENT = 1001;
            private static final int ERROR_CODE_SERVER_ERROR = 1002;
            private static final int INVALID_STATUS_CODE = -1;
            private static final int THRESHOLD_OF_ORIGINAL_ERROR_CODE = 1000;
            final int code;
            final String message;
            final int statusCode;

            WebViewError(WebResourceError error) {
                this(error.getErrorCode(), error.getDescription().toString());
            }

            WebViewError(WebResourceResponse response) {
                this(1002, response.getReasonPhrase(), response.getStatusCode());
            }

            WebViewError(int code, String message) {
                this(code, message, -1);
            }

            private WebViewError(int code, String message, int statusCode) {
                this.code = code;
                this.message = message;
                this.statusCode = statusCode;
            }

            public String toString() {
                return "WebViewError{code=" + this.code + ", message='" + this.message + "', statusCode=" + this.statusCode + '}';
            }
        }
    }

    private static class TermsOfServiceWebViewClient extends SecureWebViewClient {
        final WebViewCallback callback;
        final TermsOfServiceFragment parent;

        TermsOfServiceWebViewClient(TermsOfServiceFragment parent, WebViewCallback callback) {
            this.parent = parent;
            this.callback = callback;
        }

        @Override // com.felicanetworks.mfm.main.policy.view.SecureWebViewClient
        protected void onLaunch(Intent intent) {
            this.callback.onClickLink();
            if (intent == null) {
                return;
            }
            try {
                this.parent.startActivity(intent);
            } catch (Exception unused) {
                this.parent.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.view.SecureWebViewClient
        protected void onIncorrect(SecureWebViewClient.IncorrectType type) {
            if (type == null) {
                return;
            }
            try {
                int i = AnonymousClass6.$SwitchMap$com$felicanetworks$mfm$main$policy$view$SecureWebViewClient$IncorrectType[type.ordinal()];
                if (i == 1) {
                    this.parent.showNetworkErrorDialog(R.string.dlg_warning_ssl_access);
                    this.parent.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ssl_access");
                } else {
                    if (i != 2) {
                        return;
                    }
                    this.parent.showNetworkErrorDialog(R.string.dlg_warning_scheme_check);
                    this.parent.stampWithJudgeActivityState(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_scheme_check");
                }
            } catch (Exception e) {
                this.parent.notifyException(e);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.callback.onLoadFinished();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            if (BasicAuthentication.isNeedBasicAuthentication()) {
                handler.proceed(BasicAuthentication.getUserName(), BasicAuthentication.getPassword());
            } else {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
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

    private static class MfmTermsOfServiceWebViewClient extends TermsOfServiceWebViewClient {
        private WebViewCallback.WebViewError error;
        private final String mfmTosTitle;

        MfmTermsOfServiceWebViewClient(TermsOfServiceFragment parent, WebViewCallback callback) {
            super(parent, callback);
            this.mfmTosTitle = (String) Sg.getValue(Sg.Key.SETTING_DISCLAIMER_MFM_CONTENT_TITLE_FOR_WEBVIEW);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            onReceivedError(new WebViewCallback.WebViewError(error));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            onReceivedError(new WebViewCallback.WebViewError(errorResponse));
        }

        private void onReceivedError(WebViewCallback.WebViewError error) {
            this.error = error;
        }

        @Override // com.felicanetworks.mfm.main.view.views.TermsOfServiceFragment.TermsOfServiceWebViewClient, android.webkit.WebViewClient
        public void onPageFinished(final WebView view, String url) {
            if (this.error != null) {
                this.callback.onLoadError(this.error);
            } else if (TextUtils.equals(this.mfmTosTitle, view.getTitle())) {
                this.callback.onLoadFinished();
            } else {
                this.callback.onLoadError(new WebViewCallback.WebViewError(PointerIconCompat.TYPE_CONTEXT_MENU, "Not found 'mfmmain-site' meta tag or it's content is illegal value."));
            }
        }
    }
}
