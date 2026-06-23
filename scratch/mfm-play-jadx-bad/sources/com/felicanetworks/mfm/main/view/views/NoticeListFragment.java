package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.res.Configuration;
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
import androidx.viewpager2.widget.ViewPager2;
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

/* JADX INFO: loaded from: classes3.dex */
public class NoticeListFragment extends BaseFragment {
    private static final String DATE_FORMAT = "yyyy.M.d";
    NoticeListView mNoticeList = null;
    private BannerLayout mBannerLayout = null;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_04_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_notice, container, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof NoticeListDrawStructure)) {
                NoticeListDrawStructure noticeListDrawStructure = (NoticeListDrawStructure) structure;
                NoticeListView noticeListView = (NoticeListView) viewInflate.findViewById(R.id.lv_notice_list);
                this.mNoticeList = noticeListView;
                noticeListView.setFocusableInTouchMode(false);
                BannerLayout bannerLayout = (BannerLayout) inflater.inflate(R.layout.banner_notice_layout, (ViewGroup) null);
                this.mBannerLayout = bannerLayout;
                this.mNoticeList.addHeaderView(bannerLayout, null, true);
                List<NoticeInfoAgent> infoList = noticeListDrawStructure.getNoticeFunc().getInfoList();
                if (infoList.isEmpty()) {
                    LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.list_item_notice_warning, (ViewGroup) null);
                    ((TextView) linearLayout.findViewById(R.id.tv_warning_message_text)).setText(R.string.warning_noticelist_empty);
                    this.mNoticeList.addHeaderView(linearLayout, null, false);
                }
                this.mNoticeList.setAdapter((ListAdapter) new NoticeListAdapter(getActivity(), infoList));
                this.mNoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            NoticeInfoAgent noticeInfoAgent = (NoticeInfoAgent) ((ListView) parent).getItemAtPosition(position);
                            Structure structure2 = NoticeListFragment.this.getStructure();
                            if (structure2 instanceof NoticeListDrawStructure) {
                                ((NoticeListDrawStructure) structure2).actSelect(noticeInfoAgent);
                            }
                        } catch (Exception e) {
                            NoticeListFragment.this.notifyException(e);
                        }
                    }
                });
                noticeListDrawStructure.getNoticeFunc().orderBanner(new NoticeFuncAgent.OrderBannerListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2
                    @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.OrderBannerListener
                    public void onComplete(final List<BannerInfoAgent> list) {
                        try {
                            for (BannerInfoAgent bannerInfoAgent : list) {
                                NoticeListFragment.this.mBannerLayout.setItem(bannerInfoAgent.getImage(), R.string.voice_read_notification_banner_image, bannerInfoAgent.getDefaultIntent());
                            }
                            NoticeListFragment.this.mBannerLayout.setHorizontalMargin(R.dimen.notice_banner_horizontal_margin);
                            NoticeListFragment.this.mBannerLayout.setBanner((ViewPager2) NoticeListFragment.this.mBannerLayout.findViewById(R.id.vp_burner));
                            NoticeListFragment.this.mBannerLayout.setIndicatorLayout((IndicatorLayout) NoticeListFragment.this.mBannerLayout.findViewById(R.id.vg_indicator));
                            NoticeListFragment.this.mBannerLayout.setOnDetectErrorListener(new BannerLayout.OnDetectErrorListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2.1
                                @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnDetectErrorListener
                                public void onError(Exception e) {
                                    NoticeListFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                                }
                            });
                            NoticeListFragment.this.mBannerLayout.setOnBannerClickListener(new BannerLayout.OnBannerClickListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.2.2
                                @Override // com.felicanetworks.mfm.main.view.views.BannerLayout.OnBannerClickListener
                                public void onClick(int position) {
                                    try {
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_BANNER_NOTICE, list.get(position));
                                    } catch (Exception unused) {
                                    }
                                }
                            });
                        } catch (Exception e) {
                            NoticeListFragment.this.notifyException(e);
                        }
                    }
                });
                this.mNoticeList.setBannerLayout(this.mBannerLayout);
                adjustViewToNavigationBar(this.mNoticeList, true);
                return viewInflate;
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToNavigationBar(this.mNoticeList, true);
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
            BannerLayout bannerLayout = this.mBannerLayout;
            if (bannerLayout != null) {
                bannerLayout.setAutoScrollTimer();
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

    private void registerListener(final NoticeListDrawStructure structure) {
        structure.getNoticeFunc().registerChangeDataListener(new NoticeFuncAgent.ChangeDataListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.3
            @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent.ChangeDataListener
            public void onNewArrival(NoticeInfoAgent data, boolean isNew) {
                if (NoticeListFragment.this.getActivity() == null || NoticeListFragment.this.getActivity().isFinishing()) {
                    return;
                }
                ListAdapter adapter = NoticeListFragment.this.mNoticeList.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    NoticeListAdapter noticeListAdapter = (NoticeListAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                    noticeListAdapter.mNoticeList = structure.getNoticeFunc().getInfoList();
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
        public long getItemId(int position) {
            return position;
        }

        public class NoticeListViewHolder {
            public ImageView mNewIcon;
            public ImageView mNoticeIcon;
            public int mPosition;
            public TextView mSendDate;
            public TextView mTitle;

            public NoticeListViewHolder(View view, int position) {
                this.mPosition = position;
                this.mNoticeIcon = (ImageView) view.findViewById(R.id.iv_service_icon);
                this.mNewIcon = (ImageView) view.findViewById(R.id.iv_new_notice_image);
                this.mSendDate = (TextView) view.findViewById(R.id.tv_send_date);
                this.mTitle = (TextView) view.findViewById(R.id.tv_title_text);
            }
        }

        public NoticeListAdapter(Context context, List<NoticeInfoAgent> dataList) {
            this.mInflater = null;
            this.mNoticeList = null;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mNoticeList = dataList;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mNoticeList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.mNoticeList.get(position);
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            NoticeListViewHolder noticeListViewHolder;
            try {
                NoticeInfoAgent noticeInfoAgent = this.mNoticeList.get(position);
                if (convertView == null) {
                    convertView = this.mInflater.inflate(R.layout.list_item_notice, parent, false);
                    noticeListViewHolder = new NoticeListViewHolder(convertView, position);
                    convertView.setTag(noticeListViewHolder);
                } else {
                    noticeListViewHolder = (NoticeListViewHolder) convertView.getTag();
                    noticeListViewHolder.mPosition = position;
                }
                updateListItem(noticeListViewHolder, noticeInfoAgent);
                return convertView;
            } catch (Exception e) {
                NoticeListFragment.this.notifyException(e);
                return convertView;
            }
        }

        private void updateListItem(final NoticeListViewHolder viewHolder, final NoticeInfoAgent info) {
            Date sendDate;
            viewHolder.mNoticeIcon.setImageResource(0);
            viewHolder.mSendDate.setText("");
            viewHolder.mTitle.setText("");
            info.orderIconImg(new NoticeInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListFragment.NoticeListAdapter.1
                @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.OrderImgListener
                public void onComplete(Bitmap data) {
                    if (NoticeListFragment.this.getActivity() == null || NoticeListFragment.this.getActivity().isFinishing() || !info.getId().equals(((NoticeInfoAgent) NoticeListAdapter.this.getItem(viewHolder.mPosition)).getId()) || data == null) {
                        return;
                    }
                    viewHolder.mNoticeIcon.setImageBitmap(data);
                }
            });
            if (info.isRead()) {
                viewHolder.mNewIcon.setVisibility(4);
            } else {
                viewHolder.mNewIcon.setVisibility(0);
            }
            if (!"0".equals(info.getId()) && !"1".equals(info.getId()) && (sendDate = info.getSendDate()) != null) {
                String string = DateFormat.format(NoticeListFragment.DATE_FORMAT, sendDate).toString();
                if (!string.isEmpty()) {
                    viewHolder.mSendDate.setText(string);
                }
            }
            String title = info.getTitle();
            if (title == null || title.isEmpty()) {
                return;
            }
            viewHolder.mTitle.setText(title);
        }
    }
}
