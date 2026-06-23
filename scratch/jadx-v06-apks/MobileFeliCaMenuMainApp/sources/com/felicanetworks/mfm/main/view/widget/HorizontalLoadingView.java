package com.felicanetworks.mfm.main.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import androidx.core.content.res.ResourcesCompat;
import com.felicanetworks.mfm.main.R;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalLoadingView extends FrameLayout {
    private Executor executor;

    public HorizontalLoadingView(Context context) {
        super(context);
    }

    public HorizontalLoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.executor == null) {
            Executor executor = new Executor(this, View.MeasureSpec.getSize(i));
            this.executor = executor;
            executor.start();
        }
    }

    public void start() {
        Executor executor = this.executor;
        if (executor == null) {
            return;
        }
        executor.start();
    }

    public void stop() {
        Executor executor = this.executor;
        if (executor == null) {
            return;
        }
        executor.stop();
    }

    private static class Executor {
        private static final long TIME_PERIOD = 1500;
        private static final long TIME_RUNNING = 1200;
        private static final int WIDTH_AS_DP_FOR_RUN_AWAY_BAR = 76;
        private final View bar;
        private final int parentWidth;
        private final Handler postman = new Handler(Looper.getMainLooper());
        private Timer timer;
        private final float width;

        Executor(HorizontalLoadingView horizontalLoadingView, int i) {
            Context context = horizontalLoadingView.getContext();
            Resources resources = context.getResources();
            this.parentWidth = i;
            View view = new View(context);
            this.bar = view;
            horizontalLoadingView.addView(view);
            this.width = resources.getDisplayMetrics().density * 76.0f;
            this.bar.setLayoutParams(new FrameLayout.LayoutParams((int) this.width, -1));
            this.bar.setTranslationX(-this.width);
            this.bar.setBackground(ResourcesCompat.getDrawable(resources, R.drawable.loading_bar_gradient, null));
        }

        void start() {
            if (this.timer != null) {
                return;
            }
            this.timer = new Timer(true);
            final TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, (this.width / this.parentWidth) + 1.0f, 2, 0.0f, 2, 0.0f);
            translateAnimation.setDuration(TIME_RUNNING);
            this.timer.scheduleAtFixedRate(new TimerTask() { // from class: com.felicanetworks.mfm.main.view.widget.HorizontalLoadingView.Executor.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Executor.this.uiThread(new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.HorizontalLoadingView.Executor.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Executor.this.bar.startAnimation(translateAnimation);
                        }
                    });
                }
            }, TIME_PERIOD - (System.currentTimeMillis() % TIME_PERIOD), TIME_PERIOD);
        }

        void stop() {
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
                this.timer = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void uiThread(Runnable runnable) {
            this.postman.post(runnable);
        }
    }
}
