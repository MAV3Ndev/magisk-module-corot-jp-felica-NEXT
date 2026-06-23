package com.felicanetworks.mfm.main.view.views.list;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes3.dex */
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
        void onDragEnd(T item);

        boolean onDragMove(T from, T to);

        void onDragStart(T item);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    MyServiceDragHelper(OnDragListener listener) {
        super(3, 0);
        this.dragState = 0;
        this.listener = listener;
        this.helper = new ItemTouchHelper(this);
    }

    void attach(RecyclerView recyclerView) {
        this.helper.attachToRecyclerView(recyclerView);
    }

    void detach() {
        this.helper.attachToRecyclerView(null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: androidx.recyclerview.widget.RecyclerView$ViewHolder */
    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: androidx.recyclerview.widget.RecyclerView$ViewHolder */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if ((viewHolder instanceof Draggable) && (target instanceof Draggable)) {
            return this.listener.onDragMove((Draggable) viewHolder, (Draggable) target);
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: androidx.recyclerview.widget.RecyclerView$ViewHolder */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (this.dragState == actionState) {
            return;
        }
        if (actionState == 2) {
            Draggable draggable = (Draggable) viewHolder;
            this.draggedItem = draggable;
            this.listener.onDragStart(draggable);
            this.draggedItem.onDragStart();
        } else {
            this.draggedItem.onDragEnd();
            this.listener.onDragEnd(this.draggedItem);
            this.draggedItem = null;
        }
        this.dragState = actionState;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof Draggable) {
            return super.getMovementFlags(recyclerView, viewHolder);
        }
        return 0;
    }
}
