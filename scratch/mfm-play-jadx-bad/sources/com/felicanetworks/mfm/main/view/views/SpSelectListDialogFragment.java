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

/* JADX INFO: loaded from: classes3.dex */
public class SpSelectListDialogFragment extends CustomDialogFragment {
    private OnClickListener mPositiveButtonListener = null;
    private AdapterView.OnItemClickListener mListItemClickListener = null;
    private List<MyServiceInfoAgent> mServices = null;

    public interface OnClickListener {
        boolean onClick();
    }

    public void setPositiveButtonListener(OnClickListener listener) {
        this.mPositiveButtonListener = listener;
    }

    public void setListItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListItemClickListener = listener;
    }

    @Override // com.felicanetworks.mfm.main.view.views.CustomDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() == null) {
            return super.onCreateDialog(savedInstanceState);
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
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (SpSelectListDialogFragment.this.mListItemClickListener != null) {
                        SpSelectListDialogFragment.this.mListItemClickListener.onItemClick(parent, view, position, id);
                    }
                    if (SpSelectListDialogFragment.this.getShowsDialog()) {
                        SpSelectListDialogFragment.this.dismiss();
                    }
                }
            });
        }
        ((TextView) dialog.findViewById(R.id.tv_positive_button)).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.SpSelectListDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
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

    void setMyServiceInfoAgentList(List<MyServiceInfoAgent> services) {
        this.mServices = services;
    }

    private static class SpSelectListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<MyServiceInfoAgent> mServices;

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        public static class SpSelectListViewHolder {
            public ImageView mServiceIcon;
            public TextView mServiceName;

            public SpSelectListViewHolder(View view) {
                this.mServiceIcon = (ImageView) view.findViewById(R.id.service_icon);
                this.mServiceName = (TextView) view.findViewById(R.id.service_name);
            }
        }

        public SpSelectListAdapter(Context context, List<MyServiceInfoAgent> services) {
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mServices = services;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mServices.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.mServices.get(position);
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            SpSelectListViewHolder spSelectListViewHolder;
            try {
                MyServiceInfoAgent myServiceInfoAgent = this.mServices.get(position);
                if (convertView == null) {
                    convertView = this.mInflater.inflate(R.layout.list_item_sp_select_service, parent, false);
                    spSelectListViewHolder = new SpSelectListViewHolder(convertView);
                    convertView.setTag(spSelectListViewHolder);
                } else {
                    spSelectListViewHolder = (SpSelectListViewHolder) convertView.getTag();
                }
                updateListItem(parent, spSelectListViewHolder, myServiceInfoAgent);
            } catch (Exception unused) {
            }
            return convertView;
        }

        private void updateListItem(final ViewGroup parent, final SpSelectListViewHolder viewHolder, final MyServiceInfoAgent info) {
            viewHolder.mServiceIcon.setImageDrawable(null);
            viewHolder.mServiceName.setText("");
            viewHolder.mServiceIcon.setOutlineProvider(new ViewOutlineProvider() { // from class: com.felicanetworks.mfm.main.view.views.SpSelectListDialogFragment.SpSelectListAdapter.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) (parent.getResources().getDisplayMetrics().density * 3.0f));
                }
            });
            viewHolder.mServiceIcon.setClipToOutline(true);
            viewHolder.mServiceIcon.setImageBitmap(info.getCardFaceImage());
            String serviceName = info.getServiceName();
            if (serviceName == null || serviceName.isEmpty()) {
                return;
            }
            viewHolder.mServiceName.setText(serviceName);
        }
    }
}
