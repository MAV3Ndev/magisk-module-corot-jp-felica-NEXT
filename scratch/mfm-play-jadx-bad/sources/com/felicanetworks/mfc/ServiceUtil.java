package com.felicanetworks.mfc;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceUtil {
    private static final int ATTRIBUTE_AREA = 1;
    private static final int ATTRIBUTE_AREA_APPENDABLE = 0;
    private static final int ATTRIBUTE_ATTRIBUTE_MASK = 63;
    private static final int ATTRIBUTE_CYCLIC = 13;
    private static final int ATTRIBUTE_CYCLIC_ENCRYPTED = 12;
    private static final int ATTRIBUTE_CYCLIC_READ_ONLY = 15;
    private static final int ATTRIBUTE_CYCLIC_READ_ONLY_ENCRYPTED = 14;
    private static final int ATTRIBUTE_PIN_FOR_AREA = 33;
    private static final int ATTRIBUTE_PIN_FOR_AREA_APPENDABLE = 32;
    private static final int ATTRIBUTE_PIN_FOR_CYCLIC = 45;
    private static final int ATTRIBUTE_PIN_FOR_CYCLIC_ENCRYPTED = 44;
    private static final int ATTRIBUTE_PIN_FOR_CYCLIC_READ_ONLY = 47;
    private static final int ATTRIBUTE_PIN_FOR_CYCLIC_READ_ONLY_ENCRYPTED = 46;
    private static final int ATTRIBUTE_PIN_FOR_PURSE = 49;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_CASH_BACK = 51;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_CASH_BACK_ENCRYPTED = 50;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_DECREMENT = 53;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_DECREMENT_ENCRYPTED = 52;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_ENCRYPTED = 48;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_READ_ONLY = 55;
    private static final int ATTRIBUTE_PIN_FOR_PURSE_READ_ONLY_ENCRYPTED = 54;
    private static final int ATTRIBUTE_PIN_FOR_RANDOM = 41;
    private static final int ATTRIBUTE_PIN_FOR_RANDOM_ENCRYPTED = 40;
    private static final int ATTRIBUTE_PIN_FOR_RANDOM_READ_ONLY = 43;
    private static final int ATTRIBUTE_PIN_FOR_RANDOM_READ_ONLY_ENCRYPTED = 42;
    private static final int ATTRIBUTE_PURSE = 17;
    private static final int ATTRIBUTE_PURSE_CASH_BACK = 19;
    private static final int ATTRIBUTE_PURSE_CASH_BACK_ENCRYPTED = 18;
    private static final int ATTRIBUTE_PURSE_DECREMENT = 21;
    private static final int ATTRIBUTE_PURSE_DECREMENT_ENCRYPTED = 20;
    private static final int ATTRIBUTE_PURSE_ENCRYPTED = 16;
    private static final int ATTRIBUTE_PURSE_READ_ONLY = 23;
    private static final int ATTRIBUTE_PURSE_READ_ONLY_ENCRYPTED = 22;
    private static final int ATTRIBUTE_RANDOM = 9;
    private static final int ATTRIBUTE_RANDOM_ENCRYPTED = 8;
    private static final int ATTRIBUTE_RANDOM_READ_ONLY = 11;
    private static final int ATTRIBUTE_RANDOM_READ_ONLY_ENCRYPTED = 10;
    private static final String EXC_INVALID_AREA_ATTRIBUTE_VALUE = "The attribute value of the specified Service Code is invalid.";
    private static final String EXC_INVALID_ATTRIBUTE_VALUE = "The attribute value of the specified Service Code is invalid.";
    private static final String EXC_INVALID_BLOCK = "The specified Block is null.";
    private static final String EXC_INVALID_BLOCK_FOR_READ = "The specified Block is invalid for reading.";
    private static final String EXC_INVALID_BLOCK_NO = "The Block No must be 0x0000 to 0xffff.";
    private static final String EXC_INVALID_COMBINATION = "The combination of the Block and the Data is invalid.";
    private static final String EXC_INVALID_DATA = "The specified Data is null.";
    private static final String EXC_INVALID_NODE_ATTRIBUTE_VALUE = "The attribute value of the specified Node Code is invalid.";
    private static final String EXC_INVALID_SERVICE_ATTRIBUTE_VALUE = "The attribute value of the specified Service Code is invalid.";
    private static final int MAX_BLOCK_NO = 65535;
    private static final int MAX_SERVICE_CODE = -1;
    private static final int MAX_SERVICE_CODE_NODECODESIZE_2 = 65535;
    private static final int MIN_BLOCK_NO = 0;
    private static final int NODECODESIZE_2 = 2;
    private static ServiceUtil instance;

    private ServiceUtil() {
    }

    public static ServiceUtil getInstance() {
        if (instance == null) {
            instance = new ServiceUtil();
        }
        return instance;
    }

    public void checkServiceCode(int serviceCode, int nodeCodeSize) throws IllegalArgumentException {
        if (nodeCodeSize == 2) {
            if ((serviceCode & 65535) == 65535) {
                return;
            }
        } else if (serviceCode == -1) {
            return;
        }
        int i = serviceCode & 63;
        if (i == 0 || i == 1 || i == 32 || i == 33) {
            return;
        }
        switch (i) {
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
                return;
            default:
                switch (i) {
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                        return;
                    default:
                        throw new IllegalArgumentException("The attribute value of the specified Service Code is invalid.");
                }
        }
    }

    public int getBlockType(int serviceCode) throws IllegalArgumentException {
        int i = serviceCode & 63;
        if (i == 9) {
            return 1;
        }
        if (i == 11) {
            return 2;
        }
        if (i == 13) {
            return 3;
        }
        if (i == 15) {
            return 4;
        }
        if (i == 17) {
            return 5;
        }
        if (i == 19) {
            return 6;
        }
        if (i == 21) {
            return 7;
        }
        if (i == 23) {
            return 8;
        }
        if (i == 32) {
            return 9;
        }
        if (i == 33) {
            return 10;
        }
        switch (i) {
            case 40:
                return 11;
            case 41:
                return 12;
            case 42:
                return 13;
            case 43:
                return 14;
            case 44:
                return 15;
            case 45:
                return 16;
            case 46:
                return 17;
            case 47:
                return 18;
            case 48:
                return 19;
            case 49:
                return 20;
            case 50:
                return 21;
            case 51:
                return 22;
            case 52:
                return 23;
            case 53:
                return 24;
            case 54:
                return 25;
            case 55:
                return 26;
            default:
                throw new IllegalArgumentException("The attribute value of the specified Service Code is invalid.");
        }
    }

    public void checkBlockNo(int blockNo) throws IllegalArgumentException {
        if (blockNo < 0 || blockNo > 65535) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_NO);
        }
    }

    public void checkBlockData(Block block, Data data) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        switch (block.getType()) {
            case 1:
                if (data.getType() == 1) {
                    return;
                }
                break;
            case 3:
                if (data.getType() == 2) {
                    return;
                }
                break;
            case 5:
                if (data.getType() == 3) {
                    return;
                }
                break;
            case 6:
                int type = data.getType();
                if (type == 4 || type == 5) {
                    return;
                }
                break;
            case 7:
                if (data.getType() == 5) {
                    return;
                }
                break;
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
                int type2 = data.getType();
                if (type2 == 6 || type2 == 7 || type2 == 8) {
                    return;
                }
                break;
        }
        throw new IllegalArgumentException(EXC_INVALID_COMBINATION);
    }

    public void checkBlock(Block block, boolean checkType) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK);
        }
        if (checkType) {
            switch (block.getType()) {
                case 1:
                case 2:
                case 3:
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
                    return;
                default:
                    throw new IllegalArgumentException(EXC_INVALID_BLOCK_FOR_READ);
            }
        }
    }

    public void checkAreaCode(int areaCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s In areaCode = %d", "000", Integer.valueOf(areaCode));
        int i = areaCode & 63;
        if (i == 0 || i == 1) {
            LogMgr.log(4, "%s", "999");
        } else {
            LogMgr.log(1, "%s (areaCode & ATTRIBUTE_ATTRIBUTE_MASK) = %d", "801", Integer.valueOf(i));
            throw new IllegalArgumentException("The attribute value of the specified Service Code is invalid.");
        }
    }

    public void checkNodeCode(int nodeCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d", "000", Integer.valueOf(nodeCode));
        int i = nodeCode & 63;
        if (i != 0 && i != 1) {
            switch (i) {
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
                    break;
                default:
                    LogMgr.log(1, "%s (nodeCode & ATTRIBUTE_ATTRIBUTE_MASK) = %d", "801", Integer.valueOf(i));
                    throw new IllegalArgumentException(EXC_INVALID_NODE_ATTRIBUTE_VALUE);
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    public void checkServiceCode(int serviceCode) throws IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCode = %d", "000", Integer.valueOf(serviceCode));
        int i = serviceCode & 63;
        switch (i) {
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
                LogMgr.log(4, "%s", "999");
                return;
            default:
                LogMgr.log(1, "%s (serviceCode & ATTRIBUTE_ATTRIBUTE_MASK) = %d", "801", Integer.valueOf(i));
                throw new IllegalArgumentException("The attribute value of the specified Service Code is invalid.");
        }
    }
}
