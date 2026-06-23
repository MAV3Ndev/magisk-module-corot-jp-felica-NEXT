package com.felicanetworks.mfm.main.view.views.list;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;

/* JADX INFO: loaded from: classes.dex */
public abstract class MyServiceViewHolder<T> extends RecyclerView.ViewHolder {
    protected final CentralDrawStructure structure;

    public void bind(T t) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void unbind() {
    }

    public MyServiceViewHolder(View view, CentralDrawStructure centralDrawStructure) {
        super(view);
        this.structure = centralDrawStructure;
    }
}
