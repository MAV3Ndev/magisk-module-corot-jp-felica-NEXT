package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
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
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.views.IndicatorLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BannerLayout extends FrameLayout {
    private static final int BANNER_AUTO_SCROLL_TIMER = 5000;
    private static final int BANNER_MARGIN_SIZE = 32;
    private BannerInfoAdapter mAdapter;
    private ViewPager mBannerViewPager;
    private boolean mFirstLayout;
    private Handler mHandler;
    private int mHeightAspect;
    private IndicatorLayout mIndicatorLayout;
    private Handler mInitializeHandler;
    private DisplayMetrics mMetrics;
    private OnBannerClickListener mOnBannerClickListener;
    private OnDetectErrorListener mOnDetectErrorListener;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private Runnable mPendingInitBanner;
    View.OnTouchListener mTouchListener;
    private int mWidthAspect;

    public interface OnBannerClickListener {
        void onClick(int i);
    }

    public interface OnDetectErrorListener {
        void onError(Exception exc);
    }

    public BannerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnDetectErrorListener = null;
        this.mOnBannerClickListener = null;
        this.mWidthAspect = 0;
        this.mHeightAspect = 0;
        this.mFirstLayout = true;
        this.mPendingInitBanner = null;
        this.mTouchListener = new View.OnTouchListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                    BannerLayout.this.cancelAutoScrollTimer();
                    return false;
                }
                if (motionEvent.getAction() != 1) {
                    return false;
                }
                BannerLayout.this.setAutoScrollTimer();
                return false;
            }
        };
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.6
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    int currentItem = BannerLayout.this.mBannerViewPager.getCurrentItem();
                    int count = BannerLayout.this.mBannerViewPager.getAdapter().getCount() - 2;
                    if (currentItem == 0) {
                        BannerLayout.this.mBannerViewPager.setCurrentItem(count, false);
                    } else if (currentItem > count) {
                        BannerLayout.this.mBannerViewPager.setCurrentItem(1, false);
                    }
                }
            }
        };
        this.mAdapter = new BannerInfoAdapter();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mInitializeHandler = new Handler(Looper.getMainLooper());
        this.mMetrics = context.getResources().getDisplayMetrics();
    }

    public void setAutoScrollTimer() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postDelayed(new Runnable() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.2
            @Override // java.lang.Runnable
            public void run() {
                if (BannerLayout.this.mBannerViewPager != null) {
                    BannerLayout.this.mBannerViewPager.arrowScroll(66);
                    BannerLayout.this.mHandler.postDelayed(this, 5000L);
                }
            }
        }, 5000L);
    }

    public void cancelAutoScrollTimer() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void setBanner(ViewPager viewPager) {
        if (this.mBannerViewPager == null) {
            this.mBannerViewPager = viewPager;
            viewPager.setSoundEffectsEnabled(false);
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
        this.mBannerViewPager.setOnTouchListener(this.mTouchListener);
        this.mBannerViewPager.setOnKeyListener(new View.OnKeyListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.4
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 23) {
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
        this.mBannerViewPager.addOnPageChangeListener(this.mOnPageChangeListener);
        this.mBannerViewPager.setCurrentItem(1, false);
        this.mBannerViewPager.setPageMargin((int) (this.mMetrics.density * 32.0f));
        setAutoScrollTimer();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 && motionEvent.getAction() != 1) {
            return false;
        }
        this.mTouchListener.onTouch(this.mBannerViewPager, motionEvent);
        return false;
    }

    public void setOnDetectErrorListener(OnDetectErrorListener onDetectErrorListener) {
        this.mOnDetectErrorListener = onDetectErrorListener;
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    public void setIndicatorLayout(IndicatorLayout indicatorLayout) {
        if (!this.mFirstLayout) {
            this.mIndicatorLayout = indicatorLayout;
            indicatorLayout.setViewPager(this.mBannerViewPager, R.layout.indicator_item_myservice);
        } else {
            this.mIndicatorLayout = indicatorLayout;
        }
        indicatorLayout.setOnClickIndicatorListener(new IndicatorLayout.OnClickIndicatorListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.5
            @Override // com.felicanetworks.mfm.main.view.views.IndicatorLayout.OnClickIndicatorListener
            public void onClick(int i) {
                BannerLayout.this.mBannerViewPager.setCurrentItem(i + 1, true);
            }
        });
    }

    public ViewPager getBannerViewPager() {
        return this.mBannerViewPager;
    }

    public void setItem(Bitmap bitmap, Intent intent) {
        this.mAdapter.add(bitmap, intent);
    }

    private void setBannerViewLayoutParams() {
        if (this.mWidthAspect == 0 || this.mHeightAspect == 0) {
            return;
        }
        double width = (((double) (this.mBannerViewPager.getWidth() - (this.mBannerViewPager.getPaddingLeft() + this.mBannerViewPager.getPaddingRight()))) / ((double) this.mWidthAspect)) * ((double) this.mHeightAspect);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBannerViewPager.getLayoutParams();
        layoutParams.height = (int) Math.ceil(width);
        this.mBannerViewPager.setLayoutParams(layoutParams);
    }

    public class BannerInfoAdapter extends PagerAdapter {
        private ArrayList<Bitmap> mBitmapList = new ArrayList<>();
        private ArrayList<Intent> mIntentList = new ArrayList<>();

        public BannerInfoAdapter() {
        }

        public void add(Bitmap bitmap, Intent intent) {
            if (this.mBitmapList.size() == 0) {
                this.mBitmapList.add(bitmap);
                setAspectRatio(bitmap);
            } else if (this.mBitmapList.size() == 1) {
                Bitmap bitmap2 = this.mBitmapList.get(0);
                this.mBitmapList.remove(bitmap2);
                this.mBitmapList.add(bitmap);
                this.mBitmapList.add(bitmap2);
                this.mBitmapList.add(bitmap);
                this.mBitmapList.add(bitmap2);
            } else {
                ArrayList<Bitmap> arrayList = this.mBitmapList;
                arrayList.remove(arrayList.size() - 1);
                this.mBitmapList.add(bitmap);
                ArrayList<Bitmap> arrayList2 = this.mBitmapList;
                arrayList2.remove(arrayList2.get(0));
                this.mBitmapList.add(0, bitmap);
                ArrayList<Bitmap> arrayList3 = this.mBitmapList;
                arrayList3.add(arrayList3.get(1));
            }
            this.mIntentList.add(intent);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, final int i) {
            Bitmap bitmap = this.mBitmapList.get(i);
            RoundImageView roundImageView = new RoundImageView(BannerLayout.this.getContext());
            roundImageView.setAdjustViewBounds(true);
            roundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            roundImageView.setImageBitmap(bitmap);
            roundImageView.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayout.BannerInfoAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BannerInfoAdapter.this.startUrl(i);
                }
            });
            viewGroup.addView(roundImageView);
            return roundImageView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:12:0x006b A[Catch: Exception -> 0x0075, TRY_LEAVE, TryCatch #0 {Exception -> 0x0075, blocks: (B:4:0x0003, B:5:0x001e, B:10:0x0063, B:12:0x006b, B:6:0x0021, B:8:0x0029, B:9:0x003b), top: B:17:0x0001 }] */
        /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void startUrl(int r3) {
            /*
                r2 = this;
                r0 = 0
                if (r3 != 0) goto L21
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                android.content.Context r3 = r3.getContext()     // Catch: java.lang.Exception -> L75
                java.util.ArrayList<android.content.Intent> r0 = r2.mIntentList     // Catch: java.lang.Exception -> L75
                int r1 = r2.getCount()     // Catch: java.lang.Exception -> L75
                int r1 = r1 + (-1)
                java.lang.Object r0 = r0.get(r1)     // Catch: java.lang.Exception -> L75
                android.content.Intent r0 = (android.content.Intent) r0     // Catch: java.lang.Exception -> L75
                r3.startActivity(r0)     // Catch: java.lang.Exception -> L75
                int r3 = r2.getCount()     // Catch: java.lang.Exception -> L75
            L1e:
                int r0 = r3 + (-1)
                goto L63
            L21:
                int r1 = r2.getCount()     // Catch: java.lang.Exception -> L75
                int r1 = r1 + (-1)
                if (r3 != r1) goto L3b
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                android.content.Context r3 = r3.getContext()     // Catch: java.lang.Exception -> L75
                java.util.ArrayList<android.content.Intent> r1 = r2.mIntentList     // Catch: java.lang.Exception -> L75
                java.lang.Object r1 = r1.get(r0)     // Catch: java.lang.Exception -> L75
                android.content.Intent r1 = (android.content.Intent) r1     // Catch: java.lang.Exception -> L75
                r3.startActivity(r1)     // Catch: java.lang.Exception -> L75
                goto L63
            L3b:
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                android.content.Context r3 = r3.getContext()     // Catch: java.lang.Exception -> L75
                java.util.ArrayList<android.content.Intent> r0 = r2.mIntentList     // Catch: java.lang.Exception -> L75
                com.felicanetworks.mfm.main.view.views.BannerLayout r1 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                androidx.viewpager.widget.ViewPager r1 = com.felicanetworks.mfm.main.view.views.BannerLayout.access$000(r1)     // Catch: java.lang.Exception -> L75
                int r1 = r1.getCurrentItem()     // Catch: java.lang.Exception -> L75
                int r1 = r1 + (-1)
                java.lang.Object r0 = r0.get(r1)     // Catch: java.lang.Exception -> L75
                android.content.Intent r0 = (android.content.Intent) r0     // Catch: java.lang.Exception -> L75
                r3.startActivity(r0)     // Catch: java.lang.Exception -> L75
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                androidx.viewpager.widget.ViewPager r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.access$000(r3)     // Catch: java.lang.Exception -> L75
                int r3 = r3.getCurrentItem()     // Catch: java.lang.Exception -> L75
                goto L1e
            L63:
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                com.felicanetworks.mfm.main.view.views.BannerLayout$OnBannerClickListener r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.access$600(r3)     // Catch: java.lang.Exception -> L75
                if (r3 == 0) goto L7f
                com.felicanetworks.mfm.main.view.views.BannerLayout r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.this     // Catch: java.lang.Exception -> L75
                com.felicanetworks.mfm.main.view.views.BannerLayout$OnBannerClickListener r3 = com.felicanetworks.mfm.main.view.views.BannerLayout.access$600(r3)     // Catch: java.lang.Exception -> L75
                r3.onClick(r0)     // Catch: java.lang.Exception -> L75
                goto L7f
            L75:
                r3 = move-exception
                com.felicanetworks.mfm.main.view.views.BannerLayout r0 = com.felicanetworks.mfm.main.view.views.BannerLayout.this
                com.felicanetworks.mfm.main.view.views.BannerLayout$OnDetectErrorListener r0 = com.felicanetworks.mfm.main.view.views.BannerLayout.access$700(r0)
                r0.onError(r3)
            L7f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.view.views.BannerLayout.BannerInfoAdapter.startUrl(int):void");
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.mBitmapList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        private void setAspectRatio(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int iCalculateEuclideanAlgorithm = calculateEuclideanAlgorithm(width, height);
            if (iCalculateEuclideanAlgorithm > 1) {
                BannerLayout.this.mWidthAspect = width / iCalculateEuclideanAlgorithm;
                BannerLayout.this.mHeightAspect = height / iCalculateEuclideanAlgorithm;
            }
        }

        private int calculateEuclideanAlgorithm(int i, int i2) {
            int i3 = i % i2;
            return i3 != 0 ? calculateEuclideanAlgorithm(i2, i3) : i2;
        }
    }

    static class RoundImageView extends AppCompatImageView {
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
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            this.mBounds = new Rect(0, 0, i, i2);
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
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Handler handler;
        super.onLayout(z, i, i2, i3, i4);
        this.mFirstLayout = false;
        Runnable runnable = this.mPendingInitBanner;
        if (runnable == null || (handler = this.mInitializeHandler) == null) {
            return;
        }
        handler.post(runnable);
        this.mPendingInitBanner = null;
    }
}
