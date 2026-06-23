package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class BannerLayoutCardDetail extends FrameLayout {
    private static final int BANNER_MARGIN_SIZE = 0;
    private static final int BANNER_SIZE = 188;
    private static final int VISIBLE_DOT_COUNT = 5;
    private int mActualWidth;
    private final BannerInfoAdapter mAdapter;
    private ViewPager2 mBannerViewPager;
    private LimitableIndicatorLayout mIndicatorLayout;
    private DisplayMetrics mMetrics;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback;

    public BannerLayoutCardDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayoutCardDetail.2
            private int state = 0;

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                BannerLayoutCardDetail.this.mIndicatorLayout.onPageChange(position);
                if (this.state == 2) {
                    this.state = 0;
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                this.state = state;
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (this.state != 0) {
                    BannerLayoutCardDetail.this.mIndicatorLayout.onPageChange(position + (positionOffset > 0.5f ? 1 : 0));
                }
            }
        };
        this.mAdapter = new BannerInfoAdapter();
        this.mMetrics = context.getResources().getDisplayMetrics();
        this.mActualWidth = getActualWidth();
    }

    public void setIndicator(LimitableIndicatorLayout instaDotView) {
        this.mIndicatorLayout = instaDotView;
        instaDotView.setVisibleDotCounts(5);
    }

    public void setBanner(final ViewPager2 banner) {
        this.mIndicatorLayout.setNoOfPages(this.mAdapter.getItemCount());
        this.mBannerViewPager = banner;
        InitializeBanner();
    }

    private void InitializeBanner() {
        this.mBannerViewPager.setAdapter(this.mAdapter);
        this.mBannerViewPager.registerOnPageChangeCallback(this.mOnPageChangeCallback);
        this.mBannerViewPager.setPageTransformer(new MarginPageTransformer((int) (this.mMetrics.density * 0.0f)));
        this.mBannerViewPager.setOffscreenPageLimit(2);
        this.mBannerViewPager.setPageTransformer(new ViewPager2.PageTransformer() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayoutCardDetail.1
            @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
            public void transformPage(View page, float position) {
                page.setTranslationX((-position) * ((BannerLayoutCardDetail.this.mActualWidth - (BannerLayoutCardDetail.this.mMetrics.density * 188.0f)) / 2.0f) * 2.0f);
            }
        });
    }

    public void addItem(String name, String unionID, Bitmap image, MyCardInfoAgent.CardStatusForCardDetailUI status) {
        this.mAdapter.add(name, unionID, image, status);
    }

    public void clearItem() {
        this.mAdapter.clearItem();
    }

    public void notifyDataSetChanged() {
        this.mAdapter.notifyDataSetChanged();
    }

    public void setCurrentItem(int position) {
        this.mBannerViewPager.setCurrentItem(position, false);
        this.mIndicatorLayout.onPageChange(position);
    }

    public void update(String id, String name, Bitmap bitmap) {
        BannerInfoAdapter bannerInfoAdapter = this.mAdapter;
        if (bannerInfoAdapter != null) {
            bannerInfoAdapter.update(id, name, bitmap);
        }
    }

    public void updateMetrics() {
        if (this.mAdapter == null || this.mBannerViewPager == null) {
            return;
        }
        this.mMetrics = getContext().getResources().getDisplayMetrics();
        this.mActualWidth = getActualWidth();
        int currentItem = this.mBannerViewPager.getCurrentItem();
        InitializeBanner();
        setCurrentItem(currentItem);
        notifyDataSetChanged();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mTextView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            this.mImageView = (ImageView) itemView.findViewById(R.id.iv_page_image);
            this.mTextView = (TextView) itemView.findViewById(R.id.tv_page_text);
        }

        public ImageView getImageView() {
            return this.mImageView;
        }

        public TextView getTextView() {
            return this.mTextView;
        }
    }

    public class BannerInfoAdapter extends RecyclerView.Adapter<BannerViewHolder> {
        private final List<BannerInfo> mBannerInfoList = new ArrayList();

        public BannerInfoAdapter() {
        }

        /* JADX DEBUG: Method merged with bridge method: onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder; */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_detail_card_view_page, parent, false);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewInflate.getLayoutParams();
            marginLayoutParams.setMargins((int) ((BannerLayoutCardDetail.this.mActualWidth - (BannerLayoutCardDetail.this.mMetrics.density * 188.0f)) / 2.0f), marginLayoutParams.topMargin, (int) ((BannerLayoutCardDetail.this.mActualWidth - (BannerLayoutCardDetail.this.mMetrics.density * 188.0f)) / 2.0f), marginLayoutParams.bottomMargin);
            viewInflate.setLayoutParams(marginLayoutParams);
            return new BannerViewHolder(viewInflate);
        }

        /* JADX DEBUG: Method merged with bridge method: onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(BannerViewHolder holder, int position) {
            ImageView imageView = holder.getImageView();
            TextView textView = holder.getTextView();
            imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayoutCardDetail.BannerInfoAdapter.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ((int) BannerLayoutCardDetail.this.getContext().getResources().getDisplayMetrics().density) * 6);
                }
            });
            imageView.setClipToOutline(true);
            if (position < 0 || position >= this.mBannerInfoList.size()) {
                return;
            }
            BannerInfo bannerInfo = this.mBannerInfoList.get(position);
            setDisplayCardStatus(holder.itemView, bannerInfo);
            if (bannerInfo.mBitmap == null) {
                textView.setVisibility(0);
                textView.setText(bannerInfo.mServiceName);
            } else {
                imageView.setVisibility(0);
                imageView.setImageBitmap(bannerInfo.mBitmap);
            }
        }

        public void add(String serviceName, String unionId, Bitmap item, MyCardInfoAgent.CardStatusForCardDetailUI status) {
            this.mBannerInfoList.add(new BannerInfo(serviceName, unionId, status, item));
        }

        public void clearItem() {
            this.mBannerInfoList.clear();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mBannerInfoList.size();
        }

        public void update(String id, String name, Bitmap bitmap) {
            int position = getPosition(id);
            if (position != -1) {
                this.mBannerInfoList.get(position).mServiceName = name;
                this.mBannerInfoList.get(position).mBitmap = bitmap;
                notifyItemChanged(position);
            }
        }

        private int getPosition(String id) {
            for (int i = 0; i < this.mBannerInfoList.size(); i++) {
                if (this.mBannerInfoList.get(i).mUnionId.equals(id)) {
                    return i;
                }
            }
            return -1;
        }

        private void setDisplayCardStatus(View view, BannerInfo bannerInfo) {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_active_layout);
            View viewFindViewById = view.findViewById(R.id.view_inactive_layout);
            LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.ll_not_exist_layout);
            LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.ll_recovery_layout);
            LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.ll_not_applicable_layout);
            linearLayout.setVisibility(8);
            viewFindViewById.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            linearLayout4.setVisibility(8);
            MyCardInfoAgent.CardStatusForCardDetailUI cardStatusForCardDetailUI = bannerInfo.mStatus;
            if (MyCardInfoAgent.CardStatusForCardDetailUI.ACTIVE == cardStatusForCardDetailUI) {
                linearLayout.setVisibility(0);
                return;
            }
            if (MyCardInfoAgent.CardStatusForCardDetailUI.INACTIVE == cardStatusForCardDetailUI) {
                viewFindViewById.setVisibility(0);
                return;
            }
            if (MyCardInfoAgent.CardStatusForCardDetailUI.NOT_EXIST == cardStatusForCardDetailUI) {
                linearLayout2.setVisibility(0);
            } else if (MyCardInfoAgent.CardStatusForCardDetailUI.RECOVERY == cardStatusForCardDetailUI) {
                linearLayout3.setVisibility(0);
            } else if (MyCardInfoAgent.CardStatusForCardDetailUI.UNPROCESSED == cardStatusForCardDetailUI) {
                linearLayout4.setVisibility(0);
            }
        }

        private class BannerInfo {
            private Bitmap mBitmap;
            private String mServiceName;
            private final MyCardInfoAgent.CardStatusForCardDetailUI mStatus;
            private final String mUnionId;

            public BannerInfo(String serviceName, String unionId, MyCardInfoAgent.CardStatusForCardDetailUI state, Bitmap bitmap) {
                this.mServiceName = serviceName;
                this.mUnionId = unionId;
                this.mStatus = state;
                this.mBitmap = bitmap;
            }
        }
    }

    private int getActualWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics currentWindowMetrics = windowManager.getCurrentWindowMetrics();
            Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            return currentWindowMetrics.getBounds().width() - (insetsIgnoringVisibility.left + insetsIgnoringVisibility.right);
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }
}
