package com.felicanetworks.mfm.main.view.views.list;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class MyServiceDragHelper extends ItemTouchHelper.SimpleCallback {
    private int dragState;
    private Draggable draggedItem;
    private final ItemTouchHelper helper;
    private final OnDragListener listener;

    interface Draggable {
        void onDragEnd();

        void onDragStart();
    }

    public interface OnDragListener<T extends Draggable> {
        void onDragEnd(T t);

        boolean onDragMove(T t, T t2);

        void onDragStart(T t);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    }

    MyServiceDragHelper(OnDragListener onDragListener) {
        super(3, 0);
        this.dragState = 0;
        this.listener = onDragListener;
        this.helper = new ItemTouchHelper(this);
    }

    void attach(RecyclerView recyclerView) {
        this.helper.attachToRecyclerView(recyclerView);
    }

    void detach() {
        this.helper.attachToRecyclerView(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        if ((viewHolder instanceof Draggable) && (viewHolder2 instanceof Draggable)) {
            return this.listener.onDragMove((Draggable) viewHolder, (Draggable) viewHolder2);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        if (this.dragState == i) {
            return;
        }
        if (i == 2) {
            Draggable draggable = (Draggable) viewHolder;
            this.draggedItem = draggable;
            this.listener.onDragStart(draggable);
            this.draggedItem.onDragStart();
        } else {
            this.draggedItem.onDragEnd();
            this.listener.onDragEnd(this.draggedItem);
            this.draggedItem = null;
        }
        this.dragState = i;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof Draggable) {
            return super.getMovementFlags(recyclerView, viewHolder);
        }
        return 0;
    }
}
