package com.felicanetworks.mfm.main.view.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;

/* JADX INFO: loaded from: classes3.dex */
public class DrawerContentsLayout extends LinearLayout implements View.OnClickListener {
    private OnSelectDrawerItemListener mListener;

    public enum DrawerItemType {
        MYSERVICE_ID,
        RECOMMEND_ID,
        NOTICE_ID,
        CARD_ID,
        FAQ_ID,
        MODEL_CHANGE,
        SUPPORT_ID,
        SETUP_ID,
        MFI_LOGIN,
        MFI_LOGIN_SWITCHING
    }

    public interface OnSelectDrawerItemListener {

        public enum DrawerBehavior {
            CLOSE,
            KEEP
        }

        DrawerBehavior onSelect(DrawerItemType type);
    }

    public DrawerContentsLayout(Context context) {
        super(context);
    }

    public DrawerContentsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSelectDrawerItemListener(OnSelectDrawerItemListener listener) {
        this.mListener = listener;
    }

    public void setDrawerItemEnable(DrawerItemType drawerItemType, boolean flag) {
        View viewFindViewById;
        int iOrdinal = drawerItemType.ordinal();
        if (iOrdinal == 3) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_card);
        } else if (iOrdinal == 5) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_model);
        } else if (iOrdinal == 8) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_mfi_login);
        } else {
            viewFindViewById = iOrdinal != 9 ? null : findViewById(R.id.ll_drawer_item_mfi_login_switching);
        }
        if (viewFindViewById != null) {
            if (flag) {
                viewFindViewById.setVisibility(0);
            } else {
                viewFindViewById.setVisibility(8);
            }
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ((LinearLayout) findViewById(R.id.ll_drawer_item_myservice)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_recommend)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_notice)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_card)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_faq)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_model)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_support)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_setup)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_mfi_login)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_drawer_item_mfi_login_switching)).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        DrawerLayout drawerLayout;
        OnSelectDrawerItemListener.DrawerBehavior drawerBehaviorOnSelect = OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        if (v != null && this.mListener != null) {
            switch (v.getId()) {
                case R.id.ll_drawer_item_card /* 2131362145 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_READER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.CARD_ID);
                    break;
                case R.id.ll_drawer_item_faq /* 2131362146 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.FAQ_ID);
                    break;
                case R.id.ll_drawer_item_mfi_login /* 2131362147 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOMMEND_LOGIN_ON_DRAWER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MFI_LOGIN);
                    break;
                case R.id.ll_drawer_item_mfi_login_switching /* 2131362148 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_CHANGE_ACCOUNT_ON_DRAWER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MFI_LOGIN_SWITCHING);
                    break;
                case R.id.ll_drawer_item_model /* 2131362149 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_HOWTO_CHANGE_MODEL, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MODEL_CHANGE);
                    break;
                case R.id.ll_drawer_item_myservice /* 2131362150 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_MYSERVICE, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MYSERVICE_ID);
                    break;
                case R.id.ll_drawer_item_notice /* 2131362151 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_NOTICE, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.NOTICE_ID);
                    break;
                case R.id.ll_drawer_item_recommend /* 2131362152 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_RECOMMEND, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.RECOMMEND_ID);
                    break;
                case R.id.ll_drawer_item_setup /* 2131362153 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.SETUP_ID);
                    break;
                case R.id.ll_drawer_item_support /* 2131362154 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.SUPPORT_ID);
                    break;
            }
        }
        if ((getContext() instanceof Activity) && (drawerLayout = (DrawerLayout) ((Activity) getContext()).findViewById(R.id.dl_drawer_layout)) != null && drawerBehaviorOnSelect == OnSelectDrawerItemListener.DrawerBehavior.CLOSE) {
            drawerLayout.closeDrawers();
        }
    }
}
