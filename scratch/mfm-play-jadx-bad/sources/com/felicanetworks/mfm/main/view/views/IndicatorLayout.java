package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.views.BannerLayout;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class IndicatorLayout extends LinearLayout {
    static final int DEFAULT_LAYOUT = 0;
    private final List<ViewHolder> mIndicatorViews;
    private boolean mIsBannerIndicator;
    private OnClickIndicatorListener mOnClickIndicatorListener;
    private ViewPager2 mViewPager;

    public interface OnClickIndicatorListener {
        void onClick(int position);
    }

    static class ViewHolder {
        public Button mIndicator;
        public int mPosition;

        ViewHolder(Button v, int position) {
            this.mPosition = position;
            this.mIndicator = v;
        }
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIndicatorViews = new ArrayList();
        this.mOnClickIndicatorListener = null;
        this.mIsBannerIndicator = false;
    }

    public void setOnClickIndicatorListener(OnClickIndicatorListener listener) {
        this.mOnClickIndicatorListener = listener;
    }

    public void setViewPager(ViewPager2 viewPager) {
        setViewPager(viewPager, 0);
    }

    public void setViewPager(ViewPager2 viewPager, int layoutRes) {
        int itemCount;
        ViewGroup viewGroup;
        this.mViewPager = viewPager;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        if (this.mViewPager.getAdapter() instanceof BannerLayout.BannerInfoAdapter) {
            itemCount = this.mViewPager.getAdapter().getItemCount() - 2;
            this.mIsBannerIndicator = true;
        } else {
            itemCount = this.mViewPager.getAdapter().getItemCount();
        }
        if (itemCount > 1) {
            this.mViewPager.registerOnPageChangeCallback(new IndicatorCallback());
            for (int i = 0; i < itemCount; i++) {
                if (layoutRes == 0) {
                    viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.indicator_item, (ViewGroup) this, false);
                } else {
                    viewGroup = (ViewGroup) layoutInflater.inflate(layoutRes, (ViewGroup) this, false);
                }
                View childAt = viewGroup.getChildAt(0);
                if (childAt instanceof Button) {
                    Button button = (Button) childAt;
                    if (i + 1 >= itemCount) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        viewGroup.setLayoutParams(layoutParams);
                    }
                    if (this.mIsBannerIndicator) {
                        if (i != this.mViewPager.getCurrentItem() - 1) {
                            button.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                        } else {
                            button.setBackgroundResource(R.drawable.frame_page_indicator_active);
                        }
                    } else if (i != this.mViewPager.getCurrentItem()) {
                        button.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                    } else {
                        button.setBackgroundResource(R.drawable.frame_page_indicator_active);
                    }
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.IndicatorLayout.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            if (IndicatorLayout.this.mOnClickIndicatorListener != null) {
                                for (ViewHolder viewHolder : IndicatorLayout.this.mIndicatorViews) {
                                    if (viewHolder.mIndicator.equals(v)) {
                                        IndicatorLayout.this.mOnClickIndicatorListener.onClick(viewHolder.mPosition);
                                    }
                                }
                            }
                        }
                    });
                    this.mIndicatorViews.add(new ViewHolder(button, i));
                    addView(viewGroup, button.getLayoutParams());
                }
            }
        }
    }

    private class IndicatorCallback extends ViewPager2.OnPageChangeCallback {
        private IndicatorCallback() {
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int state) {
            int currentItem = IndicatorLayout.this.mViewPager.getCurrentItem();
            int i = 0;
            if (IndicatorLayout.this.mIsBannerIndicator) {
                int currentItem2 = IndicatorLayout.this.mViewPager.getCurrentItem();
                int itemCount = IndicatorLayout.this.mViewPager.getAdapter().getItemCount();
                currentItem = currentItem2 == 0 ? itemCount - 3 : currentItem2 > itemCount + (-2) ? 0 : currentItem2 - 1;
            }
            for (ViewHolder viewHolder : IndicatorLayout.this.mIndicatorViews) {
                if (currentItem == i) {
                    viewHolder.mIndicator.setBackgroundResource(R.drawable.frame_page_indicator_active);
                } else {
                    viewHolder.mIndicator.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                }
                i++;
            }
        }
    }
}
