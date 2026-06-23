package androidx.work.impl.background.systemalarm;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DelayMetCommandHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DelayMetCommandHandler f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [androidx.work.impl.background.systemalarm.DelayMetCommandHandler.handleProcessWork():void, androidx.work.impl.background.systemalarm.DelayMetCommandHandler.onConstraintsStateChanged(androidx.work.impl.model.WorkSpec, androidx.work.impl.constraints.ConstraintsState):void, androidx.work.impl.background.systemalarm.DelayMetCommandHandler.onTimeLimitExceeded(androidx.work.impl.model.WorkGenerationalId):void] */
    public /* synthetic */ DelayMetCommandHandler$$ExternalSyntheticLambda0(DelayMetCommandHandler delayMetCommandHandler) {
        this.f$0 = delayMetCommandHandler;
    }

    /* JADX DEBUG: Class process forced to load method for inline: androidx.work.impl.background.systemalarm.DelayMetCommandHandler.$r8$lambda$82vXfMh9MXtN-tLNgTa3KWbb4VE(androidx.work.impl.background.systemalarm.DelayMetCommandHandler):void */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.stopWork();
    }
}
