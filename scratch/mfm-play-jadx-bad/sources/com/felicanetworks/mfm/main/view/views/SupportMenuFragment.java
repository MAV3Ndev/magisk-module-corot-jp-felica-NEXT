package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.SupportMenuDrawStructure;
import com.felicanetworks.mfm.oss.OssLicensesMenuActivity;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SupportMenuFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private SupportMenuDrawStructure mStructure = null;
    private List<SupportMenuItem> mSupportMenuItemList;
    private static final SupportMenuItem MEMORY_USAGE_ITEM = new SupportMenuItem(SupportMenuItemType.MEMORY_USAGE, R.string.support_label_memory_usage, true);
    private static final SupportMenuItem TUTORIAL_ITEM = new SupportMenuItem(SupportMenuItemType.TUTORIAL, R.string.support_label_about_felica, true);
    private static final SupportMenuItem FELICA_DISCLAIMER = new SupportMenuItem(SupportMenuItemType.FELICA_DISCLAIMER, R.string.support_label_felica_user_policy, true);
    private static final SupportMenuItem APP_DISCLAIMER_ITEM = new SupportMenuItem(SupportMenuItemType.APP_DISCLAIMER, R.string.support_label_felica_app_user_policy, true);
    private static final SupportMenuItem BALANCE_DISCLAIMER_ITEM = new SupportMenuItem(SupportMenuItemType.BALANCE_DISCLAIMER, R.string.support_label_balance_user_policy, true);
    private static final SupportMenuItem ACCOUNT_CHANGE_HISTORY_ITEM = new SupportMenuItem(SupportMenuItemType.ACCOUNT_CHANGE_HISTORY, R.string.support_label_account_change_history, true);
    private static final SupportMenuItem OSS = new SupportMenuItem(SupportMenuItemType.OSS, R.string.support_label_oss, true);
    private static final SupportMenuItem CLEAR_ACCOUNT = new SupportMenuItem(SupportMenuItemType.CLEAR_ACCOUNT, R.string.support_label_clear_account, true);
    private static final SupportMenuItem VER_ITEM = new SupportMenuItem(SupportMenuItemType.VERSION, R.string.support_label_version_information, false);

    private enum SupportMenuItemType {
        MEMORY_USAGE,
        TUTORIAL,
        FELICA_DISCLAIMER,
        APP_DISCLAIMER,
        BALANCE_DISCLAIMER,
        ACCOUNT_CHANGE_HISTORY,
        OSS,
        CLEAR_ACCOUNT,
        VERSION
    }

    static class SupportMenuItem {
        final boolean mEnable;
        final SupportMenuItemType mSupportMenuItemType;
        final int mTextResId;

        SupportMenuItem(SupportMenuItemType n, int tid, boolean enable) {
            this.mSupportMenuItemType = n;
            this.mTextResId = tid;
            this.mEnable = enable;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_08_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_support_menu, container, false);
        try {
            Structure structure = getStructure();
            if (structure instanceof SupportMenuDrawStructure) {
                this.mStructure = (SupportMenuDrawStructure) structure;
                this.mSupportMenuItemList = new ArrayList();
                if (!this.mStructure.isFelicaLock()) {
                    this.mSupportMenuItemList.add(MEMORY_USAGE_ITEM);
                }
                this.mSupportMenuItemList.add(TUTORIAL_ITEM);
                if (this.mStructure.isProvideOsaifuTerms()) {
                    this.mSupportMenuItemList.add(FELICA_DISCLAIMER);
                }
                this.mSupportMenuItemList.add(APP_DISCLAIMER_ITEM);
                this.mSupportMenuItemList.add(BALANCE_DISCLAIMER_ITEM);
                this.mSupportMenuItemList.add(ACCOUNT_CHANGE_HISTORY_ITEM);
                this.mSupportMenuItemList.add(CLEAR_ACCOUNT);
                this.mSupportMenuItemList.add(OSS);
                this.mSupportMenuItemList.add(VER_ITEM);
                SupportMenuListItemAdapter supportMenuListItemAdapter = new SupportMenuListItemAdapter(getActivity());
                ListView listView = (ListView) viewInflate.findViewById(R.id.lv_support_list);
                listView.setFocusableInTouchMode(false);
                listView.setAdapter((ListAdapter) supportMenuListItemAdapter);
                listView.setOnItemClickListener(this);
                adjustViewToNavigationBar(listView, true);
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
        View view = getView();
        if (view != null) {
            adjustViewToNavigationBar(view.findViewById(R.id.lv_support_list), true);
        }
    }

    private class SupportMenuListItemAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return 0L;
        }

        private class SupportMenuViewHolder {
            TextView mSubText;
            TextView mText;

            private SupportMenuViewHolder() {
            }
        }

        public SupportMenuListItemAdapter(Context activity) {
            this.mInflater = LayoutInflater.from(activity);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return SupportMenuFragment.this.mSupportMenuItemList.size();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int position) {
            return ((SupportMenuItem) SupportMenuFragment.this.mSupportMenuItemList.get(position)).mEnable;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            SupportMenuViewHolder supportMenuViewHolder;
            SupportMenuViewHolder supportMenuViewHolder2;
            try {
                SupportMenuItem supportMenuItem = (SupportMenuItem) SupportMenuFragment.this.mSupportMenuItemList.get(position);
                if (supportMenuItem.mSupportMenuItemType.equals(SupportMenuItemType.VERSION)) {
                    if (convertView == null || ((SupportMenuViewHolder) convertView.getTag()).mSubText == null) {
                        convertView = this.mInflater.inflate(R.layout.list_item_support_menu_multi, parent, false);
                        supportMenuViewHolder2 = new SupportMenuViewHolder();
                        supportMenuViewHolder2.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                        supportMenuViewHolder2.mSubText = (TextView) convertView.findViewById(R.id.tv_menu_body_text);
                        convertView.setTag(supportMenuViewHolder2);
                    } else {
                        supportMenuViewHolder2 = (SupportMenuViewHolder) convertView.getTag();
                    }
                    String str = SupportMenuFragment.this.getString(R.string.support_label_version_information_version) + " " + Information.fullVersionName() + "\n" + SupportMenuFragment.this.getString(R.string.support_label_version_information_copyright);
                    supportMenuViewHolder2.mText.setText(supportMenuItem.mTextResId);
                    supportMenuViewHolder2.mSubText.setText(str);
                    return convertView;
                }
                if (convertView == null || ((SupportMenuViewHolder) convertView.getTag()).mSubText != null) {
                    convertView = this.mInflater.inflate(R.layout.list_item_support_menu, parent, false);
                    supportMenuViewHolder = new SupportMenuViewHolder();
                    supportMenuViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                    supportMenuViewHolder.mSubText = null;
                    convertView.setTag(supportMenuViewHolder);
                } else {
                    supportMenuViewHolder = (SupportMenuViewHolder) convertView.getTag();
                }
                supportMenuViewHolder.mText.setText(supportMenuItem.mTextResId);
                return convertView;
            } catch (Exception e) {
                SupportMenuFragment.this.notifyException(e);
                return convertView;
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (this.mSupportMenuItemList.get(position).mSupportMenuItemType) {
                case MEMORY_USAGE:
                    this.mStructure.actSelectMemory();
                    break;
                case TUTORIAL:
                    this.mStructure.actTutorial();
                    break;
                case FELICA_DISCLAIMER:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_FELICA, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.OSAIFU_TERMS));
                    break;
                case APP_DISCLAIMER:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_MFM, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.APP_TERMS));
                    break;
                case BALANCE_DISCLAIMER:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_EMONEY, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.E_MONEY_TERMS));
                    break;
                case ACCOUNT_CHANGE_HISTORY:
                    this.mStructure.actAccountChange();
                    break;
                case OSS:
                    AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_21_01, new Object[0]);
                    startActivity(new Intent(getContext(), (Class<?>) OssLicensesMenuActivity.class));
                    break;
                case CLEAR_ACCOUNT:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DELETE_ACCOUNT, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.CLEAR_ACCOUNT_URL));
                    break;
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    private void startExtLink(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception unused) {
            showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
        }
    }
}
