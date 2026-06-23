package androidx.work;

import android.content.ComponentName;
import androidx.work.Data;
import androidx.work.impl.utils.EnqueueUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WorkerParameters.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\t\u001a!\u0010\n\u001a\u00020\t\"\n\b\u0000\u0010\u000b\u0018\u0001*\u00020\f*\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\b\u001a\u001a\u0010\n\u001a\u00020\t*\u00020\t2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u000e"}, d2 = {"buildDelegatedRemoteRequestData", "Landroidx/work/Data;", "delegatedWorkerName", "", "componentName", "Landroid/content/ComponentName;", "inputData", "isRemoteWorkRequest", "", "Landroidx/work/WorkerParameters;", "usingRemoteService", "T", "Landroidx/work/ListenableWorker;", "workerClassName", "work-runtime_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class WorkerParametersExtensions {
    public static final boolean isRemoteWorkRequest(WorkerParameters workerParameters) {
        Intrinsics.checkNotNullParameter(workerParameters, "<this>");
        Data inputData = workerParameters.getInputData();
        Intrinsics.checkNotNullExpressionValue(inputData, "inputData");
        return isRemoteWorkRequest(inputData);
    }

    public static final /* synthetic */ <T extends ListenableWorker> WorkerParameters usingRemoteService(WorkerParameters workerParameters, ComponentName componentName) {
        Intrinsics.checkNotNullParameter(workerParameters, "<this>");
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.reifiedOperationMarker(4, "T");
        String name = ListenableWorker.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "T::class.java.name");
        return usingRemoteService(workerParameters, name, componentName);
    }

    public static final WorkerParameters usingRemoteService(WorkerParameters workerParameters, String workerClassName, ComponentName componentName) {
        Intrinsics.checkNotNullParameter(workerParameters, "<this>");
        Intrinsics.checkNotNullParameter(workerClassName, "workerClassName");
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        UUID id = workerParameters.getId();
        Data inputData = workerParameters.getInputData();
        Intrinsics.checkNotNullExpressionValue(inputData, "inputData");
        return new WorkerParameters(id, buildDelegatedRemoteRequestData(workerClassName, componentName, inputData), workerParameters.getTags(), workerParameters.getRuntimeExtras(), workerParameters.getRunAttemptCount(), workerParameters.getGeneration(), workerParameters.getBackgroundExecutor(), workerParameters.getWorkerContext(), workerParameters.getTaskExecutor(), workerParameters.getWorkerFactory(), workerParameters.getProgressUpdater(), workerParameters.getForegroundUpdater());
    }

    public static final Data buildDelegatedRemoteRequestData(String delegatedWorkerName, ComponentName componentName, Data inputData) {
        Intrinsics.checkNotNullParameter(delegatedWorkerName, "delegatedWorkerName");
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(inputData, "inputData");
        Data.Builder builder = new Data.Builder();
        builder.putAll(inputData).putString(EnqueueUtilsKt.ARGUMENT_SERVICE_PACKAGE_NAME, componentName.getPackageName()).putString(EnqueueUtilsKt.ARGUMENT_SERVICE_CLASS_NAME, componentName.getClassName()).putString(EnqueueUtilsKt.ARGUMENT_REMOTE_LISTENABLE_WORKER_NAME, delegatedWorkerName);
        return builder.build();
    }

    public static final boolean isRemoteWorkRequest(Data data) {
        Intrinsics.checkNotNullParameter(data, "<this>");
        return data.hasKeyWithValueOfType(EnqueueUtilsKt.ARGUMENT_SERVICE_PACKAGE_NAME, String.class) && data.hasKeyWithValueOfType(EnqueueUtilsKt.ARGUMENT_SERVICE_CLASS_NAME, String.class) && data.hasKeyWithValueOfType(EnqueueUtilsKt.ARGUMENT_REMOTE_LISTENABLE_WORKER_NAME, String.class);
    }
}
