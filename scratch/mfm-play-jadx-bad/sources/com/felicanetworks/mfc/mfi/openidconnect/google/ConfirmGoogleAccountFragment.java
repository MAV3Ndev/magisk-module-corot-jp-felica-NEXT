package com.felicanetworks.mfc.mfi.openidconnect.google;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectActivity;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.EventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class ConfirmGoogleAccountFragment extends OpenIdConnectFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private final LayoutType mLayoutType;
    private OpenIdConnectActivity.OnConfirmListener mListener;
    private View mView;

    private interface LayoutType {
        String getAALogLoginEventName();

        String getAALogShowEventName();

        int getLayoutResourceId();

        void initViews(View view, Bundle savedInstanceState);

        boolean isAgree(View view);
    }

    public ConfirmGoogleAccountFragment() {
        this(1);
    }

    public ConfirmGoogleAccountFragment(int layoutType) throws IllegalArgumentException {
        this.mView = null;
        this.mListener = null;
        if (layoutType == 1) {
            this.mLayoutType = new LayoutTypeWithTerms();
        } else if (layoutType == 2) {
            this.mLayoutType = new LayoutTypeSkippableWithoutTerms();
        } else {
            if (layoutType == 3) {
                this.mLayoutType = new LayoutTypeWithoutTerms();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.mLayoutType.getLayoutResourceId(), container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogMgr.log(4, "000");
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;
        view.findViewById(R.id.mfi_sign_in_button).setOnClickListener(this);
        this.mLayoutType.initViews(view, savedInstanceState);
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        LogMgr.log(4, "000");
        super.onStart();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        LogMgr.log(4, "000");
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(LogSender.EXTRA_KEY_MFIC_APP_VER, getString(R.string.mfi_client_version));
        LogSender.send(getContext(), this.mLayoutType.getAALogShowEventName(), LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN, bundle);
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        LogMgr.log(4, "000");
        super.onPause();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        LogMgr.log(4, "000");
        super.onStop();
        LogMgr.log(4, "999");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        LogMgr.log(4, "000");
        super.onDestroy();
        LogMgr.log(4, "999");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        LogMgr.log(4, "000");
        int id = view.getId();
        if (R.id.mfi_sign_in_button == id) {
            View view2 = this.mView;
            if (view2 != null && this.mLayoutType.isAgree(view2)) {
                disableSignInButton();
                onConfirm(0);
            } else {
                LogMgr.log(2, "700 No agree.");
            }
            LogSender.send(getContext(), this.mLayoutType.getAALogLoginEventName(), "action");
        } else if (R.id.mfi_button_detailed == id) {
            launchBrowser(R.string.mfi_Url_Detailed);
            LogSender.send(getContext(), LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_DETAIL, "action");
        } else {
            LogMgr.log(1, "800 Unknown view ID. id=" + id);
        }
        LogMgr.log(4, "999");
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LogMgr.log(4, "000");
        int id = buttonView.getId();
        if (R.id.mfi_checkbox_agree == id) {
            View view = this.mView;
            if (view != null && this.mLayoutType.isAgree(view)) {
                enableSignInButton();
            } else {
                disableSignInButton();
            }
        } else {
            LogMgr.log(1, "800 Unknown view ID. id=" + id);
        }
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void setListener(EventListener listener) {
        this.mListener = (OpenIdConnectActivity.OnConfirmListener) listener;
    }

    @Override // com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectFragment
    public void onBackPressed() {
        onConfirm(4);
    }

    public void enableAgreeCheckBox() {
        View viewFindViewById;
        View view = this.mView;
        if (view == null || (viewFindViewById = view.findViewById(R.id.mfi_checkbox_agree)) == null) {
            return;
        }
        viewFindViewById.setEnabled(true);
    }

    public void disableAgreeCheckBox() {
        View viewFindViewById;
        View view = this.mView;
        if (view == null || (viewFindViewById = view.findViewById(R.id.mfi_checkbox_agree)) == null) {
            return;
        }
        viewFindViewById.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addLink(int viewId, int linkStrId, int urlStrId) {
        TextView textView = (TextView) this.mView.findViewById(viewId);
        final String string = getString(urlStrId);
        Linkify.addLinks(textView, Pattern.compile(getString(linkStrId)), string, (Linkify.MatchFilter) null, new Linkify.TransformFilter() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.1
            @Override // android.text.util.Linkify.TransformFilter
            public String transformUrl(Matcher match, String matchedUrl) {
                return string;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSkipAction(int viewId, int strId) {
        String string = getString(strId);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View textView) {
                LogSender.send(ConfirmGoogleAccountFragment.this.getContext(), LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_LATER, "action");
                ConfirmGoogleAccountFragment.this.onConfirm(8);
            }
        }, 0, string.length(), 18);
        TextView textView = (TextView) this.mView.findViewById(viewId);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void launchBrowser(int urlStrId) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getString(urlStrId)));
        intent.putExtra("com.android.browser.application_id", getContext().getPackageName());
        startActivity(intent);
    }

    private void enableSignInButton() {
        this.mView.findViewById(R.id.mfi_sign_in_button).setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableSignInButton() {
        this.mView.findViewById(R.id.mfi_sign_in_button).setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConfirm(int confirm) {
        LogMgr.performanceIn("UI", "ConfirmGoogleAccountFragment", "onConfirm", "confirm = " + confirm);
        this.mListener.onConfirm(confirm);
        LogMgr.performanceOut("UI", "ConfirmGoogleAccountFragment", "onConfirm");
    }

    private class LayoutTypeWithTerms implements LayoutType {
        private LayoutTypeWithTerms() {
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public int getLayoutResourceId() {
            return R.layout.mfi_openidconnect_activity_confirm_google_account_fragment;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public void initViews(View view, Bundle savedInstanceState) {
            ((CheckBox) view.findViewById(R.id.mfi_checkbox_agree)).setOnCheckedChangeListener(ConfirmGoogleAccountFragment.this);
            ConfirmGoogleAccountFragment.this.addLink(R.id.mfi_text_agree, R.string.mfi_SentenceText_Agree, R.string.mfi_Url_Agree);
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
            ConfirmGoogleAccountFragment.this.disableSignInButton();
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public boolean isAgree(View view) {
            return ((CheckBox) view.findViewById(R.id.mfi_checkbox_agree)).isChecked();
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return "login";
        }
    }

    private class LayoutTypeSkippableWithoutTerms implements LayoutType {
        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public boolean isAgree(View view) {
            return true;
        }

        private LayoutTypeSkippableWithoutTerms() {
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public int getLayoutResourceId() {
            return R.layout.mfi_openidconnect_activity_confirm_google_account_skippable_without_terms_fragment;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public void initViews(View view, Bundle savedInstanceState) {
            ConfirmGoogleAccountFragment.this.addSkipAction(R.id.mfi_text_sign_in_later, R.string.mfi_SentenceText_SignInLater);
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SKIPPABLE_SIGN_IN;
        }
    }

    private class LayoutTypeWithoutTerms implements LayoutType {
        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public boolean isAgree(View view) {
            return true;
        }

        private LayoutTypeWithoutTerms() {
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public int getLayoutResourceId() {
            return R.layout.mfi_openidconnect_activity_confirm_google_account_without_terms_fragment;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public void initViews(View view, Bundle savedInstanceState) {
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SIGN_IN_ONLY;
        }
    }
}
