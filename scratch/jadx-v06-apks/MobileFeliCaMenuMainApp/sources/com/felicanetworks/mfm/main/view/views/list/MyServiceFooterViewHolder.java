package com.felicanetworks.mfm.main.view.views.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.view.views.BannerLayout;
import com.felicanetworks.mfm.main.view.views.IndicatorLayout;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyServiceFooterViewHolder extends MyServiceViewHolder<Void> implements CentralFuncAgent.OrderBannerListener {
    private BannerLayout bannerFrame;
    private final IndicatorLayout bannerIndicator;
    private final ViewPager bannerPager;
    private final CentralFuncAgent central;
    private final View goToRecommend;
    private final OnClickFooterListener listener;

    interface OnClickFooterListener {
        void onClickBanner(BannerInfoAgent bannerInfoAgent);

        void onClickBannerError(Exception exc);

        void onClickGoToRecommend();
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void bind(Void r1) {
    }

    MyServiceFooterViewHolder(ViewGroup viewGroup, CentralDrawStructure centralDrawStructure, final OnClickFooterListener onClickFooterListener) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_myservice_footer, viewGroup, false), centralDrawStructure);
        this.listener = onClickFooterListener;
        this.goToRecommend = this.itemView.findViewById(R.id.goToRecommend);
        this.bannerPager = (ViewPager) this.itemView.findViewById(R.id.bannerPager);
        this.bannerIndicator = (IndicatorLayout) this.itemView.findViewById(R.id.bannerIndicator);
        CentralFuncAgent centralFunc = centralDrawStructure.getCentralFunc();
        this.central = centralFunc;
        this.goToRecommend.setVisibility(centralFunc.getRecommendInfoList().isEmpty() ? 8 : 0);
        this.goToRecommend.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickFooterListener.onClickGoToRecommend();
            }
        });
        this.central.orderBanner(this);
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void resume() {
        BannerLayout bannerLayout = this.bannerFrame;
        if (bannerLayout != null) {
            bannerLayout.setAutoScrollTimer();
        }
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void pause() {
        BannerLayout bannerLayout = this.bannerFrame;
        if (bannerLayout != null) {
            bannerLayout.cancelAutoScrollTimer();
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.OrderBannerListener
    public void onComplete(final List<BannerInfoAgent> list) {
        this.bannerFrame = (BannerLayout) this.itemView.findViewById(R.id.bannerFrame);
        for (BannerInfoAgent bannerInfoAgent : list) {
            this.bannerFrame.setItem(bannerInfoAgent.getImage(), bannerInfoAgent.getDefaultIntent());
        }
        this.bannerFrame.setBanner(this.bannerPager);
        this.bannerFrame.setIndicatorLayout(this.bannerIndicator);
        this.bannerFrame.setOnDetectErrorListener(new BannerLayout.OnDetectErrorListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.2
            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnDetectErrorListener
            public void onError(Exception exc) {
                MyServiceFooterViewHolder.this.listener.onClickBannerError(exc);
            }
        });
        this.bannerFrame.setOnBannerClickListener(new BannerLayout.OnBannerClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.3
            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnBannerClickListener
            public void onClick(int i) {
                MyServiceFooterViewHolder.this.listener.onClickBanner((BannerInfoAgent) list.get(i));
            }
        });
        this.bannerFrame.setAutoScrollTimer();
    }
}
