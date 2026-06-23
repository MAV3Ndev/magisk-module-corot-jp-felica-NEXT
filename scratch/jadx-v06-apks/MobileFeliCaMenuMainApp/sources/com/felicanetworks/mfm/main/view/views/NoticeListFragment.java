package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.NoticeListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.views.BannerLayout;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NoticeListFragment extends BaseFragment {
    private static final String DATE_FORMAT = "yyyy.M.d";
    NoticeListView mNoticeList = null;
    private BannerLayout mBannerLayout = null;
    private LinearLayout mWarningMessage = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_04_01, new Object[0]);
        final View viewInflate = layoutInflater.inflate(R.layout.fragment_notice, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof NoticeListDrawStructure)) {
            NoticeListDrawStructure noticeListDrawStructure = (NoticeListDrawStructure) structure;
            NoticeListView noticeListView = (NoticeListView) viewInflate.findViewById(R.id.lv_notice_list);
            this.mNoticeList = noticeListView;
            noticeListView.setFocusableInTouchMode(false);
            BannerLayout bannerLayout = (BannerLayout) layoutInflater.inflate(R.layout.banner_notice_layout, (ViewGroup) null);
            this.mBannerLayout = bannerLayout;
            this.mNoticeList.addHeaderView(bannerLayout, null, true);
            List<NoticeInfoAgent> infoList = noticeListDrawStructure.getNoticeFunc().getInfoList();
            if (infoList.isEmpty()) {
                LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_notice_warning, (ViewGroup) null);
                this.mWarningMessage = linearLayout;
                ((TextView) linearLayout.findViewById(R.id.tv_warning_message_text)).setText(R.string.warning_noticelist_empty);
                this.mNoticeList.addHeaderView(this.mWarningMessage, null, false);
            }
            this.mNoticeList.setAdapter((ListAdapter) new NoticeListAdapter(getActivity(), infoList));
            this.mNoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    try {
                        NoticeInfoAgent noticeInfoAgent = (NoticeInfoAgent) ((ListView) adapterView).getItemAtPosition(i);
                        Structure structure2 = NoticeListFragment.this.getStructure();
                        if (structure2 instanceof NoticeListDrawStructure) {
                            ((NoticeListDrawStructure) structure2).actSelect(noticeInfoAgent);
                        }
                    } catch (Exception e2) {
                        NoticeListFragment.this.notifyException(e2);
                    }
                }
            });
            noticeListDrawStructure.getNoticeFunc().orderBanner(new NoticeFuncAgent.OrderBannerListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2
                @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.OrderBannerListener
                public void onComplete(final List<BannerInfoAgent> list) {
                    try {
                        for (BannerInfoAgent bannerInfoAgent : list) {
                            NoticeListFragment.this.mBannerLayout.setItem(bannerInfoAgent.getImage(), bannerInfoAgent.getDefaultIntent());
                        }
                        NoticeListFragment.this.mBannerLayout.setBanner((ViewPager) viewInflate.findViewById(R.id.vp_burner));
                        NoticeListFragment.this.mBannerLayout.setIndicatorLayout((IndicatorLayout) viewInflate.findViewById(R.id.vg_indicator));
                        NoticeListFragment.this.mBannerLayout.setOnDetectErrorListener(new BannerLayout.OnDetectErrorListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2.1
                            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnDetectErrorListener
                            public void onError(Exception exc) {
                                NoticeListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                            }
                        });
                        NoticeListFragment.this.mBannerLayout.setOnBannerClickListener(new BannerLayout.OnBannerClickListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2.2
                            @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnBannerClickListener
                            public void onClick(int i) {
                                try {
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_BANNER_NOTICE, list.get(i));
                                } catch (Exception unused) {
                                }
                            }
                        });
                    } catch (Exception e2) {
                        NoticeListFragment.this.notifyException(e2);
                    }
                }
            });
            this.mNoticeList.setBannerLayout(this.mBannerLayout);
            return viewInflate;
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        try {
            super.onResume();
            Structure structure = getStructure();
            if (structure instanceof NoticeListDrawStructure) {
                registerListener((NoticeListDrawStructure) structure);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    ListAdapter adapter = this.mNoticeList.getAdapter();
                    if (adapter instanceof HeaderViewListAdapter) {
                        NoticeListAdapter noticeListAdapter = (NoticeListAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                        noticeListAdapter.mNoticeList = ((NoticeListDrawStructure) structure).getNoticeFunc().getInfoList();
                        noticeListAdapter.notifyDataSetChanged();
                    }
                }
            }
            if (this.mBannerLayout != null) {
                this.mBannerLayout.setAutoScrollTimer();
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        BannerLayout bannerLayout = this.mBannerLayout;
        if (bannerLayout != null) {
            bannerLayout.cancelAutoScrollTimer();
        }
    }

    private void registerListener(final NoticeListDrawStructure noticeListDrawStructure) {
        noticeListDrawStructure.getNoticeFunc().registerChangeDataListener(new NoticeFuncAgent.ChangeDataListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.3
            @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.ChangeDataListener
            public void onNewArrival(NoticeInfoAgent noticeInfoAgent, boolean z) {
                if (NoticeListFragment.this.getActivity() == null || NoticeListFragment.this.getActivity().isFinishing()) {
                    return;
                }
                ListAdapter adapter = NoticeListFragment.this.mNoticeList.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    NoticeListAdapter noticeListAdapter = (NoticeListAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                    noticeListAdapter.mNoticeList = noticeListDrawStructure.getNoticeFunc().getInfoList();
                    noticeListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class NoticeListAdapter extends BaseAdapter {
        private static final String INITIAL_NOTIFICATION_ID = "0";
        private static final String INITIAL_NOTIFICATION_ID1 = "1";
        private LayoutInflater mInflater;
        private List<NoticeInfoAgent> mNoticeList;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public class NoticeListViewHolder {
            public ImageView mNewIcon;
            public ImageView mNoticeIcon;
            public int mPosition;
            public TextView mSendDate;
            public TextView mTitle;

            public NoticeListViewHolder(View view, int i) {
                this.mPosition = i;
                this.mNoticeIcon = (ImageView) view.findViewById(R.id.iv_service_icon);
                this.mNewIcon = (ImageView) view.findViewById(R.id.iv_new_notice_image);
                this.mSendDate = (TextView) view.findViewById(R.id.tv_send_date);
                this.mTitle = (TextView) view.findViewById(R.id.tv_title_text);
            }
        }

        public NoticeListAdapter(Context context, List<NoticeInfoAgent> list) {
            this.mInflater = null;
            this.mNoticeList = null;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mNoticeList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mNoticeList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mNoticeList.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            NoticeListViewHolder noticeListViewHolder;
            try {
                NoticeInfoAgent noticeInfoAgent = this.mNoticeList.get(i);
                if (view == null) {
                    view = this.mInflater.inflate(R.layout.list_item_notice, viewGroup, false);
                    noticeListViewHolder = new NoticeListViewHolder(view, i);
                    view.setTag(noticeListViewHolder);
                } else {
                    noticeListViewHolder = (NoticeListViewHolder) view.getTag();
                    noticeListViewHolder.mPosition = i;
                }
                updateListItem(noticeListViewHolder, noticeInfoAgent);
            } catch (Exception e) {
                NoticeListFragment.this.notifyException(e);
            }
            return view;
        }

        private void updateListItem(final NoticeListViewHolder noticeListViewHolder, final NoticeInfoAgent noticeInfoAgent) {
            Date sendDate;
            noticeListViewHolder.mNoticeIcon.setImageResource(0);
            noticeListViewHolder.mSendDate.setText("");
            noticeListViewHolder.mTitle.setText("");
            noticeInfoAgent.orderIconImg(new NoticeInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.NoticeListAdapter.1
                @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.OrderImgListener
                public void onComplete(Bitmap bitmap) {
                    if (NoticeListFragment.this.getActivity() == null || NoticeListFragment.this.getActivity().isFinishing() || !noticeInfoAgent.getId().equals(((NoticeInfoAgent) NoticeListAdapter.this.getItem(noticeListViewHolder.mPosition)).getId()) || bitmap == null) {
                        return;
                    }
                    noticeListViewHolder.mNoticeIcon.setImageBitmap(bitmap);
                }
            });
            if (noticeInfoAgent.isRead()) {
                noticeListViewHolder.mNewIcon.setVisibility(4);
            } else {
                noticeListViewHolder.mNewIcon.setVisibility(0);
            }
            if (!"0".equals(noticeInfoAgent.getId()) && !"1".equals(noticeInfoAgent.getId()) && (sendDate = noticeInfoAgent.getSendDate()) != null) {
                String string = DateFormat.format(NoticeListFragment.DATE_FORMAT, sendDate).toString();
                if (!string.isEmpty()) {
                    noticeListViewHolder.mSendDate.setText(string);
                }
            }
            String title = noticeInfoAgent.getTitle();
            if (title == null || title.isEmpty()) {
                return;
            }
            noticeListViewHolder.mTitle.setText(title);
        }
    }
}
