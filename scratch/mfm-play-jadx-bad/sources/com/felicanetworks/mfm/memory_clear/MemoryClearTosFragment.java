package com.felicanetworks.mfm.memory_clear;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.memory_clear.MemoryClearConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearTosFragment extends MemoryClearBaseFragment implements View.OnClickListener {
    private int mOrientation = 1;
    private boolean mIsBtnStateChanged = true;

    private int getLayout(int orientation) {
        return orientation == 1 ? R.layout.fragment_memory_clear_terms_of_service : R.layout.fragment_memory_clear_terms_of_service_land;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mOrientation = activity.getResources().getConfiguration().orientation;
        }
        return inflater.inflate(getLayout(this.mOrientation), container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setViewItem(view);
    }

    private void setViewItem(View view) {
        TextView textView = (TextView) view.findViewById(R.id.memory_clear_tos_link);
        textView.setText(createSpannableString((String) Sg.getValue(Sg.Key.MEMORY_CLEAR_TERMS_OF_SERVICE_URL_GUIDANCE), (String) Sg.getValue(Sg.Key.MEMORY_CLEAR_TERMS_OF_SERVICE_URL_STRING), (String) Sg.getValue(Sg.Key.MEMORY_CLEAR_TERMS_OF_SERVICE_URL)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        final Button button = (Button) view.findViewById(R.id.memory_clear_tos_agree);
        button.setOnClickListener(this);
        ((Button) view.findViewById(R.id.memory_clear_tos_cancel)).setOnClickListener(this);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.memory_clear_tos_check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearTosFragment.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (MemoryClearTosFragment.this.mIsBtnStateChanged) {
                    button.setEnabled(b);
                }
                MemoryClearTosFragment.this.mIsBtnStateChanged = true;
            }
        });
        ((LinearLayout) view.findViewById(R.id.layout_memory_clear_tos_check)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearTosFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                checkBox.performClick();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            View view = getView();
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.root);
                boolean zIsChecked = ((CheckBox) viewGroup.findViewById(R.id.memory_clear_tos_check_box)).isChecked();
                boolean zIsEnabled = ((Button) view.findViewById(R.id.memory_clear_tos_agree)).isEnabled();
                viewGroup.removeAllViews();
                View viewInflate = getLayoutInflater().inflate(getLayout(this.mOrientation), viewGroup);
                setViewItem(viewInflate);
                if (zIsChecked) {
                    CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.memory_clear_tos_check_box);
                    this.mIsBtnStateChanged = zIsEnabled;
                    checkBox.setChecked(true);
                }
            }
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearBaseFragment, android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memory_clear_tos_agree) {
            sendMessageCheckIssueState();
        } else if (id == R.id.memory_clear_tos_cancel) {
            cancelMemoryClear();
        }
        super.onClick(v);
    }

    private void sendMessageCheckIssueState() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).sendMessage(MemoryClearConstants.EVENT_ID.CHECK_ISSUE_STATE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBrowserStartupErrorDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_APP_START_FAILED);
        }
    }

    private void cancelMemoryClear() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).cancelMemoryClear(MemoryClearConstants.EVENT_ID.TOS);
        }
    }

    private SpannableString createSpannableString(String message, String link, final String url) {
        int iStart;
        int iEnd;
        SpannableString spannableString = new SpannableString(message);
        Matcher matcher = Pattern.compile(link).matcher(message);
        if (matcher.find()) {
            iStart = matcher.start();
            iEnd = matcher.end();
        } else {
            iStart = 0;
            iEnd = 0;
        }
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearTosFragment.3
            @Override // android.text.style.ClickableSpan
            public void onClick(View textView) {
                try {
                    MemoryClearTosFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    LogUtil.error(e);
                    MemoryClearTosFragment.this.showBrowserStartupErrorDialog();
                }
            }
        }, iStart, iEnd, 18);
        return spannableString;
    }
}
