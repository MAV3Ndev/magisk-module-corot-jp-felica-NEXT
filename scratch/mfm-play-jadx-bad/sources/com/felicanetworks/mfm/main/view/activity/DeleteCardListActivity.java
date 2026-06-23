package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.DeleteCardListFragment;
import com.felicanetworks.mfm.main.view.views.DialogFactory;

/* JADX INFO: loaded from: classes3.dex */
public class DeleteCardListActivity extends BranchBaseActivity {
    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.delete_card_list_title_text);
            }
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

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (structure.getType() == StructureType.DELETE_CARD_LIST) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new DeleteCardListFragment());
                fragmentTransactionBeginTransaction.commit();
                return;
            }
            super.dispatchLatestStructure(structure);
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.DeleteCardListActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.DATA_LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DELETE_CARD_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CENTRAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 1) {
            Intent intent = new Intent(this, (Class<?>) ServiceListActivity.class);
            intent.setFlags(603979776);
            intent.putExtra(EXTRA_KEY_CALLING_ACTIVITY, getClass().getName());
            startActivity(intent);
            return;
        }
        if (i == 2) {
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new DeleteCardListFragment());
            fragmentTransactionBeginTransaction.commit();
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
}
