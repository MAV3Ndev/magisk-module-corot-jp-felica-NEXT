package com.felicanetworks.mfm.main.view.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.internal.ReaderModeManager;
import com.felicanetworks.mfm.main.presenter.structure.SettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.SettingListActivity;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SettingListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private List<SettingItem> mSettingItemList;
    private SettingDrawStructure mStructure = null;
    private static final SettingItem NOTIFICATION_SETTING_ITEM = new SettingItem(SettingListItemType.NOTIFICATION_SETTING, R.string.settings_label_notification_setting);
    private static final SettingItem EXTCARD_START_SETTING_ITEM = new SettingItem(SettingListItemType.EXTCARD_START_SETTING, R.string.settings_label_ext_card_setting);
    private static final SettingItem STOP_CHIP_ACCESS_ITEM = new SettingItem(SettingListItemType.STOP_CHIP_ACCESS_SETTING, R.string.settings_label_stop_chip_access_setting);
    private static final SettingItem MFI_TAPINTERACTION_ITEM = new SettingItem(SettingListItemType.MFI_TAP_INTERACTION, R.string.settings_label_mfi_tap_interaction);
    private static final SettingItem Account_Change_History_ITEM = new SettingItem(SettingListItemType.ACCOUNT_CHANGE_HISTORY_SETTING, R.string.settings_label_account_change_notification);
    private static final SettingItem Restrict_Card_Read_ITEM = new SettingItem(SettingListItemType.RESTRICT_CARD_READ_SETTING, R.string.settings_label_restrict_card_read);

    private enum SettingListItemType {
        NOTIFICATION_SETTING,
        EXTCARD_START_SETTING,
        STOP_CHIP_ACCESS_SETTING,
        MFI_TAP_INTERACTION,
        ACCOUNT_CHANGE_HISTORY_SETTING,
        RESTRICT_CARD_READ_SETTING
    }

    static class SettingItem {
        final SettingListItemType mSettingListItemType;
        final int mTextResId;

        SettingItem(SettingListItemType n, int tid) {
            this.mSettingListItemType = n;
            this.mTextResId = tid;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_14_01, new Object[0]);
        View viewInflate = inflater.inflate(R.layout.fragment_setting_menu, container, false);
        try {
            Structure structure = getStructure();
            if (structure != null && (structure instanceof SettingDrawStructure)) {
                this.mStructure = (SettingDrawStructure) structure;
                ArrayList arrayList = new ArrayList();
                this.mSettingItemList = arrayList;
                arrayList.add(NOTIFICATION_SETTING_ITEM);
                if (!Settings.getTIIncompatibleFlag() && Settings.DeviceType.FAVER != Settings.getDeviceType()) {
                    this.mSettingItemList.add(STOP_CHIP_ACCESS_ITEM);
                    this.mSettingItemList.add(MFI_TAPINTERACTION_ITEM);
                }
                this.mSettingItemList.add(Account_Change_History_ITEM);
                if (this.mStructure.hasNFC()) {
                    this.mSettingItemList.add(Restrict_Card_Read_ITEM);
                }
                SettingListItemAdapter settingListItemAdapter = new SettingListItemAdapter(getActivity());
                ListView listView = (ListView) viewInflate.findViewById(R.id.lv_setting_list);
                listView.setFocusableInTouchMode(false);
                listView.setAdapter((ListAdapter) settingListItemAdapter);
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
            adjustViewToNavigationBar(view.findViewById(R.id.lv_setting_list), true);
        }
    }

    private class SettingListItemAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return 0L;
        }

        private class SettingViewHolder {
            SettingListItemType mItemType;
            LinearLayout mLayout;
            TextView mSubText;
            Switch mSwitch;
            TextView mText;

            private SettingViewHolder() {
            }
        }

        public SettingListItemAdapter(Context activity) {
            this.mInflater = LayoutInflater.from(activity);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return SettingListFragment.this.mSettingItemList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, final ViewGroup parent) {
            final SettingViewHolder settingViewHolder;
            final SettingViewHolder settingViewHolder2;
            try {
                SettingItem settingItem = (SettingItem) SettingListFragment.this.mSettingItemList.get(position);
                if (convertView == null) {
                    if (settingItem.mSettingListItemType == SettingListItemType.NOTIFICATION_SETTING) {
                        convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting, parent, false);
                        settingViewHolder = new SettingViewHolder();
                        settingViewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch);
                        settingViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                        settingViewHolder.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch);
                        convertView.setTag(settingViewHolder);
                        settingViewHolder.mSwitch.setChecked(SettingListFragment.this.mStructure.isEnableNoticeSetting());
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                AnalysisManager.stamp(MfmStamp.Event.ACTION_PERMIT_NOTIFICATION_SETTING, Boolean.valueOf(!zIsChecked));
                                settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                SettingListFragment.this.mStructure.actSetPushStatus(!zIsChecked);
                                if (zIsChecked) {
                                    return;
                                }
                                try {
                                    SettingListFragment.this.showOSManagementScreenGuidanceDialog();
                                } catch (Exception unused) {
                                }
                            }
                        });
                    } else if (settingItem.mSettingListItemType == SettingListItemType.EXTCARD_START_SETTING) {
                        convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting_with_description, parent, false);
                        settingViewHolder = new SettingViewHolder();
                        settingViewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch_with_description);
                        settingViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text_with_description);
                        settingViewHolder.mSubText = (TextView) convertView.findViewById(R.id.tv_toggle_with_description_text);
                        settingViewHolder.mSubText.setText(R.string.settings_label_ext_card_setting_description);
                        settingViewHolder.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch_with_description);
                        convertView.setTag(settingViewHolder);
                        int componentEnabledSetting = SettingListFragment.this.getContext().getPackageManager().getComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                        if (componentEnabledSetting == 1 || componentEnabledSetting == 0) {
                            settingViewHolder.mSwitch.setChecked(true);
                        } else {
                            settingViewHolder.mSwitch.setChecked(false);
                        }
                        settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                try {
                                    PackageManager packageManager = SettingListFragment.this.getContext().getPackageManager();
                                    int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class));
                                    if (componentEnabledSetting2 != 1 && componentEnabledSetting2 != 0) {
                                        packageManager.setComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 1, 1);
                                        AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, true);
                                        settingViewHolder.mSwitch.setChecked(true);
                                        return;
                                    }
                                    packageManager.setComponentEnabledSetting(new ComponentName(SettingListFragment.this.getContext(), (Class<?>) ReceiveNfcTagActivity.class), 2, 1);
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NFC_SETTING, false);
                                    settingViewHolder.mSwitch.setChecked(false);
                                } catch (Exception e) {
                                    SettingListFragment.this.notifyException(e);
                                }
                            }
                        });
                    } else {
                        if (settingItem.mSettingListItemType == SettingListItemType.STOP_CHIP_ACCESS_SETTING) {
                            convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting, parent, false);
                            settingViewHolder2 = new SettingViewHolder();
                            settingViewHolder2.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch);
                            settingViewHolder2.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                            settingViewHolder2.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch);
                            convertView.setTag(settingViewHolder2);
                            settingViewHolder2.mSwitch.setChecked(SettingListFragment.this.mStructure.isStopChipAccessSetting());
                            settingViewHolder2.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.3
                                @Override // android.view.View.OnClickListener
                                public void onClick(View v) {
                                    boolean zIsChecked = settingViewHolder2.mSwitch.isChecked();
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_TAP_INTERACTION_ANALYSIS_SETTING, Boolean.valueOf(!zIsChecked));
                                    settingViewHolder2.mSwitch.setChecked(!zIsChecked);
                                    SettingListFragment.this.mStructure.setStopChipAccessSetting(!zIsChecked);
                                    if (!zIsChecked && SettingListFragment.this.mStructure.isTapInteractionNotificationSetting()) {
                                        ListView listView = (ListView) parent.findViewById(R.id.lv_setting_list);
                                        int childCount = listView.getChildCount();
                                        int i = 0;
                                        while (true) {
                                            if (i >= childCount) {
                                                break;
                                            }
                                            SettingViewHolder settingViewHolder3 = (SettingViewHolder) listView.getChildAt(i).getTag();
                                            if (SettingListItemType.MFI_TAP_INTERACTION == settingViewHolder3.mItemType) {
                                                settingViewHolder3.mSwitch.setChecked(false);
                                                SettingListFragment.this.mStructure.setTapInteractionNotificationSetting(false);
                                                SettingListFragment.this.getContext().sendBroadcast(SettingListFragment.this.mStructure.getTapInteractionIntent(false));
                                                break;
                                            }
                                            i++;
                                        }
                                    }
                                    SettingListFragment.this.getContext().sendBroadcast(SettingListFragment.this.mStructure.getStopChipAccessIntent(!zIsChecked));
                                }
                            });
                        } else if (settingItem.mSettingListItemType == SettingListItemType.MFI_TAP_INTERACTION) {
                            convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting, parent, false);
                            settingViewHolder2 = new SettingViewHolder();
                            settingViewHolder2.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch);
                            settingViewHolder2.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                            settingViewHolder2.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch);
                            convertView.setTag(settingViewHolder2);
                            settingViewHolder2.mSwitch.setChecked(SettingListFragment.this.mStructure.isTapInteractionNotificationSetting());
                            settingViewHolder2.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.4
                                @Override // android.view.View.OnClickListener
                                public void onClick(View v) {
                                    boolean zIsChecked = settingViewHolder2.mSwitch.isChecked();
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_MFI_TAP_INTERACTION_SETTING, Boolean.valueOf(!zIsChecked));
                                    settingViewHolder2.mSwitch.setChecked(!zIsChecked);
                                    SettingListFragment.this.mStructure.setTapInteractionNotificationSetting(!zIsChecked);
                                    if (!zIsChecked && SettingListFragment.this.mStructure.isStopChipAccessSetting()) {
                                        ListView listView = (ListView) parent.findViewById(R.id.lv_setting_list);
                                        int childCount = listView.getChildCount();
                                        int i = 0;
                                        while (true) {
                                            if (i >= childCount) {
                                                break;
                                            }
                                            SettingViewHolder settingViewHolder3 = (SettingViewHolder) listView.getChildAt(i).getTag();
                                            if (SettingListItemType.STOP_CHIP_ACCESS_SETTING == settingViewHolder3.mItemType) {
                                                settingViewHolder3.mSwitch.setChecked(false);
                                                SettingListFragment.this.mStructure.setStopChipAccessSetting(false);
                                                SettingListFragment.this.getContext().sendBroadcast(SettingListFragment.this.mStructure.getStopChipAccessIntent(false));
                                                break;
                                            }
                                            i++;
                                        }
                                    }
                                    MfiTapPreference.getInstance().removeTapInteractionFlag2(SettingListFragment.this.getContext());
                                    SettingListFragment.this.getContext().sendBroadcast(SettingListFragment.this.mStructure.getTapInteractionIntent(!zIsChecked));
                                    if (zIsChecked) {
                                        return;
                                    }
                                    try {
                                        SettingListFragment.this.showOSManagementScreenGuidanceDialog();
                                    } catch (Exception unused) {
                                    }
                                }
                            });
                        } else if (settingItem.mSettingListItemType == SettingListItemType.ACCOUNT_CHANGE_HISTORY_SETTING) {
                            convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting, parent, false);
                            settingViewHolder = new SettingViewHolder();
                            settingViewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch);
                            settingViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                            settingViewHolder.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch);
                            convertView.setTag(settingViewHolder);
                            settingViewHolder.mSwitch.setChecked(SettingListFragment.this.mStructure.isEnableAccountChangeHistorySetting());
                            settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.5
                                @Override // android.view.View.OnClickListener
                                public void onClick(View v) {
                                    boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_AC_NOTIFICATION_SETTING, Boolean.valueOf(!zIsChecked));
                                    settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                    SettingListFragment.this.mStructure.setAccountChangeNotificationSetting(!zIsChecked);
                                    if (zIsChecked) {
                                        return;
                                    }
                                    try {
                                        SettingListFragment.this.showOSManagementScreenGuidanceDialog();
                                    } catch (Exception unused) {
                                    }
                                }
                            });
                        } else if (settingItem.mSettingListItemType == SettingListItemType.RESTRICT_CARD_READ_SETTING) {
                            convertView = this.mInflater.inflate(R.layout.list_item_toggle_setting, parent, false);
                            settingViewHolder = new SettingViewHolder();
                            settingViewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_switch);
                            settingViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                            settingViewHolder.mSwitch = (Switch) convertView.findViewById(R.id.s_item_switch);
                            convertView.setTag(settingViewHolder);
                            settingViewHolder.mSwitch.setChecked(SettingListFragment.this.mStructure.isRestrictCardReadSetting());
                            settingViewHolder.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.SettingListItemAdapter.6
                                @Override // android.view.View.OnClickListener
                                public void onClick(View v) {
                                    boolean zIsChecked = settingViewHolder.mSwitch.isChecked();
                                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RESTRICT_CARD_READ, Boolean.valueOf(!zIsChecked));
                                    settingViewHolder.mSwitch.setChecked(!zIsChecked);
                                    SettingListFragment.this.mStructure.setRestrictCardReadSetting(!zIsChecked);
                                    ReaderModeManager readerModeManager = new ReaderModeManager(SettingListFragment.this.getContext(), null);
                                    if (zIsChecked) {
                                        readerModeManager.enableForegroundDispatch(SettingListFragment.this.getActivity());
                                    } else {
                                        readerModeManager.disableForegroundDispatch(SettingListFragment.this.getActivity());
                                    }
                                }
                            });
                        } else {
                            convertView = this.mInflater.inflate(R.layout.list_item_setting, parent, false);
                            settingViewHolder = new SettingViewHolder();
                            settingViewHolder.mText = (TextView) convertView.findViewById(R.id.tv_menu_name_text);
                            convertView.setTag(settingViewHolder);
                        }
                        settingViewHolder = settingViewHolder2;
                    }
                } else {
                    settingViewHolder = (SettingViewHolder) convertView.getTag();
                }
                settingViewHolder.mItemType = settingItem.mSettingListItemType;
                settingViewHolder.mText.setText(settingItem.mTextResId);
                return convertView;
            } catch (Exception e) {
                SettingListFragment.this.notifyException(e);
                return convertView;
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SettingDrawStructure.enableInput()) {
            try {
                if (this.mSettingItemList.get(position).mSettingListItemType.ordinal() != 3) {
                    return;
                }
                this.mStructure.actTISetting();
            } catch (Exception e) {
                notifyException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOSManagementScreenGuidanceDialog() {
        if (checkNotificationPermission()) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            if (fragmentActivityRequireActivity instanceof SettingListActivity) {
                ((SettingListActivity) fragmentActivityRequireActivity).showOSManagementScreenGuidanceDialog(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SettingListFragment.1
                    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                    public boolean onClick() {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_OS_MANAGEMENT_SCREEN_GUIDANCE, false);
                        return true;
                    }
                });
            }
        }
    }
}
