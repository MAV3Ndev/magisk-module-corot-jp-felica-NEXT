package com.felicanetworks.mfm.main.view.views.parts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class UpdateButton extends RelativeLayout {
    ImageButton mUpdateButton;
    UpdateButtonClickListener mUpdateButtonClickListener;
    ImageView mUpdateButtonError;

    public interface UpdateButtonClickListener {
        void onClick();
    }

    public UpdateButton(Context context) {
        super(context);
        init();
    }

    public UpdateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UpdateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.parts_update_button, (ViewGroup) this, true);
        this.mUpdateButton = (ImageButton) findViewById(R.id.parts_update_button);
        this.mUpdateButtonError = (ImageView) findViewById(R.id.parts_update_button_error);
        this.mUpdateButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.parts.UpdateButton.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (UpdateButton.this.mUpdateButtonClickListener == null) {
                    return;
                }
                UpdateButton.this.mUpdateButtonClickListener.onClick();
            }
        });
        this.mUpdateButtonError.setVisibility(8);
    }

    public void setError(boolean isError) {
        ImageView imageView = this.mUpdateButtonError;
        if (imageView != null) {
            if (isError) {
                imageView.setVisibility(0);
                this.mUpdateButton.setContentDescription(getContext().getString(R.string.voice_read_button_update_error));
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public void startAnimation() {
        if (this.mUpdateButton != null) {
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            animationLoadAnimation.setInterpolator(new LinearInterpolator());
            this.mUpdateButton.startAnimation(animationLoadAnimation);
            this.mUpdateButton.setContentDescription(getContext().getString(R.string.voice_read_button_update_animation));
        }
    }

    public void stopAnimation() {
        ImageButton imageButton = this.mUpdateButton;
        if (imageButton != null) {
            imageButton.clearAnimation();
        }
    }

    public void setUpdateButtonClickListener(UpdateButtonClickListener updateButtonClickListener) {
        this.mUpdateButtonClickListener = updateButtonClickListener;
    }
}
