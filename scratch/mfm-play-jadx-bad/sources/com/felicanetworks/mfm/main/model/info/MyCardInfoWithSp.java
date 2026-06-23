package com.felicanetworks.mfm.main.model.info;

import android.text.TextUtils;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.CardDetailFuncEntity;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.tis.datatype.NotificationJsonInfo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class MyCardInfoWithSp extends MyCardInfo {
    private SpAdditionalBalanceInfo spAdditionalBalanceInfo;

    public MyCardInfoWithSp(String serviceId, String cardId, String cardCategory, MyCardInfo.CardStatus status, MyCardInfo.CardPosition position, String mfiCardAdditionalInfoHashValue, NetworkExpert networkExpert, JSONObject spAdditionalJson, MyCardInfo.CardSpStatus spStatus) throws MfmException {
        super(serviceId, cardId, cardCategory, status, position, mfiCardAdditionalInfoHashValue, null, networkExpert, false);
        try {
            this.spAdditionalBalanceInfo = parseSpAdditional(spAdditionalJson);
        } catch (MfmException unused) {
        }
        setCardSpStatus(spStatus);
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyCardInfo
    public String toString() {
        return "MyCardInfoWithSp{spAdditionalBalanceInfo=" + this.spAdditionalBalanceInfo + '}';
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyCardInfo
    public String getBalance() {
        if (getCardStatus() == MyCardInfo.CardStatus.STATUS_DELETED && getCardPosition() == MyCardInfo.CardPosition.POSITION_NOT_EXIST && getCardSpStatus() == MyCardInfo.CardSpStatus.SP_STATUS_REISSUABLE) {
            SpAdditionalBalanceInfo spAdditionalBalanceInfo = this.spAdditionalBalanceInfo;
            if (spAdditionalBalanceInfo == null || spAdditionalBalanceInfo.getCardBalance() == null) {
                return "-1";
            }
            return this.spAdditionalBalanceInfo.getCardBalance();
        }
        return super.getBalance();
    }

    public void absorb(MyCardInfo src) {
        if (src == null || !TextUtils.equals(getCardId(), src.getCardId())) {
            return;
        }
        setCardAdditionalInfo(src.getCardAdditionalInfo());
    }

    private SpAdditionalBalanceInfo parseSpAdditional(JSONObject object) throws MfmException {
        String string = null;
        if (object == null) {
            return null;
        }
        try {
            JSONObject jSONObject = object.getJSONObject("balance");
            String string2 = jSONObject.getString(NotificationJsonInfo.KEY_NAME_AMOUNT);
            DataCheckerUtil.checkNumPointFormat(string2);
            DataCheckerUtil.checkLessEqualLength(string2.length(), 16);
            String string3 = jSONObject.getString(NotificationJsonInfo.KEY_NAME_CURRENCY_CODE);
            DataCheckerUtil.checkAlphaFormat(string3);
            DataCheckerUtil.checkMidwayLength(string3.length(), 1, 4);
            if (jSONObject.has(LogSender.EXTRA_KEY_DATE)) {
                string = jSONObject.getString(LogSender.EXTRA_KEY_DATE);
                DataCheckerUtil.checkDecNumberFormat(string);
                DataCheckerUtil.checkLessEqualLength(string.length(), 14);
            }
            return new SpAdditionalBalanceInfo(string2, string3, string);
        } catch (DataCheckerException | JSONException e) {
            LogUtil.warning(e);
            throw new MfmException(CardDetailFuncEntity.class, 1280, e);
        }
    }
}
