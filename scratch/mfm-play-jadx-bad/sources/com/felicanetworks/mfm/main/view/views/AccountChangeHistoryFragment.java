package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.AccountChangeHistoryInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.structure.AccountChangeHistoryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class AccountChangeHistoryFragment extends BaseFragment {
    private LayoutInflater mInflater;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_20_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_account_change_history, container, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof AccountChangeHistoryDrawStructure)) {
                this.mInflater = inflater;
                AccountChangeHistoryListView accountChangeHistoryListView = (AccountChangeHistoryListView) viewInflate.findViewById(R.id.lv_account_list);
                accountChangeHistoryListView.setFocusableInTouchMode(false);
                List<AccountChangeHistoryInfo> dataList = ((AccountChangeHistoryDrawStructure) structure).getDataList(getContext());
                if (dataList.isEmpty()) {
                    accountChangeHistoryListView.addHeaderView((LinearLayout) inflater.inflate(R.layout.list_item_account_change_history_null, (ViewGroup) null), null, false);
                }
                accountChangeHistoryListView.setAdapter((ListAdapter) new AccountListAdapter(getActivity(), dataList));
                TextView textView = (TextView) viewInflate.findViewById(R.id.tv_account);
                textView.setText(createSpannableString(getString(R.string.guide_account_change_description) + getString(R.string.text_account_change_guidance_page_link), getString(R.string.text_account_change_guidance_page_link), (String) Sg.getValue(Sg.Key.NET_ACCOUNT_CHANGE_GUIDANCE_PAGE_URL)));
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                adjustViewToNavigationBar(viewInflate.findViewById(R.id.ll_guide_account_change), false);
            }
            return viewInflate;
        } catch (Exception e) {
            notifyException(e);
            return viewInflate;
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = getView();
        if (view != null) {
            adjustViewToNavigationBar(view.findViewById(R.id.ll_guide_account_change), false);
        }
    }

    private SpannableString createSpannableString(String message, String link, final String url) {
        int iStart;
        int iEnd;
        SpannableString spannableString = new SpannableString(message);
        Matcher matcher = Pattern.compile(link).matcher(message);
        if (matcher.find()) {
            iStart = matcher.start();
            iEnd = matcher.end();
        } else {
            iStart = 0;
            iEnd = 0;
        }
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfm.main.view.views.AccountChangeHistoryFragment.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View textView) {
                try {
                    AccountChangeHistoryFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception unused) {
                    AccountChangeHistoryFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                }
            }
        }, iStart, iEnd, 18);
        return spannableString;
    }

    private class AccountListAdapter extends BaseAdapter {
        private List<AccountChangeHistoryInfo> mAccountChangeHistoryList;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public class AccountChangeHistoryViewHolder {
            public TextView mAccount;
            public TextView mChangeAppId;
            public TextView mChangeDate;

            public AccountChangeHistoryViewHolder(View view) {
                this.mAccount = (TextView) view.findViewById(R.id.tv_account_change_history);
                this.mChangeDate = (TextView) view.findViewById(R.id.tv_change_date);
                this.mChangeAppId = (TextView) view.findViewById(R.id.tv_change_app);
            }
        }

        public AccountListAdapter(Context context, List<AccountChangeHistoryInfo> dataList) {
            AccountChangeHistoryFragment.this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mAccountChangeHistoryList = dataList;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mAccountChangeHistoryList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            AccountChangeHistoryViewHolder accountChangeHistoryViewHolder;
            try {
                AccountChangeHistoryInfo accountChangeHistoryInfo = this.mAccountChangeHistoryList.get(i);
                if (view == null) {
                    view = AccountChangeHistoryFragment.this.mInflater.inflate(R.layout.list_item_account_change_history, viewGroup, false);
                    accountChangeHistoryViewHolder = new AccountChangeHistoryViewHolder(view);
                    view.setTag(accountChangeHistoryViewHolder);
                } else {
                    accountChangeHistoryViewHolder = (AccountChangeHistoryViewHolder) view.getTag();
                }
                updateListItem(accountChangeHistoryViewHolder, accountChangeHistoryInfo);
                return view;
            } catch (Exception e) {
                AccountChangeHistoryFragment.this.notifyException(e);
                return view;
            }
        }

        private void updateListItem(final AccountChangeHistoryViewHolder viewHolder, final AccountChangeHistoryInfo info) {
            viewHolder.mAccount.setText("");
            viewHolder.mChangeDate.setText("");
            viewHolder.mChangeAppId.setText("");
            String account = info.getAccount();
            if (account != null && !account.isEmpty()) {
                viewHolder.mAccount.setText(account);
            }
            String changeDate = info.getChangeDate();
            if (changeDate != null && !changeDate.isEmpty()) {
                viewHolder.mChangeDate.setText(changeDate);
            }
            String changeAppId = info.getChangeAppId();
            if (changeAppId == null || changeAppId.isEmpty()) {
                return;
            }
            if (changeAppId.equals("01")) {
                viewHolder.mChangeAppId.setText(R.string.text_change_app_name_osaifu);
            } else if (changeAppId.equals("02")) {
                viewHolder.mChangeAppId.setText(R.string.text_change_app_name_google_payments_app);
            } else {
                viewHolder.mChangeAppId.setText(R.string.text_change_app_name_other_app);
            }
        }
    }
}
