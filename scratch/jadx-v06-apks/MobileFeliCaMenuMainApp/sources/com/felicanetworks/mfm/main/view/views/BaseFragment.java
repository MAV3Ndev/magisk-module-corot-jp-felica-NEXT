package com.felicanetworks.mfm.main.view.views;

import androidx.fragment.app.Fragment;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseFragment extends Fragment {
    public void showNormalDialog(CustomDialogFragment customDialogFragment) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseActivity baseActivity = (BaseActivity) getActivity();
        customDialogFragment.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
        baseActivity.showNormalDialog(customDialogFragment);
    }

    public void showModelChangeDialog(DepositDialogFragment depositDialogFragment) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseActivity baseActivity = (BaseActivity) getActivity();
        depositDialogFragment.setTargetTag(BaseActivity.TAG_NORMAL_DIALOG);
        baseActivity.showNormalDialog(depositDialogFragment);
    }

    public CustomDialogFragment showWarningDialog(String str) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).showWarningDialog(str);
    }

    public CustomDialogFragment showWarningDialog(int i) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).showWarningDialog(i);
    }

    void notifyException(Exception exc) {
        Structure structure = getStructure();
        if (structure != null) {
            structure.detectException(exc);
        }
    }

    Structure getStructure() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }
        return ((BaseActivity) getActivity()).getCurrentRequest();
    }

    public void stampWithJudgeActivityState(MfmStamp.Event event, Object... objArr) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        AnalysisManager.stamp(event, objArr);
    }
}
