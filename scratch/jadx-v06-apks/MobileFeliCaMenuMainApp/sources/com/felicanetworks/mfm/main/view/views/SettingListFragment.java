package com.felicanetworks.mfm.main.view.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.structure.SettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SettingListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private List<SettingItem> mSettingItemList;
    private SettingDrawStructure mStructure = null;
    private static final SettingItem NOTIFICATION_SETTING_ITEM = new SettingItem(SettingListItemType.NOTIFICATION_SETTING, R.string.settings_label_notification_setting);
    private static final SettingItem EXTCARD_START_SETTING_ITEM = new SettingItem(SettingListItemType.EXTCARD_START_SETTING, R.string.settings_label_ext_card_setting);
    private static final SettingItem MFI_TAPINTERACTION_ITEM = new SettingItem(SettingListItemType.MFI_TAP_INTERACTION, R.string.settings_label_mfi_tap_interaction);
    private static final SettingItem Account_Change_History_ITEM = new SettingItem(SettingListItemType.ACCOUNT_CHANGE_HISTORY_SETTING, R.string.settings_label_account_change_notification);

    private enum SettingListItemType {
        NOTIFICATION_SETTING,
        EXTCARD_START_SETTING,
        MFI_TAP_INTERACTION,
        ACCOUNT_CHANGE_HISTORY_SETTING
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
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_14_01, new Object[0]);
        View viewInflate = layoutInflater.inflate(R.layout.fragment_setting_menu, viewGroup, false);
        try {
            structure = getStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (structure != null && (structure instanceof SettingDrawStructure)) {
            this.mStructure = (SettingDrawStructure) structure;
            ArrayList arrayList = new ArrayList();
            this.mSettingItemList = arrayList;
            arrayList.add(NOTIFICATION_SETTING_ITEM);
            if (!Settings.getTIIncompatibleFlag() && Settings.DeviceType.FAVER != Settings.getDeviceType()) {
                this.mSettingItemList.add(MFI_TAPINTERACTION_ITEM);
            }
            this.mSettingItemList.add(Account_Change_History_ITEM);
            SettingListItemAdapter settingListItemAdapter = new SettingListItemAdapter(getActivity());
            ListView listView = (ListView) viewInflate.findViewById(R.id.lv_setting_list);
            listView.setFocusableInTouchMode(false);
            listView.setAdapter((ListAdapter) settingListItemAdapter);
            listView.setOnItemClickListener(this);
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

            /* synthetic */ SettingViewHolder(SettingListItemAdapter settingListItemAdapter, AnonymousClass1 anonymousClass1) {
                this();
            }
        }

        public SettingListItemAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return SettingListFragment.this.mSettingItemList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            final SettingViewHolder settingViewHolder;
            try {
                SettingItem settingItem = (SettingItem) SettingListFragment.this.mSettingItemList.get(i);
                if (view == null) {
                    AnonymousClass1 anonymousClass1 = null;
                    if (settingItem.mSettingListItemType == SettingListItemType.NOTIFICATION_SETTING) {
                        view = this.mInflater.inflate(R.layout.list_item_toggle_setting, viewGroup, false);
                        settingViewHolder = new SettingViewHolder(this, anonymousClass1);
                        settingViewHolder.mLayout = (LinearLayout) view.findViewById(R.id.ll_item_switch);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                        settingViewHolder.mSwitch = (Switch) view.findViewById(R.id.s_item_switch);
                        view.setTag(settingViewHolder);
                        settingViewHolder.mSwitch.setChecked(SettingListFragment.this.mStructure.isEnableNoticeSetting());
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view2) {
                                boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                AnalysisManager.stamp(MfmStamp.Event.ACTION_PERMIT_NOTIFICATION_SETTING, Boolean.valueOf(!zIsChecked));
                                settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                SettingListFragment.this.mStructure.actSetPushStatus(!zIsChecked);
                            }
                        });
                    } else if (settingItem.mSettingListItemType == SettingListItemType.EXTCARD_START_SETTING) {
                        view = this.mInflater.inflate(R.layout.list_item_toggle_setting_with_description, viewGroup, false);
                        settingViewHolder = new SettingViewHolder(this, anonymousClass1);
                        settingViewHolder.mLayout = (LinearLayout) view.findViewById(R.id.ll_item_switch_with_description);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text_with_description);
                        settingViewHolder.mSubText = (TextView) view.findViewById(R.id.tv_toggle_with_description_text);
                        settingViewHolder.mSubText.setText(R.string.settings_label_ext_card_setting_description);
                        settingViewHolder.mSwitch = (Switch) view.findViewById(R.id.s_item_switch_with_description);
                        view.setTag(settingViewHolder);
                        int componentEnabledSetting = SettingListFragment.this.getContext().getPackageManager().getComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                        if (componentEnabledSetting == 1 || componentEnabledSetting == 0) {
                            settingViewHolder.mSwitch.setChecked(true);
                        } else {
                            settingViewHolder.mSwitch.setChecked(false);
                        }
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view2) {
                                try {
                                    PackageManager packageManager = SettingListFragment.this.getContext().getPackageManager();
                                    int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                                    if (componentEnabledSetting2 == 1 || componentEnabledSetting2 == 0) {
                                        packageManager.setComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 2, 1);
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, false);
                                        settingViewHolder.mSwitch.setChecked(false);
                                    } else {
                                        packageManager.setComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 1, 1);
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, true);
                                        settingViewHolder.mSwitch.setChecked(true);
                                    }
                                } catch (Exception e) {
                                    SettingListFragment.this.notifyException(e);
                                }
                            }
                        });
                    } else if (settingItem.mSettingListItemType == SettingListItemType.MFI_TAP_INTERACTION) {
                        view = this.mInflater.inflate(R.layout.list_item_toggle_setting, viewGroup, false);
                        settingViewHolder = new SettingViewHolder(this, anonymousClass1);
                        settingViewHolder.mLayout = (LinearLayout) view.findViewById(R.id.ll_item_switch);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                        settingViewHolder.mSwitch = (Switch) view.findViewById(R.id.s_item_switch);
                        view.setTag(settingViewHolder);
                        settingViewHolder.mSwitch.setChecked(MfiTapPreference.getInstance().loadTapInteractionFlag1(SettingListFragment.this.getContext()));
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.3
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view2) {
                                boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                AnalysisManager.stamp(MfmStamp.Event.ACTION_MFI_TAP_INTERACTION_SETTING, Boolean.valueOf(!zIsChecked));
                                settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                MfiTapPreference.getInstance().saveTapInteractionFlag1(SettingListFragment.this.getContext(), !zIsChecked);
                                MfiTapPreference.getInstance().removeTapInteractionFlag2(SettingListFragment.this.getContext());
                                SettingListFragment.this.getContext().sendBroadcast(SettingListFragment.this.mStructure.getTapInteractionIntent(!zIsChecked));
                            }
                        });
                    } else if (settingItem.mSettingListItemType == SettingListItemType.ACCOUNT_CHANGE_HISTORY_SETTING) {
                        view = this.mInflater.inflate(R.layout.list_item_toggle_setting, viewGroup, false);
                        settingViewHolder = new SettingViewHolder(this, anonymousClass1);
                        settingViewHolder.mLayout = (LinearLayout) view.findViewById(R.id.ll_item_switch);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                        settingViewHolder.mSwitch = (Switch) view.findViewById(R.id.s_item_switch);
                        view.setTag(settingViewHolder);
                        settingViewHolder.mSwitch.setChecked(SettingListFragment.this.mStructure.isEnableAccountChangeHistorySetting());
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.4
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view2) {
                                boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                AnalysisManager.stamp(MfmStamp.Event.ACTION_AC_NOTIFICATION_SETTING, Boolean.valueOf(!zIsChecked));
                                settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                SettingListFragment.this.mStructure.setAccountChangeNotificationSetting(!zIsChecked);
                            }
                        });
                    } else {
                        view = this.mInflater.inflate(R.layout.list_item_setting, viewGroup, false);
                        settingViewHolder = new SettingViewHolder(this, anonymousClass1);
                        settingViewHolder.mText = (TextView) view.findViewById(R.id.tv_menu_name_text);
                        view.setTag(settingViewHolder);
                    }
                } else {
                    settingViewHolder = (SettingViewHolder) view.getTag();
                }
                settingViewHolder.mText.setText(settingItem.mTextResId);
            } catch (Exception e) {
                SettingListFragment.this.notifyException(e);
            }
            return view;
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (SettingDrawStructure.enableInput()) {
            try {
                if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$SettingListFragment$SettingListItemType[this.mSettingItemList.get(i).mSettingListItemType.ordinal()] != 1) {
                    return;
                }
                this.mStructure.actTISetting();
            } catch (Exception e) {
                notifyException(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.SettingListFragment$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$SettingListFragment$SettingListItemType;

        static {
            int[] iArr = new int[SettingListItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$SettingListFragment$SettingListItemType = iArr;
            try {
                iArr[SettingListItemType.MFI_TAP_INTERACTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }
}
