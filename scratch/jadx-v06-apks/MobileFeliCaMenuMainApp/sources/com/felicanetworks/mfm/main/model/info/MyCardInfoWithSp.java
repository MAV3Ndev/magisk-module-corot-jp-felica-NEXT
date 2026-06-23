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
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MyCardInfoWithSp extends MyCardInfo {
    private SpAdditionalBalanceInfo spAdditionalBalanceInfo;

    public MyCardInfoWithSp(String str, String str2, String str3, MyCardInfo.CardStatus cardStatus, MyCardInfo.CardPosition cardPosition, String str4, NetworkExpert networkExpert, JSONObject jSONObject, MyCardInfo.CardSpStatus cardSpStatus) throws MfmException {
        super(str, str2, str3, cardStatus, cardPosition, str4, null, networkExpert);
        try {
            this.spAdditionalBalanceInfo = parseSpAdditional(jSONObject);
        } catch (MfmException unused) {
        }
        setCardSpStatus(cardSpStatus);
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyCardInfo
    public String toString() {
        return "MyCardInfoWithSp{spAdditionalBalanceInfo=" + this.spAdditionalBalanceInfo + '}';
    }

    @Override // com.felicanetworks.mfm.main.model.info.MyCardInfo
    public String getBalance() {
        if (getCardStatus() == MyCardInfo.CardStatus.STATUS_DELETED && getCardPosition() == MyCardInfo.CardPosition.POSITION_NOT_EXIST && getCardSpStatus() == MyCardInfo.CardSpStatus.SP_STATUS_REISSUABLE) {
            SpAdditionalBalanceInfo spAdditionalBalanceInfo = this.spAdditionalBalanceInfo;
            return (spAdditionalBalanceInfo == null || spAdditionalBalanceInfo.getCardBalance() == null) ? "-1" : this.spAdditionalBalanceInfo.getCardBalance();
        }
        return super.getBalance();
    }

    public void absorb(MyCardInfo myCardInfo) {
        if (myCardInfo == null || !TextUtils.equals(getCardId(), myCardInfo.getCardId())) {
            return;
        }
        setCardAdditionalInfo(myCardInfo.getCardAdditionalInfo());
    }

    private SpAdditionalBalanceInfo parseSpAdditional(JSONObject jSONObject) throws MfmException {
        String string = null;
        if (jSONObject == null) {
            return null;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("balance");
            String string2 = jSONObject2.getString("amount");
            DataCheckerUtil.checkNumPointFormat(string2);
            DataCheckerUtil.checkLessEqualLength(string2.length(), 16);
            String string3 = jSONObject2.getString("currencyCode");
            DataCheckerUtil.checkAlphaFormat(string3);
            DataCheckerUtil.checkMidwayLength(string3.length(), 1, 4);
            if (jSONObject2.has(LogSender.EXTRA_KEY_DATE)) {
                string = jSONObject2.getString(LogSender.EXTRA_KEY_DATE);
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
