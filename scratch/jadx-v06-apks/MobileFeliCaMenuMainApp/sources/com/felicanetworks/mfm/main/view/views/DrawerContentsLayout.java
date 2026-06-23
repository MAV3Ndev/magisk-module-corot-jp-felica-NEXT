package com.felicanetworks.mfm.main.view.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;

/* JADX INFO: loaded from: classes.dex */
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

        DrawerBehavior onSelect(DrawerItemType drawerItemType);
    }

    public DrawerContentsLayout(Context context) {
        super(context);
    }

    public DrawerContentsLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setOnSelectDrawerItemListener(OnSelectDrawerItemListener onSelectDrawerItemListener) {
        this.mListener = onSelectDrawerItemListener;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.DrawerContentsLayout$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType;

        static {
            int[] iArr = new int[DrawerItemType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType = iArr;
            try {
                iArr[DrawerItemType.CARD_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerItemType.MODEL_CHANGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerItemType.MFI_LOGIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[DrawerItemType.MFI_LOGIN_SWITCHING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setDrawerItemEnable(DrawerItemType drawerItemType, boolean z) {
        View viewFindViewById;
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$DrawerContentsLayout$DrawerItemType[drawerItemType.ordinal()];
        if (i == 1) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_card);
        } else if (i == 2) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_model);
        } else if (i == 3) {
            viewFindViewById = findViewById(R.id.ll_drawer_item_mfi_login);
        } else {
            viewFindViewById = i != 4 ? null : findViewById(R.id.ll_drawer_item_mfi_login_switching);
        }
        if (viewFindViewById != null) {
            if (z) {
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
    public void onClick(View view) {
        DrawerLayout drawerLayout;
        OnSelectDrawerItemListener.DrawerBehavior drawerBehaviorOnSelect = OnSelectDrawerItemListener.DrawerBehavior.KEEP;
        if (view != null && this.mListener != null) {
            switch (view.getId()) {
                case R.id.ll_drawer_item_card /* 2131362114 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_READER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.CARD_ID);
                    break;
                case R.id.ll_drawer_item_faq /* 2131362115 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.FAQ_ID);
                    break;
                case R.id.ll_drawer_item_mfi_login /* 2131362116 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_RECOMMEND_LOGIN_ON_DRAWER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MFI_LOGIN);
                    break;
                case R.id.ll_drawer_item_mfi_login_switching /* 2131362117 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_CHANGE_ACCOUNT_ON_DRAWER, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MFI_LOGIN_SWITCHING);
                    break;
                case R.id.ll_drawer_item_model /* 2131362118 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_HOWTO_CHANGE_MODEL, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MODEL_CHANGE);
                    break;
                case R.id.ll_drawer_item_myservice /* 2131362119 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_MYSERVICE, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.MYSERVICE_ID);
                    break;
                case R.id.ll_drawer_item_notice /* 2131362120 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_NOTICE, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.NOTICE_ID);
                    break;
                case R.id.ll_drawer_item_recommend /* 2131362121 */:
                    AnalysisManager.stamp(MfmStamp.Event.ACTION_NAVI_RECOMMEND, new Object[0]);
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.RECOMMEND_ID);
                    break;
                case R.id.ll_drawer_item_setup /* 2131362122 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.SETUP_ID);
                    break;
                case R.id.ll_drawer_item_support /* 2131362123 */:
                    drawerBehaviorOnSelect = this.mListener.onSelect(DrawerItemType.SUPPORT_ID);
                    break;
            }
        }
        if ((getContext() instanceof Activity) && (drawerLayout = (DrawerLayout) ((Activity) getContext()).findViewById(R.id.dl_drawer_layout)) != null && drawerBehaviorOnSelect == OnSelectDrawerItemListener.DrawerBehavior.CLOSE) {
            drawerLayout.closeDrawers();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        DrawerLayout drawerLayout;
        if (keyEvent.getKeyCode() == 4) {
            if (keyEvent.getAction() == 1 && (getContext() instanceof Activity) && (drawerLayout = (DrawerLayout) ((Activity) getContext()).findViewById(R.id.dl_drawer_layout)) != null) {
                drawerLayout.closeDrawers();
            }
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
