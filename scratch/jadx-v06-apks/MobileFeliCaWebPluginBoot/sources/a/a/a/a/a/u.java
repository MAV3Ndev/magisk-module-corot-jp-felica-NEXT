package a.a.a.a.a;

import a.a.a.a.c.u1;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: InProcessView.java */
/* JADX INFO: loaded from: classes.dex */
class u implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ v f10a;

    u(v vVar) {
        this.f10a = vVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ImageView imageView = (ImageView) ((u1) this.f10a).f.g().e(R.id.anim_view);
        this.f10a.j = (AnimationDrawable) imageView.getBackground();
        this.f10a.j.start();
    }
}
