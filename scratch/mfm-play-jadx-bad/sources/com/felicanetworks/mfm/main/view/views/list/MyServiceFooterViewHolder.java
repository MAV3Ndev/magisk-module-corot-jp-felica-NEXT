package com.felicanetworks.mfm.main.view.views.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.view.views.BannerLayout;
import com.felicanetworks.mfm.main.view.views.IndicatorLayout;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceFooterViewHolder extends MyServiceViewHolder<Void> implements CentralFuncAgent.OrderBannerListener {
    private BannerLayout bannerFrame;
    private final IndicatorLayout bannerIndicator;
    private final ViewPager2 bannerPager;
    private final CentralFuncAgent central;
    private final View goToRecommend;
    private final OnClickFooterListener listener;

    interface OnClickFooterListener {
        void onClickBanner(BannerInfoAgent banner);

        void onClickBannerError(Exception e);

        void onClickGoToRecommend();
    }

    /* JADX DEBUG: Method merged with bridge method: bind(Ljava/lang/Object;)V */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void bind(Void data) {
    }

    MyServiceFooterViewHolder(ViewGroup parent, CentralDrawStructure structure, final OnClickFooterListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_myservice_footer, parent, false), structure);
        this.listener = listener;
        View viewFindViewById = this.itemView.findViewById(R.id.goToRecommend);
        this.goToRecommend = viewFindViewById;
        this.bannerPager = (ViewPager2) this.itemView.findViewById(R.id.bannerPager);
        this.bannerIndicator = (IndicatorLayout) this.itemView.findViewById(R.id.bannerIndicator);
        CentralFuncAgent centralFunc = structure.getCentralFunc();
        this.central = centralFunc;
        viewFindViewById.setVisibility(centralFunc.getRecommendInfoList().isEmpty() ? 8 : 0);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                listener.onClickGoToRecommend();
            }
        });
        centralFunc.orderBanner(this);
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
            this.bannerFrame.setItem(bannerInfoAgent.getImage(), R.string.voice_read_banner_image, bannerInfoAgent.getDefaultIntent());
        }
        this.bannerFrame.setHorizontalMargin(R.dimen.myservice_banner_horizontal_margin);
        this.bannerFrame.setBanner(this.bannerPager);
        this.bannerFrame.setIndicatorLayout(this.bannerIndicator);
        this.bannerFrame.setOnDetectErrorListener(new BannerLayout.OnDetectErrorListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.2
            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnDetectErrorListener
            public void onError(Exception e) {
                MyServiceFooterViewHolder.this.listener.onClickBannerError(e);
            }
        });
        this.bannerFrame.setOnBannerClickListener(new BannerLayout.OnBannerClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder.3
            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnBannerClickListener
            public void onClick(int position) {
                MyServiceFooterViewHolder.this.listener.onClickBanner((BannerInfoAgent) list.get(position));
            }
        });
        this.bannerFrame.setAutoScrollTimer();
    }
}
