package com.felicanetworks.mfm.memory_clear;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.memory_clear.MemoryClearConstants;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearMfiCardDeleteFragment extends MemoryClearBaseFragment implements View.OnClickListener {
    private static final String ARGS_MFI_TYPE2CARD_LIST_SERVICE_ID = "mfiType2CardList_service_id";
    private int mOrientation = 1;

    private int getLayout(int orientation) {
        return orientation == 1 ? R.layout.fragment_memory_clear_mfi_card_delete : R.layout.fragment_memory_clear_mfi_card_delete_land;
    }

    public static MemoryClearMfiCardDeleteFragment newInstance(List<String> mfiType2CardServiceNameList) {
        MemoryClearMfiCardDeleteFragment memoryClearMfiCardDeleteFragment = new MemoryClearMfiCardDeleteFragment();
        StringBuilder sb = new StringBuilder();
        for (String str : mfiType2CardServiceNameList) {
            sb.append("・");
            sb.append(str);
            sb.append("\n");
        }
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_MFI_TYPE2CARD_LIST_SERVICE_ID, sb.toString());
        memoryClearMfiCardDeleteFragment.setArguments(bundle);
        return memoryClearMfiCardDeleteFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mOrientation = activity.getResources().getConfiguration().orientation;
        }
        return inflater.inflate(getLayout(this.mOrientation), container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setViewItem(view);
    }

    private void setViewItem(View view) {
        ((Button) view.findViewById(R.id.memory_clear_mfi_card_delete_next)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.memory_clear_mfi_card_delete_cancel)).setOnClickListener(this);
        TextView textView = (TextView) view.findViewById(R.id.memory_clear_mfi_card_delete_body);
        Bundle arguments = getArguments();
        if (arguments != null) {
            textView.setText(arguments.getString(ARGS_MFI_TYPE2CARD_LIST_SERVICE_ID));
        } else {
            textView.setVisibility(8);
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            View view = getView();
            if (view != null) {
                ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.root);
                boolean zIsEnabled = ((Button) viewGroup.findViewById(R.id.memory_clear_mfi_card_delete_next)).isEnabled();
                viewGroup.removeAllViews();
                View viewInflate = getLayoutInflater().inflate(getLayout(this.mOrientation), viewGroup);
                setViewItem(viewInflate);
                if (zIsEnabled) {
                    return;
                }
                ((Button) viewInflate.findViewById(R.id.memory_clear_mfi_card_delete_next)).setEnabled(false);
            }
        }
    }

    @Override // com.felicanetworks.mfm.memory_clear.MemoryClearBaseFragment, android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memory_clear_mfi_card_delete_next) {
            cardDelete();
        } else if (id == R.id.memory_clear_mfi_card_delete_cancel) {
            cancelMemoryClear();
        }
        super.onClick(v);
    }

    private void cardDelete() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).sendMessage(MemoryClearConstants.EVENT_ID.CARD_DELETE);
        }
    }

    private void cancelMemoryClear() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MemoryClearActivity) {
            ((MemoryClearActivity) activity).cancelMemoryClear(MemoryClearConstants.EVENT_ID.SHOW_CARD_DELETE);
        }
    }
}
