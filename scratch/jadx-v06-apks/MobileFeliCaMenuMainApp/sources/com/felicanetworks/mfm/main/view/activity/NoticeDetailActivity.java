package com.felicanetworks.mfm.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.CloseDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.NoticeDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import com.felicanetworks.mfm.main.view.views.CustomDialogFragment;
import com.felicanetworks.mfm.main.view.views.NoticeDetailFragment;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDetailActivity extends BranchBaseActivity {
    @Override // com.felicanetworks.mfm.main.view.activity.BranchBaseActivity, com.felicanetworks.mfm.main.view.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.toolbar_title_notice_detail);
            }
            Structure currentRequest = getCurrentRequest();
            if (currentRequest != null) {
                dispatchLatestStructure(currentRequest);
            }
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.activity.NoticeDetailActivity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType;

        static {
            int[] iArr = new int[StructureType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType = iArr;
            try {
                iArr[StructureType.NOTICE_DETAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.NOTICE_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[StructureType.DATA_LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchLatestStructure(Structure structure) {
        try {
            if (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()] == 1) {
                if (((NoticeDetailDrawStructure) structure).getNoticeInfo().isExpiration()) {
                    showExpiredWarningDialog((NoticeDetailDrawStructure) structure);
                } else {
                    showContentView(structure);
                }
            } else {
                super.dispatchLatestStructure(structure);
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }

    @Override // com.felicanetworks.mfm.main.view.activity.BaseActivity
    protected void dispatchStructure(Structure structure) {
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$StructureType[structure.getType().ordinal()];
        if (i == 2) {
            Intent intent = new Intent(this, (Class<?>) NoticeListActivity.class);
            intent.setFlags(603979776);
            startActivity(intent);
        } else {
            if (i == 3) {
                Intent intent2 = new Intent(this, (Class<?>) ServiceListActivity.class);
                intent2.setFlags(603979776);
                startActivity(intent2);
                return;
            }
            super.dispatchStructure(structure);
        }
    }

    private void showContentView(Structure structure) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fl_content_fragment, new NoticeDetailFragment());
        fragmentTransactionBeginTransaction.commit();
    }

    public void showExpiredWarningDialog(final NoticeDetailDrawStructure noticeDetailDrawStructure) {
        CustomDialogFragment customDialogFragmentShowWarningDialog = showWarningDialog(R.string.dlg_warning_expiration_notice);
        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_expiration_notice");
        customDialogFragmentShowWarningDialog.setPositiveButtonListener(new CustomDialogFragment.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.activity.NoticeDetailActivity.1
            @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment.OnClickListener
            public boolean onClick() {
                noticeDetailDrawStructure.actClose();
                return true;
            }
        });
    }
}
