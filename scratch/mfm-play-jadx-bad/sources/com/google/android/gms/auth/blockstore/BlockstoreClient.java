package com.google.android.gms.auth.blockstore;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-blockstore@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public interface BlockstoreClient extends HasApiKey<Api.ApiOptions.NoOptions> {
    public static final String DEFAULT_BYTES_DATA_KEY = "com.google.android.gms.auth.blockstore.DEFAULT_BYTES_DATA_KEY";
    public static final int MAX_ENTRY_COUNT = 16;
    public static final int MAX_SIZE = 4096;

    Task<Boolean> deleteBytes(DeleteBytesRequest deleteBytesRequest);

    Task<Boolean> isEndToEndEncryptionAvailable();

    @Deprecated
    Task<byte[]> retrieveBytes();

    Task<RetrieveBytesResponse> retrieveBytes(RetrieveBytesRequest retrieveBytesRequest);

    Task<Integer> storeBytes(StoreBytesData storeBytesData);
}
