package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

/* JADX INFO: loaded from: classes3.dex */
public abstract class DrawerBaseActivity extends BaseActivity implements DrawerContentsLayout.OnSelectDrawerItemListener, MfiLoginReadyingDrawStructure.FailureObserver {
    private Insets mInsets = Insets.NONE;
    public Toolbar mToolbar;

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar = toolbar;
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout);
        if (drawerLayout != null) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, this.mToolbar, R.string.toolbar_title_drawer_open, R.string.toolbar_title_drawer_close) { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.1
                @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                public void onDrawerOpened(View drawerView) {
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_SHOW_DRAWER, new Object[0]);
                    super.onDrawerOpened(drawerView);
                }
            };
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
        adjustViewToSystemBar();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToSystemBar();
    }

    public DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior onSelect(DrawerContentsLayout.DrawerItemType type) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[type.ordinal()];
        if (i == 1) {
            if (getCurrentRequest() instanceof IActionMenu) {
                ((IActionMenu) getCurrentRequest()).actMfiLogin();
            }
            return DrawerContentsLayout.OnSelectDrawerItemListener.DrawerBehavior.CLOSE;
        }
        if (i == 2) {
            activityResultLaunch(new Intent(getApplicationContext(), (Class<?>) MfiAccountSwitchingActivity.class));
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

    public boolean dispatchBackKeyToDrawer() {
        try {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout);
            if (!drawerLayout.isDrawerOpen(findViewById(R.id.left_drawer))) {
                return false;
            }
            drawerLayout.closeDrawers();
            return true;
        } catch (Exception e) {
            notifyException(e);
            return false;
        }
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
    protected void dispatchStructure(final Structure structure) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            showDialog(DialogFactory.createCircleProgressDialog(this));
            ((MfiLoginReadyingDrawStructure) structure).setFailureObserver(this);
        } else {
            if (i == 2 || i == 3) {
                finish();
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(1, 0, R.anim.fade_out);
                    return;
                } else {
                    overridePendingTransition(0, R.anim.fade_out);
                    return;
                }
            }
            super.dispatchStructure(structure);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructureOnResume(Structure structure) {
        int i = AnonymousClass5.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            ((MfiLoginReadyingDrawStructure) structure).setFailureObserver(this);
            return;
        }
        if (i == 2 || i == 3) {
            finish();
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(1, 0, R.anim.fade_out);
                return;
            } else {
                overridePendingTransition(0, R.anim.fade_out);
                return;
            }
        }
        super.dispatchStructureOnResume(structure);
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure.FailureObserver
    public void onMfiLoginReadyingFailure(final MfiLoginReadyingDrawStructure structure, MfiLoginReadyingDrawStructure.FailureObserver.Type type) {
        showWarningDialog(type.getStringRes()).setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.3
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                structure.actShownFailure();
                return true;
            }
        });
    }

    @Override // com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure.FailureObserver
    public void onMfiLoginReadyingFailureDetail(final MfiLoginReadyingDrawStructure structure, MfiLoginReadyingDrawStructure.FailureObserver.Type type, ModelErrorInfo errorInfo) {
        String str;
        String errorCode;
        String string;
        String string2 = "";
        if (errorInfo == null) {
            str = "";
            errorCode = str;
        } else {
            errorCode = errorInfo.getErrorCode();
            if (errorCode == null) {
                string = "";
            } else {
                string = getString(R.string.dlg_error_code, new Object[]{errorCode});
            }
            String mfcErrorCode = errorInfo.getMfcErrorCode();
            if (mfcErrorCode != null) {
                string2 = getString(R.string.dlg_error_code_mfc, new Object[]{mfcErrorCode});
            }
            str = string2;
            string2 = string;
        }
        showWarningDialog(getString(type.getStringRes()) + string2 + str, errorCode).setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity.4
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                structure.actShownFailure();
                return true;
            }
        });
    }

    private void adjustViewToSystemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dl_drawer_layout), new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.activity.DrawerBaseActivity$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m415x28c6f960(view, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToSystemBar$0$com-felicanetworks-mfm-main-view-activity-DrawerBaseActivity, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m415x28c6f960(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        View viewFindViewById = view.findViewById(R.id.left_drawer);
        if (this.mInsets.top != insets.top) {
            Toolbar toolbar = this.mToolbar;
            toolbar.setPadding(toolbar.getPaddingLeft(), (this.mToolbar.getPaddingTop() + insets.top) - this.mInsets.top, this.mToolbar.getPaddingRight(), this.mToolbar.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams();
            layoutParams.height = (layoutParams.height + insets.top) - this.mInsets.top;
            this.mToolbar.setLayoutParams(layoutParams);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams();
            marginLayoutParams.topMargin = (marginLayoutParams.topMargin + insets.top) - this.mInsets.top;
            viewFindViewById.setLayoutParams(marginLayoutParams);
        }
        if (this.mInsets.bottom != insets.bottom) {
            View viewFindViewById2 = view.findViewById(R.id.ll_drawer_under_line);
            viewFindViewById2.setPadding(viewFindViewById2.getPaddingLeft(), viewFindViewById2.getPaddingTop(), viewFindViewById2.getPaddingRight(), (viewFindViewById2.getPaddingBottom() + insets.bottom) - this.mInsets.bottom);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (this.mInsets.left != insets.left) {
            marginLayoutParams2.leftMargin = (marginLayoutParams2.leftMargin + insets.left) - this.mInsets.left;
        }
        if (this.mInsets.right != insets.right) {
            marginLayoutParams2.rightMargin = (marginLayoutParams2.rightMargin + insets.right) - this.mInsets.right;
        }
        view.setLayoutParams(marginLayoutParams2);
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
