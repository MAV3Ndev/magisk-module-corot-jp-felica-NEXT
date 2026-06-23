package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CardDetailFragment;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;

/* JADX INFO: loaded from: classes3.dex */
public class CardDetailActivity extends BranchBaseActivity {
    private Insets mInsets = Insets.NONE;

    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(this.mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("");
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.detail_popup_colse);
                if (this.mToolbar.getNavigationIcon() != null) {
                    this.mToolbar.getNavigationIcon().mutate().setTint(getResources().getColor(R.color.blue, getTheme()));
                }
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.bgcolor)));
            }
            adjustViewToSystemBarCardDetail();
            dispatchLatestStructure(getCurrentRequest());
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == 16908332) {
                Structure currentRequest = getCurrentRequest();
                if (currentRequest instanceof CloseDrawStructure) {
                    ((CloseDrawStructure) currentRequest).actClose();
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        } catch (Exception e) {
            notifyException(e);
            return true;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.CardDetailActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.CARD_DETAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DATA_LOADING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CENTRAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new CardDetailFragment());
                fragmentTransactionBeginTransaction.commit();
                return;
            }
            super.dispatchLatestStructure(structure);
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToSystemBarCardDetail();
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new CardDetailFragment());
            fragmentTransactionBeginTransaction.commit();
            return;
        }
        if (i == 2) {
            Intent intent = new Intent(this, (Class<?>) ServiceListActivity.class);
            intent.setFlags(603979776);
            intent.putExtra(EXTRA_KEY_CALLING_ACTIVITY, getClass().getName());
            startActivity(intent);
            return;
        }
        if (i == 3) {
            dismissAllDialog();
            finish();
        } else {
            super.dispatchStructure(structure);
        }
    }

    public CustomDialogFragment showProgressDialog() {
        CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = DialogFactory.createCircleProgressDialog(getApplicationContext());
        showNormalDialog(customDialogFragmentCreateCircleProgressDialog);
        return customDialogFragmentCreateCircleProgressDialog;
    }

    private void adjustViewToSystemBarCardDetail() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ll_branch_layout), new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.activity.CardDetailActivity$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m414x5bafa8be(view, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToSystemBarCardDetail$0$com-felicanetworks-mfm-main-view-activity-CardDetailActivity, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m414x5bafa8be(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        if (this.mInsets.top != insets.top) {
            View viewFindViewById = view.findViewById(R.id.ll_status_bar);
            viewFindViewById.setVisibility(0);
            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
            layoutParams.height = (layoutParams.height + insets.top) - this.mInsets.top;
            viewFindViewById.setLayoutParams(layoutParams);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (this.mInsets.left != insets.left) {
            marginLayoutParams.leftMargin = (marginLayoutParams.leftMargin + insets.left) - this.mInsets.left;
        }
        if (this.mInsets.right != insets.right) {
            marginLayoutParams.rightMargin = (marginLayoutParams.rightMargin + insets.right) - this.mInsets.right;
        }
        view.setLayoutParams(marginLayoutParams);
        this.mInsets = insets;
        return windowInsetsCompat;
    }
}
