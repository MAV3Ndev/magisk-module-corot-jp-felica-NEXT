package com.felicanetworks.mfm.main.presenter.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.core.app.NotificationCompat;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DummyEventSender {
    private static Activity activity;
    private static Map<String, StateMachine.Event> eventNameMap = new HashMap<String, StateMachine.Event>() { // from class: com.felicanetworks.mfm.main.presenter.internal.DummyEventSender.1
        {
            put("EV1", StateMachine.Event.EV_CLOSE);
            put("EV2", StateMachine.Event.EV_POSITIVE);
            put("EV3", StateMachine.Event.EV_NEGATIVE);
            put("EV4", StateMachine.Event.EV_SERVICE_START);
            put("EV5", StateMachine.Event.EV_MYSERVICE);
            put("EV6", StateMachine.Event.EV_RECOMMEND);
            put("EV7", StateMachine.Event.EV_NOTICE_LIST);
            put("EV8", StateMachine.Event.EV_SETTING);
            put("EV9", StateMachine.Event.EV_CARD_READER);
            put("EV10", StateMachine.Event.EV_FAQ);
            put("EV11", StateMachine.Event.EV_LOCK_SETTING);
            put("EV12", StateMachine.Event.EV_UPDATE);
            put("EV14", StateMachine.Event.EV_RECOMMEND_DETAIL);
            put("EV15", StateMachine.Event.EV_SERVICE_START_SUCCESS);
            put("EV16", StateMachine.Event.EV_NOTICE_DETAIL);
            put("EV17", StateMachine.Event.EV_NOTICE_DETAIL_SITE_ACCESS);
            put("EV18", StateMachine.Event.EV_TICKETING);
            put("EV19", StateMachine.Event.EV_MRMORY_USAGE);
            put("EV20", StateMachine.Event.EV_TUTORIAL);
            put("EV23", StateMachine.Event.EV_PUSH_SETTING_CHAGE);
            put("EV24", StateMachine.Event.EV_RESULT_FELICA_SETTING_COMPLETE);
            put("EV25", StateMachine.Event.EV_RESULT_FELICA_SETTING_INCOMPLETE);
            put("EV26", StateMachine.Event.EV_RESULT_LOCK_STATE_CHANGE);
            put("EV27", StateMachine.Event.EV_RESULT_LOCK_CANCEL);
            put("EV28", StateMachine.Event.EV_REBOOT_COMPLETE);
            put("EV30", StateMachine.Event.EV_END);
            put("EV31", StateMachine.Event.EV_SUPPORT_MENU);
            put("EV32", StateMachine.Event.EV_RESULT_OTHERS);
            put("EM1", StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_REQUIRE);
            put("EM2", StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_ASK);
            put("EM3", StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_NONE);
            put("EM4", StateMachine.Event.EM_FELICA_INITIALIZE_INCOMPLETE);
            put("EM5", StateMachine.Event.EM_FELICA_INITIALIZE_COMPLETE);
            put("EM6", StateMachine.Event.EM_CREATE_SIM_DATA_COMPLETE);
            put("EM7", StateMachine.Event.EM_UNSUPPORT_FELICA_SETTING_APP);
            put("EM9", StateMachine.Event.EM_VERSIONUP_CONFIRM_FAILED);
            put("EM10", StateMachine.Event.EM_MFCPERMINSPECT_ERROR);
            put("EM11", StateMachine.Event.EM_FELICA_TIMEOUT_ERROR);
            put("EM12", StateMachine.Event.EM_MFC_OTHER_ERROR);
            put("EM13", StateMachine.Event.EM_OTHER_APP_USING_MFC_ERROR);
            put("EM14", StateMachine.Event.EM_DATABASE_ACCESS_ERROR);
            put("EM15", StateMachine.Event.EM_FELICA_LOCK_ERROR);
            put("EP1", StateMachine.Event.EP_START_KIND_MYSERVICE);
            put("EP3", StateMachine.Event.EP_START_KIND_NOTICE);
            put("EP4", StateMachine.Event.EP_START_KIND_CARD_READER);
            put("EP5", StateMachine.Event.EP_JUDGE_SCREEN_MYSERVICE);
            put("EP6", StateMachine.Event.EP_JUDGE_SCREEN_RECOMMEND);
            put("EP7", StateMachine.Event.EP_JUDGE_SCREEN_CARD_READING);
            put("EP8", StateMachine.Event.EP_JUDGE_TUTORIAL_SHOW);
            put("EP9", StateMachine.Event.EP_JUDGE_TUTORIAL_UNSHOWN);
            put("EP10", StateMachine.Event.EP_JUDGE_RECOMMEND_DETAIL_TRANSITABLE);
            put("EP11", StateMachine.Event.EP_JUDGE_RECOMMEND_DETAIL_NOT_TRANSITABLE);
            put("EP12", StateMachine.Event.EP_OUT_OF_MEMORY_ERROR);
            put("EA1", StateMachine.Event.EA_START_APPLICATION);
            put("EI1", StateMachine.Event.EI_NOTIFICATION);
            put("EI2", StateMachine.Event.EI_PUSH);
            put("EI3", StateMachine.Event.EI_NFC_TAG_RECEIVE);
            put("EI4", StateMachine.Event.EI_CONFIG_CHANGE);
            put("EI5", StateMachine.Event.EI_LOCK_STATE_CHANGE);
            put("EI6", StateMachine.Event.EI_FATAL_ERROR);
            put("EI7", StateMachine.Event.EI_MFS_MOVEMENT_RECEIVE);
        }
    };

    public static void setCurrentActivity(Activity activity2) {
        activity = activity2;
    }

    public static void dummyEventSend(Intent intent) {
        Integer.valueOf(0);
        Bundle extras = intent.getExtras();
        String stringExtra = "";
        Object mfmException = null;
        if (extras != null) {
            for (String str : extras.keySet()) {
                if (NotificationCompat.CATEGORY_EVENT.equals(str)) {
                    stringExtra = intent.getStringExtra(str);
                    if (!eventNameMap.containsKey(stringExtra)) {
                        return;
                    }
                } else if ("parameter".equals(str)) {
                    int iIntValue = Integer.valueOf(intent.getStringExtra(str)).intValue();
                    if (iIntValue == 1) {
                        mfmException = new Object();
                    } else if (iIntValue == 2) {
                        mfmException = new MfmException(DummyEventSender.class, 1, "test Exception");
                    }
                }
            }
        }
        if (eventNameMap.get(stringExtra) == StateMachine.Event.EV_CARD_READER) {
            Intent intent2 = new Intent(activity.getApplicationContext(), activity.getClass());
            intent2.putExtra("android.nfc.extra.TAG", new Parcelable[0]);
            intent2.setAction("android.nfc.action.TECH_DISCOVERED");
            intent2.setFlags(268435456);
            activity.getApplicationContext().startActivity(intent2);
            return;
        }
        StateController.postStateEvent(eventNameMap.get(stringExtra), new Structure(), mfmException);
    }
}
