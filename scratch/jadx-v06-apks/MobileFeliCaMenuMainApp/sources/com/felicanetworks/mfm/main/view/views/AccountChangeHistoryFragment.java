package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
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

/* JADX INFO: loaded from: classes.dex */
public class AccountChangeHistoryFragment extends BaseFragment {
    private LayoutInflater mInflater;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_20_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_account_change_history, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof AccountChangeHistoryDrawStructure)) {
            this.mInflater = layoutInflater;
            AccountChangeHistoryListView accountChangeHistoryListView = (AccountChangeHistoryListView) viewInflate.findViewById(R.id.lv_account_list);
            accountChangeHistoryListView.setFocusableInTouchMode(false);
            List<AccountChangeHistoryInfo> dataList = ((AccountChangeHistoryDrawStructure) structure).getDataList(getContext());
            if (dataList.isEmpty()) {
                accountChangeHistoryListView.addHeaderView((LinearLayout) layoutInflater.inflate(R.layout.list_item_account_change_history_null, (ViewGroup) null), null, false);
            }
            accountChangeHistoryListView.setAdapter((ListAdapter) new AccountListAdapter(getActivity(), dataList));
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_account);
            textView.setText(createSpannableString(getString(R.string.guide_account_change_description) + getString(R.string.text_account_change_guidance_page_link), getString(R.string.text_account_change_guidance_page_link), (String) Sg.getValue(Sg.Key.NET_ACCOUNT_CHANGE_GUIDANCE_PAGE_URL)));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            return viewInflate;
        }
        return viewInflate;
    }

    private SpannableString createSpannableString(String str, String str2, final String str3) {
        int iEnd;
        SpannableString spannableString = new SpannableString(str);
        Matcher matcher = Pattern.compile(str2).matcher(str);
        int iStart = 0;
        if (matcher.find()) {
            iStart = matcher.start();
            iEnd = matcher.end();
        } else {
            iEnd = 0;
        }
        spannableString.setSpan(new ClickableSpan() { // from class: com.felicanetworks.mfm.main.view.views.AccountChangeHistoryFragment.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                try {
                    AccountChangeHistoryFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str3)));
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

        public AccountListAdapter(Context context, List<AccountChangeHistoryInfo> list) {
            AccountChangeHistoryFragment.this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mAccountChangeHistoryList = list;
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
            } catch (Exception e) {
                AccountChangeHistoryFragment.this.notifyException(e);
            }
            return view;
        }

        private void updateListItem(AccountChangeHistoryViewHolder accountChangeHistoryViewHolder, AccountChangeHistoryInfo accountChangeHistoryInfo) {
            accountChangeHistoryViewHolder.mAccount.setText("");
            accountChangeHistoryViewHolder.mChangeDate.setText("");
            accountChangeHistoryViewHolder.mChangeAppId.setText("");
            String account = accountChangeHistoryInfo.getAccount();
            if (account != null && !account.isEmpty()) {
                accountChangeHistoryViewHolder.mAccount.setText(account);
            }
            String changeDate = accountChangeHistoryInfo.getChangeDate();
            if (changeDate != null && !changeDate.isEmpty()) {
                accountChangeHistoryViewHolder.mChangeDate.setText(changeDate);
            }
            String changeAppId = accountChangeHistoryInfo.getChangeAppId();
            if (changeAppId == null || changeAppId.isEmpty()) {
                return;
            }
            if (changeAppId.equals("01")) {
                accountChangeHistoryViewHolder.mChangeAppId.setText(R.string.text_change_app_name_osaifu);
            } else {
                accountChangeHistoryViewHolder.mChangeAppId.setText(R.string.text_change_app_name_googlepay);
            }
        }
    }
}
