package com.felicanetworks.mfm.messenger;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
class MessageSender {
    MessageSender() {
    }

    private enum Addressee {
        MENU(null),
        MFIC("MFIC"),
        SEMC("SEMC"),
        UNKNOWN(null);

        private static final String PAYLOAD_KEY_ADDRESS = "address";
        private final String addressKey;

        Addressee(String str) {
            this.addressKey = str;
        }

        static Addressee resolve(Map<String, String> map) {
            String str = map.get("address");
            Addressee addressee = MENU;
            if (TextUtils.equals(str, addressee.addressKey)) {
                return addressee;
            }
            Addressee addressee2 = MFIC;
            if (TextUtils.equals(str, addressee2.addressKey)) {
                return addressee2;
            }
            Addressee addressee3 = SEMC;
            return TextUtils.equals(str, addressee3.addressKey) ? addressee3 : UNKNOWN;
        }
    }

    static abstract class LocalPacket {
        final MessageProtocol.Action action;
        final Context context;
        final Intent intent;

        LocalPacket(Context context, MessageProtocol.Action action) {
            Intent intent = new Intent();
            this.intent = intent;
            this.context = context;
            this.action = action;
            intent.setAction(action.value());
        }

        final Intent intent(String str) {
            return this.intent.setClassName(this.context, str);
        }
    }

    static class TokenPacket extends LocalPacket {
        private static final String KEY_TOKEN = "key_token";

        TokenPacket(Context context, String str) {
            super(context, MessageProtocol.Action.RECEIVE_TOKEN);
            this.intent.putExtra(KEY_TOKEN, str);
        }

        static String findToken(Intent intent) {
            if (intent.hasExtra(KEY_TOKEN)) {
                return intent.getStringExtra(KEY_TOKEN);
            }
            return null;
        }

        public String toString() {
            return "TokenPacket{context=" + this.context + ", action=" + this.action + ", intent=" + this.intent + '}';
        }
    }

    static abstract class MessagePacket extends LocalPacket {
        MessagePacket(Context context, MessageProtocol.Action action, Map<String, String> map) {
            super(context, action);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.intent.putExtra(entry.getKey(), entry.getValue());
            }
        }

        static Map<String, String> findMessages(Intent intent) {
            HashMap map = new HashMap();
            if (intent.getExtras() != null) {
                for (String str : intent.getExtras().keySet()) {
                    map.put(str, intent.getStringExtra(str));
                }
            }
            return map;
        }
    }

    static class MenuMessagePacket extends MessagePacket {
        MenuMessagePacket(Context context, Map<String, String> map) {
            super(context, MessageProtocol.Action.RECEIVE_MESSAGE_TO_MENU, map);
        }

        public String toString() {
            return "MenuMessagePacket{context=" + this.context + ", action=" + this.action + ", intent=" + this.intent + '}';
        }
    }

    static class MficMessagePacket extends MessagePacket {
        MficMessagePacket(Context context, Map<String, String> map) {
            super(context, MessageProtocol.Action.RECEIVE_MESSAGE_TO_MFIC, map);
        }

        public String toString() {
            return "MficMessagePacket{context=" + this.context + ", action=" + this.action + ", intent=" + this.intent + '}';
        }
    }

    static class SemcMessagePacket extends MessagePacket {
        SemcMessagePacket(Context context, Map<String, String> map) {
            super(context, MessageProtocol.Action.RECEIVE_MESSAGE_TO_SEMC, map);
        }

        public String toString() {
            return "SemcMessagePacket{context=" + this.context + ", action=" + this.action + ", intent=" + this.intent + '}';
        }
    }

    static class DeletedMessagePacket extends LocalPacket {
        DeletedMessagePacket(Context context) {
            super(context, MessageProtocol.Action.RECEIVE_DELETED_MESSAGE);
        }

        public String toString() {
            return "DeletedMessagePacket{context=" + this.context + ", action=" + this.action + ", intent=" + this.intent + '}';
        }
    }

    static void sendToken(Context context, String str) {
        Logger.debug(context, str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Messenger.updateNewToken(context, str);
        postLocalPacket(new TokenPacket(context, str));
    }

    static void sendMessage(Context context, Map<String, String> map) {
        Logger.debug(context, map);
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee[Addressee.resolve(map).ordinal()];
        if (i == 1) {
            postLocalPacket(new MenuMessagePacket(context, map));
        } else if (i == 2) {
            postLocalPacket(new MficMessagePacket(context, map));
        } else {
            if (i != 3) {
                return;
            }
            postLocalPacket(new SemcMessagePacket(context, map));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.messenger.MessageSender$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee;

        static {
            int[] iArr = new int[Addressee.values().length];
            $SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee = iArr;
            try {
                iArr[Addressee.MENU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee[Addressee.MFIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee[Addressee.SEMC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageSender$Addressee[Addressee.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static void sendDeletedMessage(Context context) {
        Logger.debug(context);
        postLocalPacket(new DeletedMessagePacket(context));
    }

    private static void postLocalPacket(LocalPacket localPacket) {
        Logger.debug(localPacket);
        Iterator<String> it = Messenger.queryTargetReceivers(localPacket.context, localPacket.action).iterator();
        while (it.hasNext()) {
            postLocalPacketToReceiver(localPacket, it.next());
        }
    }

    private static void postLocalPacketToReceiver(LocalPacket localPacket, String str) {
        Logger.info(localPacket, str);
        localPacket.context.sendBroadcast(localPacket.intent(str), null);
    }
}
