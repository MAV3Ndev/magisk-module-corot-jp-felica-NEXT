package com.felicanetworks.mfm.playIntegrity.agent;

import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
class PlayIntegrityErrorMapping {
    static final HashMap<Integer, PlayIntegrityAgentErrorType.Error> ERROR_MAP;
    static final Set<Integer> REQUEST_RETRY_ERROR_LIST;

    PlayIntegrityErrorMapping() {
    }

    static {
        HashMap<Integer, PlayIntegrityAgentErrorType.Error> map = new HashMap<>();
        ERROR_MAP = map;
        map.put(-1, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR);
        map.put(-2, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR);
        map.put(-3, PlayIntegrityAgentErrorType.Error.TYPE_NETWORK_UNAVAILABLE);
        map.put(-4, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR);
        map.put(-5, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-6, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_SERVICE_ERROR);
        map.put(-7, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-8, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED);
        map.put(-9, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR);
        map.put(-10, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-11, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-12, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED);
        map.put(-13, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-14, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_STORE_ERROR);
        map.put(-15, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_SERVICE_ERROR);
        map.put(-16, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED);
        map.put(-17, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED);
        map.put(-100, PlayIntegrityAgentErrorType.Error.TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED);
        HashSet hashSet = new HashSet();
        REQUEST_RETRY_ERROR_LIST = hashSet;
        hashSet.add(-17);
        hashSet.add(-12);
        hashSet.add(-100);
        hashSet.add(-8);
    }
}
