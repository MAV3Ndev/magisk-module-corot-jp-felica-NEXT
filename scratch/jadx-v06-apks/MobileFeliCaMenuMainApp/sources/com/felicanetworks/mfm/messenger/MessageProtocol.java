package com.felicanetworks.mfm.messenger;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class MessageProtocol {
    public static final String LOGCAT_TAG = "messenger";

    private MessageProtocol() {
    }

    public enum Action {
        RECEIVE_TOKEN("com.felicanetworks.mfm.messenger.action.RECEIVE_TOKEN"),
        RECEIVE_MESSAGE_TO_MENU("com.felicanetworks.mfm.messenger.action.RECEIVE_MESSAGE_TO_MENU"),
        RECEIVE_MESSAGE_TO_MFIC("com.felicanetworks.mfm.messenger.action.RECEIVE_MESSAGE_TO_MFIC"),
        RECEIVE_DELETED_MESSAGE("com.felicanetworks.mfm.messenger.action.RECEIVE_DELETED_MESSAGE"),
        UNKNOWN(null);

        private final String value;

        Action(String str) {
            this.value = str;
        }

        static Action resolve(String str) {
            for (Action action : values()) {
                if (TextUtils.equals(action.value, str)) {
                    return action;
                }
            }
            return UNKNOWN;
        }

        public String value() {
            return this.value;
        }
    }
}
