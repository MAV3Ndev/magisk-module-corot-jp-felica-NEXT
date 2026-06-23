package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.presenter.structure.FaqDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FeliCaLockAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.DrawerContentsLayout;
import com.felicanetworks.mfm.main.view.views.FaqFragment;

/* JADX INFO: loaded from: classes.dex */
public class FaqActivity extends DrawerBaseActivity {
    private boolean mIsReturnFromOtherScreen = false;
    private Menu mOptionMenu = null;

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.toolbar_title_faq);
            }
            Structure currentRequest = getCurrentRequest();
            if (currentRequest != null) {
                dispatchLatestStructure(currentRequest);
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        try {
            if (getCurrentRequest() instanceof FaqDrawStructure) {
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
            if (this.mOptionMenu != null) {
                updateOptionMenu(this.mOptionMenu);
            }
            if (this.mIsReturnFromOtherScreen) {
                this.mIsReturnFromOtherScreen = false;
                dispatchLatestStructure(getCurrentRequest());
            }
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        try {
            Fragment fragmentFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.fl_content_fragment);
            if ((fragmentFindFragmentById instanceof FaqFragment) && ((FaqFragment) fragmentFindFragmentById).goBack()) {
                return;
            }
            super.onBackPressed();
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        try {
            int itemId = menuItem.getItemId();
            Structure currentRequest = getCurrentRequest();
            if ((currentRequest instanceof FaqDrawStructure) && itemId == R.id.tool_lock) {
                AnalysisManager.stamp(MfmStamp.Event.ACTION_LOCK_SETTING, new Object[0]);
                if (Settings.DeviceType.GP == Settings.getDeviceType()) {
                    try {
                        startActivity(((FaqDrawStructure) currentRequest).getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT >= 29) {
                    try {
                        startActivity(((FaqDrawStructure) currentRequest).getDefaultIntent(PrimaryDrawStructure.LinkType.SCREEN_LOCK_SETTING));
                    } catch (Exception unused2) {
                        showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                } else {
                    ((FaqDrawStructure) currentRequest).actLock();
                }
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return true;
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (structure.getType() == StructureType.FAQ) {
                showContentView();
            } else {
                super.dispatchLatestStructure(structure);
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()]) {
            case 1:
                Intent intent = new Intent(this, (Class<?>) CentralActivity.class);
                intent.setFlags(603979776);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this, (Class<?>) ExtIcCardReaderActivity.class);
                intent2.setFlags(603979776);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, (Class<?>) NoticeListActivity.class);
                intent3.setFlags(603979776);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(this, (Class<?>) SupportMenuActivity.class);
                intent4.setFlags(603979776);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(this, (Class<?>) SettingListActivity.class);
                intent5.setFlags(603979776);
                startActivity(intent5);
                break;
            case 6:
                startActivityForResult(((FeliCaLockAppStructure) structure).getDefaultIntent(), REQUEST_CODE_FELICA_LOCK_APP);
                break;
            case 7:
                break;
            default:
                super.dispatchStructure(structure);
                break;
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity, com.felicanetworks.mfm.main.view.views.DrawerContentsLayout.OnSelectDrawerItemListener
    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior onSelect(DrawerContentsLayout.DrawerItemType drawerItemType) {
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehaviorOnSelect;
        DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior drawerBehavior = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        try {
            Structure currentRequest = getCurrentRequest();
            if (!(currentRequest instanceof FaqDrawStructure)) {
                return drawerBehavior;
            }
            FaqDrawStructure faqDrawStructure = (FaqDrawStructure) currentRequest;
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[drawerItemType.ordinal()]) {
                case 1:
                    faqDrawStructure.actMyService();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 2:
                    faqDrawStructure.actRecommend();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 3:
                    faqDrawStructure.actNotice();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 4:
                    faqDrawStructure.actReader();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 5:
                    faqDrawStructure.actFaq();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 6:
                    startActivity(faqDrawStructure.getDefaultIntent(PrimaryDrawStructure.LinkType.CHANGE_MODEL));
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 7:
                    faqDrawStructure.actSupportMenu();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                case 8:
                    faqDrawStructure.actSetting();
                    drawerBehaviorOnSelect = DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
                    break;
                default:
                    drawerBehaviorOnSelect = super.onSelect(drawerItemType);
                    break;
            }
            return drawerBehaviorOnSelect;
        } catch (Exception e) {
            notifyException(e);
            return drawerBehavior;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.FaqActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
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
                iArr2[StructureType.CENTRAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.READER.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SUPPORT_MENU.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SETTING.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FELICA_LOCK_APP.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.FAQ.ordinal()] = 7;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private void showContentView() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new FaqFragment());
        fragmentTransactionBeginTransaction.commit();
    }

    private void updateOptionMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.tool_lock);
        Structure currentRequest = getCurrentRequest();
        if (Settings.DeviceType.PIXEL == Settings.getDeviceType() && Build.VERSION.SDK_INT <= 28) {
            menuItemFindItem.setVisible(false);
            return;
        }
        if (Settings.DeviceType.FAVER == Settings.getDeviceType()) {
            if (currentRequest instanceof FaqDrawStructure) {
                if (((FaqDrawStructure) currentRequest).isFelicaLock()) {
                    menuItemFindItem.setIcon(R.drawable.ic_lock_lock_active);
                    return;
                } else {
                    menuItemFindItem.setIcon(R.drawable.ic_lock_unlock_active);
                    return;
                }
            }
            return;
        }
        if (currentRequest instanceof FaqDrawStructure) {
            if (((FaqDrawStructure) currentRequest).isScreenLock(getApplicationContext())) {
                menuItemFindItem.setIcon(R.drawable.header_lock);
            } else {
                menuItemFindItem.setIcon(R.drawable.header_unlock);
            }
        }
    }
}
