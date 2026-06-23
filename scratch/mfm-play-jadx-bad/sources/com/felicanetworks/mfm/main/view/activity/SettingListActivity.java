package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FeliCaLockAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfsAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.SettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;
import com.felicanetworks.mfm.main.view.views.DrawerContentsLayout;
import com.felicanetworks.mfm.main.view.views.SettingListFragment;

/* JADX INFO: loaded from: classes3.dex */
public class SettingListActivity extends DrawerBaseActivity implements DrawerContentsLayout.OnSelectDrawerItemListener {
    private boolean mIsReturnFromOtherScreen = false;
    private Menu mOptionMenu = null;
    private DrawerContentsLayout.DrawerItemType mSelectType;

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.toolbar_title_setting);
            }
            Structure currentRequest = getCurrentRequest();
            if (currentRequest != null) {
                dispatchLatestStructure(currentRequest);
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.views.DrawerContentsLayout.OnSelectDrawerItemListener
    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior onSelect(DrawerContentsLayout.DrawerItemType type) {
        SettingDrawStructure myStructure;
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehavior = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        try {
            myStructure = getMyStructure();
        } catch (Exception e) {
            notifyException(e);
        }
        if (myStructure == null) {
            return drawerBehavior;
        }
        if (myStructure.isNotificationPermissionRequirement() && checkNotificationPermission()) {
            this.mSelectType = type;
            showOSManagementScreenGuidanceDialog(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.SettingListActivity.1
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_OS_MANAGEMENT_SCREEN_GUIDANCE, false);
                    SettingListActivity settingListActivity = SettingListActivity.this;
                    settingListActivity.selectDrawerItem(settingListActivity.getMyStructure(), SettingListActivity.this.mSelectType);
                    return true;
                }
            });
            return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
        }
        return selectDrawerItem(myStructure, type);
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
                showContentView(structure);
            } else {
                super.dispatchLatestStructure(structure);
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        try {
            switch (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()]) {
                case 1:
                    break;
                case 2:
                    if (structure instanceof CentralDrawStructure) {
                        CentralDrawStructure centralDrawStructure = (CentralDrawStructure) structure;
                        centralDrawStructure.setRequestPage(centralDrawStructure.getFirstPage());
                    }
                    Intent intent = new Intent(this, (Class<?>) CentralActivity.class);
                    intent.setFlags(603979776);
                    startActivity(intent);
                    break;
                case 3:
                    Intent intent2 = new Intent(this, (Class<?>) ExtIcCardReaderActivity.class);
                    intent2.setFlags(603979776);
                    startActivity(intent2);
                    break;
                case 4:
                    Intent intent3 = new Intent(this, (Class<?>) NoticeListActivity.class);
                    intent3.setFlags(603979776);
                    startActivity(intent3);
                    break;
                case 5:
                    activityResultLaunch(((FeliCaLockAppStructure) structure).getDefaultIntent());
                    break;
                case 6:
                    Intent intent4 = new Intent(this, (Class<?>) FaqActivity.class);
                    intent4.setFlags(603979776);
                    startActivity(intent4);
                    break;
                case 7:
                    Intent intent5 = new Intent(this, (Class<?>) SupportMenuActivity.class);
                    intent5.setFlags(603979776);
                    startActivity(intent5);
                    break;
                case 8:
                    activityResultLaunch(((MfsAppStructure) structure).getDefaultIntent());
                    break;
                default:
                    super.dispatchStructure(structure);
                    break;
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        try {
            if (getCurrentRequest() instanceof SettingDrawStructure) {
                this.mIsReturnFromOtherScreen = true;
            }
            super.onNewIntent(intent);
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        try {
            super.onResume();
            Menu menu = this.mOptionMenu;
            if (menu != null) {
                updateOptionMenu(menu);
            }
            if (this.mIsReturnFromOtherScreen) {
                this.mIsReturnFromOtherScreen = false;
                dispatchLatestStructure(getCurrentRequest());
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            this.mOptionMenu = menu;
            getMenuInflater().inflate(R.menu.menu_lock, this.mOptionMenu);
            updateOptionMenu(this.mOptionMenu);
            return true;
        } catch (Exception e) {
            notifyException(e);
            return true;
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int itemId = item.getItemId();
            Structure currentRequest = getCurrentRequest();
            if ((currentRequest instanceof SettingDrawStructure) && itemId == R.id.tool_lock) {
                if (SettingDrawStructure.enableInput()) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                    if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                        try {
                            startActivity(((SettingDrawStructure) currentRequest).getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                        } catch (Exception unused) {
                            showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    } else if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT >= 29) {
                        try {
                            startActivity(((SettingDrawStructure) currentRequest).getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                        } catch (Exception unused2) {
                            showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                            AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                        }
                    } else {
                        ((SettingDrawStructure) currentRequest).actLock();
                    }
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return true;
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    public void onBackPressedDispatch() {
        if (dispatchBackKeyToDrawer()) {
            return;
        }
        SettingDrawStructure myStructure = getMyStructure();
        if (myStructure != null && myStructure.isNotificationPermissionRequirement() && checkNotificationPermission()) {
            showOSManagementScreenGuidanceDialog(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.SettingListActivity.2
                @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
                public boolean onClick() {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_OS_MANAGEMENT_SCREEN_GUIDANCE, false);
                    SettingListActivity.this.backPressedDispatch();
                    return true;
                }
            });
        } else {
            super.onBackPressedDispatch();
        }
    }

    private void updateOptionMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.tool_lock);
        Structure currentRequest = getCurrentRequest();
        if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT <= 28) {
            menuItemFindItem.setVisible(false);
            return;
        }
        if (Settings.DeviceType.FAVER == Settings.getDeviceType()) {
            if (currentRequest == null || !(currentRequest instanceof SettingDrawStructure)) {
                return;
            }
            if (((SettingDrawStructure) currentRequest).isFelicaLock()) {
                menuItemFindItem.setIcon(R.drawable.ic_lock_lock_active);
                return;
            } else {
                menuItemFindItem.setIcon(R.drawable.ic_lock_unlock_active);
                return;
            }
        }
        if (currentRequest instanceof SettingDrawStructure) {
            if (((SettingDrawStructure) currentRequest).isScreenLock(getApplicationContext())) {
                menuItemFindItem.setIcon(R.drawable.header_lock);
            } else {
                menuItemFindItem.setIcon(R.drawable.header_unlock);
            }
        }
    }

    private void showContentView(Structure structure) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new SettingListFragment());
        fragmentTransactionBeginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SettingDrawStructure getMyStructure() {
        Structure currentRequest = getCurrentRequest();
        if (currentRequest instanceof SettingDrawStructure) {
            return (SettingDrawStructure) currentRequest;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior selectDrawerItem(SettingDrawStructure structure, DrawerContentsLayout.DrawerItemType type) {
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehavior = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        if (structure == null) {
            return drawerBehavior;
        }
        switch (AnonymousClass4.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[type.ordinal()]) {
            case 1:
                structure.actMyService();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 2:
                structure.actRecommend();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 3:
                structure.actNotice();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 4:
                structure.actReader();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 5:
                structure.actFaq();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 6:
                startActivity(structure.getDefaultIntent(PrimaryDrawStructure.LinkType.CHANGE_MODEL));
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 7:
                structure.actSupportMenu();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            case 8:
                structure.actSetting();
                return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
            default:
                return super.onSelect(type);
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.SettingListActivity$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType;

        static {
            int[] iArr = new int[DrawerContentsLayout.DrawerItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType = iArr;
            try {
                iArr[DrawerContentsLayout.DrawerItemType.MYSERVICE_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.RECOMMEND_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.NOTICE_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.CARD_ID.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.FAQ_ID.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.MODEL_CHANGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.SUPPORT_ID.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.SETUP_ID.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr2;
            try {
                iArr2[StructureType.SETTING.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CENTRAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.READER.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_LOCK_APP.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FAQ.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SUPPORT_MENU.ordinal()] = 7;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFS_APP.ordinal()] = 8;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    public void showOSManagementScreenGuidanceDialog(CustomDialogFragment.OnClickListener negativeListener) {
        CustomDialogFragment customDialogFragmentCreateSelectDialog = DialogFactory.createSelectDialog(getApplicationContext());
        customDialogFragmentCreateSelectDialog.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
        customDialogFragmentCreateSelectDialog.setTitle(getString(R.string.dlg_title_os_management_screen_guidance));
        customDialogFragmentCreateSelectDialog.setText(getString(R.string.dlg_text_os_management_screen_guidance));
        customDialogFragmentCreateSelectDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.SettingListActivity.3
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_OS_MANAGEMENT_SCREEN_GUIDANCE, true);
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + SettingListActivity.this.getPackageName()));
                if (intent.resolveActivity(SettingListActivity.this.getPackageManager()) != null) {
                    SettingListActivity.this.startActivity(intent);
                }
                return true;
            }
        });
        customDialogFragmentCreateSelectDialog.setNegativeButtonListener(negativeListener);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_W1_14_04, new Object[0]);
        showNormalDialog(customDialogFragmentCreateSelectDialog);
    }
}
