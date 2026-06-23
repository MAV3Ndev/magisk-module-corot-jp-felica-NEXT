package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.views.BannerLayout;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class IndicatorLayout extends LinearLayout {
    static final int DEFAULT_LAYOUT = 0;
    private List<ViewHolder> mIndicatorViews;
    private boolean mIsBannerIndicator;
    private OnClickIndicatorListener mOnClickIndicatorListener;
    private ViewPager mViewPager;

    public interface OnClickIndicatorListener {
        void onClick(int i);
    }

    static class ViewHolder {
        public Button mIndicator;
        public int mPosition;

        ViewHolder(Button button, int i) {
            this.mPosition = i;
            this.mIndicator = button;
        }
    }

    public IndicatorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIndicatorViews = new ArrayList();
        this.mOnClickIndicatorListener = null;
        this.mIsBannerIndicator = false;
    }

    public void setOnClickIndicatorListener(OnClickIndicatorListener onClickIndicatorListener) {
        this.mOnClickIndicatorListener = onClickIndicatorListener;
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, 0);
    }

    public void setViewPager(ViewPager viewPager, int i) {
        int count;
        ViewGroup viewGroup;
        this.mViewPager = viewPager;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        if (this.mViewPager.getAdapter() instanceof BannerLayout.BannerInfoAdapter) {
            count = this.mViewPager.getAdapter().getCount() - 2;
            this.mIsBannerIndicator = true;
        } else {
            count = this.mViewPager.getAdapter().getCount();
        }
        if (count > 1) {
            this.mViewPager.addOnPageChangeListener(new IndicatorListener());
            for (int i2 = 0; i2 < count; i2++) {
                if (i == 0) {
                    viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.indicator_item, (ViewGroup) this, false);
                } else {
                    viewGroup = (ViewGroup) layoutInflater.inflate(i, (ViewGroup) this, false);
                }
                View childAt = viewGroup.getChildAt(0);
                if (childAt instanceof Button) {
                    Button button = (Button) childAt;
                    if (i2 + 1 >= count) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        viewGroup.setLayoutParams(layoutParams);
                    }
                    if (this.mIsBannerIndicator) {
                        if (i2 != this.mViewPager.getCurrentItem() - 1) {
                            button.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                        } else {
                            button.setBackgroundResource(R.drawable.frame_page_indicator_active);
                        }
                    } else if (i2 != this.mViewPager.getCurrentItem()) {
                        button.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                    } else {
                        button.setBackgroundResource(R.drawable.frame_page_indicator_active);
                    }
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.IndicatorLayout.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (IndicatorLayout.this.mOnClickIndicatorListener != null) {
                                for (ViewHolder viewHolder : IndicatorLayout.this.mIndicatorViews) {
                                    if (viewHolder.mIndicator.equals(view)) {
                                        IndicatorLayout.this.mOnClickIndicatorListener.onClick(viewHolder.mPosition);
                                    }
                                }
                            }
                        }
                    });
                    this.mIndicatorViews.add(new ViewHolder(button, i2));
                    addView(viewGroup, button.getLayoutParams());
                }
            }
        }
    }

    private class IndicatorListener implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }

        private IndicatorListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            int currentItem = IndicatorLayout.this.mViewPager.getCurrentItem();
            int i2 = 0;
            if (IndicatorLayout.this.mIsBannerIndicator) {
                int currentItem2 = IndicatorLayout.this.mViewPager.getCurrentItem();
                int count = IndicatorLayout.this.mViewPager.getAdapter().getCount() - 2;
                currentItem = currentItem2 == 0 ? count - 1 : currentItem2 > count ? 0 : currentItem2 - 1;
            }
            for (ViewHolder viewHolder : IndicatorLayout.this.mIndicatorViews) {
                if (currentItem == i2) {
                    viewHolder.mIndicator.setBackgroundResource(R.drawable.frame_page_indicator_active);
                } else {
                    viewHolder.mIndicator.setBackgroundResource(R.drawable.frame_page_indicator_inactive);
                }
                i2++;
            }
        }
    }
}
