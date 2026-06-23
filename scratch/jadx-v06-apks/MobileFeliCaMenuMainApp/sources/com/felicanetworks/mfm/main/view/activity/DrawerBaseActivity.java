package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.felicanetworks.mfm.main.MfiAccountSwitchingActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.action.IActionMenu;
import com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;
import com.felicanetworks.mfm.main.view.views.DrawerContentsLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class DrawerBaseActivity extends BaseActivity implements DrawerContentsLayout.OnSelectDrawerItemListener, MfiLoginReadyingDrawStructure.FailureObserver {
    public Toolbar mToolbar;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.drawer_activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar = toolbar;
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout);
        if (drawerLayout != null) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, this.mToolbar, R.string.toolbar_title_drawer_open, R.string.toolbar_title_drawer_close) { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.1
                @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                public void onDrawerOpened(View view) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DRAWER, new Object[0]);
                    super.onDrawerOpened(view);
                }
            };
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
    }

    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior onSelect(DrawerContentsLayout.DrawerItemType drawerItemType) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[drawerItemType.ordinal()];
        if (i == 1) {
            if (getCurrentRequest() instanceof IActionMenu) {
                ((IActionMenu) getCurrentRequest()).actMfiLogin();
            }
            return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
        }
        if (i == 2) {
            startActivityForResult(new Intent(getApplicationContext(), (Class<?>) MfiAccountSwitchingActivity.class), REQUEST_CODE_MFI_ACCOUNT_SWITCHING);
            return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
        }
        return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.KEEP;
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateDrawer();
    }

    public void updateDrawer() {
        DrawerContentsLayout drawerContentsLayout = (DrawerContentsLayout) findViewById(R.id.left_drawer);
        if (drawerContentsLayout != null) {
            drawerContentsLayout.setOnSelectDrawerItemListener(this);
            Structure currentRequest = getCurrentRequest();
            if (currentRequest instanceof PrimaryDrawStructure) {
                PrimaryDrawStructure primaryDrawStructure = (PrimaryDrawStructure) currentRequest;
                if (!primaryDrawStructure.isEnoughExtCardServiceInfo() || !primaryDrawStructure.hasNFC()) {
                    drawerContentsLayout.setDrawerItemEnable(DrawerContentsLayout.DrawerItemType.CARD_ID, false);
                }
                if (!primaryDrawStructure.isProvideChangeModel()) {
                    drawerContentsLayout.setDrawerItemEnable(DrawerContentsLayout.DrawerItemType.MODEL_CHANGE, false);
                }
                String mfiAccountName = primaryDrawStructure.getMfiAccountName();
                if (mfiAccountName != null) {
                    drawerContentsLayout.setDrawerItemEnable(DrawerContentsLayout.DrawerItemType.MFI_LOGIN, false);
                    ((TextView) findViewById(R.id.account)).setText(mfiAccountName);
                } else {
                    drawerContentsLayout.setDrawerItemEnable(DrawerContentsLayout.DrawerItemType.MFI_LOGIN_SWITCHING, false);
                }
                Toolbar toolbar = this.mToolbar;
                if (toolbar != null) {
                    toolbar.setNavigationIcon(R.drawable.ic_drawer);
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        try {
            if (((DrawerLayout) findViewById(R.id.dl_drawer_layout)).isDrawerOpen(findViewById(R.id.left_drawer))) {
                return findViewById(R.id.left_drawer).dispatchKeyEvent(keyEvent);
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.MFI_LOGIN_READYING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.MFI_LOGIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DATA_LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DrawerContentsLayout.DrawerItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType = iArr2;
            try {
                iArr2[DrawerContentsLayout.DrawerItemType.MFI_LOGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerContentsLayout.DrawerItemType.MFI_LOGIN_SWITCHING.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            showDialog(DialogFactory.createCircleProgressDialog(this));
            ((MfiLoginReadyingDrawStructure) structure).setFailureObserver(this);
        } else if (i == 2 || i == 3) {
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        } else {
            super.dispatchStructure(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructureOnResume(Structure structure) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            ((MfiLoginReadyingDrawStructure) structure).setFailureObserver(this);
        } else if (i == 2 || i == 3) {
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        } else {
            super.dispatchStructureOnResume(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure.FailureObserver
    public void onMfiLoginReadyingFailure(final MfiLoginReadyingDrawStructure mfiLoginReadyingDrawStructure, MfiLoginReadyingDrawStructure.FailureObserver.Type type) {
        showWarningDialog(type.getStringRes()).setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.3
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                mfiLoginReadyingDrawStructure.actShownFailure();
                return true;
            }
        });
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure.FailureObserver
    public void onMfiLoginReadyingFailureDetail(final MfiLoginReadyingDrawStructure mfiLoginReadyingDrawStructure, MfiLoginReadyingDrawStructure.FailureObserver.Type type, ModelErrorInfo modelErrorInfo) {
        String str;
        String string;
        str = "";
        if (modelErrorInfo != null) {
            String errorCode = modelErrorInfo.getErrorCode();
            String string2 = errorCode != null ? getString(R.string.dlg_error_code, new Object[]{errorCode}) : "";
            String mfcErrorCode = modelErrorInfo.getMfcErrorCode();
            string = mfcErrorCode != null ? getString(R.string.dlg_error_code_mfc, new Object[]{mfcErrorCode}) : "";
            str = string2;
        } else {
            string = "";
        }
        showWarningDialog(getString(type.getStringRes()) + str + string).setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.4
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                mfiLoginReadyingDrawStructure.actShownFailure();
                return true;
            }
        });
    }
}
