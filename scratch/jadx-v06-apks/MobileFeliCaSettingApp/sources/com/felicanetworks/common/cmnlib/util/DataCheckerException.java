package com.felicanetworks.common.cmnlib.util;

import com.felicanetworks.common.cmnlib.CommonAppException;
import com.felicanetworks.common.cmnlib.ExceptionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class DataCheckerException extends CommonAppException implements ExceptionCodeInterface {
    public static final int ID_ATTRIBUTE_ERROR = 1;
    public static final int ID_LENGTH_ERROR = 0;
    private int id;

    @Override // com.felicanetworks.common.cmnlib.ExceptionCodeInterface
    public int getExceptionCode() {
        return 9;
    }

    public DataCheckerException() {
        this.id = -1;
    }

    public DataCheckerException(int i) {
        super("errorId:" + i);
        this.id = -1;
        this.id = i;
    }

    public DataCheckerException(int i, String str) {
        this(i, str, "");
    }

    public DataCheckerException(int i, String str, String str2) {
        super("errorId:" + i + " " + str2 + " {" + str + "}");
        this.id = -1;
        this.id = i;
    }

    public int getErrorId() {
        return this.id;
    }
}
