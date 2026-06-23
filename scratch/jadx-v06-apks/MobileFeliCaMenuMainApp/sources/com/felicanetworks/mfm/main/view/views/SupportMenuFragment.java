package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.Intent;
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
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SupportMenuFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private SupportMenuDrawStructure mStructure = null;
    private List<SupportMenuItem> mSupportMenuItemList;
    private static final SupportMenuItem MEMORY_USAGE_ITEM = new SupportMenuItem(SupportMenuItemType.MEMORY_USAGE, R.string.support_label_memory_usage);
    private static final SupportMenuItem TUTORIAL_ITEM = new SupportMenuItem(SupportMenuItemType.TUTORIAL, R.string.support_label_about_felica);
    private static final SupportMenuItem FELICA_DISCLAIMER = new SupportMenuItem(SupportMenuItemType.FELICA_DISCLAIMER, R.string.support_label_felica_user_policy);
    private static final SupportMenuItem APP_DISCLAIMER_ITEM = new SupportMenuItem(SupportMenuItemType.APP_DISCLAIMER, R.string.support_label_felica_app_user_policy);
    private static final SupportMenuItem BALANCE_DISCLAIMER_ITEM = new SupportMenuItem(SupportMenuItemType.BALANCE_DISCLAIMER, R.string.support_label_balance_user_policy);
    private static final SupportMenuItem VER_ITEM = new SupportMenuItem(SupportMenuItemType.VERSION, R.string.support_label_version_information);
    private static final SupportMenuItem ACCOUNT_CHANGE_HISTORY_ITEM = new SupportMenuItem(SupportMenuItemType.ACCOUNT_CHANGE_HISTORY, R.string.support_label_account_change_history);

    private enum SupportMenuItemType {
        MEMORY_USAGE,
        TUTORIAL,
        FELICA_DISCLAIMER,
        APP_DISCLAIMER,
        BALANCE_DISCLAIMER,
        VERSION,
        ACCOUNT_CHANGE_HISTORY
    }

    static class SupportMenuItem {
        final SupportMenuItemType mSupportMenuItemType;
        final int mTextResId;

        SupportMenuItem(SupportMenuItemType supportMenuItemType, int i) {
            this.mSupportMenuItemType = supportMenuItemType;
            this.mTextResId = i;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_08_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_support_menu, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof SupportMenuDrawStructure)) {
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
            this.mSupportMenuItemList.add(VER_ITEM);
            this.mSupportMenuItemList.add(ACCOUNT_CHANGE_HISTORY_ITEM);
            SupportMenuListItemAdapter supportMenuListItemAdapter = new SupportMenuListItemAdapter(getActivity());
            ListView listView = (ListView) viewInflate.findViewById(R.id.lv_support_list);
            listView.setFocusableInTouchMode(false);
            listView.setAdapter((ListAdapter) supportMenuListItemAdapter);
            listView.setOnItemClickListener(this);
            return viewInflate;
        }
        return viewInflate;
    }

    private class SupportMenuListItemAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        private class SupportMenuViewHolder {
            TextView mText;

            private SupportMenuViewHolder() {
            }
        }

        public SupportMenuListItemAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return SupportMenuFragment.this.mSupportMenuItemList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            SupportMenuViewHolder supportMenuViewHolder;
            try {
                SupportMenuItem supportMenuItem = (SupportMenuItem) SupportMenuFragment.this.mSupportMenuItemList.get(i);
                if (view == null) {
                    view = this.mInflater.inflate(R.layout.list_item_support_menu, viewGroup, false);
                    supportMenuViewHolder = new SupportMenuViewHolder();
                    supportMenuViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                    view.setTag(supportMenuViewHolder);
                } else {
                    supportMenuViewHolder = (SupportMenuViewHolder) view.getTag();
                }
                supportMenuViewHolder.mText.setText(supportMenuItem.mTextResId);
            } catch (Exception e) {
                SupportMenuFragment.this.notifyException(e);
            }
            return view;
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        try {
            switch (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[this.mSupportMenuItemList.get(i).mSupportMenuItemType.ordinal()]) {
                case 1:
                    this.mStructure.actSelectMemory();
                    break;
                case 2:
                    this.mStructure.actTutorial();
                    break;
                case 3:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_FELICA, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.OSAIFU_TERMS));
                    break;
                case 4:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_MFM, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.APP_TERMS));
                    break;
                case 5:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_DISCLAIMER_EMONEY, new Object[0]);
                    startExtLink(this.mStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.E_MONEY_TERMS));
                    break;
                case 6:
                    showVersionDialog();
                    break;
                case 7:
                    this.mStructure.actAccountChange();
                    break;
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.SupportMenuFragment$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType;

        static {
            int[] iArr = new int[SupportMenuItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType = iArr;
            try {
                iArr[SupportMenuItemType.MEMORY_USAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.TUTORIAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.FELICA_DISCLAIMER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.APP_DISCLAIMER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.BALANCE_DISCLAIMER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.VERSION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$SupportMenuFragment$SupportMenuItemType[SupportMenuItemType.ACCOUNT_CHANGE_HISTORY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private void showVersionDialog() {
        CustomDialogFragment customDialogFragmentCreateSingleChoiceDialog = DialogFactory.createSingleChoiceDialog(getContext());
        customDialogFragmentCreateSingleChoiceDialog.setTitle(getString(R.string.support_label_version_information));
        customDialogFragmentCreateSingleChoiceDialog.setText(getString(R.string.dlg_text_support_version_information_1) + Information.fullVersionName() + "\n" + getString(R.string.dlg_text_support_version_information_2));
        customDialogFragmentCreateSingleChoiceDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SupportMenuFragment.1
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                return true;
            }
        });
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_08_02, new Object[0]);
        showNormalDialog(customDialogFragmentCreateSingleChoiceDialog);
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
