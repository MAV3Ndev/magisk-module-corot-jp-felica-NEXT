package com.felicanetworks.mnlib.felica;

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
    private static final String EXC_INVALID_ATTRIBUTE_VALUE = "The attribute value of the specified Service Code is invalid.";
    private static final String EXC_INVALID_BLOCK = "The specified Block is null.";
    private static final String EXC_INVALID_BLOCK_FOR_READ = "The specified Block is invalid for reading.";
    private static final String EXC_INVALID_BLOCK_NO = "The Block No must be 0x0000 to 0xffff.";
    private static final String EXC_INVALID_COMBINATION = "The combination of the Block and the Data is invalid.";
    private static final String EXC_INVALID_DATA = "The specified Data is null.";
    private static final int MAX_BLOCK_NO = 65535;
    private static final int MAX_SERVICE_CODE = -1;
    private static final int MAX_SERVICE_CODE_AT_NODE_CODE_SIZE_2 = 65535;
    private static final int MIN_BLOCK_NO = 0;

    private ServiceUtil() {
    }

    public static void checkServiceCode(int i, int i2) throws IllegalArgumentException {
        if (i2 == 2) {
            if ((i & 65535) == 65535) {
                return;
            }
        } else if (i == -1) {
            return;
        }
        int i3 = i & 63;
        if (i3 == 0 || i3 == 1 || i3 == 32 || i3 == 33) {
            return;
        }
        switch (i3) {
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
                switch (i3) {
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
                        throw new IllegalArgumentException(EXC_INVALID_ATTRIBUTE_VALUE);
                }
        }
    }

    public static int getBlockType(int i) throws IllegalArgumentException {
        int i2 = i & 63;
        if (i2 == 9) {
            return 7;
        }
        if (i2 == 11) {
            return 8;
        }
        if (i2 == 13) {
            return 1;
        }
        if (i2 == 15) {
            return 2;
        }
        if (i2 == 17) {
            return 3;
        }
        if (i2 == 19) {
            return 4;
        }
        if (i2 == 21) {
            return 5;
        }
        if (i2 == 23) {
            return 6;
        }
        throw new IllegalArgumentException(EXC_INVALID_ATTRIBUTE_VALUE);
    }

    public static void checkBlockNo(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_NO);
        }
    }

    public static void checkBlockData(Block block, Data data) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK);
        }
        if (data == null) {
            throw new IllegalArgumentException(EXC_INVALID_DATA);
        }
        int type = block.getType();
        if (type != 1) {
            if (type != 7) {
                if (type != 3) {
                    if (type == 4) {
                        int type2 = data.getType();
                        if (type2 == 4 || type2 == 5) {
                            return;
                        }
                    } else if (type == 5 && data.getType() == 5) {
                        return;
                    }
                } else if (data.getType() == 3) {
                    return;
                }
            } else if (data.getType() == 1) {
                return;
            }
        } else if (data.getType() == 2) {
            return;
        }
        throw new IllegalArgumentException(EXC_INVALID_COMBINATION);
    }

    public static void checkBlock(Block block, boolean z) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException(EXC_INVALID_BLOCK);
        }
        if (z) {
            switch (block.getType()) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    return;
                default:
                    throw new IllegalArgumentException(EXC_INVALID_BLOCK_FOR_READ);
            }
        }
    }
}
