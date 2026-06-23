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

/* JADX INFO: loaded from: classes.dex */
public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceViewHolder> implements MyServiceDragHelper.OnDragListener<MyServiceContentViewHolder> {
    private final AdapterClient client;
    private MyServiceFooterViewHolder footer;
    private MyServiceHeaderViewHolder header;
    private final CentralDrawStructure structure;
    private final List<MyServiceGroupInfoAgent> groups = new ArrayList();
    private final List<MyServiceGroupInfoAgent> timestampForDrag = new ArrayList();
    private final MyServiceDragHelper dragHelper = new MyServiceDragHelper(this);
    private List<MyServiceViewHolder> viewHolders = new ArrayList();

    public static abstract class AdapterClient implements MyServiceHeaderViewHolder.OnClickHeaderListener, MyServiceFooterViewHolder.OnClickFooterListener, MyServiceContentViewHolder.OnClickContentListener {
        protected abstract void onChangedItems(List<MyServiceGroupInfoAgent> list);
    }

    private enum ViewType {
        HEADER(1),
        CONTENT(2),
        FOOTER(3),
        NONE(0);

        private final int value;

        ViewType(int i) {
            this.value = i;
        }

        static ViewType resolve(int i) {
            for (ViewType viewType : values()) {
                if (viewType.value == i) {
                    return viewType;
                }
            }
            return NONE;
        }
    }

    public MyServiceAdapter(CentralDrawStructure centralDrawStructure, AdapterClient adapterClient) {
        this.structure = centralDrawStructure;
        this.client = adapterClient;
        this.groups.addAll(centralDrawStructure.getCentralFunc().getMyServiceGroupInfoList());
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.list.MyServiceAdapter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$list$MyServiceAdapter$ViewType;

        static {
            int[] iArr = new int[ViewType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$list$MyServiceAdapter$ViewType = iArr;
            try {
                iArr[ViewType.HEADER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$list$MyServiceAdapter$ViewType[ViewType.CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$list$MyServiceAdapter$ViewType[ViewType.FOOTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyServiceViewHolder myServiceContentViewHolder;
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$list$MyServiceAdapter$ViewType[ViewType.resolve(i).ordinal()];
        if (i2 == 1) {
            if (this.header == null) {
                this.header = new MyServiceHeaderViewHolder(viewGroup, this.structure, this.client);
            }
            myServiceContentViewHolder = this.header;
        } else if (i2 == 2) {
            myServiceContentViewHolder = new MyServiceContentViewHolder(viewGroup, this.structure, this.client);
        } else if (i2 == 3) {
            if (this.footer == null) {
                this.footer = new MyServiceFooterViewHolder(viewGroup, this.structure, this.client);
            }
            myServiceContentViewHolder = this.footer;
        } else {
            throw new IllegalStateException("This is Bug !!");
        }
        this.viewHolders.add(myServiceContentViewHolder);
        return myServiceContentViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyServiceViewHolder myServiceViewHolder, int i) {
        if ((myServiceViewHolder instanceof MyServiceHeaderViewHolder) || (myServiceViewHolder instanceof MyServiceFooterViewHolder)) {
            myServiceViewHolder.bind(null);
        } else if (myServiceViewHolder instanceof MyServiceContentViewHolder) {
            myServiceViewHolder.bind(this.groups.get(i - 1));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.groups.size() + 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ViewType viewType;
        if (i == 0) {
            viewType = ViewType.HEADER;
        } else if (i == this.groups.size() + 1) {
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

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public void onDragStart(MyServiceContentViewHolder myServiceContentViewHolder) {
        this.timestampForDrag.clear();
        this.timestampForDrag.addAll(this.groups);
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public boolean onDragMove(MyServiceContentViewHolder myServiceContentViewHolder, MyServiceContentViewHolder myServiceContentViewHolder2) {
        notifyItemMoved(myServiceContentViewHolder.getBindingAdapterPosition(), myServiceContentViewHolder2.getBindingAdapterPosition());
        MyServiceGroupInfoAgent data = myServiceContentViewHolder.getData();
        MyServiceGroupInfoAgent data2 = myServiceContentViewHolder2.getData();
        int iIndexOf = this.timestampForDrag.indexOf(data);
        int iIndexOf2 = this.timestampForDrag.indexOf(data2);
        MyServiceGroupInfoAgent myServiceGroupInfoAgent = this.timestampForDrag.get(iIndexOf);
        this.timestampForDrag.remove(iIndexOf);
        this.timestampForDrag.add(iIndexOf2, myServiceGroupInfoAgent);
        return true;
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.OnDragListener
    public void onDragEnd(MyServiceContentViewHolder myServiceContentViewHolder) {
        if (!this.timestampForDrag.equals(this.groups)) {
            this.groups.clear();
            this.groups.addAll(this.timestampForDrag);
            this.client.onChangedItems(this.groups);
        }
        this.timestampForDrag.clear();
    }
}
