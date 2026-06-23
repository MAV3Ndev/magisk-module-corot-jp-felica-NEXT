package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.felicanetworks.mfm.main.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class FpServiceListView extends FrameLayout {
    private static final int NEXT_VIEW_POSITION = 92;
    private final Handler handler;
    private Adapter mAdapter;
    private List<ListViewRunnable> mListViewRunnable;
    private DisplayMetrics mMetrics;

    public FpServiceListView(Context context) {
        super(context);
        this.handler = new Handler(Looper.getMainLooper());
        this.mListViewRunnable = new ArrayList();
        this.mMetrics = context.getResources().getDisplayMetrics();
    }

    public FpServiceListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.handler = new Handler(Looper.getMainLooper());
        this.mListViewRunnable = new ArrayList();
        this.mMetrics = context.getResources().getDisplayMetrics();
    }

    public FpServiceListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.handler = new Handler(Looper.getMainLooper());
        this.mListViewRunnable = new ArrayList();
        this.mMetrics = context.getResources().getDisplayMetrics();
    }

    public void setAdapter(final Adapter adapter) {
        this.mAdapter = adapter;
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            if (i > 0) {
                paddingTop += (int) (this.mMetrics.density * 92.0f);
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
            layoutParams.setMargins(paddingLeft, paddingTop, 0, 0);
            this.mListViewRunnable.add(new ListViewRunnable(i, layoutParams) { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListView.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        View view = FpServiceListView.this.mAdapter.getView(this.mPosition, null, null);
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(FpServiceListView.this.getContext(), R.anim.list_item);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.felicanetworks.mfm.main.view.views.FpServiceListView.1.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                int i2 = AnonymousClass1.this.mPosition + 1;
                                if (FpServiceListView.this.mListViewRunnable.size() > i2) {
                                    FpServiceListView.this.handler.post((Runnable) FpServiceListView.this.mListViewRunnable.get(i2));
                                }
                            }
                        });
                        view.setAnimation(animationLoadAnimation);
                        FpServiceListView.this.addView(view, this.mLayoutParams);
                    } catch (Exception unused) {
                    }
                }
            });
        }
        if (this.mListViewRunnable.size() > 0) {
            this.handler.post(this.mListViewRunnable.get(0));
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) getLayoutParams();
            layoutParams2.width = -1;
            layoutParams2.height = paddingTop + ((int) (this.mMetrics.density * 108.0f));
            setLayoutParams(layoutParams2);
            return;
        }
        setVisibility(8);
    }

    private abstract class ListViewRunnable implements Runnable {
        public FrameLayout.LayoutParams mLayoutParams;
        public int mPosition;

        ListViewRunnable(int position, FrameLayout.LayoutParams layoutParams) {
            this.mPosition = position;
            this.mLayoutParams = layoutParams;
        }
    }
}
