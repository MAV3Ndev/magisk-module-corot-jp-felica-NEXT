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

/* JADX INFO: loaded from: classes.dex */
public class ConfirmGoogleAccountFragment extends OpenIdConnectFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private final LayoutType mLayoutType;
    private OpenIdConnectActivity.OnConfirmListener mListener;
    private View mView;

    private interface LayoutType {
        String getAALogLoginEventName();

        String getAALogShowEventName();

        int getLayoutResourceId();

        void initViews(View view, Bundle bundle);

        boolean isAgree(View view);
    }

    public ConfirmGoogleAccountFragment() {
        this(1);
    }

    public ConfirmGoogleAccountFragment(int i) throws IllegalArgumentException {
        this.mView = null;
        this.mListener = null;
        if (i == 1) {
            this.mLayoutType = new LayoutTypeWithTerms();
        } else if (i == 2) {
            this.mLayoutType = new LayoutTypeSkippableWithoutTerms();
        } else {
            if (i == 3) {
                this.mLayoutType = new LayoutTypeWithoutTerms();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(this.mLayoutType.getLayoutResourceId(), viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        LogMgr.log(4, "000");
        super.onViewCreated(view, bundle);
        this.mView = view;
        view.findViewById(R.id.mfi_sign_in_button).setOnClickListener(this);
        this.mLayoutType.initViews(view, bundle);
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
            LogSender.send(getContext(), this.mLayoutType.getAALogLoginEventName(), LogSender.EXTRA_VALUE_LOG_TYPE_ACTION);
        } else if (R.id.mfi_button_detailed == id) {
            launchBrowser(R.string.mfi_Url_Detailed);
            LogSender.send(getContext(), LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_DETAIL, LogSender.EXTRA_VALUE_LOG_TYPE_ACTION);
        } else {
            LogMgr.log(1, "800 Unknown view ID. id=" + id);
        }
        LogMgr.log(4, "999");
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        LogMgr.log(4, "000");
        int id = compoundButton.getId();
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
    public void setListener(EventListener eventListener) {
        this.mListener = (OpenIdConnectActivity.OnConfirmListener) eventListener;
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
    public void addLink(int i, int i2, int i3) {
        TextView textView = (TextView) this.mView.findViewById(i);
        final String string = getString(i3);
        Linkify.addLinks(textView, Pattern.compile(getString(i2)), string, (Linkify.MatchFilter) null, new Linkify.TransformFilter() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.1
            @Override // android.text.util.Linkify.TransformFilter
            public String transformUrl(Matcher matcher, String str) {
                return string;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSkipAction(int i, int i2) {
        String string = getString(i2);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                LogSender.send(ConfirmGoogleAccountFragment.this.getContext(), LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_LATER, LogSender.EXTRA_VALUE_LOG_TYPE_ACTION);
                ConfirmGoogleAccountFragment.this.onConfirm(8);
            }
        }, 0, string.length(), 18);
        TextView textView = (TextView) this.mView.findViewById(i);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void launchBrowser(int i) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getString(i)));
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
    public void onConfirm(int i) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_UI, "ConfirmGoogleAccountFragment", "onConfirm", "confirm = " + i);
        this.mListener.onConfirm(i);
        LogMgr.performanceOut(LogMgr.PERFORMANCE_UI, "ConfirmGoogleAccountFragment", "onConfirm");
    }

    private class LayoutTypeWithTerms implements LayoutType {
        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT;
        }

        private LayoutTypeWithTerms() {
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public int getLayoutResourceId() {
            return R.layout.mfi_openidconnect_activity_confirm_google_account_fragment;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public void initViews(View view, Bundle bundle) {
            ((CheckBox) view.findViewById(R.id.mfi_checkbox_agree)).setOnCheckedChangeListener(ConfirmGoogleAccountFragment.this);
            ConfirmGoogleAccountFragment.this.addLink(R.id.mfi_text_agree, R.string.mfi_SentenceText_Agree, R.string.mfi_Url_Agree);
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
            ConfirmGoogleAccountFragment.this.disableSignInButton();
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public boolean isAgree(View view) {
            return ((CheckBox) view.findViewById(R.id.mfi_checkbox_agree)).isChecked();
        }
    }

    private class LayoutTypeSkippableWithoutTerms implements LayoutType {
        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SKIPPABLE_SIGN_IN;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN;
        }

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
        public void initViews(View view, Bundle bundle) {
            ConfirmGoogleAccountFragment.this.addSkipAction(R.id.mfi_text_sign_in_later, R.string.mfi_SentenceText_SignInLater);
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
        }
    }

    private class LayoutTypeWithoutTerms implements LayoutType {
        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogLoginEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SIGN_IN_ONLY;
        }

        @Override // com.felicanetworks.mfc.mfi.openidconnect.google.ConfirmGoogleAccountFragment.LayoutType
        public String getAALogShowEventName() {
            return LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY;
        }

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
        public void initViews(View view, Bundle bundle) {
            view.findViewById(R.id.mfi_button_detailed).setOnClickListener(ConfirmGoogleAccountFragment.this);
        }
    }
}
