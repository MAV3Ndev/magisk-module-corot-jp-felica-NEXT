package com.felicanetworks.mfm.main.view.views.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper;
import java.text.NumberFormat;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceContentViewHolder extends MyServiceViewHolder<MyServiceGroupInfoAgent> implements MyServiceDragHelper.Draggable {
    private final View borderLine;
    private final ViewGroup cardListFrame;
    private final View changeCardButton;
    private MyServiceGroupInfoAgent data;
    private final TextView deleteCardListConductor;
    private final View groupHeaderFrame;
    private final ImageView groupIcon;
    private final TextView groupName;
    private final LayoutInflater inflater;
    private final OnClickContentListener listener;

    interface OnClickContentListener {
        void onClickCard(MyServiceGroupInfoAgent group, MyServiceInfoAgent service);

        void onClickChangeCard(MyServiceGroupInfoAgent group);

        void onClickDeleteCardListConductor(MyServiceGroupInfoAgent group);

        void onClickMfiLogin(MyServiceInfoAgent info);

        void onClickMoreInformation(MyServiceGroupInfoAgent group, MyServiceInfoAgent service);

        void onClickRegisterService(MyServiceInfoAgent info);

        void onClickRegisterServiceClose(MyServiceInfoAgent info);
    }

    MyServiceContentViewHolder(ViewGroup parent, CentralDrawStructure structure, OnClickContentListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_myservice_group, parent, false), structure);
        this.listener = listener;
        this.inflater = LayoutInflater.from(parent.getContext());
        this.groupHeaderFrame = this.itemView.findViewById(R.id.groupHeaderFrame);
        this.groupIcon = (ImageView) this.itemView.findViewById(R.id.groupIcon);
        this.groupName = (TextView) this.itemView.findViewById(R.id.groupName);
        this.changeCardButton = this.itemView.findViewById(R.id.changeCardButton);
        this.cardListFrame = (ViewGroup) this.itemView.findViewById(R.id.cardListFrame);
        this.borderLine = this.itemView.findViewById(R.id.borderLine);
        this.deleteCardListConductor = (TextView) this.itemView.findViewById(R.id.deleteCardListConductor);
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.Draggable
    public void onDragStart() {
        this.itemView.setSelected(true);
    }

    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceDragHelper.Draggable
    public void onDragEnd() {
        this.itemView.setSelected(false);
    }

    public MyServiceGroupInfoAgent getData() {
        return this.data;
    }

    /* JADX DEBUG: Method merged with bridge method: bind(Ljava/lang/Object;)V */
    @Override // com.felicanetworks.mfm.main.view.views.list.MyServiceViewHolder
    public void bind(final MyServiceGroupInfoAgent data) {
        this.data = data;
        Context context = this.itemView.getContext();
        this.cardListFrame.removeAllViews();
        if (data.isHidden()) {
            this.groupHeaderFrame.setVisibility(8);
            this.itemView.setVisibility(8);
            return;
        }
        this.itemView.setVisibility(0);
        this.itemView.setOnDragListener(new View.OnDragListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.1
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
        this.groupHeaderFrame.setVisibility(data.hasHeader() ? 0 : 8);
        this.groupIcon.setImageBitmap(data.getGroupIcon(context));
        this.groupName.setText(data.getGroupTitle(context));
        this.changeCardButton.setVisibility(data.isChangeableCard() ? 0 : 8);
        this.changeCardButton.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickChangeCard(data);
            }
        });
        for (MyServiceInfoAgent myServiceInfoAgent : data.getServices()) {
            if (!myServiceInfoAgent.isHidden()) {
                ViewGroup viewGroup = this.cardListFrame;
                viewGroup.addView(buildCardView(viewGroup, data, myServiceInfoAgent));
            }
        }
        this.borderLine.setVisibility(data.hasDeleteCard() ? 0 : 8);
        this.deleteCardListConductor.setVisibility(data.hasDeleteCard() ? 0 : 8);
        this.deleteCardListConductor.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickDeleteCardListConductor(data);
            }
        });
    }

    public void update() {
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_card, this.cardListFrame, false);
        for (MyServiceInfoAgent myServiceInfoAgent : this.data.getServices()) {
            if (!myServiceInfoAgent.isHidden()) {
                View viewBuildLeadView = buildLeadView((ViewGroup) viewInflate.findViewById(R.id.leadFrame), myServiceInfoAgent);
                ViewGroup viewGroup = (ViewGroup) viewInflate.findViewById(R.id.assetFrame);
                View viewBuildAssetView = buildAssetView(viewGroup, myServiceInfoAgent);
                if (viewBuildAssetView == null || !myServiceInfoAgent.isActiveService() || viewBuildLeadView != null) {
                    viewGroup.setVisibility(8);
                } else {
                    viewGroup.setVisibility(0);
                    viewGroup.addView(viewBuildAssetView);
                }
            }
        }
    }

    private View buildCardView(final ViewGroup parent, final MyServiceGroupInfoAgent group, final MyServiceInfoAgent service) {
        final View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_card, parent, false);
        final ImageView imageView = (ImageView) viewInflate.findViewById(R.id.cardImage);
        imageView.setImageBitmap(null);
        imageView.setVisibility(4);
        final View viewFindViewById = viewInflate.findViewById(R.id.cardShadowImage);
        if (service.isShowCardShadowImage()) {
            viewFindViewById.setVisibility(4);
        } else {
            viewFindViewById.setVisibility(8);
        }
        this.structure.getCentralFunc().getCardFaceImage(service, new CentralFuncAgent.CardFaceImageListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.4
            @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.CardFaceImageListener
            public void onGetImage(Bitmap image, CentralFuncAgent.CardFaceImageListener.Type type) {
                imageView.setVisibility(0);
                if (image != null) {
                    imageView.setImageBitmap(image);
                    int i = AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CardFaceImageListener$Type[type.ordinal()];
                    if (i == 1) {
                        imageView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                        imageView.setClipToOutline(false);
                    } else if (i == 2) {
                        imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.4.1
                            @Override // android.view.ViewOutlineProvider
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) (parent.getResources().getDisplayMetrics().density * 3.0f));
                            }
                        });
                        imageView.setClipToOutline(true);
                    }
                } else {
                    imageView.setImageResource(R.drawable.sg_default_service_icon);
                }
                Animation animationLoadAnimation = AnimationUtils.loadAnimation(viewInflate.getContext(), android.R.anim.fade_in);
                imageView.startAnimation(animationLoadAnimation);
                if (viewFindViewById.getVisibility() == 4) {
                    viewFindViewById.setVisibility(0);
                    viewFindViewById.startAnimation(animationLoadAnimation);
                }
            }
        });
        ViewGroup viewGroup = (ViewGroup) viewInflate.findViewById(R.id.leadFrame);
        View viewBuildLeadView = buildLeadView(viewGroup, service);
        if (viewBuildLeadView == null) {
            viewGroup.setVisibility(8);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.5
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    MyServiceContentViewHolder.this.listener.onClickCard(group, service);
                }
            });
        } else {
            viewGroup.setVisibility(0);
            viewGroup.addView(viewBuildLeadView);
        }
        TextView textView = (TextView) viewInflate.findViewById(R.id.cardNameText);
        textView.setText(service.getServiceName());
        if (viewBuildLeadView != null || !service.isActiveService()) {
            textView.setEnabled(false);
        } else {
            textView.setEnabled(true);
        }
        ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(R.id.assetFrame);
        View viewBuildAssetView = buildAssetView(viewGroup2, service);
        if (viewBuildAssetView == null || !service.isActiveService() || viewBuildLeadView != null) {
            viewGroup2.setVisibility(8);
        } else {
            viewGroup2.setVisibility(0);
            viewGroup2.addView(viewBuildAssetView);
        }
        View viewFindViewById2 = viewInflate.findViewById(R.id.detailImage);
        if (!service.hasMoreInformation() || viewBuildLeadView != null) {
            viewFindViewById2.setVisibility(8);
        } else {
            viewFindViewById2.setVisibility(0);
        }
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickMoreInformation(group, service);
            }
        });
        return viewInflate;
    }

    private View buildAssetView(ViewGroup parent, MyServiceInfoAgent info) {
        int i = AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ServiceInfoAgent$AssetType[info.getAssetType().ordinal()];
        if (i == 1) {
            return buildAssetPostpayView(parent, info);
        }
        if (i == 2) {
            return buildAssetPrepaidView(parent, info);
        }
        if (i != 3) {
            return null;
        }
        return buildAssetPrepaidWithPointView(parent, info);
    }

    private View buildAssetPostpayView(ViewGroup parent, MyServiceInfoAgent info) {
        if (info.getPostpayEmoney() == null) {
            return null;
        }
        int availableCredit = info.getPostpayEmoney().getAvailableCredit();
        int creditLimit = info.getPostpayEmoney().getCreditLimit();
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_asset_postpay, parent, false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.spendAsset);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.limitAsset);
        if (availableCredit >= 0 && availableCredit <= 99999) {
            textView.setText(asAsset(availableCredit));
        } else {
            textView.setText(R.string.warning_value_nothing);
        }
        if (creditLimit >= 0 && creditLimit <= 99999) {
            textView2.setText(asAsset(creditLimit));
            return viewInflate;
        }
        textView2.setText(R.string.warning_value_nothing);
        return viewInflate;
    }

    private View buildAssetPrepaidView(ViewGroup parent, MyServiceInfoAgent info) {
        if (info.getPrepaidEmoney() == null) {
            return null;
        }
        int balance = info.getPrepaidEmoney().getBalance();
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_asset_prepaied, parent, false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.balanceAsset);
        if (balance >= 0 && balance <= 99999) {
            textView.setText(asAsset(balance));
            return viewInflate;
        }
        textView.setText(R.string.warning_value_nothing);
        return viewInflate;
    }

    private View buildAssetPrepaidWithPointView(ViewGroup parent, MyServiceInfoAgent info) {
        int pointValue = info.getPointValue();
        int balance = info.getPrepaidEmoney() != null ? info.getPrepaidEmoney().getBalance() : -1;
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_asset_prepaied_with_point, parent, false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.balanceAsset);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.pointAsset);
        if (balance >= 0 && balance <= 99999) {
            textView.setText(asAsset(balance));
        } else {
            textView.setText(R.string.warning_value_nothing);
        }
        if (pointValue >= 0 && pointValue <= 99999999) {
            textView2.setText(asAsset(pointValue));
            return viewInflate;
        }
        textView2.setText(R.string.warning_value_nothing);
        return viewInflate;
    }

    private String asAsset(int value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder$11, reason: invalid class name */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CardFaceImageListener$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MyServiceInfoAgent$LeadType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ServiceInfoAgent$AssetType;

        static {
            int[] iArr = new int[MyServiceInfoAgent.LeadType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MyServiceInfoAgent$LeadType = iArr;
            try {
                iArr[MyServiceInfoAgent.LeadType.MFI_LOGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MyServiceInfoAgent$LeadType[MyServiceInfoAgent.LeadType.MFI_LOGIN_FOR_SUICA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MyServiceInfoAgent$LeadType[MyServiceInfoAgent.LeadType.REGISTER_SERVICE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[ServiceInfoAgent.AssetType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ServiceInfoAgent$AssetType = iArr2;
            try {
                iArr2[ServiceInfoAgent.AssetType.ONLY_POSTPAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ServiceInfoAgent$AssetType[ServiceInfoAgent.AssetType.ONLY_PREPAID.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$ServiceInfoAgent$AssetType[ServiceInfoAgent.AssetType.PREPAID_AND_POINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[CentralFuncAgent.CardFaceImageListener.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CardFaceImageListener$Type = iArr3;
            try {
                iArr3[CentralFuncAgent.CardFaceImageListener.Type.SERVICE_ICON.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$agent$CentralFuncAgent$CardFaceImageListener$Type[CentralFuncAgent.CardFaceImageListener.Type.FACE_CARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private View buildLeadView(ViewGroup parent, MyServiceInfoAgent info) {
        int i = AnonymousClass11.$SwitchMap$com$felicanetworks$mfm$main$presenter$agent$MyServiceInfoAgent$LeadType[info.getLeadType().ordinal()];
        if (i == 1) {
            return buildLeadMfiLoginView(parent, info);
        }
        if (i == 2) {
            return buildLeadMfiLoginForLegacySuicaView(parent, info);
        }
        if (i != 3) {
            return null;
        }
        return buildLeadRegisterService(parent, info);
    }

    private View buildLeadMfiLoginView(ViewGroup parent, final MyServiceInfoAgent info) {
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_lead_mfi_login, parent, false);
        viewInflate.findViewById(R.id.selectable).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickMfiLogin(info);
            }
        });
        return viewInflate;
    }

    private View buildLeadMfiLoginForLegacySuicaView(ViewGroup parent, final MyServiceInfoAgent info) {
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_lead_mfi_login_for_legacy_suica, parent, false);
        viewInflate.findViewById(R.id.selectable).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickMfiLogin(info);
            }
        });
        return viewInflate;
    }

    private View buildLeadRegisterService(ViewGroup parent, final MyServiceInfoAgent info) {
        View viewInflate = this.inflater.inflate(R.layout.view_holder_myservice_lead_register_service, parent, false);
        viewInflate.findViewById(R.id.selectable).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickRegisterService(info);
            }
        });
        viewInflate.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() { // from class: com.felicanetworks.mfm.main.view.views.list.MyServiceContentViewHolder.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MyServiceContentViewHolder.this.listener.onClickRegisterServiceClose(info);
            }
        });
        return viewInflate;
    }
}
