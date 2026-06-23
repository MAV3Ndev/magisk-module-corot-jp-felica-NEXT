package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.RecommendDetailFragment;

/* JADX INFO: loaded from: classes.dex */
public class RecommendDetailActivity extends BranchBaseActivity {
    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            getSupportActionBar().setTitle(R.string.toolbar_title_recommend_detail);
            dispatchLatestStructure(getCurrentRequest());
        } catch (Exception e) {
            notifyException(e);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean zOnOptionsItemSelected = true;
        try {
            if (menuItem.getItemId() == 16908332) {
                Structure currentRequest = getCurrentRequest();
                if (currentRequest != null && (currentRequest instanceof CloseDrawStructure)) {
                    ((CloseDrawStructure) currentRequest).actClose();
                }
            } else {
                zOnOptionsItemSelected = super.onOptionsItemSelected(menuItem);
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return zOnOptionsItemSelected;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.RecommendDetailActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.RECOMMEND_DETAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.CENTRAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.READER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new RecommendDetailFragment());
                fragmentTransactionBeginTransaction.commit();
            } else {
                super.dispatchLatestStructure(structure);
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 2) {
            Intent intent = new Intent(this, (Class<?>) CentralActivity.class);
            intent.setFlags(603979776);
            startActivity(intent);
        } else {
            if (i == 3) {
                Intent intent2 = new Intent(this, (Class<?>) ExtIcCardReaderActivity.class);
                intent2.setFlags(603979776);
                startActivity(intent2);
                return;
            }
            super.dispatchStructure(structure);
        }
    }
}
