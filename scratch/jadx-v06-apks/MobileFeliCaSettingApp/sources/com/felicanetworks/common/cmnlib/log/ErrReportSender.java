package com.felicanetworks.common.cmnlib.log;

import com.felicanetworks.common.cmnctrl.net.DataParseException;
import com.felicanetworks.common.cmnctrl.net.NetworkAccess;
import com.felicanetworks.common.cmnctrl.net.NetworkAccessException;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class ErrReportSender implements FunctionCodeInterface {
    static final String MSG_ILLEGAL_STATE = "Sending error report now.";
    static final String MSG_START_ERR_REPORT_ILLEGALARGUMENT = "A listener or logdata is nothing.";
    private ErrReportSenderThread agent;
    private AppContext context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 6;
    }

    protected AppContext getContext() {
        return this.context;
    }

    public ErrReportSender(AppContext appContext) {
        this.context = appContext;
    }

    public void start(LogMgrEventListener logMgrEventListener, String str, String str2) throws LogMgrException, IllegalArgumentException {
        if (logMgrEventListener == null) {
            throw new IllegalArgumentException(MSG_START_ERR_REPORT_ILLEGALARGUMENT);
        }
        if (str2 == null) {
            throw new IllegalArgumentException(MSG_START_ERR_REPORT_ILLEGALARGUMENT);
        }
        synchronized (this) {
            if (this.agent != null && this.agent.isAlive()) {
                throw new LogMgrException(MSG_ILLEGAL_STATE);
            }
            ErrReportSenderThread errReportSenderThread = new ErrReportSenderThread(this.context, logMgrEventListener, str, str2);
            this.agent = errReportSenderThread;
            errReportSenderThread.start();
        }
    }

    public synchronized void stop() {
        if (this.agent != null) {
            this.agent.cancel();
            this.agent = null;
        }
    }

    protected class ErrReportSenderThread extends Thread {
        private AppContext context;
        protected String idm;
        protected LogMgrEventListener listener;
        protected String logData;
        protected NetworkAccess web;

        public ErrReportSenderThread(AppContext appContext, LogMgrEventListener logMgrEventListener, String str, String str2) {
            this.listener = logMgrEventListener;
            this.idm = str;
            this.logData = str2;
            this.context = appContext;
        }

        public synchronized void cancel() {
            this.listener = null;
            interrupt();
            if (this.web != null) {
                this.web.cancel();
            }
        }

        protected void checkCanceled() throws InterruptedException {
            if (isInterrupted()) {
                throw new InterruptedException();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:140:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:141:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0088, code lost:
        
            r4 = r9.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x008a, code lost:
        
            monitor-enter(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0091, code lost:
        
            if (r9.this$0.agent == null) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x009d, code lost:
        
            if (r9.this$0.agent.equals(r9) == false) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x009f, code lost:
        
            r9.this$0.agent = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00a4, code lost:
        
            monitor-exit(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00a5, code lost:
        
            if (r3 == null) goto L140;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00a7, code lost:
        
            r3.finished(r0, r2);
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Not initialized variable reg: 3, insn: 0x00de: MOVE (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:67:0x00dd */
        /* JADX WARN: Not initialized variable reg: 3, insn: 0x00e4: MOVE (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:69:0x00e3 */
        /* JADX WARN: Not initialized variable reg: 3, insn: 0x00e9: MOVE (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:71:0x00e8 */
        /* JADX WARN: Removed duplicated region for block: B:125:0x012e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:133:0x0155 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:135:0x00fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r3v22 */
        /* JADX WARN: Type inference failed for: r3v29 */
        /* JADX WARN: Type inference failed for: r3v33 */
        /* JADX WARN: Type inference failed for: r3v42 */
        /* JADX WARN: Type inference failed for: r3v43 */
        /* JADX WARN: Type inference failed for: r3v44 */
        /* JADX WARN: Type inference failed for: r4v1, types: [com.felicanetworks.common.cmnlib.log.LogMgrEventListener] */
        /* JADX WARN: Type inference failed for: r4v10 */
        /* JADX WARN: Type inference failed for: r4v11 */
        /* JADX WARN: Type inference failed for: r4v12 */
        /* JADX WARN: Type inference failed for: r4v13 */
        /* JADX WARN: Type inference failed for: r4v14 */
        /* JADX WARN: Type inference failed for: r4v15, types: [com.felicanetworks.common.cmnlib.log.LogMgrEventListener] */
        /* JADX WARN: Type inference failed for: r4v16 */
        /* JADX WARN: Type inference failed for: r4v17 */
        /* JADX WARN: Type inference failed for: r4v18 */
        /* JADX WARN: Type inference failed for: r4v19 */
        /* JADX WARN: Type inference failed for: r4v23 */
        /* JADX WARN: Type inference failed for: r4v25 */
        /* JADX WARN: Type inference failed for: r4v27 */
        /* JADX WARN: Type inference failed for: r4v7 */
        /* JADX WARN: Type inference failed for: r4v8 */
        /* JADX WARN: Type inference failed for: r4v9 */
        /* JADX WARN: Type inference failed for: r6v3, types: [com.felicanetworks.common.cmnlib.log.ErrReportSender$ErrReportSenderThread, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v4 */
        /* JADX WARN: Type inference failed for: r8v5 */
        /* JADX WARN: Type inference failed for: r9v0, types: [com.felicanetworks.common.cmnlib.log.ErrReportSender$ErrReportSenderThread, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r9v1 */
        /* JADX WARN: Type inference failed for: r9v11 */
        /* JADX WARN: Type inference failed for: r9v15 */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        /* JADX WARN: Type inference failed for: r9v18 */
        /* JADX WARN: Type inference failed for: r9v19 */
        /* JADX WARN: Type inference failed for: r9v2, types: [com.felicanetworks.common.cmnlib.log.ErrReportSender$ErrReportSenderThread, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r9v20 */
        /* JADX WARN: Type inference failed for: r9v6 */
        /* JADX WARN: Type inference failed for: r9v8 */
        /* JADX WARN: Type inference failed for: r9v9 */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 376
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.common.cmnlib.log.ErrReportSender.ErrReportSenderThread.run():void");
        }

        protected int convExceptionToNotificationParam(DataParseException dataParseException) {
            return dataParseException.getErrorId() != 2 ? -3 : -2;
        }

        protected int convExceptionToNotificationParam(NetworkAccessException networkAccessException) {
            int errorId = networkAccessException.getErrorId();
            if (errorId != 1) {
                if (errorId == 3) {
                    return -1;
                }
                if (errorId != 4) {
                    return -3;
                }
            }
            return -2;
        }
    }
}
