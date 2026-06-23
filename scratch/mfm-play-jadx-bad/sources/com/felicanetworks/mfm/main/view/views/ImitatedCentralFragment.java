package com.felicanetworks.mfm.main.view.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.view.widget.HorizontalLoadingView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ImitatedCentralFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private Insets mInsets = Insets.NONE;
    private Adapter adapter = new Adapter();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_imitated_central, container, false);
        adjustViewToSystemBar(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustViewToSystemBar(getView());
    }

    private void adjustViewToSystemBar(View view) {
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.main.view.views.ImitatedCentralFragment$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m418x1e711536(view2, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$adjustViewToSystemBar$0$com-felicanetworks-mfm-main-view-views-ImitatedCentralFragment, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m418x1e711536(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        if (this.mInsets.top != insets.top) {
            View viewFindViewById = view.findViewById(R.id.toolbar);
            viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), (viewFindViewById.getPaddingTop() + insets.top) - this.mInsets.top, viewFindViewById.getPaddingRight(), viewFindViewById.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
            layoutParams.height = (layoutParams.height + insets.top) - this.mInsets.top;
            viewFindViewById.setLayoutParams(layoutParams);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (this.mInsets.left != insets.left) {
            marginLayoutParams.leftMargin = (marginLayoutParams.leftMargin + insets.left) - this.mInsets.left;
        }
        if (this.mInsets.right != insets.right) {
            marginLayoutParams.rightMargin = (marginLayoutParams.rightMargin + insets.right) - this.mInsets.right;
        }
        view.setLayoutParams(marginLayoutParams);
        this.mInsets = insets;
        return windowInsetsCompat;
    }

    private static class Adapter extends RecyclerView.Adapter<LoadingServiceViewHolder> {
        private List<LoadingServiceViewHolder> holders;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return 3;
        }

        /* JADX DEBUG: Method merged with bridge method: onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(LoadingServiceViewHolder holder, int position) {
        }

        private Adapter() {
            this.holders = new ArrayList();
        }

        /* JADX DEBUG: Method merged with bridge method: onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder; */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public LoadingServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LoadingServiceViewHolder loadingServiceViewHolder = new LoadingServiceViewHolder(parent);
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
        LoadingServiceViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_loading_service, parent, false));
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
