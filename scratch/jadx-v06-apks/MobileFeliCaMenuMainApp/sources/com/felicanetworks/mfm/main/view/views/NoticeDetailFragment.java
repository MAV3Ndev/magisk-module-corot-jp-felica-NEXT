package com.felicanetworks.mfm.main.view.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.NoticeDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDetailFragment extends BaseFragment {

    private class LaunchAppListener implements View.OnClickListener {
        private LaunchAppListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                Structure structure = NoticeDetailFragment.this.getStructure();
                if (structure instanceof NoticeDetailDrawStructure) {
                    if (view instanceof FrameLayout) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_MESSAGE_LINK_AREA, ((NoticeDetailDrawStructure) structure).getNoticeInfo());
                    } else if (view instanceof Button) {
                        AnalysisManager.stamp(MfmStamp.Event.ACTION_MESSAGE_LINK_BUTTON, ((NoticeDetailDrawStructure) structure).getNoticeInfo());
                    }
                    try {
                        NoticeDetailFragment.this.startActivity(((NoticeDetailDrawStructure) structure).getNoticeInfo().getLinkIntent());
                        ((NoticeDetailDrawStructure) structure).actLaunchExternalApp();
                    } catch (Exception unused) {
                        NoticeDetailFragment.this.showWarningDialog(R.string.dlg_warning_ext_app_start_failed);
                        AnalysisManager.stamp(MfmStamp.Event.SCREEN_WARNING, "dlg_warning_ext_app_start_failed");
                    }
                }
            } catch (Exception e) {
                NoticeDetailFragment.this.notifyException(e);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_notice_detail, viewGroup, false);
        try {
            NoticeInfoAgent noticeInfo = ((NoticeDetailDrawStructure) getStructure()).getNoticeInfo();
            stampWithJudgeActivityState(MfmStamp.Event.SCREEN_W1_05_01, noticeInfo);
            String title = noticeInfo.getTitle();
            if (title != null && !title.isEmpty()) {
                ((TextView) viewInflate.findViewById(R.id.tv_notice_title)).setText(title);
            }
            String message = noticeInfo.getMessage();
            if (message != null && !message.isEmpty()) {
                ((TextView) viewInflate.findViewById(R.id.tv_notice_text)).setText(message);
            }
            final ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_notice_icon);
            noticeInfo.orderIconImg(new NoticeInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeDetailFragment.1
                @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.OrderImgListener
                public void onComplete(Bitmap bitmap) {
                    if (NoticeDetailFragment.this.getActivity() == null || NoticeDetailFragment.this.getActivity().isFinishing() || bitmap == null) {
                        return;
                    }
                    imageView.setImageBitmap(bitmap);
                }
            });
            ((FrameLayout) viewInflate.findViewById(R.id.fl_message_area)).setOnClickListener(new LaunchAppListener());
            final ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.iv_notice_image);
            noticeInfo.orderMsgImg(new NoticeInfoAgent.OrderImgListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeDetailFragment.2
                @Override // com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent.OrderImgListener
                public void onComplete(Bitmap bitmap) {
                    if (NoticeDetailFragment.this.getActivity() == null || NoticeDetailFragment.this.getActivity().isFinishing() || bitmap == null) {
                        return;
                    }
                    ((TextView) NoticeDetailFragment.this.getView().findViewById(R.id.tv_notice_text)).setVisibility(8);
                    imageView2.setImageBitmap(bitmap);
                    imageView2.setVisibility(0);
                }
            });
            String btnMsg = noticeInfo.getBtnMsg();
            if (btnMsg != null && !btnMsg.isEmpty()) {
                Button button = (Button) viewInflate.findViewById(R.id.b_detail_button);
                button.setText(btnMsg);
                button.setOnClickListener(new LaunchAppListener());
            } else {
                viewInflate.findViewById(R.id.ll_detail_button).setVisibility(8);
            }
        } catch (Exception e) {
            notifyException(e);
        }
        return viewInflate;
    }
}
