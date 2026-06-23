package com.felicanetworks.mfm.main.view.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SpSelectListDialogFragment extends CustomDialogFragment {
    private OnClickListener mPositiveButtonListener = null;
    private AdapterView.OnItemClickListener mListItemClickListener = null;
    private List<MyServiceInfoAgent> mServices = null;

    public interface OnClickListener {
        boolean onClick();
    }

    public void setPositiveButtonListener(OnClickListener onClickListener) {
        this.mPositiveButtonListener = onClickListener;
    }

    public void setListItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mListItemClickListener = onItemClickListener;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (getActivity() == null) {
            return super.onCreateDialog(bundle);
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_sp_select_list);
        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.width = (int) (((double) getResources().getDisplayMetrics().widthPixels) * 0.8d);
            dialog.getWindow().setAttributes(attributes);
        }
        if (getContext() != null) {
            SpSelectListAdapter spSelectListAdapter = new SpSelectListAdapter(getContext(), this.mServices);
            ScrollListView scrollListView = (ScrollListView) dialog.findViewById(R.id.service_list);
            scrollListView.setAdapter((ListAdapter) spSelectListAdapter);
            scrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SpSelectListDialogFragment.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (SpSelectListDialogFragment.this.mListItemClickListener != null) {
                        SpSelectListDialogFragment.this.mListItemClickListener.onItemClick(adapterView, view, i, j);
                    }
                    if (SpSelectListDialogFragment.this.getShowsDialog()) {
                        SpSelectListDialogFragment.this.dismiss();
                    }
                }
            });
        }
        ((TextView) dialog.findViewById(R.id.tv_positive_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SpSelectListDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SpSelectListDialogFragment.this.mPositiveButtonListener != null) {
                    if (SpSelectListDialogFragment.this.mPositiveButtonListener.onClick() && SpSelectListDialogFragment.this.getShowsDialog()) {
                        SpSelectListDialogFragment.this.dismiss();
                        return;
                    }
                    return;
                }
                if (SpSelectListDialogFragment.this.getShowsDialog()) {
                    SpSelectListDialogFragment.this.dismiss();
                }
            }
        });
        setCancelable(false);
        return dialog;
    }

    void setMyServiceInfoAgentList(List<MyServiceInfoAgent> list) {
        this.mServices = list;
    }

    private static class SpSelectListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<MyServiceInfoAgent> mServices;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public static class SpSelectListViewHolder {
            public ImageView mServiceIcon;
            public TextView mServiceName;

            public SpSelectListViewHolder(View view) {
                this.mServiceIcon = (ImageView) view.findViewById(R.id.service_icon);
                this.mServiceName = (TextView) view.findViewById(R.id.service_name);
            }
        }

        public SpSelectListAdapter(Context context, List<MyServiceInfoAgent> list) {
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mServices = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mServices.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mServices.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            SpSelectListViewHolder spSelectListViewHolder;
            try {
                MyServiceInfoAgent myServiceInfoAgent = this.mServices.get(i);
                if (view == null) {
                    view = this.mInflater.inflate(R.layout.list_item_sp_select_service, viewGroup, false);
                    spSelectListViewHolder = new SpSelectListViewHolder(view);
                    view.setTag(spSelectListViewHolder);
                } else {
                    spSelectListViewHolder = (SpSelectListViewHolder) view.getTag();
                }
                updateListItem(viewGroup, spSelectListViewHolder, myServiceInfoAgent);
            } catch (Exception unused) {
            }
            return view;
        }

        private void updateListItem(final ViewGroup viewGroup, SpSelectListViewHolder spSelectListViewHolder, MyServiceInfoAgent myServiceInfoAgent) {
            spSelectListViewHolder.mServiceIcon.setImageDrawable(null);
            spSelectListViewHolder.mServiceName.setText("");
            spSelectListViewHolder.mServiceIcon.setOutlineProvider(new ViewOutlineProvider() { // from class: com.felicanetworks.mfm.main.view.views.SpSelectListDialogFragment.SpSelectListAdapter.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) (viewGroup.getResources().getDisplayMetrics().density * 3.0f));
                }
            });
            spSelectListViewHolder.mServiceIcon.setClipToOutline(true);
            spSelectListViewHolder.mServiceIcon.setImageBitmap(myServiceInfoAgent.getCardFaceImage());
            String serviceName = myServiceInfoAgent.getServiceName();
            if (serviceName == null || serviceName.isEmpty()) {
                return;
            }
            spSelectListViewHolder.mServiceName.setText(serviceName);
        }
    }
}
