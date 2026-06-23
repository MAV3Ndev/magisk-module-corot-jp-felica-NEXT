package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes3.dex */
class MficErrorMapping {
    MficErrorMapping() {
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x006c A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static MfcException.Type get(int type) {
        if (type != 163) {
            if (type == 164) {
                return MfcException.Type.UNSUPPORTED_CHIP_ERROR;
            }
            if (type == 177) {
                return MfcException.Type.MEMORY_CLEAR_IN_PROGRESS;
            }
            if (type == 178) {
                return MfcException.Type.UNKNOWN_CHIP_STATE;
            }
            if (type == 226) {
                return MfcException.Type.INVALID_REQUEST_TOKEN;
            }
            if (type != 227) {
                switch (type) {
                    case 8:
                        return MfcException.Type.FELICA_OPEN_ERROR;
                    case 31:
                        return MfcException.Type.NOT_IC_CHIP_FORMATTING;
                    case 39:
                        return MfcException.Type.MFC_USED_BY_OTHER_APP;
                    case 55:
                        return MfcException.Type.LOCKED_FELICA;
                    case MfiClientException.TYPE_CARD_NOT_FOUND /* 167 */:
                    case MfiClientException.TYPE_MFI_OFFLINE_CANCELED /* 169 */:
                    case 231:
                        return MfcException.Type.MFC_WARNING;
                    case 219:
                        return MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE;
                    case 221:
                        return MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR;
                    case 240:
                        break;
                    case 242:
                        return MfcException.Type.MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE;
                    case 245:
                        return MfcException.Type.INSIDE_TRANSIT_GATE_ERROR;
                    default:
                        switch (type) {
                            case MfiClientException.TYPE_NO_ACCOUNT_INFO /* 156 */:
                                return MfcException.Type.MFIC_NO_ACCOUNT_INFO;
                            case 157:
                                return MfcException.Type.MFI_CARD_NOT_CACHED;
                            default:
                                switch (type) {
                                    case 203:
                                        return MfcException.Type.FELICA_HTTP_ERROR;
                                    case 204:
                                        return MfcException.Type.MFI_SERVER_MAINTENANCE_ERROR;
                                    case 205:
                                        return MfcException.Type.FELICA_NETWORK_FAILED;
                                    default:
                                        switch (type) {
                                            case 210:
                                            case 211:
                                            case 214:
                                                break;
                                            case 212:
                                                return MfcException.Type.TYPE_EXIST_UNKNOWN_CARD;
                                            case 213:
                                                return MfcException.Type.MFI_ISSUE_LIMIT_EXCEEDED;
                                            case 215:
                                                return MfcException.Type.MFI_INTERRUPTED_ERROR;
                                            case 216:
                                            case 217:
                                                break;
                                            default:
                                                switch (type) {
                                                    case 234:
                                                        return MfcException.Type.MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED;
                                                    case 235:
                                                        return MfcException.Type.MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED;
                                                    case 236:
                                                        return MfcException.Type.MFI_OTHER_SP_CARD_EXIST;
                                                    case 237:
                                                        return MfcException.Type.MFI_INSTANCE_NOT_VACANT;
                                                    default:
                                                        switch (type) {
                                                            case 247:
                                                                return MfcException.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE;
                                                            case 248:
                                                                return MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE;
                                                            case 249:
                                                                return MfcException.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE;
                                                            case 250:
                                                                return MfcException.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED;
                                                            case 251:
                                                                return MfcException.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED;
                                                            default:
                                                                return MfcException.Type.MFC_OTHER_ERROR;
                                                        }
                                                }
                                        }
                                    case 206:
                                    case 207:
                                    case 208:
                                        break;
                                }
                            case 158:
                                break;
                        }
                        break;
                }
            } else {
                return MfcException.Type.MFI_INSUFFICIENT_CHIP_SPACE;
            }
        }
        return MfcException.Type.UNSUPPORTED_DEVICE_ERROR;
    }
}
