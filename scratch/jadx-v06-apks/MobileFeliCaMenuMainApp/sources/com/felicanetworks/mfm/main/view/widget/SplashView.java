package com.felicanetworks.mfm.main.view.widget;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public class SplashView extends FrameLayout {
    private final Activity activity;
    private Animator animator;
    private boolean running;

    public interface Listener {
        void onFinish();
    }

    private enum Time {
        IN(300),
        STAY(1400),
        OUT(300);

        private final long value;

        Time(long j) {
            this.value = j;
        }
    }

    private enum Effect {
        FADE_IN(new AlphaAnimation(0.0f, 1.0f), Time.IN, new DecelerateInterpolator()),
        FADE_OUT(new AlphaAnimation(1.0f, 0.0f), Time.OUT, new LinearInterpolator());

        private final Animation anim;

        Effect(Animation animation, Time time, Interpolator interpolator) {
            animation.setDuration(time.value);
            animation.setFillAfter(true);
            if (interpolator != null) {
                animation.setInterpolator(interpolator);
            }
            this.anim = animation;
        }
    }

    public SplashView(Activity activity) {
        super(activity);
        this.running = false;
        this.activity = activity;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.widget_splash_view, (ViewGroup) this, true);
    }

    public void startSplash() {
        startSplash(null);
    }

    public void startSplash(final Listener listener) {
        this.running = true;
        View viewFindViewById = findViewById(R.id.frame_animatable);
        if (this.animator != null) {
            abort();
        }
        final ViewGroup viewGroup = (ViewGroup) this.activity.findViewById(android.R.id.content);
        viewGroup.addView(this);
        Animator animator = new Animator(this, viewFindViewById, new Listener() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.1
            @Override // com.felicanetworks.mfm.main.view.widget.SplashView.Listener
            public void onFinish() {
                viewGroup.removeView(this);
                SplashView.this.running = false;
                Listener listener2 = listener;
                if (listener2 != null) {
                    listener2.onFinish();
                }
            }
        });
        this.animator = animator;
        animator.start();
    }

    public boolean isRunning() {
        return this.running;
    }

    public void abort() {
        if (this.animator == null) {
            return;
        }
        setVisibility(4);
        this.animator.interrupt();
    }

    private static class Animator extends Thread {
        private final View frame;
        private final Listener listener;
        private final View logo;
        private final Handler postman = new Handler(Looper.getMainLooper());

        Animator(View view, View view2, Listener listener) {
            this.frame = view;
            this.logo = view2;
            this.listener = listener;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Runnable runnable;
            try {
                uiThread(new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.Animator.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Animator.this.logo.setVisibility(0);
                        Animator.this.logo.startAnimation(Effect.FADE_IN.anim);
                    }
                });
                sleep(Time.IN.value);
                sleep(Time.STAY.value);
                uiThread(new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.Animator.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Animator.this.frame.startAnimation(Effect.FADE_OUT.anim);
                    }
                });
                sleep(Time.OUT.value);
                runnable = new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.Animator.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Animator.this.listener.onFinish();
                    }
                };
            } catch (InterruptedException unused) {
                runnable = new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.Animator.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Animator.this.listener.onFinish();
                    }
                };
            } catch (Throwable th) {
                uiThread(new Runnable() { // from class: com.felicanetworks.mfm.main.view.widget.SplashView.Animator.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Animator.this.listener.onFinish();
                    }
                });
                throw th;
            }
            uiThread(runnable);
        }

        private void uiThread(Runnable runnable) {
            this.postman.post(runnable);
        }
    }
}
