package com.felicanetworks.common.cmnctrl.net;

import com.felicanetworks.common.cmnlib.CommonAppException;

/* JADX INFO: loaded from: classes.dex */
public class DataParseException extends CommonAppException {
    public static final int ID_ATTRIBUTE_ERROR = 1;
    public static final int ID_COMMUNICATION_ERROR = 2;
    public static final int ID_LENGTH_ERROR = 0;
    public static final int ID_OTHER_ERROR = 3;
    private int errorId;
    private int errorRecordPosition;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 4;
    }

    public DataParseException(int i, int i2, String str) {
        super(str + ", errorId:" + i2 + ", pos:" + i);
        this.errorRecordPosition = i;
        this.errorId = i2;
    }

    public DataParseException(Throwable th, String str, int i, int i2) {
        super("errorId:" + i2 + ", pos:" + i + ", cd:" + str, th);
        this.errorRecordPosition = i;
        this.errorId = i2;
        setErrIdentifierCode(str);
    }

    public DataParseException(Throwable th, String str, int i) {
        super("errorId:" + i + ", cd:" + str, th);
        this.errorId = i;
        setErrIdentifierCode(str);
    }

    public DataParseException(int i, String str) {
        super(str + ", errorId:" + i);
        this.errorId = i;
    }

    public int getErrorRecordPosition() {
        return this.errorRecordPosition;
    }

    public int getErrorId() {
        return this.errorId;
    }
}
