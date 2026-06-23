package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BannerLayoutCardDetail extends FrameLayout {
    private static final int BANNER_MARGIN_SIZE = 18;
    private static final int VISIBLE_DOT_COUNT = 5;
    private BannerInfoAdapter mAdapter;
    private ViewPager mBannerViewPager;
    private LimitableIndicatorLayout mIndicatorLayout;
    private DisplayMetrics mMetrics;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public BannerLayoutCardDetail(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayoutCardDetail.1
            private int state = 0;

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                BannerLayoutCardDetail.this.mBannerViewPager.setCurrentItem(i, false);
                BannerLayoutCardDetail.this.mIndicatorLayout.onPageChange(i);
                if (this.state == 2) {
                    this.state = 0;
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                this.state = i;
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                if (this.state != 0) {
                    BannerLayoutCardDetail.this.mIndicatorLayout.onPageChange(i + (f > 0.5f ? 1 : 0));
                }
            }
        };
        this.mAdapter = new BannerInfoAdapter();
        this.mMetrics = context.getResources().getDisplayMetrics();
    }

    public void setIndicator(LimitableIndicatorLayout limitableIndicatorLayout) {
        this.mIndicatorLayout = limitableIndicatorLayout;
        limitableIndicatorLayout.setVisibleDotCounts(5);
    }

    public void setBanner(ViewPager viewPager) {
        this.mIndicatorLayout.setNoOfPages(this.mAdapter.getCount());
        this.mBannerViewPager = viewPager;
        InitializeBanner();
    }

    private void InitializeBanner() {
        this.mBannerViewPager.setAdapter(this.mAdapter);
        this.mBannerViewPager.addOnPageChangeListener(this.mOnPageChangeListener);
        this.mBannerViewPager.setPageMargin((int) (this.mMetrics.density * 18.0f));
        setPadding();
    }

    private void setPadding() {
        this.mBannerViewPager.setPadding((int) ((this.mMetrics.widthPixels - (this.mMetrics.density * 166.0f)) / 2.0f), 0, (int) ((this.mMetrics.widthPixels - (this.mMetrics.density * 166.0f)) / 2.0f), 0);
    }

    public void addItem(String str, String str2, String str3, Bitmap bitmap, MyCardInfoAgent.CardStatusForCardDetailUI cardStatusForCardDetailUI) {
        this.mAdapter.add(str, str2, str3, bitmap, cardStatusForCardDetailUI);
    }

    public void clearItem() {
        this.mAdapter.clearItem();
    }

    public void notifyDataSetChanged() {
        this.mAdapter.notifyDataSetChanged();
    }

    public void setCurrentItem(int i) {
        this.mBannerViewPager.setCurrentItem(i);
        this.mIndicatorLayout.onPageChange(i);
    }

    public void update(String str, String str2, Bitmap bitmap) {
        BannerInfoAdapter bannerInfoAdapter = this.mAdapter;
        if (bannerInfoAdapter != null) {
            bannerInfoAdapter.update(str, str2, bitmap);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void updateMetrics() {
        if (this.mAdapter == null || this.mBannerViewPager == null) {
            return;
        }
        this.mMetrics = getContext().getResources().getDisplayMetrics();
        setPadding();
        this.mAdapter.notifyDataSetChanged();
    }

    public class BannerInfoAdapter extends PagerAdapter {
        List<BannerInfo> mBannerInfoList = new ArrayList();

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        public BannerInfoAdapter() {
        }

        public void add(String str, String str2, String str3, Bitmap bitmap, MyCardInfoAgent.CardStatusForCardDetailUI cardStatusForCardDetailUI) {
            this.mBannerInfoList.add(new BannerInfo(str, str2, str3, cardStatusForCardDetailUI, bitmap));
        }

        public void clearItem() {
            this.mBannerInfoList.clear();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_detail_card_view_page, viewGroup, false);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_page_image);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_page_text);
            imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.felicanetworks.mfm.main.view.views.BannerLayoutCardDetail.BannerInfoAdapter.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ((int) BannerLayoutCardDetail.this.getContext().getResources().getDisplayMetrics().density) * 6);
                }
            });
            imageView.setClipToOutline(true);
            List<BannerInfo> list = this.mBannerInfoList;
            if (list != null && i >= 0 && i < list.size()) {
                BannerInfo bannerInfo = this.mBannerInfoList.get(i);
                setDisplayCardStatus(viewInflate, bannerInfo);
                if (bannerInfo.mBitmap == null) {
                    textView.setVisibility(0);
                    textView.setText(bannerInfo.mServiceName);
                } else {
                    imageView.setVisibility(0);
                    imageView.setImageBitmap(bannerInfo.mBitmap);
                }
            }
            viewGroup.addView(viewInflate);
            return viewInflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            List<BannerInfo> list = this.mBannerInfoList;
            if (list != null) {
                return list.size();
            }
            return -1;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        public void update(String str, String str2, Bitmap bitmap) {
            int positon;
            if (this.mBannerInfoList == null || (positon = getPositon(str)) == -1) {
                return;
            }
            this.mBannerInfoList.get(positon).mServiceName = str2;
            this.mBannerInfoList.get(positon).mBitmap = bitmap;
        }

        private int getPositon(String str) {
            if (this.mBannerInfoList != null) {
                for (int i = 0; i < this.mBannerInfoList.size(); i++) {
                    if (this.mBannerInfoList.get(i).mUnionId.equals(str)) {
                        return i;
                    }
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
            private String mServiceId;
            private String mServiceName;
            private MyCardInfoAgent.CardStatusForCardDetailUI mStatus;
            private String mUnionId;

            public BannerInfo(String str, String str2, String str3, MyCardInfoAgent.CardStatusForCardDetailUI cardStatusForCardDetailUI, Bitmap bitmap) {
                this.mServiceId = str;
                this.mServiceName = str2;
                this.mUnionId = str3;
                this.mStatus = cardStatusForCardDetailUI;
                this.mBitmap = bitmap;
            }
        }
    }
}
