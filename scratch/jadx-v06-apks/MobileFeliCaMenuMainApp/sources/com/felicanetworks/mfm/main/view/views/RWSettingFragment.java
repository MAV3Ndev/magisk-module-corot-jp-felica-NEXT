package com.felicanetworks.mfm.main.view.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.RWSettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RWSettingFragment extends BaseFragment {
    private static final SettingItem EXTCARD_START_SETTING_ITEM = new SettingItem(SettingListItemType.EXTCARD_START_SETTING, R.string.settings_label_ext_card_setting);
    private List<SettingItem> mSettingItemList;

    private enum SettingListItemType {
        EXTCARD_START_SETTING
    }

    static class SettingItem {
        final SettingListItemType mSettingListItemType;
        final int mTextResId;

        SettingItem(SettingListItemType settingListItemType, int i) {
            this.mSettingListItemType = settingListItemType;
            this.mTextResId = i;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Structure structure;
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_14_03, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_setting_menu, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof RWSettingDrawStructure)) {
            ArrayList arrayList = new ArrayList();
            this.mSettingItemList = arrayList;
            arrayList.add(EXTCARD_START_SETTING_ITEM);
            SettingListItemAdapter settingListItemAdapter = new SettingListItemAdapter(getActivity());
            ListView listView = (ListView) viewInflate.findViewById(R.id.lv_setting_list);
            listView.setFocusableInTouchMode(false);
            listView.setAdapter((ListAdapter) settingListItemAdapter);
            return viewInflate;
        }
        return viewInflate;
    }

    private class SettingListItemAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        private class SettingViewHolder {
            LinearLayout mLayout;
            TextView mSubText;
            Switch mSwitch;
            TextView mText;

            private SettingViewHolder() {
            }
        }

        public SettingListItemAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return RWSettingFragment.this.mSettingItemList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            final SettingViewHolder settingViewHolder;
            try {
                SettingItem settingItem = (SettingItem) RWSettingFragment.this.mSettingItemList.get(i);
                if (view == null) {
                    if (settingItem.mSettingListItemType == SettingListItemType.EXTCARD_START_SETTING) {
                        view = this.mInflater.inflate(R.layout.list_item_toggle_setting_with_description, viewGroup, false);
                        settingViewHolder = new SettingViewHolder();
                        settingViewHolder.mLayout = (LinearLayout) view.findViewById(R.id.ll_item_switch_with_description);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text_with_description);
                        settingViewHolder.mSubText = (TextView) view.findViewById(R.id.tv_toggle_with_description_text);
                        settingViewHolder.mSubText.setText(R.string.settings_label_ext_card_setting_description);
                        settingViewHolder.mSwitch = (Switch) view.findViewById(R.id.s_item_switch_with_description);
                        view.setTag(settingViewHolder);
                        int componentEnabledSetting = RWSettingFragment.this.getContext().getPackageManager().getComponentEnabledSetting(new ComponentName(RWSettingFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                        if (componentEnabledSetting == 1 || componentEnabledSetting == 0) {
                            settingViewHolder.mSwitch.setChecked(true);
                        } else {
                            settingViewHolder.mSwitch.setChecked(false);
                        }
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.RWSettingFragment.SettingListItemAdapter.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view2) {
                                try {
                                    PackageManager packageManager = RWSettingFragment.this.getContext().getPackageManager();
                                    int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(new ComponentName(RWSettingFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                                    if (componentEnabledSetting2 == 1 || componentEnabledSetting2 == 0) {
                                        packageManager.setComponentEnabledSetting(new ComponentName(RWSettingFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 2, 1);
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, false);
                                        settingViewHolder.mSwitch.setChecked(false);
                                    } else {
                                        packageManager.setComponentEnabledSetting(new ComponentName(RWSettingFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 1, 1);
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, true);
                                        settingViewHolder.mSwitch.setChecked(true);
                                    }
                                } catch (Exception e) {
                                    RWSettingFragment.this.notifyException(e);
                                }
                            }
                        });
                    } else {
                        view = this.mInflater.inflate(R.layout.list_item_setting, viewGroup, false);
                        settingViewHolder = new SettingViewHolder();
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                        view.setTag(settingViewHolder);
                    }
                } else {
                    settingViewHolder = (SettingViewHolder) view.getTag();
                }
                settingViewHolder.mText.setText(settingItem.mTextResId);
            } catch (Exception e) {
                RWSettingFragment.this.notifyException(e);
            }
            return view;
        }
    }
}
