package com.felicanetworks.mfm.main.view.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.widget.HorizontalLoadingView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ImitatedCentralFragment extends BaseFragment {
    private Adapter adapter = new Adapter();
    private RecyclerView recyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_imitated_central, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            this.recyclerView = recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            this.recyclerView.setAdapter(this.adapter);
            appCompatActivity.setTitle(R.string.toolbar_title_myservice);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            appCompatActivity.setSupportActionBar(toolbar);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(appCompatActivity, (DrawerLayout) view.findViewById(R.id.drawer), toolbar, R.string.toolbar_title_drawer_open, R.string.toolbar_title_drawer_close);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer_imitated);
            View view2 = new View(getContext());
            view2.setFocusableInTouchMode(true);
            view2.setClickable(true);
            view2.requestFocus();
            ((ViewGroup) getActivity().findViewById(R.id.drawer)).addView(view2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.adapter.destroy();
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.adapter.resume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.adapter.pause();
    }

    private static class Adapter extends RecyclerView.Adapter<LoadingServiceViewHolder> {
        private List<LoadingServiceViewHolder> holders;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return 3;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(LoadingServiceViewHolder loadingServiceViewHolder, int i) {
        }

        private Adapter() {
            this.holders = new ArrayList();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public LoadingServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LoadingServiceViewHolder loadingServiceViewHolder = new LoadingServiceViewHolder(viewGroup);
            this.holders.add(loadingServiceViewHolder);
            return loadingServiceViewHolder;
        }

        void resume() {
            Iterator<LoadingServiceViewHolder> it = this.holders.iterator();
            while (it.hasNext()) {
                it.next().resume();
            }
        }

        void pause() {
            Iterator<LoadingServiceViewHolder> it = this.holders.iterator();
            while (it.hasNext()) {
                it.next().pause();
            }
        }

        void destroy() {
            Iterator<LoadingServiceViewHolder> it = this.holders.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.holders.clear();
        }
    }

    private static class LoadingServiceViewHolder extends RecyclerView.ViewHolder {
        LoadingServiceViewHolder(ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_loading_service, viewGroup, false));
        }

        HorizontalLoadingView view() {
            return (HorizontalLoadingView) this.itemView.findViewById(R.id.loadingView);
        }

        void resume() {
            view().start();
        }

        void pause() {
            view().stop();
        }

        void destroy() {
            view().stop();
        }
    }
}
