package com.felicanetworks.mfm.main.view.views;

import android.os.Build;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseFragment extends Fragment {
    private Insets mInsets = Insets.NONE;

    public interface SpannableOnClickListener {
        void onClick(View textView);
    }

    public void showNormalDialog(CustomDialogFragment dialogFragment) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseActivity baseActivity = (BaseActivity) getActivity();
        dialogFragment.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
        baseActivity.showNormalDialog(dialogFragment);
    }

    public void showModelChangeDialog(DepositDialogFragment dialogFragment) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseActivity baseActivity = (BaseActivity) getActivity();
        dialogFragment.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
        baseActivity.showNormalDialog(dialogFragment);
    }

    public CustomDialogFragment showWarningDialog(String message, String errorCode) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).showWarningDialog(message, errorCode);
    }

    public CustomDialogFragment showWarningDialog(int resId) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).showWarningDialog(resId);
    }

    void notifyException(Exception ex) {
        Structure structure = getStructure();
        if (structure != null) {
            structure.detectException(ex);
        }
    }

    Structure getStructure() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).getCurrentRequest();
    }

    public void stampWithJudgeActivityState(final MfmStamp.Event event, final Object... args) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        AnalysisManager.stamp(event, args);
    }

    public boolean checkNotificationPermission() {
        return Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(requireActivity(), "android.permission.POST_NOTIFICATIONS") == -1;
    }

    protected SpannableString createSpannableString(String message, String link, final SpannableOnClickListener listener) {
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
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfm.main.view.views.BaseFragment.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View textView) {
                SpannableOnClickListener spannableOnClickListener = listener;
                if (spannableOnClickListener != null) {
                    spannableOnClickListener.onClick(textView);
                }
            }
        }, iStart, iEnd, 18);
        return spannableString;
    }

    protected void adjustViewToNavigationBar(View view, final boolean padding) {
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.views.BaseFragment$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m416x9614b6ca(padding, view2, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToNavigationBar$0$com-felicanetworks-mfm-main-view-views-BaseFragment, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m416x9614b6ca(boolean z, View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        if (this.mInsets.bottom != insets.bottom) {
            if (z) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), (view.getPaddingBottom() + insets.bottom) - this.mInsets.bottom);
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams.bottomMargin = (marginLayoutParams.bottomMargin + insets.bottom) - this.mInsets.bottom;
                view.setLayoutParams(marginLayoutParams);
            }
        }
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
