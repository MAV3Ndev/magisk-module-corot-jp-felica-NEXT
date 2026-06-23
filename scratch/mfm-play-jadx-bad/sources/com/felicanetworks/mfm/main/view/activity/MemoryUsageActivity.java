package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;
import com.felicanetworks.mfm.main.view.views.MemoryUsageFragment;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryUsageActivity extends BranchBaseActivity {
    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.settings_text_memory_usage);
        try {
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
                if (currentRequest != null && (currentRequest instanceof CloseDrawStructure)) {
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.MemoryUsageActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.MEMORY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.SUPPORT_MENU.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.REQUIRE_APP_UPGRADE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new MemoryUsageFragment());
                fragmentTransactionBeginTransaction.commit();
                return;
            }
            super.dispatchLatestStructure(structure);
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 2) {
            Intent intent = new Intent(this, (Class<?>) SupportMenuActivity.class);
            intent.setFlags(603979776);
            startActivity(intent);
        } else if (i == 3) {
            showRequireAppUpgrade((RequireAppUpgradeDrawStructure) structure);
        } else {
            super.dispatchStructure(structure);
        }
    }

    public CustomDialogFragment showProgressDialog() {
        CustomDialogFragment customDialogFragmentCreateCircleProgressDialog = DialogFactory.createCircleProgressDialog(getApplicationContext());
        customDialogFragmentCreateCircleProgressDialog.setTargetTag(TAG_NORMAL_DIALOG);
        showNormalDialog(customDialogFragmentCreateCircleProgressDialog);
        return customDialogFragmentCreateCircleProgressDialog;
    }
}
