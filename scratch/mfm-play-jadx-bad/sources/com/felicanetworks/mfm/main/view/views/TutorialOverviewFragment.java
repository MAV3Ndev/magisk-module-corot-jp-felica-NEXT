package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class TutorialOverviewFragment extends BaseFragment {
    private Action action;
    private Insets mInsets = Insets.NONE;

    public interface Action {
        void onCompleteTutorialOverview();
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
        View viewInflate = inflater.inflate(R.layout.fragment_tutorial_overview, container, false);
        adjustViewToSystemBar(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TutorialOverviewFragment.this.action == null) {
                    return;
                }
                view2.setEnabled(false);
                TutorialOverviewFragment.this.action.onCompleteTutorialOverview();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToSystemBar(getView());
    }

    private void adjustViewToSystemBar(View view) {
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.views.TutorialOverviewFragment$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m420x3027e635(view2, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToSystemBar$0$com-felicanetworks-mfm-main-view-views-TutorialOverviewFragment, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m420x3027e635(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        View viewFindViewById = view.findViewById(R.id.scrollView);
        if (this.mInsets.top != insets.top) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams();
            marginLayoutParams.topMargin = (marginLayoutParams.topMargin + insets.top) - this.mInsets.top;
            viewFindViewById.setLayoutParams(marginLayoutParams);
        }
        if (this.mInsets.bottom != insets.bottom) {
            viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), (viewFindViewById.getPaddingBottom() + insets.bottom) - this.mInsets.bottom);
            View viewFindViewById2 = view.findViewById(R.id.next);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewFindViewById2.getLayoutParams();
            marginLayoutParams2.bottomMargin = (marginLayoutParams2.bottomMargin + insets.bottom) - this.mInsets.bottom;
            viewFindViewById2.setLayoutParams(marginLayoutParams2);
        }
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        if (this.mInsets.left != insets.left) {
            paddingLeft = (view.getPaddingLeft() + insets.left) - this.mInsets.left;
        }
        if (this.mInsets.right != insets.right) {
            paddingRight = (view.getPaddingRight() + insets.right) - this.mInsets.right;
        }
        view.setPadding(paddingLeft, view.getPaddingTop(), paddingRight, view.getPaddingBottom());
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
