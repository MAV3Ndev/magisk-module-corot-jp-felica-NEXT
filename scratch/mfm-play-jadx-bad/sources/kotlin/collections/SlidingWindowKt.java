package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: SlidingWindow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aD\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0000\u001aH\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\r\"\u0004\b\u0000\u0010\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\b0\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0000¨\u0006\u000f"}, d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedSequence", "Lkotlin/sequences/Sequence;", "", "T", "partialWindows", "", "reuseBuffer", "windowedIterator", "", "iterator", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class SlidingWindowKt {
    public static final void checkWindowSizeStep(int i, int i2) {
        String str;
        if (i <= 0 || i2 <= 0) {
            if (i != i2) {
                str = "Both size " + i + " and step " + i2 + " must be greater than zero.";
            } else {
                str = "size " + i + " must be greater than zero.";
            }
            throw new IllegalArgumentException(str.toString());
        }
    }

    public static final <T> Sequence<List<T>> windowedSequence(final Sequence<? extends T> sequence, final int i, final int i2, final boolean z, final boolean z2) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        checkWindowSizeStep(i, i2);
        return new Sequence<List<? extends T>>() { // from class: kotlin.collections.SlidingWindowKt$windowedSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<List<? extends T>> iterator() {
                return SlidingWindowKt.windowedIterator(sequence.iterator(), i, i2, z, z2);
            }
        };
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlin.collections.SlidingWindowKt$windowedIterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: SlidingWindow.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\n"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", ""}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", f = "SlidingWindow.kt", i = {0, 0, 0, 2, 2, 3, 3}, l = {34, 40, 49, 55, 58}, m = "invokeSuspend", n = {"$this$iterator", "buffer", "gap", "$this$iterator", "buffer", "$this$iterator", "buffer"}, s = {"L$0", "L$1", "I$0", "L$0", "L$1", "L$0", "L$1"})
    static final class AnonymousClass1<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Iterator<T> $iterator;
        final /* synthetic */ boolean $partialWindows;
        final /* synthetic */ boolean $reuseBuffer;
        final /* synthetic */ int $size;
        final /* synthetic */ int $step;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.util.Iterator<? extends T> */
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(int i, int i2, Iterator<? extends T> it, boolean z, boolean z2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$size = i;
            this.$step = i2;
            this.$iterator = it;
            this.$reuseBuffer = z;
            this.$partialWindows = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX DEBUG: Method merged with bridge method: invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; */
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super List<? extends T>> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00ad  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00b1  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0143  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x016d  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x00bb A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00a5 -> B:16:0x0055). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x012c -> B:59:0x012f). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x0164 -> B:72:0x0167). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r10.label
                r2 = 5
                r3 = 4
                r4 = 3
                r5 = 2
                r6 = 1
                r7 = 0
                if (r1 == 0) goto L57
                if (r1 == r6) goto L44
                if (r1 == r5) goto L3f
                if (r1 == r4) goto L2e
                if (r1 == r3) goto L21
                if (r1 != r2) goto L19
                goto L3f
            L19:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r0)
                throw r11
            L21:
                java.lang.Object r1 = r10.L$1
                kotlin.collections.RingBuffer r1 = (kotlin.collections.RingBuffer) r1
                java.lang.Object r4 = r10.L$0
                kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
                kotlin.ResultKt.throwOnFailure(r11)
                goto L167
            L2e:
                java.lang.Object r1 = r10.L$2
                java.util.Iterator r1 = (java.util.Iterator) r1
                java.lang.Object r5 = r10.L$1
                kotlin.collections.RingBuffer r5 = (kotlin.collections.RingBuffer) r5
                java.lang.Object r6 = r10.L$0
                kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
                kotlin.ResultKt.throwOnFailure(r11)
                goto L12f
            L3f:
                kotlin.ResultKt.throwOnFailure(r11)
                goto L188
            L44:
                int r1 = r10.I$0
                java.lang.Object r2 = r10.L$2
                java.util.Iterator r2 = (java.util.Iterator) r2
                java.lang.Object r3 = r10.L$1
                java.util.ArrayList r3 = (java.util.ArrayList) r3
                java.lang.Object r4 = r10.L$0
                kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
                kotlin.ResultKt.throwOnFailure(r11)
            L55:
                r11 = r1
                goto La9
            L57:
                kotlin.ResultKt.throwOnFailure(r11)
                java.lang.Object r11 = r10.L$0
                kotlin.sequences.SequenceScope r11 = (kotlin.sequences.SequenceScope) r11
                int r1 = r10.$size
                r8 = 1024(0x400, float:1.435E-42)
                int r1 = kotlin.ranges.RangesKt.coerceAtMost(r1, r8)
                int r8 = r10.$step
                int r9 = r10.$size
                int r8 = r8 - r9
                if (r8 < 0) goto Le3
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>(r1)
                java.util.Iterator<T> r1 = r10.$iterator
                r3 = 0
                r4 = r11
                r11 = r3
                r3 = r2
                r2 = r1
                r1 = r8
            L7a:
                boolean r8 = r2.hasNext()
                if (r8 == 0) goto Lbb
                java.lang.Object r8 = r2.next()
                if (r11 <= 0) goto L89
                int r11 = r11 + (-1)
                goto L7a
            L89:
                r3.add(r8)
                int r8 = r3.size()
                int r9 = r10.$size
                if (r8 != r9) goto L7a
                r11 = r10
                kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                r10.L$0 = r4
                r10.L$1 = r3
                r10.L$2 = r2
                r10.I$0 = r1
                r10.label = r6
                java.lang.Object r11 = r4.yield(r3, r11)
                if (r11 != r0) goto L55
                goto L187
            La9:
                boolean r1 = r10.$reuseBuffer
                if (r1 == 0) goto Lb1
                r3.clear()
                goto Lb9
            Lb1:
                java.util.ArrayList r1 = new java.util.ArrayList
                int r3 = r10.$size
                r1.<init>(r3)
                r3 = r1
            Lb9:
                r1 = r11
                goto L7a
            Lbb:
                r11 = r3
                java.util.Collection r11 = (java.util.Collection) r11
                boolean r11 = r11.isEmpty()
                if (r11 != 0) goto L188
                boolean r11 = r10.$partialWindows
                if (r11 != 0) goto Ld0
                int r11 = r3.size()
                int r1 = r10.$size
                if (r11 != r1) goto L188
            Ld0:
                r11 = r10
                kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                r10.L$0 = r7
                r10.L$1 = r7
                r10.L$2 = r7
                r10.label = r5
                java.lang.Object r11 = r4.yield(r3, r11)
                if (r11 != r0) goto L188
                goto L187
            Le3:
                kotlin.collections.RingBuffer r5 = new kotlin.collections.RingBuffer
                r5.<init>(r1)
                java.util.Iterator<T> r1 = r10.$iterator
                r6 = r11
            Leb:
                boolean r11 = r1.hasNext()
                if (r11 == 0) goto L135
                java.lang.Object r11 = r1.next()
                r5.add(r11)
                boolean r11 = r5.isFull()
                if (r11 == 0) goto Leb
                int r11 = r5.size()
                int r8 = r10.$size
                if (r11 >= r8) goto L10b
                kotlin.collections.RingBuffer r5 = r5.expanded(r8)
                goto Leb
            L10b:
                boolean r11 = r10.$reuseBuffer
                if (r11 == 0) goto L113
                r11 = r5
                java.util.List r11 = (java.util.List) r11
                goto L11d
            L113:
                java.util.ArrayList r11 = new java.util.ArrayList
                r8 = r5
                java.util.Collection r8 = (java.util.Collection) r8
                r11.<init>(r8)
                java.util.List r11 = (java.util.List) r11
            L11d:
                r8 = r10
                kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
                r10.L$0 = r6
                r10.L$1 = r5
                r10.L$2 = r1
                r10.label = r4
                java.lang.Object r11 = r6.yield(r11, r8)
                if (r11 != r0) goto L12f
                goto L187
            L12f:
                int r11 = r10.$step
                r5.removeFirst(r11)
                goto Leb
            L135:
                boolean r11 = r10.$partialWindows
                if (r11 == 0) goto L188
                r1 = r5
                r4 = r6
            L13b:
                int r11 = r1.size()
                int r5 = r10.$step
                if (r11 <= r5) goto L16d
                boolean r11 = r10.$reuseBuffer
                if (r11 == 0) goto L14b
                r11 = r1
                java.util.List r11 = (java.util.List) r11
                goto L155
            L14b:
                java.util.ArrayList r11 = new java.util.ArrayList
                r5 = r1
                java.util.Collection r5 = (java.util.Collection) r5
                r11.<init>(r5)
                java.util.List r11 = (java.util.List) r11
            L155:
                r5 = r10
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r10.L$0 = r4
                r10.L$1 = r1
                r10.L$2 = r7
                r10.label = r3
                java.lang.Object r11 = r4.yield(r11, r5)
                if (r11 != r0) goto L167
                goto L187
            L167:
                int r11 = r10.$step
                r1.removeFirst(r11)
                goto L13b
            L16d:
                r11 = r1
                java.util.Collection r11 = (java.util.Collection) r11
                boolean r11 = r11.isEmpty()
                if (r11 != 0) goto L188
                r11 = r10
                kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                r10.L$0 = r7
                r10.L$1 = r7
                r10.L$2 = r7
                r10.label = r2
                java.lang.Object r11 = r4.yield(r1, r11)
                if (r11 != r0) goto L188
            L187:
                return r0
            L188:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.SlidingWindowKt.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static final <T> Iterator<List<T>> windowedIterator(Iterator<? extends T> iterator, int i, int i2, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return !iterator.hasNext() ? EmptyIterator.INSTANCE : SequencesKt.iterator(new AnonymousClass1(i, i2, iterator, z2, z, null));
    }
}
