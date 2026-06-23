package com.felicanetworks.mfw.i.cmn;

/* JADX INFO: loaded from: classes.dex */
public class ArrayList {
    private static final int DEFAULT_SIZE = 10;
    private Object[] mElementData;
    private int mSize;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int i) {
        if (i < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [initialCapacity = " + i + "]");
            throw new SysException((Class<?>) ArrayList.class, "ArrayList", stringBuffer.toString());
        }
        this.mElementData = new Object[i];
    }

    public int size() {
        return this.mSize;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(Object obj) {
        if (obj == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [element = " + obj + "]");
            throw new SysException((Class<?>) ArrayList.class, "indexOf", stringBuffer.toString());
        }
        for (int i = 0; i < this.mSize; i++) {
            if (obj.equals(this.mElementData[i])) {
                return i;
            }
        }
        return -1;
    }

    public void add(Object obj) {
        if (obj == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [element = " + obj + "]");
            throw new SysException((Class<?>) ArrayList.class, "add", stringBuffer.toString());
        }
        int i = this.mSize;
        Object[] objArr = this.mElementData;
        if (i == objArr.length) {
            this.mElementData = new Object[i + 10];
            System.arraycopy(objArr, 0, this.mElementData, 0, i);
        }
        Object[] objArr2 = this.mElementData;
        int i2 = this.mSize;
        this.mSize = i2 + 1;
        objArr2[i2] = obj;
    }

    public void addAllList(ArrayList arrayList) {
        if (arrayList == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [list = " + arrayList + "]");
            throw new SysException((Class<?>) ArrayList.class, "addAllList", stringBuffer.toString());
        }
        for (int i = 0; i < arrayList.mSize; i++) {
            add(arrayList.get(i));
        }
    }

    public void addAllArray(Object[] objArr) {
        if (objArr == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [array = " + objArr + "]");
            throw new SysException((Class<?>) ArrayList.class, "addAllArray", stringBuffer.toString());
        }
        for (Object obj : objArr) {
            add(obj);
        }
    }

    public Object get(int i) {
        if (i < 0 || this.mSize <= i) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [index = " + i);
            stringBuffer.append(", size = " + this.mSize + "]");
            throw new SysException((Class<?>) ArrayList.class, "get", stringBuffer.toString());
        }
        return this.mElementData[i];
    }

    public void remove(int i) {
        int i2;
        if (i < 0 || (i2 = this.mSize) <= i) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [index = " + i);
            stringBuffer.append(", size = " + this.mSize + "]");
            throw new SysException((Class<?>) ArrayList.class, "remove", stringBuffer.toString());
        }
        int i3 = (i2 - i) - 1;
        if (i3 > 0) {
            Object[] objArr = this.mElementData;
            System.arraycopy(objArr, i + 1, objArr, i, i3);
        }
        Object[] objArr2 = this.mElementData;
        int i4 = this.mSize - 1;
        this.mSize = i4;
        objArr2[i4] = null;
    }

    public void clear() {
        for (int i = 0; i < this.mSize; i++) {
            this.mElementData[i] = null;
        }
        this.mSize = 0;
    }

    public Object[] toArray() {
        int i = this.mSize;
        Object[] objArr = new Object[i];
        System.arraycopy(this.mElementData, 0, objArr, 0, i);
        return objArr;
    }
}
