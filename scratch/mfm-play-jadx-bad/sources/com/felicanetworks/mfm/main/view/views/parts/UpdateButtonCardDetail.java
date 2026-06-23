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
public class UpdateButtonCardDetail extends RelativeLayout {
    ImageButton mUpdateButton;
    UpdateButtonCardDetailClickListener mUpdateButtonCardDetailClickListener;
    ImageView mUpdateButtonError;

    public interface UpdateButtonCardDetailClickListener {
        void onClick();
    }

    public UpdateButtonCardDetail(Context context) {
        super(context);
        init();
    }

    public UpdateButtonCardDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UpdateButtonCardDetail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.parts_update_button_card_detail, (ViewGroup) this, true);
        this.mUpdateButton = (ImageButton) findViewById(R.id.parts_update_button);
        ImageView imageView = (ImageView) findViewById(R.id.parts_update_button_error);
        this.mUpdateButtonError = imageView;
        imageView.setVisibility(8);
    }

    public void setError(boolean isError) {
        ImageView imageView = this.mUpdateButtonError;
        if (imageView != null) {
            if (isError) {
                imageView.setVisibility(0);
                this.mUpdateButton.setContentDescription(getContext().getString(R.string.voice_read_button_update_error));
                this.mUpdateButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.parts.UpdateButtonCardDetail.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (UpdateButtonCardDetail.this.mUpdateButtonCardDetailClickListener == null) {
                            return;
                        }
                        UpdateButtonCardDetail.this.mUpdateButtonCardDetailClickListener.onClick();
                    }
                });
                return;
            }
            imageView.setVisibility(8);
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

    public void setUpdateButtonClickListener(UpdateButtonCardDetailClickListener updateButtonClickListener) {
        this.mUpdateButtonCardDetailClickListener = updateButtonClickListener;
    }
}
