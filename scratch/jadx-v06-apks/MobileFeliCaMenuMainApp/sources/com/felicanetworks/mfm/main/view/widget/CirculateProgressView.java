package com.felicanetworks.mfm.main.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public class CirculateProgressView extends FrameLayout implements Animation.AnimationListener {
    private static final float CIRCLE_MAX_ANGLE = 360.0f;
    private static final float CIRCULAR_ROUND_ANGLE = 432.0f;
    private float fromDegrees;
    private boolean isRunning;
    private float toDegrees;

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public CirculateProgressView(Context context) {
        super(context);
        this.fromDegrees = 0.0f;
        this.toDegrees = CIRCULAR_ROUND_ANGLE;
        this.isRunning = false;
        init(null);
    }

    public CirculateProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fromDegrees = 0.0f;
        this.toDegrees = CIRCULAR_ROUND_ANGLE;
        this.isRunning = false;
        init(attributeSet);
    }

    public CirculateProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.fromDegrees = 0.0f;
        this.toDegrees = CIRCULAR_ROUND_ANGLE;
        this.isRunning = false;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_circulate_progress_view, (ViewGroup) this, true);
        setVisibility(8);
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CirculateProgressView);
        String string = typedArrayObtainStyledAttributes.getString(1);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, true);
        typedArrayObtainStyledAttributes.recycle();
        setText(string);
        if (z) {
            start();
        } else {
            setVisibility(8);
        }
    }

    public void setText(CharSequence charSequence) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(charSequence);
        textView.setVisibility(TextUtils.isEmpty(textView.getText()) ? 8 : 0);
    }

    private void rotate() {
        View viewFindViewById = findViewById(R.id.circularProgress);
        RotateAnimation rotateAnimation = new RotateAnimation(this.fromDegrees, this.toDegrees, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(1200L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(this);
        viewFindViewById.startAnimation(rotateAnimation);
    }

    public void start() {
        this.isRunning = true;
        setVisibility(0);
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        rotate();
    }

    public void stop() {
        this.isRunning = false;
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.felicanetworks.mfm.main.view.widget.CirculateProgressView.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                CirculateProgressView.this.setVisibility(8);
            }
        });
        startAnimation(animationLoadAnimation);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        float f = this.toDegrees - CIRCLE_MAX_ANGLE;
        this.fromDegrees = f;
        this.toDegrees = f + CIRCULAR_ROUND_ANGLE;
        if (this.isRunning) {
            rotate();
        }
    }
}
