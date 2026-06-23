package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.action.IActionLock;
import com.felicanetworks.mfm.main.presenter.action.IActionMenu;
import com.felicanetworks.mfm.main.presenter.agent.IFuncState;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public abstract class PrimaryDrawStructure extends CloseDrawStructure implements IActionMenu, IActionLock, IFuncState, IActionAppResult {
    private boolean _isEnoughExtCardServiceInfo;
    private boolean _isLock;
    private boolean hasNeverLoggedIn;
    private String mfiAccountName;

    public enum LinkType {
        RW_P2P_SETTING,
        NOTES_SITE,
        CHANGE_MODEL,
        OSAIFU_TERMS,
        APP_TERMS,
        E_MONEY_TERMS,
        SCREEN_LOCK_SETTING,
        OSAIFULIFE_PLUS_DL_URL,
        OSAIFULIFE_PLUS_DL_TRAFFIC_URL,
        OSAIFULIFE_PLUS_DL_EDY_URL,
        OSAIFULIFE_PLUS_DL_NANACO_URL,
        OSAIFULIFE_PLUS_DL_WAON_URL,
        LINK_TYPE_MAX
    }

    protected PrimaryDrawStructure(StructureType structureType, boolean z, boolean z2, boolean z3, String str) {
        super(structureType);
        this._isLock = z;
        this._isEnoughExtCardServiceInfo = z2;
        this.hasNeverLoggedIn = z3;
        this.mfiAccountName = str;
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actMyService() {
        StateController.postStateEvent(StateMachine.Event.EV_MYSERVICE, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actRecommend() {
        StateController.postStateEvent(StateMachine.Event.EV_RECOMMEND, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actMfiLogin() {
        StateController.postStateEvent(StateMachine.Event.EV_MFI_LOGIN, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actNotice() {
        StateController.postStateEvent(StateMachine.Event.EV_NOTICE_LIST, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actReader() {
        StateController.postStateEvent(StateMachine.Event.EV_CARD_READER, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actFaq() {
        StateController.postStateEvent(StateMachine.Event.EV_FAQ, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actSetting() {
        StateController.postStateEvent(StateMachine.Event.EV_SETTING, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionMenu
    public final void actSupportMenu() {
        StateController.postStateEvent(StateMachine.Event.EV_SUPPORT_MENU, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionLock
    public final void actLock() {
        StateController.postStateEvent(StateMachine.Event.EV_LOCK_SETTING, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncState
    public final boolean isFelicaLock() {
        return this._isLock;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncState
    public boolean hasNeverLoggedIn() {
        return this.hasNeverLoggedIn;
    }

    public String getMfiAccountName() {
        return this.mfiAccountName;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncState
    public final boolean isScreenLock(Context context) {
        return Settings.isScreenLock(context);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncState
    public final boolean hasNFC() {
        return PresenterData.getInstance().hasNFC();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncState
    public final boolean isEnoughExtCardServiceInfo() {
        return this._isEnoughExtCardServiceInfo;
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int i, Intent intent) {
        if (i != -1) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return;
                        }
                    }
                }
            }
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_ACCOUNT_CANCEL, this, new Object[0]);
            return;
        }
        StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_ACCOUNT_COMPLETE, this, new Object[0]);
    }

    public boolean isProvideChangeModel() {
        try {
            String str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_HOW_TO_CHANGE_MODEL);
            if (str != null) {
                return !str.isEmpty();
            }
            return false;
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.structure.PrimaryDrawStructure$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType;

        static {
            int[] iArr = new int[LinkType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType = iArr;
            try {
                iArr[LinkType.RW_P2P_SETTING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.NOTES_SITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.CHANGE_MODEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFU_TERMS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.APP_TERMS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.E_MONEY_TERMS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.SCREEN_LOCK_SETTING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFULIFE_PLUS_DL_URL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFULIFE_PLUS_DL_TRAFFIC_URL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFULIFE_PLUS_DL_EDY_URL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFULIFE_PLUS_DL_NANACO_URL.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[LinkType.OSAIFULIFE_PLUS_DL_WAON_URL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public Intent getDefaultIntent(LinkType linkType) {
        Intent uri;
        String str;
        Intent intent = null;
        str = null;
        str = null;
        String str2 = null;
        try {
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$structure$PrimaryDrawStructure$LinkType[linkType.ordinal()]) {
                case 1:
                    uri = Intent.parseUri((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_URI_NFC_SETTING), 1);
                    break;
                case 2:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_NOTES_OF_READ_EX_IC_CARD);
                    str2 = str;
                    uri = null;
                    break;
                case 3:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_HOW_TO_CHANGE_MODEL);
                    str2 = str;
                    uri = null;
                    break;
                case 4:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_SECURE_ELEMENT_F);
                    str2 = str;
                    uri = null;
                    break;
                case 5:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_MFM_FOR_BROWSER);
                    str2 = str;
                    uri = null;
                    break;
                case 6:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_EMONEY);
                    str2 = str;
                    uri = null;
                    break;
                case 7:
                    uri = Intent.parseUri((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_SCREEN_LOCK_SETTING), 1);
                    break;
                case 8:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_URL);
                    str2 = str;
                    uri = null;
                    break;
                case 9:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_TRAFFIC_URL);
                    str2 = str;
                    uri = null;
                    break;
                case 10:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_EDY_URL);
                    str2 = str;
                    uri = null;
                    break;
                case 11:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_NANACO_URL);
                    str2 = str;
                    uri = null;
                    break;
                case 12:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_WAON_URL);
                    str2 = str;
                    uri = null;
                    break;
                default:
                    uri = null;
                    break;
            }
            if (str2 == null) {
                return uri;
            }
            try {
                return new Intent("android.intent.action.VIEW", Uri.parse(str2));
            } catch (Exception e) {
                intent = uri;
                e = e;
                LogUtil.warning(e);
                return intent;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }
}
