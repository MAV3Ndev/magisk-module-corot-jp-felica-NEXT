package com.felicanetworks.mfm.main.view.views.list;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder;
import com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper;
import com.felicanetworks.mfm.main.view.views.list.MyServiceFooterViewHolder;
import com.felicanetworks.mfm.main.view.views.list.MyServiceHeaderViewHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceViewHolder> implements MyServiceDragHelper.OnDragListener<MyServiceContentViewHolder> {
    private final AdapterClient client;
    private final MyServiceDragHelper dragHelper;
    private MyServiceFooterViewHolder footer;
    private final List<MyServiceGroupInfoAgent> groups;
    private MyServiceHeaderViewHolder header;
    private final CentralDrawStructure structure;
    private final List<MyServiceGroupInfoAgent> timestampForDrag;
    private List<MyServiceViewHolder> viewHolders;

    public static abstract class AdapterClient implements MyServiceHeaderViewHolder.OnClickHeaderListener, MyServiceFooterViewHolder.OnClickFooterListener, MyServiceContentViewHolder.OnClickContentListener {
        protected abstract void onChangedItems(List<MyServiceGroupInfoAgent> groups);
    }

    private enum ViewType {
        HEADER(1),
        CONTENT(2),
        FOOTER(3),
        NONE(0);

        private final int value;

        ViewType(int value) {
            this.value = value;
        }

        static ViewType resolve(int value) {
            for (ViewType viewType : values()) {
                if (viewType.value == value) {
                    return viewType;
                }
            }
            return NONE;
        }
    }

    public MyServiceAdapter(CentralDrawStructure structure, AdapterClient client) {
        ArrayList arrayList = new ArrayList();
        this.groups = arrayList;
        this.timestampForDrag = new ArrayList();
        this.dragHelper = new MyServiceDragHelper(this);
        this.viewHolders = new ArrayList();
        this.structure = structure;
        this.client = client;
        arrayList.addAll(structure.getCentralFunc().getMyServiceGroupInfoList());
    }

    public void invalidateAdapter() {
        this.groups.clear();
        this.groups.addAll(this.structure.getCentralFunc().getMyServiceGroupInfoList());
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.dragHelper.attach(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.dragHelper.detach();
    }

    /* JADX DEBUG: Method merged with bridge method: onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder; */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyServiceViewHolder myServiceContentViewHolder;
        int iOrdinal = ViewType.resolve(viewType).ordinal();
        if (iOrdinal == 0) {
            if (this.header == null) {
                this.header = new MyServiceHeaderViewHolder(parent, this.structure, this.client);
            }
            myServiceContentViewHolder = this.header;
        } else if (iOrdinal == 1) {
            myServiceContentViewHolder = new MyServiceContentViewHolder(parent, this.structure, this.client);
        } else if (iOrdinal == 2) {
            if (this.footer == null) {
                this.footer = new MyServiceFooterViewHolder(parent, this.structure, this.client);
            }
            myServiceContentViewHolder = this.footer;
        } else {
            throw new IllegalStateException("This is Bug !!");
        }
        this.viewHolders.add(myServiceContentViewHolder);
        return myServiceContentViewHolder;
    }

    /* JADX DEBUG: Method merged with bridge method: onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyServiceViewHolder holder, int position) {
        if ((holder instanceof MyServiceHeaderViewHolder) || (holder instanceof MyServiceFooterViewHolder)) {
            holder.bind(null);
        } else if (holder instanceof MyServiceContentViewHolder) {
            holder.bind(this.groups.get(position - 1));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.groups.size() + 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        ViewType viewType;
        if (position == 0) {
            viewType = ViewType.HEADER;
        } else if (position == this.groups.size() + 1) {
            viewType = ViewType.FOOTER;
        } else {
            viewType = ViewType.CONTENT;
        }
        return viewType.value;
    }

    public void resume() {
        Iterator<MyServiceViewHolder> it = this.viewHolders.iterator();
        while (it.hasNext()) {
            it.next().resume();
        }
    }

    public void pause() {
        Iterator<MyServiceViewHolder> it = this.viewHolders.iterator();
        while (it.hasNext()) {
            it.next().pause();
        }
    }

    public void destroy() {
        Iterator<MyServiceViewHolder> it = this.viewHolders.iterator();
        while (it.hasNext()) {
            it.next().unbind();
        }
    }

    /* JADX DEBUG: Method merged with bridge method: onDragStart(Lcom/felicanetworks/mfm/main/view/views/list/MyServiceDragHelper$Draggable;)V */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public void onDragStart(MyServiceContentViewHolder item) {
        this.timestampForDrag.clear();
        this.timestampForDrag.addAll(this.groups);
    }

    /* JADX DEBUG: Method merged with bridge method: onDragMove(Lcom/felicanetworks/mfm/main/view/views/list/MyServiceDragHelper$Draggable;Lcom/felicanetworks/mfm/main/view/views/list/MyServiceDragHelper$Draggable;)Z */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public boolean onDragMove(MyServiceContentViewHolder from, MyServiceContentViewHolder to) {
        notifyItemMoved(from.getBindingAdapterPosition(), to.getBindingAdapterPosition());
        MyServiceGroupInfoAgent data = from.getData();
        MyServiceGroupInfoAgent data2 = to.getData();
        int iIndexOf = this.timestampForDrag.indexOf(data);
        int iIndexOf2 = this.timestampForDrag.indexOf(data2);
        MyServiceGroupInfoAgent myServiceGroupInfoAgent = this.timestampForDrag.get(iIndexOf);
        this.timestampForDrag.remove(iIndexOf);
        this.timestampForDrag.add(iIndexOf2, myServiceGroupInfoAgent);
        return true;
    }

    /* JADX DEBUG: Method merged with bridge method: onDragEnd(Lcom/felicanetworks/mfm/main/view/views/list/MyServiceDragHelper$Draggable;)V */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public void onDragEnd(MyServiceContentViewHolder item) {
        if (!this.timestampForDrag.equals(this.groups)) {
            this.groups.clear();
            this.groups.addAll(this.timestampForDrag);
            this.client.onChangedItems(this.groups);
        }
        this.timestampForDrag.clear();
    }

    public void update() {
        this.groups.clear();
        this.groups.addAll(this.structure.getCentralFunc().getMyServiceGroupInfoList());
        notifyDataSetChanged();
        for (MyServiceViewHolder myServiceViewHolder : this.viewHolders) {
            if (myServiceViewHolder instanceof MyServiceHeaderViewHolder) {
                ((MyServiceHeaderViewHolder) myServiceViewHolder).update();
            } else if (myServiceViewHolder instanceof MyServiceContentViewHolder) {
                ((MyServiceContentViewHolder) myServiceViewHolder).update();
            }
        }
    }
}
