package com.felicanetworks.mfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.analysis.MfsStamp;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class MfsMovementReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            AnalysisManager.stampReceive(context);
            MfsStamp.Event intent2 = MfsStamp.parseIntent(intent);
            AnalysisManager.stamp(MfmStamp.Event.MFS_MOVEMENT, intent2);
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[intent2.ordinal()]) {
                case 1:
                case 2:
                    MfsMovementReceiverWorker.startActionCheckOperable(context);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    MfsMovementReceiverWorker.startActionSendLogs(context);
                    break;
            }
            StateController.postStateEvent(StateMachine.Event.EI_MFS_MOVEMENT_RECEIVE, null, new Object[0]);
        } catch (Exception e) {
            LogUtil.warning(new MfmException(MfsMovementReceiver.class, 259, e));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.MfsMovementReceiver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event;

        static {
            int[] iArr = new int[MfsStamp.Event.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event = iArr;
            try {
                iArr[MfsStamp.Event.SCREEN_WID_1_2.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_26.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_2_YES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_2_NO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_26_REQUEST_APP_UPDATE_POSITIVE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_26_REQUEST_APP_UPDATE_NEGATIVE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_28_REQUEST_APP_UPDATE_POSITIVE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_28_REQUEST_APP_UPDATE_NEGATIVE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_29_REQUEST_APP_UPDATE_POSITIVE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.ACTION_WID_1_29_REQUEST_APP_UPDATE_NEGATIVE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_4.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_5.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_9.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_6.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_7.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_8.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_10.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_11.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_12.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_13.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_17.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_18.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_25.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_27.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_1_30.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_2_1.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_2_2.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_2_3.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_2_4.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event[MfsStamp.Event.SCREEN_WID_2_5.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
        }
    }
}
