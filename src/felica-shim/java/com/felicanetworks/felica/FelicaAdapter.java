package com.felicanetworks.felica;

import android.content.Context;

public final class FelicaAdapter {
    private static FelicaAdapter sAdapter;

    private final Context context;
    private final IFelicaRf rfService = new UnavailableFelicaRf();
    private final IFelicaSe seService = new UnavailableFelicaSe();

    private FelicaAdapter(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        this.context = context.getApplicationContext();
    }

    public static synchronized FelicaAdapter getDefaultAdapter(Context context) {
        if (sAdapter == null) {
            sAdapter = new FelicaAdapter(context);
        }
        return sAdapter;
    }

    public Context getContext() {
        return context;
    }

    public IFelicaRf getFelicaRfService() {
        return rfService;
    }

    public IFelicaSe getFelicaSeService() {
        return seService;
    }
}
