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

/* JADX INFO: loaded from: classes3.dex */
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
        OSAIFULIFE_PLUS_DL_TRAFFIC_URL,
        OSAIFULIFE_PLUS_DL_EDY_URL,
        OSAIFULIFE_PLUS_DL_NANACO_URL,
        OSAIFULIFE_PLUS_DL_WAON_URL,
        CLEAR_ACCOUNT_URL,
        LINK_TYPE_MAX
    }

    protected PrimaryDrawStructure(StructureType type, boolean isLock, boolean isEnoughExtCardServiceInfo, boolean hasNeverLoggedIn, String mfiAccountName) {
        super(type);
        this._isLock = isLock;
        this._isEnoughExtCardServiceInfo = isEnoughExtCardServiceInfo;
        this.hasNeverLoggedIn = hasNeverLoggedIn;
        this.mfiAccountName = mfiAccountName;
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

    public final void setFelicaLock(boolean lock) {
        this._isLock = lock;
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
    public void actResult(int resultCode, Intent data) {
        if (resultCode != -1) {
            if (resultCode != 0) {
                if (resultCode != 1) {
                    if (resultCode != 2) {
                        if (resultCode != 3) {
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

    public Intent getDefaultIntent(LinkType linkType) {
        Intent uri;
        String str;
        Intent intent = null;
        str = null;
        str = null;
        String str2 = null;
        try {
            switch (linkType) {
                case RW_P2P_SETTING:
                    uri = Intent.parseUri((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_URI_NFC_SETTING), 1);
                    break;
                case NOTES_SITE:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_NOTES_OF_READ_EX_IC_CARD);
                    str2 = str;
                    uri = null;
                    break;
                case CHANGE_MODEL:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_HOW_TO_CHANGE_MODEL);
                    str2 = str;
                    uri = null;
                    break;
                case OSAIFU_TERMS:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_SECURE_ELEMENT_F);
                    str2 = str;
                    uri = null;
                    break;
                case APP_TERMS:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_MFM_FOR_BROWSER);
                    str2 = str;
                    uri = null;
                    break;
                case E_MONEY_TERMS:
                    str = (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DISCLAIMER_EMONEY);
                    str2 = str;
                    uri = null;
                    break;
                case SCREEN_LOCK_SETTING:
                    uri = Intent.parseUri((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_SCREEN_LOCK_SETTING), 1);
                    break;
                case OSAIFULIFE_PLUS_DL_TRAFFIC_URL:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_TRAFFIC_URL);
                    str2 = str;
                    uri = null;
                    break;
                case OSAIFULIFE_PLUS_DL_EDY_URL:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_EDY_URL);
                    str2 = str;
                    uri = null;
                    break;
                case OSAIFULIFE_PLUS_DL_NANACO_URL:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_NANACO_URL);
                    str2 = str;
                    uri = null;
                    break;
                case OSAIFULIFE_PLUS_DL_WAON_URL:
                    str = (String) Sg.getValue(Sg.Key.NET_OSAIFULIFE_PLUS_DL_WAON_URL);
                    str2 = str;
                    uri = null;
                    break;
                case CLEAR_ACCOUNT_URL:
                    str = (String) Sg.getValue(Sg.Key.SETTING_CLEAR_ACCOUNT_URL);
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
