package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.views.IndicatorLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class BannerLayout extends FrameLayout {
    private static final int BANNER_AUTO_SCROLL_TIMER = 5000;
    private float initialX;
    private float initialY;
    private final BannerInfoAdapter mAdapter;
    private int mBannerHorizontalMargin;
    private ViewPager2 mBannerViewPager;
    private boolean mFirstLayout;
    private final Handler mHandler;
    private int mHeightAspect;
    private IndicatorLayout mIndicatorLayout;
    private final Handler mInitializeHandler;
    private OnBannerClickListener mOnBannerClickListener;
    private OnDetectErrorListener mOnDetectErrorListener;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    private Runnable mPendingInitBanner;
    View.OnTouchListener mTouchListener;
    private int mWidthAspect;
    private final int touchSlop;

    public interface OnBannerClickListener {
        void onClick(int position);
    }

    public interface OnDetectErrorListener {
        void onError(Exception e);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOnDetectErrorListener = null;
        this.mOnBannerClickListener = null;
        this.mWidthAspect = 0;
        this.mHeightAspect = 0;
        this.mFirstLayout = true;
        this.mPendingInitBanner = null;
        this.mBannerHorizontalMargin = 0;
        this.initialX = 0.0f;
        this.initialY = 0.0f;
        this.mTouchListener = new View.OnTouchListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                if (event.getAction() == 0 || event.getAction() == 2) {
                    BannerLayout.this.cancelAutoScrollTimer();
                    return false;
                }
                if (event.getAction() != 1) {
                    return false;
                }
                BannerLayout.this.setAutoScrollTimer();
                return false;
            }
        };
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.6
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    int currentItem = BannerLayout.this.mBannerViewPager.getCurrentItem();
                    BannerInfoAdapter bannerInfoAdapter = (BannerInfoAdapter) BannerLayout.this.mBannerViewPager.getAdapter();
                    if (bannerInfoAdapter == null) {
                        return;
                    }
                    int itemCount = bannerInfoAdapter.getItemCount() - 2;
                    if (currentItem == 0) {
                        BannerLayout.this.mBannerViewPager.setCurrentItem(itemCount, false);
                    } else if (currentItem > itemCount) {
                        BannerLayout.this.mBannerViewPager.setCurrentItem(1, false);
                    }
                }
            }
        };
        this.mAdapter = new BannerInfoAdapter();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mInitializeHandler = new Handler(Looper.getMainLooper());
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setAutoScrollTimer() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postDelayed(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.2
            @Override // java.lang.Runnable
            public void run() {
                if (BannerLayout.this.mBannerViewPager != null) {
                    BannerLayout.this.mBannerViewPager.setCurrentItem(BannerLayout.this.mBannerViewPager.getCurrentItem() + 1, true);
                    BannerLayout.this.mHandler.postDelayed(this, 5000L);
                }
            }
        }, 5000L);
    }

    public void cancelAutoScrollTimer() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void setBanner(final ViewPager2 banner) {
        if (this.mBannerViewPager == null) {
            this.mBannerViewPager = banner;
            banner.setSoundEffectsEnabled(false);
            if (!this.mFirstLayout) {
                InitializeBanner();
            } else {
                this.mPendingInitBanner = new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.3
                    @Override // java.lang.Runnable
                    public void run() {
                        BannerLayout.this.InitializeBanner();
                        BannerLayout.this.mIndicatorLayout.setViewPager(BannerLayout.this.mBannerViewPager, R.layout.indicator_item_myservice);
                    }
                };
            }
        }
        this.mBannerViewPager.getChildAt(0).setOnTouchListener(this.mTouchListener);
        this.mBannerViewPager.setOnKeyListener(new View.OnKeyListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.4
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != 1 || event.getKeyCode() != 23) {
                    return false;
                }
                BannerLayout.this.mAdapter.startUrl(BannerLayout.this.mBannerViewPager.getCurrentItem());
                return false;
            }
        });
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        animationLoadAnimation.setDuration(1000L);
        this.mBannerViewPager.startAnimation(animationLoadAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void InitializeBanner() {
        setBannerViewLayoutParams();
        this.mBannerViewPager.setAdapter(this.mAdapter);
        this.mBannerViewPager.registerOnPageChangeCallback(this.mOnPageChangeCallback);
        this.mBannerViewPager.setCurrentItem(1, false);
        setAutoScrollTimer();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        handleInterceptTouchEvent(event);
        return false;
    }

    public void setOnDetectErrorListener(OnDetectErrorListener listener) {
        this.mOnDetectErrorListener = listener;
    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.mOnBannerClickListener = listener;
    }

    public void setIndicatorLayout(IndicatorLayout indicator) {
        if (!this.mFirstLayout) {
            this.mIndicatorLayout = indicator;
            indicator.setViewPager(this.mBannerViewPager, R.layout.indicator_item_myservice);
        } else {
            this.mIndicatorLayout = indicator;
        }
        indicator.setOnClickIndicatorListener(new IndicatorLayout.OnClickIndicatorListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.5
            @Override // com.felicanetworks.mfm.main.view.views.IndicatorLayout.OnClickIndicatorListener
            public void onClick(int position) {
                BannerLayout.this.mBannerViewPager.setCurrentItem(position + 1, true);
            }
        });
    }

    public ViewPager2 getBannerViewPager() {
        return this.mBannerViewPager;
    }

    public void setHorizontalMargin(int margin) {
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(margin);
        this.mBannerHorizontalMargin = dimensionPixelOffset;
        this.mAdapter.setHorizontalMargin(dimensionPixelOffset);
    }

    public void setItem(Bitmap item, int readText, Intent intent) {
        this.mAdapter.add(item, getResources().getString(readText), intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBannerViewLayoutParams() {
        ViewPager2 viewPager2 = this.mBannerViewPager;
        if (viewPager2 == null || this.mWidthAspect == 0 || this.mHeightAspect == 0) {
            return;
        }
        int width = ((View) viewPager2.getParent()).getWidth();
        int i = this.mBannerHorizontalMargin;
        int i2 = width - (i * 2);
        double d = (((double) i2) / ((double) this.mWidthAspect)) * ((double) this.mHeightAspect);
        int i3 = i2 + (i * 2);
        int iCeil = (int) Math.ceil(d);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBannerViewPager.getLayoutParams();
        if (layoutParams.width == i3 && layoutParams.height == iCeil) {
            return;
        }
        layoutParams.height = iCeil;
        layoutParams.width = i3;
        this.mBannerViewPager.setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.7
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                BannerLayout.this.post(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BannerLayout.this.setBannerViewLayoutParams();
                    }
                });
                BannerLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        RoundImageView imageView;

        public BannerViewHolder(RoundImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }

    public class BannerInfoAdapter extends RecyclerView.Adapter<BannerViewHolder> {
        private int mHorizontalMargin = 0;
        private final ArrayList<Bitmap> mBitmapList = new ArrayList<>();
        private final ArrayList<String> mReadTextList = new ArrayList<>();
        private final ArrayList<Intent> mIntentList = new ArrayList<>();

        public BannerInfoAdapter() {
        }

        /* JADX DEBUG: Method merged with bridge method: onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder; */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RoundImageView roundImageView = new RoundImageView(BannerLayout.this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            int i = this.mHorizontalMargin;
            layoutParams.setMargins(i, 0, i, 0);
            roundImageView.setLayoutParams(layoutParams);
            final BannerViewHolder bannerViewHolder = new BannerViewHolder(roundImageView);
            bannerViewHolder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.BannerInfoAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    BannerInfoAdapter.this.startUrl(bannerViewHolder.getBindingAdapterPosition());
                }
            });
            return bannerViewHolder;
        }

        /* JADX DEBUG: Method merged with bridge method: onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BannerViewHolder holder, int position) {
            Bitmap bitmap = this.mBitmapList.get(position);
            holder.imageView.setAdjustViewBounds(true);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageView.setImageBitmap(bitmap);
            holder.imageView.setContentDescription(getReadText(position));
        }

        public void setHorizontalMargin(int margin) {
            this.mHorizontalMargin = margin;
        }

        public void add(Bitmap item, String readText, Intent intent) {
            if (this.mBitmapList.isEmpty()) {
                this.mBitmapList.add(item);
                setAspectRatio(item);
            } else if (this.mBitmapList.size() == 1) {
                Bitmap bitmap = this.mBitmapList.get(0);
                this.mBitmapList.remove(bitmap);
                this.mBitmapList.add(item);
                this.mBitmapList.add(bitmap);
                this.mBitmapList.add(item);
                this.mBitmapList.add(bitmap);
            } else {
                ArrayList<Bitmap> arrayList = this.mBitmapList;
                arrayList.remove(arrayList.size() - 1);
                this.mBitmapList.add(item);
                ArrayList<Bitmap> arrayList2 = this.mBitmapList;
                arrayList2.remove(arrayList2.get(0));
                this.mBitmapList.add(0, item);
                ArrayList<Bitmap> arrayList3 = this.mBitmapList;
                arrayList3.add(arrayList3.get(1));
            }
            this.mReadTextList.add(readText);
            this.mIntentList.add(intent);
        }

        private String getReadText(int position) {
            int i;
            try {
                if (position == 0) {
                    int itemCount = getItemCount();
                    i = itemCount - 1;
                    if (i > 0) {
                        i = itemCount - 3;
                    }
                } else {
                    i = position == getItemCount() + (-1) ? 0 : position - 1;
                }
                return this.mReadTextList.get(i);
            } catch (Exception unused) {
                return "";
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startUrl(int position) {
            int currentItem;
            try {
                if (position == 0) {
                    int itemCount = getItemCount();
                    currentItem = itemCount - 1;
                    if (currentItem > 0) {
                        currentItem = itemCount - 3;
                    }
                    BannerLayout.this.getContext().startActivity(this.mIntentList.get(currentItem));
                } else if (position == getItemCount() - 1) {
                    BannerLayout.this.getContext().startActivity(this.mIntentList.get(0));
                    currentItem = 0;
                } else {
                    BannerLayout.this.getContext().startActivity(this.mIntentList.get(BannerLayout.this.mBannerViewPager.getCurrentItem() - 1));
                    currentItem = BannerLayout.this.mBannerViewPager.getCurrentItem() - 1;
                }
                if (BannerLayout.this.mOnBannerClickListener != null) {
                    BannerLayout.this.mOnBannerClickListener.onClick(currentItem);
                }
            } catch (Exception e) {
                BannerLayout.this.mOnDetectErrorListener.onError(e);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mBitmapList.size();
        }

        private void setAspectRatio(Bitmap item) {
            int width = item.getWidth();
            int height = item.getHeight();
            int iCalculateEuclideanAlgorithm = calculateEuclideanAlgorithm(width, height);
            if (iCalculateEuclideanAlgorithm > 1) {
                BannerLayout.this.mWidthAspect = width / iCalculateEuclideanAlgorithm;
                BannerLayout.this.mHeightAspect = height / iCalculateEuclideanAlgorithm;
            }
        }

        private int calculateEuclideanAlgorithm(int dividend, int divisor) {
            int i = dividend % divisor;
            return i != 0 ? calculateEuclideanAlgorithm(divisor, i) : divisor;
        }
    }

    public static class RoundImageView extends AppCompatImageView {
        Rect mBounds;
        RectF mBoundsF;
        Paint mCopyPaint;
        Drawable mMaskDrawable;
        Paint mMaskedPaint;

        public RoundImageView(Context context) {
            super(context);
            this.mMaskedPaint = new Paint();
            if (Build.VERSION.SDK_INT >= 29) {
                this.mMaskedPaint.setBlendMode(BlendMode.SRC_ATOP);
            } else {
                this.mMaskedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            }
            this.mCopyPaint = new Paint();
            this.mMaskDrawable = ContextCompat.getDrawable(context, R.drawable.banner_round);
        }

        @Override // android.view.View
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            this.mBounds = new Rect(0, 0, w, h);
            this.mBoundsF = new RectF(this.mBounds);
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            if (this.mBounds != null) {
                int iSaveLayer = canvas.saveLayer(this.mBoundsF, this.mCopyPaint);
                this.mMaskDrawable.setBounds(this.mBounds);
                this.mMaskDrawable.draw(canvas);
                canvas.saveLayer(this.mBoundsF, this.mMaskedPaint);
                super.onDraw(canvas);
                canvas.restoreToCount(iSaveLayer);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Handler handler;
        super.onLayout(changed, left, top, right, bottom);
        this.mFirstLayout = false;
        Runnable runnable = this.mPendingInitBanner;
        if (runnable == null || (handler = this.mInitializeHandler) == null) {
            return;
        }
        handler.post(runnable);
        this.mPendingInitBanner = null;
    }

    private boolean isChildScroll(int orientation, float delta) {
        int i = (int) (-delta);
        View childAt = getChildAt(0);
        if (orientation == 0) {
            return childAt.canScrollHorizontally(i);
        }
        if (orientation == 1) {
            return childAt.canScrollVertically(i);
        }
        return false;
    }

    private void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 viewPager2 = this.mBannerViewPager;
        if (viewPager2 == null) {
            return;
        }
        int orientation = viewPager2.getOrientation();
        if (isChildScroll(orientation, -1.0f) || isChildScroll(orientation, 1.0f)) {
            if (e.getAction() == 0) {
                this.initialX = e.getX();
                this.initialY = e.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                return;
            }
            if (e.getAction() == 2) {
                float x = e.getX() - this.initialX;
                float y = e.getY() - this.initialY;
                boolean z = orientation == 0;
                float fAbs = Math.abs(x) * (z ? 0.5f : 1.0f);
                float fAbs2 = Math.abs(y) * (z ? 1.0f : 0.5f);
                int i = this.touchSlop;
                if (fAbs > i || fAbs2 > i) {
                    if (z == (fAbs2 > fAbs)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return;
                    }
                    ViewParent parent = getParent();
                    if (!z) {
                        x = y;
                    }
                    parent.requestDisallowInterceptTouchEvent(isChildScroll(orientation, x));
                }
            }
        }
    }
}
