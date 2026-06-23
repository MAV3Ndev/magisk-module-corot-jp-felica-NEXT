package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.DeleteCardListFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.CardDetailFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.DeleteCardListFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MfiCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.TermsOfServiceFuncAgent;
import com.felicanetworks.mfm.main.presenter.structure.AccountChangeHistoryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.AppSetupDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.AskAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CardDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CardRecoveryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.CompleteTutorialDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.DataLoadingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.DeleteCardListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.DismissStructure;
import com.felicanetworks.mfm.main.presenter.structure.ExtIcCardDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FaqDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FatalErrorDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FeliCaFormatCheckingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.FeliCaLockAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.FpServiceListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MemoryDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfiCardDataDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfiLoginDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.MfsAppStructure;
import com.felicanetworks.mfm.main.presenter.structure.NoticeDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.NoticeListDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RWSettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RebootStructure;
import com.felicanetworks.mfm.main.presenter.structure.RecommendDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireAppUpgradeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireMissingAppInstallDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.SettingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.SupportMenuDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TapInteractionDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TermsOfServiceDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialAnytimeDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialOverviewDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.TutorialSettingsDrawStructure;

/* JADX INFO: loaded from: classes3.dex */
public enum StateMachine {
    P_STATE_NONE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.1
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 1) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 2) {
                exe.S00_EA1_next(args);
                return P_STATE_INITIALIZING;
            }
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 5) {
                return null;
            }
            exe.S00_EI2_next(args);
            return null;
        }
    },
    P_STATE_INITIALIZING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.2
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S01_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            switch (AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()]) {
                case 3:
                    return P_STATE_REBOOT;
                case 4:
                    return P_STATE_FATAL_ERROR;
                case 5:
                    exe.S01_EI2_next(args);
                    return null;
                case 6:
                    exe.S01_EV1_next();
                    return P_STATE_DISMISS;
                case 7:
                    return P_STATE_REQUIRE_APP_UPGRADE;
                case 8:
                    return P_STATE_ASK_APP_UPGRADE;
                case 9:
                    return P_STATE_JUDGE_TUTORIAL;
                case 10:
                    return P_STATE_FATAL_ERROR;
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S01_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    return null;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new AppSetupDrawStructure();
        }
    },
    P_STATE_REQUIRE_APP_UPGRADE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.3
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S02_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S02_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S02_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 16) {
                return P_STATE_DISMISS;
            }
            if (i != 17) {
                return null;
            }
            return P_STATE_DISMISS;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RequireAppUpgradeDrawStructure();
        }
    },
    P_STATE_ASK_APP_UPGRADE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.4
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S03_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S03_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S03_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 16) {
                return P_STATE_DISMISS;
            }
            if (i != 17) {
                return null;
            }
            return P_STATE_JUDGE_TUTORIAL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new AskAppUpgradeDrawStructure();
        }
    },
    P_STATE_JUDGE_TUTORIAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.5
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S04_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S04_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S04_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 18) {
                return P_STATE_TUTORIAL_FIRST;
            }
            if (i != 19) {
                return null;
            }
            return P_STATE_JUDGE_TOS_SITE_VERSION_UPGRADE;
        }
    },
    P_STATE_TUTORIAL_FIRST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.6
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S05_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S05_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S05_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 20) {
                return null;
            }
            return P_STATE_JUDGE_TOS_SITE_VERSION_UPGRADE;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new TutorialOverviewDrawStructure();
        }
    },
    P_STATE_CHECK_FORMAT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.7
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S06_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S06_EI2_next(args);
                return null;
            }
            if (i == 6) {
                exe.S06_EV1_next();
                return P_STATE_DISMISS;
            }
            switch (i) {
                case 10:
                    return P_STATE_FATAL_ERROR;
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S06_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    switch (i) {
                        case 21:
                            return P_STATE_LAUNCH_SETTING_APP_FROM_CHECK_FORMAT;
                        case 22:
                            return P_STATE_JUDGE_COMPLETE_TUTORIAL_FROM_CHECK_FORMAT;
                        case 23:
                            return P_STATE_CHECK_FORMAT;
                        case 24:
                            return P_STATE_FATAL_ERROR;
                        case 25:
                            return P_STATE_FATAL_ERROR;
                        case 26:
                            return P_STATE_FATAL_ERROR;
                        case 27:
                            return P_STATE_FATAL_ERROR;
                        case 28:
                            return P_STATE_FATAL_ERROR;
                        case 29:
                            return P_STATE_FATAL_ERROR;
                        case 30:
                            exe.S06_EM15_next();
                            return P_STATE_JUDGE_COMPLETE_TUTORIAL_FROM_CHECK_FORMAT;
                        case 31:
                            return P_STATE_FATAL_ERROR;
                        case 32:
                            return P_STATE_FATAL_ERROR;
                        case 33:
                            return P_STATE_FATAL_ERROR;
                        case 34:
                            return P_STATE_REQUIRE_APP_UPGRADE;
                        case 35:
                            return P_STATE_FATAL_ERROR;
                        case 36:
                            return P_STATE_REQUIRE_MISSING_APP_INSTALL;
                        default:
                            return null;
                    }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaFormatCheckingDrawStructure();
        }
    },
    P_STATE_REQUIRE_MISSING_APP_INSTALL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.8
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S36_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S36_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S36_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 16) {
                return P_STATE_DISMISS;
            }
            if (i != 17) {
                return null;
            }
            return P_STATE_DISMISS;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RequireMissingAppInstallDrawStructure(params.missingAppInfo);
        }
    },
    P_STATE_LAUNCH_SETTING_APP_FROM_CHECK_FORMAT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.9
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S07_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S07_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S07_1_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                switch (i) {
                    case 37:
                        return P_STATE_JUDGE_COMPLETE_TUTORIAL_FROM_CHECK_FORMAT;
                    case 38:
                        return P_STATE_DISMISS;
                    case 39:
                        return P_STATE_DISMISS;
                    case 40:
                        return P_STATE_FATAL_ERROR;
                    case 41:
                        exe.S07_1_EP17_next();
                        return P_STATE_REQUIRE_MISSING_APP_INSTALL;
                    case 42:
                        return P_STATE_FATAL_ERROR;
                    case 43:
                        return P_STATE_JUDGE_COMPLETE_TUTORIAL_FROM_CHECK_FORMAT;
                    default:
                        return null;
                }
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MfsAppStructure(params.termsOfServiceFuncAgent);
        }
    },
    P_STATE_JUDGE_START_KIND { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.10
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S08_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S08_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S08_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                switch (i) {
                    case 44:
                        return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
                    case 45:
                        return P_STATE_NOTICE_DETAIL_FROM_SPECIFIC;
                    case 46:
                        return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
                    default:
                        return null;
                }
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new AppSetupDrawStructure();
        }
    },
    P_STATE_CREATE_SIM_DATA_FROM_NORMAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.11
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S09_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S09_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S09_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 33) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 47) {
                exe.S09_1_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
            }
            if (i == 48) {
                return P_STATE_CREATE_MFI_CARD_DATA_FROM_NORMAL;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S09_1_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    return null;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new DataLoadingDrawStructure(params.centralFuncAgent);
        }
    },
    P_STATE_CREATE_SIM_DATA_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.12
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S09_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S09_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S09_2_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 48) {
                return P_STATE_CREATE_MFI_CARD_DATA_FROM_CENTRAL;
            }
            if (i == 33) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 34) {
                switch (i) {
                    case 11:
                        return P_STATE_FATAL_ERROR;
                    case 12:
                        exe.S09_2_EI3_next(args);
                        return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                    case 13:
                        return P_STATE_REBOOT;
                    default:
                        return null;
                }
            }
            return P_STATE_REQUIRE_APP_UPGRADE;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new DataLoadingDrawStructure(params.centralFuncAgent);
        }
    },
    P_STATE_CREATE_SIM_DATA_FROM_NOTICE_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.13
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S09_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S09_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S09_3_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 48) {
                return P_STATE_CREATE_MFI_CARD_DATA_FROM_NOTICE_DETAIL;
            }
            if (i == 33) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 34) {
                switch (i) {
                    case 11:
                        return P_STATE_FATAL_ERROR;
                    case 12:
                        exe.S09_3_EI3_next(args);
                        return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                    case 13:
                        return P_STATE_REBOOT;
                    default:
                        return null;
                }
            }
            return P_STATE_REQUIRE_APP_UPGRADE;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new DataLoadingDrawStructure(params.centralFuncAgent);
        }
    },
    P_STATE_CREATE_MFI_CARD_DATA_FROM_NORMAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.14
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S30_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S30_common_exit(args);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S30_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 47) {
                exe.S30_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S30_1_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    switch (i) {
                        case 49:
                        case 50:
                            exe.S30_common_marshal();
                            return P_STATE_JUDGE_SCREEN;
                        case 51:
                            return P_STATE_TUTORIAL_ACCOUNT;
                        case 52:
                            return P_STATE_EXECUTE_CARD_RECOVERY_FROM_NORMAL;
                        default:
                            return null;
                    }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MfiCardDataDrawStructure();
        }
    },
    P_STATE_CREATE_MFI_CARD_DATA_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.15
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S30_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S30_common_exit(args);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S30_2_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 47) {
                exe.S30_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S30_2_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    switch (i) {
                        case 49:
                        case 50:
                            exe.S30_common_marshal();
                            return P_STATE_CENTRAL;
                        case 51:
                            return P_STATE_TUTORIAL_ACCOUNT;
                        case 52:
                            return P_STATE_EXECUTE_CARD_RECOVERY_FROM_CENTRAL;
                        default:
                            return null;
                    }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MfiCardDataDrawStructure();
        }
    },
    P_STATE_CREATE_MFI_CARD_DATA_FROM_NOTICE_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.16
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S30_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S30_common_exit(args);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S30_3_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 47) {
                exe.S30_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S30_3_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    switch (i) {
                        case 49:
                        case 50:
                            exe.S30_common_marshal();
                            return P_STATE_NOTICE_LIST;
                        case 51:
                            return P_STATE_TUTORIAL_ACCOUNT;
                        case 52:
                            return P_STATE_EXECUTE_CARD_RECOVERY_FROM_NOTICE_DETAIL;
                        default:
                            return null;
                    }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MfiCardDataDrawStructure();
        }
    },
    P_STATE_JUDGE_SCREEN { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.17
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S10_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S10_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S10_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 47) {
                exe.S10_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_NORMAL;
            }
            switch (i) {
                case 53:
                    return P_STATE_CARD_DETAIL;
                case 54:
                    return P_STATE_CENTRAL;
                case 55:
                    exe.S10_EP6_next();
                    return P_STATE_CENTRAL;
                case 56:
                    return P_STATE_CARD_READER;
                case 57:
                    return P_STATE_DELETE_CARD_LIST;
                default:
                    return null;
            }
        }
    },
    P_STATE_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.18
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S11_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S11_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S11_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 30) {
                return P_STATE_REBOOT_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 47) {
                exe.S11_EV12_next(args);
                return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
            }
            if (i == 71) {
                return P_STATE_REBOOT;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    if (exe.S11_EI3_next(args)) {
                        return P_STATE_CARD_READER;
                    }
                    return P_STATE_CENTRAL;
                case 13:
                    return P_STATE_REBOOT;
                case 14:
                    return P_STATE_REBOOT;
                default:
                    switch (i) {
                        case 58:
                            return P_STATE_NOTICE_LIST;
                        case 59:
                            return P_STATE_SETTING;
                        case 60:
                            return P_STATE_CARD_READER;
                        case 61:
                            return P_STATE_FAQ;
                        case 62:
                            return P_STATE_LAUNCH_LOCK_APP_FROM_CENTRAL;
                        case 63:
                            exe.S11_EV14_next(args);
                            return P_STATE_RECOMMEND_DETAIL_TRANSITABLE_DECISION_FROM_CENTRAL;
                        case 64:
                            return P_STATE_SUPPORT_MENU_SETTING;
                        case 65:
                            return P_STATE_FP_SERVICE_LIST_FROM_CENTRAL;
                        case 66:
                            exe.S11_EV42_next(args);
                            return P_STATE_CARD_DETAIL;
                        case 67:
                            exe.S11_EV51_next(args);
                            return P_STATE_DELETE_CARD_LIST;
                        case 68:
                            return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                        case 69:
                            return P_STATE_MFI_LOGIN;
                        default:
                            return null;
                    }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            boolean z = params.isFirstPageRecommend;
            boolean z2 = params.isFailTransitionExtCard;
            boolean z3 = params.isWarningNotLogined;
            boolean z4 = params.isWarningLackServiceInfo;
            boolean z5 = params.isWarningLackCardInfo;
            params.isFirstPageRecommend = false;
            params.isFailTransitionExtCard = false;
            params.isWarningNotLogined = false;
            params.isWarningLackServiceInfo = false;
            params.isWarningLackCardInfo = false;
            params.isSimCacheUpdate = false;
            params.isMfiCacheUpdate = false;
            params.isAssetCacheUpdate = false;
            return new CentralDrawStructure(params.centralFuncAgent, params.noticeFuncAgent, params.isFelicaLock, z, params.isEnoughExtCardServiceInfo, z2, params.mfiCardFuncAgent, z3, z4, z5, params.mfiAccountName);
        }
    },
    P_STATE_NOTICE_DETAIL_FROM_SPECIFIC { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.19
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S12_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S12_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_CREATE_SIM_DATA_FROM_NOTICE_DETAIL;
            }
            if (i == 12) {
                exe.S12_1_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 72) {
                return null;
            }
            exe.S12_1_EV17_next();
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new NoticeDetailDrawStructure(params.noticeInfoAgent);
        }
    },
    P_STATE_NOTICE_DETAIL_FROM_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.20
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S12_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S12_2_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_NOTICE_LIST;
            }
            if (i == 12) {
                exe.S12_2_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 72) {
                return null;
            }
            exe.S12_2_EV17_next();
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new NoticeDetailDrawStructure(params.noticeInfoAgent);
        }
    },
    P_STATE_CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.21
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S13_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S13_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_REBOOT;
            }
            if (i == 12) {
                exe.S13_EI3_next(args);
                return null;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 59) {
                return P_STATE_SETTING;
            }
            if (i == 65) {
                return P_STATE_FP_SERVICE_LIST_FROM__CARD_READER;
            }
            if (i == 73) {
                exe.S13_EV18_next(args);
                return P_STATE_RECOMMEND_DETAIL_TRANSITABLE_DECISION_FROM_CARD_READER;
            }
            if (i != 74) {
                return null;
            }
            return P_STATE_CARD_READER_SETTING;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new ExtIcCardDrawStructure(params.extIcCardFuncAgent, params.isFelicaLock, params.isEnoughExtCardServiceInfo, params.mfiCardFuncAgent.hasNeverLoggedIn(), params.mfiAccountName);
        }
    },
    P_STATE_RECOMMEND_DETAIL_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.22
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S14_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S14_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            if (i == 12) {
                exe.S14_1_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 75) {
                return null;
            }
            exe.S14_1_EV15_next();
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RecommendDetailDrawStructure(params.recommendInfoAgent, params.centralFuncAgent);
        }
    },
    P_STATE_RECOMMEND_DETAIL_FROM_CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.23
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S14_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S14_2_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_CARD_READER;
            }
            if (i == 12) {
                exe.S14_2_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 75) {
                return null;
            }
            exe.S14_2_EV15_next();
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RecommendDetailDrawStructure(params.recommendInfoAgent, params.centralFuncAgent);
        }
    },
    P_STATE_NOTICE_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.24
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S15_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 5) {
                if (i != 6) {
                    if (i == 12) {
                        exe.S15_EI3_next(args);
                        return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                    }
                    if (i == 13) {
                        return P_STATE_REBOOT;
                    }
                    if (i == 64) {
                        return P_STATE_SUPPORT_MENU_SETTING;
                    }
                    if (i == 69) {
                        return P_STATE_MFI_LOGIN;
                    }
                    if (i != 71) {
                        switch (i) {
                            case 59:
                                return P_STATE_SETTING;
                            case 60:
                                return P_STATE_CARD_READER;
                            case 61:
                                return P_STATE_FAQ;
                            case 62:
                                return P_STATE_LAUNCH_LOCK_APP_FROM_NOTICE_LIST;
                            default:
                                switch (i) {
                                    case 76:
                                        break;
                                    case 77:
                                        exe.S15_EV6_next();
                                        if (exe.isModelDataUpdate()) {
                                            return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                                        }
                                        return P_STATE_CENTRAL;
                                    case 78:
                                        exe.S15_EV16_next(args);
                                        return P_STATE_NOTICE_DETAIL_FROM_LIST;
                                    default:
                                        return null;
                                }
                                break;
                        }
                    } else {
                        return P_STATE_REBOOT;
                    }
                }
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            exe.S15_EI2_next(args);
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new NoticeListDrawStructure(params.noticeFuncAgent, params.isFelicaLock, params.isEnoughExtCardServiceInfo, params.mfiCardFuncAgent.hasNeverLoggedIn(), params.mfiAccountName);
        }
    },
    P_STATE_LAUNCH_LOCK_APP_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.25
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S16_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S16_1_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S16_1_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 79) {
                return P_STATE_REBOOT;
            }
            if (i != 80) {
                return null;
            }
            return P_STATE_CENTRAL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaLockAppStructure();
        }
    },
    P_STATE_LAUNCH_LOCK_APP_FROM_NOTICE_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.26
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S16_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S16_2_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S16_2_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 79) {
                return P_STATE_REBOOT;
            }
            if (i != 80) {
                return null;
            }
            return P_STATE_NOTICE_LIST;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaLockAppStructure();
        }
    },
    P_STATE_LAUNCH_LOCK_APP_FROM_FAQ { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.27
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S16_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S16_3_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S16_3_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 79) {
                return P_STATE_REBOOT;
            }
            if (i != 80) {
                return null;
            }
            return P_STATE_FAQ;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaLockAppStructure();
        }
    },
    P_STATE_LAUNCH_LOCK_APP_FROM_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.28
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S16_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S16_4_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S16_4_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 79) {
                return P_STATE_REBOOT;
            }
            if (i != 80) {
                return null;
            }
            return P_STATE_SETTING;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaLockAppStructure();
        }
    },
    P_STATE_LAUNCH_LOCK_APP_FROM_SUPPORT_MENU { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.29
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S16_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S16_5_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S16_5_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 79) {
                return P_STATE_REBOOT;
            }
            if (i != 80) {
                return null;
            }
            return P_STATE_SUPPORT_MENU_SETTING;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FeliCaLockAppStructure();
        }
    },
    P_STATE_FAQ { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.30
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S17_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 5) {
                if (i != 6) {
                    if (i == 12) {
                        exe.S17_EI3_next(args);
                        return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                    }
                    if (i == 13) {
                        return P_STATE_REBOOT;
                    }
                    if (i == 62) {
                        return P_STATE_LAUNCH_LOCK_APP_FROM_FAQ;
                    }
                    if (i == 64) {
                        return P_STATE_SUPPORT_MENU_SETTING;
                    }
                    if (i == 69) {
                        return P_STATE_MFI_LOGIN;
                    }
                    if (i == 71) {
                        return P_STATE_REBOOT;
                    }
                    if (i != 76) {
                        if (i == 77) {
                            exe.S17_EV6_next();
                            if (exe.isModelDataUpdate()) {
                                return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                            }
                            return P_STATE_CENTRAL;
                        }
                        switch (i) {
                            case 58:
                                return P_STATE_NOTICE_LIST;
                            case 59:
                                return P_STATE_SETTING;
                            case 60:
                                return P_STATE_CARD_READER;
                            default:
                                return null;
                        }
                    }
                }
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            exe.S17_EI2_next(args);
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FaqDrawStructure(params.isFelicaLock, params.isEnoughExtCardServiceInfo, params.mfiCardFuncAgent.hasNeverLoggedIn(), params.mfiAccountName);
        }
    },
    P_STATE_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.31
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S18_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S18_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 5) {
                if (i != 6) {
                    if (i == 58) {
                        return P_STATE_NOTICE_LIST;
                    }
                    if (i == 64) {
                        return P_STATE_SUPPORT_MENU_SETTING;
                    }
                    if (i != 76) {
                        if (i == 77) {
                            exe.S18_EV6_next();
                            if (exe.isModelDataUpdate()) {
                                return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                            }
                            return P_STATE_CENTRAL;
                        }
                        if (i == 81) {
                            exe.S18_EV23_next(args);
                            return null;
                        }
                        if (i == 82) {
                            return P_STATE_TAP_INTERACTION;
                        }
                        switch (i) {
                            case 12:
                                exe.S18_EI3_next(args);
                                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                            case 13:
                                return P_STATE_REBOOT;
                            case 14:
                            case 15:
                                return null;
                            default:
                                switch (i) {
                                    case 60:
                                        return P_STATE_CARD_READER;
                                    case 61:
                                        return P_STATE_FAQ;
                                    case 62:
                                        return P_STATE_LAUNCH_LOCK_APP_FROM_SETTING;
                                    default:
                                        switch (i) {
                                            case 69:
                                                return P_STATE_MFI_LOGIN;
                                            case 70:
                                                return null;
                                            case 71:
                                                return P_STATE_REBOOT;
                                            default:
                                                return super.next(exe, evt, args);
                                        }
                                }
                        }
                    }
                }
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            exe.S18_EI2_next(args);
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new SettingDrawStructure(params.noticeFuncAgent, params.isFelicaLock, params.isEnoughExtCardServiceInfo, params.mfiCardFuncAgent.hasNeverLoggedIn(), params.mfiAccountName);
        }
    },
    P_STATE_MEMORY_USAGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.32
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S19_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S19_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_SUPPORT_MENU_SETTING;
            }
            if (i == 12) {
                exe.S19_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 30) {
                return P_STATE_REBOOT_ERROR;
            }
            if (i != 34) {
                return null;
            }
            return P_STATE_REQUIRE_APP_UPGRADE;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MemoryDrawStructure(params.memoryUsageFuncAgent);
        }
    },
    P_STATE_TUTORIAL_ANYTIME { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.33
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S20_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S20_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_SUPPORT_MENU_SETTING;
            }
            if (i == 12) {
                exe.S20_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new TutorialAnytimeDrawStructure();
        }
    },
    P_STATE_TAP_INTERACTION { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.34
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S34_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 6) {
                return P_STATE_SETTING;
            }
            if (i == 12) {
                exe.S21_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new TapInteractionDrawStructure();
        }
    },
    P_STATE_FP_SERVICE_LIST_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.35
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S29_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S20_EI2_next(args);
                return null;
            }
            if (i == 6) {
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            if (i == 12) {
                exe.S20_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FpServiceListDrawStructure(params.centralFuncAgent);
        }
    },
    P_STATE_FP_SERVICE_LIST_FROM__CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.36
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S29_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S20_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_CARD_READER;
            }
            if (i == 12) {
                exe.S20_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 77) {
                return null;
            }
            exe.S29_EV6_next();
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new FpServiceListDrawStructure(params.extIcCardFuncAgent);
        }
    },
    P_STATE_REBOOT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.37
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S22_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 5) {
                exe.S22_EI2_next(args);
                return null;
            }
            if (i != 83) {
                return null;
            }
            return P_STATE_INITIALIZING;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RebootStructure();
        }
    },
    P_STATE_DISMISS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.38
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S23_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 5) {
                exe.S23_EI2_next(args);
                return null;
            }
            if (i != 84) {
                return null;
            }
            return P_STATE_NONE;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S23_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new DismissStructure();
        }
    },
    P_STATE_REBOOT_NFC_TAG_RECEIVE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.39
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RebootStructure();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S28_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 5) {
                exe.S28_EI2_next(args);
                return null;
            }
            if (i != 83) {
                return null;
            }
            return P_STATE_INITIALIZING;
        }
    },
    P_STATE_FATAL_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.40
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S24_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 5) {
                exe.S24_1_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 85) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            if (params.extra instanceof MfmException) {
                if (params.specificError == 1) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.FAILED_TO_CONFIRM_APP_UPGRADE);
                }
                if (params.specificError == 2) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.EXHAUSTION_OF_DATA_DRIVE);
                }
                if (params.specificError == 3) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.SG_LOAD_ERROR);
                }
                if (params.specificError == 4) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.MFC_DISABLED_ERROR);
                }
                if (params.specificError == 5) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.MFS_DISABLED_ERROR);
                }
                if (params.specificError == 6) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.MFS_SIGNATURE_UNMATCHED_ERROR);
                }
                if (params.specificError == 7) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.INBOUND_INCOMPATIBLE_ERROR);
                }
                if (params.specificError == 8) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.TERMS_OF_SERVICE_LOADING_ERROR);
                }
                if (params.specificError == 9) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.TERMS_OF_SERVICE_SERVER_ERROR);
                }
                if (params.specificError == 10) {
                    return new FatalErrorDrawStructure((MfmException) params.extra, FatalErrorDrawStructure.ErrorType.MEMORY_CLEAR_RUNNING_ERROR);
                }
                return new FatalErrorDrawStructure((MfmException) params.extra);
            }
            if (params.extra instanceof ModelErrorInfo) {
                return new FatalErrorDrawStructure((ModelErrorInfo) params.extra);
            }
            return new FatalErrorDrawStructure(new MfmException(StateMachine.class, 357, "Unknown Error."));
        }
    },
    P_STATE_REBOOT_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.41
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S24_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 5) {
                exe.S24_2_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_REBOOT;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            if (params.extra instanceof MfmException) {
                return new FatalErrorDrawStructure((MfmException) params.extra);
            }
            if (params.extra instanceof ModelErrorInfo) {
                return new FatalErrorDrawStructure((ModelErrorInfo) params.extra);
            }
            return new FatalErrorDrawStructure(new MfmException(StateMachine.class, 357, "Unknown Error."));
        }
    },
    P_STATE_SUPPORT_MENU_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.42
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S25_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i != 5) {
                if (i != 6) {
                    if (i == 12) {
                        exe.S25_EI3_next(args);
                        return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                    }
                    if (i == 13) {
                        return P_STATE_REBOOT;
                    }
                    if (i == 69) {
                        return P_STATE_MFI_LOGIN;
                    }
                    if (i == 71) {
                        return P_STATE_REBOOT;
                    }
                    if (i != 76) {
                        if (i == 77) {
                            exe.S25_EV6_next();
                            if (exe.isModelDataUpdate()) {
                                return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                            }
                            return P_STATE_CENTRAL;
                        }
                        switch (i) {
                            case 58:
                                return P_STATE_NOTICE_LIST;
                            case 59:
                                return P_STATE_SETTING;
                            case 60:
                                return P_STATE_CARD_READER;
                            case 61:
                                return P_STATE_FAQ;
                            case 62:
                                return P_STATE_LAUNCH_LOCK_APP_FROM_SUPPORT_MENU;
                            default:
                                switch (i) {
                                    case 86:
                                        return P_STATE_MEMORY_USAGE;
                                    case 87:
                                        return P_STATE_ACCOUNT_CHANGE_HISTORY;
                                    case 88:
                                        return P_STATE_TUTORIAL_ANYTIME;
                                    default:
                                        return null;
                                }
                        }
                    }
                }
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            exe.S25_EI2_next(args);
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new SupportMenuDrawStructure(params.isFelicaLock, params.isEnoughExtCardServiceInfo, params.mfiCardFuncAgent.hasNeverLoggedIn(), params.mfiAccountName);
        }
    },
    P_STATE_RECOMMEND_DETAIL_TRANSITABLE_DECISION_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.43
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S26_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S26_1_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S26_1_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 89) {
                return P_STATE_RECOMMEND_DETAIL_FROM_CENTRAL;
            }
            if (i != 90) {
                return null;
            }
            return P_STATE_CENTRAL;
        }
    },
    P_STATE_RECOMMEND_DETAIL_TRANSITABLE_DECISION_FROM_CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.44
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S26_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S26_2_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S26_2_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 89) {
                return P_STATE_RECOMMEND_DETAIL_FROM_CARD_READER;
            }
            if (i != 90) {
                return null;
            }
            return P_STATE_CENTRAL;
        }
    },
    P_STATE_JUDGE_ExtCard { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.45
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S27_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 91) {
                return P_STATE_CARD_READER;
            }
            if (i != 92) {
                return null;
            }
            return P_STATE_CHECK_FORMAT;
        }
    },
    P_STATE_CARD_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.46
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S33_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S33_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S33_EI2_next(args);
                return null;
            }
            if (i == 6) {
                if (exe.isModelDataUpdate()) {
                    return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                }
                return P_STATE_CENTRAL;
            }
            if (i == 25) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 27) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 30) {
                return P_STATE_REBOOT_ERROR;
            }
            if (i == 34) {
                return P_STATE_REQUIRE_APP_UPGRADE;
            }
            if (i == 66) {
                exe.S11_EV42_next(args);
                return P_STATE_CARD_DETAIL;
            }
            switch (i) {
                case 11:
                    return P_STATE_FATAL_ERROR;
                case 12:
                    exe.S33_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                case 13:
                    return P_STATE_REBOOT;
                default:
                    return null;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            CardDetailFuncAgent cardDetailFuncAgent = new CardDetailFuncAgent(params.cardDetailFunc, params.centralFuncAgent, params.mfiCardFuncAgent, params.groupInfoForDetail, params.serviceInfoForDetail, params.cardInfoForDetail);
            params.groupInfoForDetail = null;
            params.serviceInfoForDetail = null;
            params.cardInfoForDetail = null;
            return new CardDetailDrawStructure(cardDetailFuncAgent);
        }
    },
    P_STATE_CARD_READER_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.47
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S13_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S13_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_CARD_READER;
            }
            if (i == 12) {
                exe.S35_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new RWSettingDrawStructure();
        }
    },
    P_STATE_JUDGE_TOS_SITE_VERSION_UPGRADE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.48
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S37_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S37_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S37_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 93) {
                return P_STATE_TERMS_OF_SERVICE;
            }
            if (i != 94) {
                return null;
            }
            return P_STATE_JUDGE_ExtCard;
        }
    },
    P_STATE_TERMS_OF_SERVICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.49
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S38_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S38_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S38_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 95) {
                return null;
            }
            exe.S38_EV47_next(args);
            return P_STATE_JUDGE_ExtCard;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new TermsOfServiceDrawStructure(params.termsOfServiceFuncAgent);
        }
    },
    P_STATE_MFI_LOGIN { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.50
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S39_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S39_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S39_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S39_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 51) {
                return P_STATE_TUTORIAL_ACCOUNT;
            }
            if (i != 96) {
                return null;
            }
            return P_STATE_JUDGE_COMPLETE_TUTORIAL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new MfiLoginReadyingDrawStructure();
        }
    },
    P_STATE_TUTORIAL_ACCOUNT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.51
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S40_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S40_EI2_next(args);
                return null;
            }
            if (i == 12) {
                exe.S40_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                switch (i) {
                    case 97:
                        exe.S39_EV39_next(args);
                        return P_STATE_JUDGE_COMPLETE_TUTORIAL;
                    case 98:
                        exe.S39_common_cancel(args);
                        return P_STATE_JUDGE_COMPLETE_TUTORIAL;
                    case 99:
                        exe.S39_EV41_next(args);
                        return P_STATE_JUDGE_COMPLETE_TUTORIAL;
                    default:
                        return null;
                }
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            Intent intent = params.startIntentForMfiLogin;
            params.startIntentForMfiLogin = null;
            return new MfiLoginDrawStructure(intent);
        }
    },
    P_STATE_TUTORIAL_SETTINGS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.52
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S41_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S41_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S41_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 100) {
                return null;
            }
            return P_STATE_JUDGE_START_KIND;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new TutorialSettingsDrawStructure(params.noticeFuncAgent, PresenterData.getInstance().hasNFC());
        }
    },
    P_STATE_JUDGE_COMPLETE_TUTORIAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.53
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S42_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S42_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S42_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i == 101) {
                return P_STATE_TUTORIAL_SETTINGS;
            }
            if (i != 102) {
                return null;
            }
            return P_STATE_JUDGE_START_KIND;
        }
    },
    P_STATE_EXECUTE_CARD_RECOVERY_FROM_NORMAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.54
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S43_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S43_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S42_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 103) {
                return null;
            }
            exe.S43_X_EM31_next(args);
            return P_STATE_CREATE_MFI_CARD_DATA_FROM_NORMAL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new CardRecoveryDrawStructure();
        }
    },
    P_STATE_EXECUTE_CARD_RECOVERY_FROM_CENTRAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.55
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S43_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S43_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S42_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 103) {
                return null;
            }
            exe.S43_X_EM31_next(args);
            return P_STATE_CREATE_MFI_CARD_DATA_FROM_CENTRAL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new CardRecoveryDrawStructure();
        }
    },
    P_STATE_EXECUTE_CARD_RECOVERY_FROM_NOTICE_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.56
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S43_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S43_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_DISMISS;
            }
            if (i == 12) {
                exe.S42_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i == 13) {
                return P_STATE_REBOOT;
            }
            if (i != 103) {
                return null;
            }
            exe.S43_X_EM31_next(args);
            return P_STATE_CREATE_MFI_CARD_DATA_FROM_NOTICE_DETAIL;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new CardRecoveryDrawStructure();
        }
    },
    P_STATE_JUDGE_COMPLETE_TUTORIAL_FROM_CHECK_FORMAT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.57
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S44_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i != 3) {
                if (i == 4) {
                    return P_STATE_FATAL_ERROR;
                }
                if (i == 5) {
                    exe.S44_EI2_next(args);
                    return null;
                }
                if (i == 6) {
                    return P_STATE_DISMISS;
                }
                if (i == 12) {
                    exe.S44_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                }
                if (i != 13) {
                    if (i == 30) {
                        return P_STATE_TUTORIAL_SETTINGS;
                    }
                    if (i == 101) {
                        return P_STATE_MFI_LOGIN;
                    }
                    if (i != 102) {
                        return null;
                    }
                    return P_STATE_JUDGE_START_KIND;
                }
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new CompleteTutorialDrawStructure(params.noticeFuncAgent);
        }
    },
    P_STATE_DELETE_CARD_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.58
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S45_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void exit(OperationExecutor exe, Object[] args) {
            exe.S45_common_exit();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i != 3) {
                if (i == 4) {
                    return P_STATE_FATAL_ERROR;
                }
                if (i == 5) {
                    exe.S45_EI2_next(args);
                    return null;
                }
                if (i == 6) {
                    if (exe.isModelDataUpdate()) {
                        return P_STATE_CREATE_SIM_DATA_FROM_CENTRAL;
                    }
                    return P_STATE_CENTRAL;
                }
                if (i == 12) {
                    exe.S45_EI3_next(args);
                    return P_STATE_REBOOT_NFC_TAG_RECEIVE;
                }
                if (i != 13) {
                    return null;
                }
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            DeleteCardListFuncAgent deleteCardListFuncAgent = new DeleteCardListFuncAgent(params.deleteCardListFunc, params.serviceInfoForDetail, params.cardInfoForDetail, params.mfiCardFuncAgent.isLoggedIn(), params.mfiCardFuncAgent.isChaced());
            params.serviceInfoForDetail = null;
            params.cardInfoForDetail = null;
            return new DeleteCardListDrawStructure(deleteCardListFuncAgent);
        }
    },
    P_STATE_ACCOUNT_CHANGE_HISTORY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.59
        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        void entry(OperationExecutor exe, Object[] args) {
            exe.S46_common_entry();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
            int i = AnonymousClass60.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[evt.ordinal()];
            if (i == 3) {
                return P_STATE_REBOOT;
            }
            if (i == 4) {
                return P_STATE_FATAL_ERROR;
            }
            if (i == 5) {
                exe.S46_EI2_next(args);
                return null;
            }
            if (i == 6) {
                return P_STATE_SUPPORT_MENU_SETTING;
            }
            if (i == 12) {
                exe.S46_EI3_next(args);
                return P_STATE_REBOOT_NFC_TAG_RECEIVE;
            }
            if (i != 13) {
                return null;
            }
            return P_STATE_REBOOT;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine
        Structure structure(StructureParams params) {
            return new AccountChangeHistoryDrawStructure();
        }
    };

    interface OperationExecutor {
        void S00_EA1_next(Object[] args);

        void S00_EI2_next(Object[] args);

        void S01_EI2_next(Object[] args);

        void S01_EI3_next(Object[] args);

        void S01_EV1_next();

        void S01_common_entry();

        void S02_EI2_next(Object[] args);

        void S02_EI3_next(Object[] args);

        void S02_common_entry();

        void S03_EI2_next(Object[] args);

        void S03_EI3_next(Object[] args);

        void S03_common_entry();

        void S04_EI2_next(Object[] args);

        void S04_EI3_next(Object[] args);

        void S04_common_entry();

        void S05_EI2_next(Object[] args);

        void S05_EI3_next(Object[] args);

        void S05_common_entry();

        void S06_EI2_next(Object[] args);

        void S06_EI3_next(Object[] args);

        void S06_EM15_next();

        void S06_EV1_next();

        void S06_common_entry();

        void S07_1_EI2_next(Object[] args);

        void S07_1_EI3_next(Object[] args);

        void S07_1_EP17_next();

        void S07_2_EI2_next(Object[] args);

        void S07_2_EI3_next(Object[] args);

        void S07_common_entry();

        void S08_EI2_next(Object[] args);

        void S08_EI3_next(Object[] args);

        void S08_common_entry();

        void S09_1_EI2_next(Object[] args);

        void S09_1_EI3_next(Object[] args);

        void S09_1_EV12_next(Object[] args);

        void S09_2_EI2_next(Object[] args);

        void S09_2_EI3_next(Object[] args);

        void S09_3_EI2_next(Object[] args);

        void S09_3_EI3_next(Object[] args);

        void S09_common_entry();

        void S09_common_exit();

        void S10_EI2_next(Object[] args);

        void S10_EI3_next(Object[] args);

        void S10_EP6_next();

        void S10_EV12_next(Object[] args);

        void S10_common_entry();

        void S11_EI2_next(Object[] args);

        boolean S11_EI3_next(Object[] args);

        void S11_EV12_next(Object[] args);

        void S11_EV14_next(Object[] args);

        void S11_EV42_next(Object[] args);

        void S11_EV51_next(Object[] args);

        void S11_common_entry();

        void S11_common_exit();

        void S12_1_EI2_next(Object[] args);

        void S12_1_EI3_next(Object[] args);

        void S12_1_EV17_next();

        void S12_2_EI2_next(Object[] args);

        void S12_2_EI3_next(Object[] args);

        void S12_2_EV17_next();

        void S12_common_entry();

        void S13_EI2_next(Object[] args);

        void S13_EI3_next(Object[] args);

        void S13_EV18_next(Object[] args);

        void S13_common_entry();

        void S14_1_EI2_next(Object[] args);

        void S14_1_EI3_next(Object[] args);

        void S14_1_EV15_next();

        void S14_2_EI2_next(Object[] args);

        void S14_2_EI3_next(Object[] args);

        void S14_2_EV15_next();

        void S14_common_entry();

        void S15_EI2_next(Object[] args);

        void S15_EI3_next(Object[] args);

        void S15_EV16_next(Object[] args);

        void S15_EV6_next();

        void S15_common_entry();

        void S16_1_EI2_next(Object[] args);

        void S16_1_EI3_next(Object[] args);

        void S16_2_EI2_next(Object[] args);

        void S16_2_EI3_next(Object[] args);

        void S16_3_EI2_next(Object[] args);

        void S16_3_EI3_next(Object[] args);

        void S16_4_EI2_next(Object[] args);

        void S16_4_EI3_next(Object[] args);

        void S16_5_EI2_next(Object[] args);

        void S16_5_EI3_next(Object[] args);

        void S16_common_entry();

        void S17_EI2_next(Object[] args);

        void S17_EI3_next(Object[] args);

        void S17_EV6_next();

        void S17_common_entry();

        void S18_EI2_next(Object[] args);

        void S18_EI3_next(Object[] args);

        void S18_EV23_next(Object[] args);

        void S18_EV6_next();

        void S18_common_entry();

        void S18_common_exit();

        void S19_EI2_next(Object[] args);

        void S19_EI3_next(Object[] args);

        void S19_common_entry();

        void S20_EI2_next(Object[] args);

        void S20_EI3_next(Object[] args);

        void S20_common_entry();

        void S21_EI2_next(Object[] args);

        void S21_EI3_next(Object[] args);

        void S21_common_entry();

        void S22_EI2_next(Object[] args);

        void S22_common_entry();

        void S23_EI2_next(Object[] args);

        void S23_common_entry();

        void S23_common_exit();

        void S24_1_EI2_next(Object[] args);

        void S24_2_EI2_next(Object[] args);

        void S24_common_entry();

        void S25_EI2_next(Object[] args);

        void S25_EI3_next(Object[] args);

        void S25_EV6_next();

        void S25_common_entry();

        void S26_1_EI2_next(Object[] args);

        void S26_1_EI3_next(Object[] args);

        void S26_2_EI2_next(Object[] args);

        void S26_2_EI3_next(Object[] args);

        void S26_common_entry();

        void S27_common_entry();

        void S28_EI2_next(Object[] args);

        void S28_common_entry();

        void S29_EV6_next();

        void S29_common_entry();

        void S30_1_EI2_next(Object[] args);

        void S30_1_EI3_next(Object[] args);

        void S30_2_EI2_next(Object[] args);

        void S30_2_EI3_next(Object[] args);

        void S30_3_EI2_next(Object[] args);

        void S30_3_EI3_next(Object[] args);

        void S30_EV12_next(Object[] args);

        void S30_common_entry();

        void S30_common_exit(Object[] args);

        void S30_common_marshal();

        void S33_EI2_next(Object[] args);

        void S33_EI3_next(Object[] args);

        void S33_common_entry();

        void S33_common_exit();

        void S34_common_entry();

        void S35_EI3_next(Object[] args);

        void S36_EI2_next(Object[] args);

        void S36_EI3_next(Object[] args);

        void S36_common_entry();

        void S37_EI2_next(Object[] args);

        void S37_EI3_next(Object[] args);

        void S37_common_entry();

        void S38_EI2_next(Object[] args);

        void S38_EI3_next(Object[] args);

        void S38_EV47_next(Object[] args);

        void S38_common_entry();

        void S39_EI2_next(Object[] args);

        void S39_EI3_next(Object[] args);

        void S39_EV39_next(Object[] args);

        void S39_EV41_next(Object[] args);

        void S39_common_cancel(Object[] args);

        void S39_common_entry();

        void S39_common_exit();

        void S40_EI2_next(Object[] args);

        void S40_EI3_next(Object[] args);

        void S40_common_entry();

        void S41_EI2_next(Object[] args);

        void S41_EI3_next(Object[] args);

        void S41_common_entry();

        void S42_EI2_next(Object[] args);

        void S42_EI3_next(Object[] args);

        void S42_common_entry();

        void S43_EI2_next(Object[] args);

        void S43_X_EM31_next(Object[] args);

        void S43_common_entry();

        void S44_EI2_next(Object[] args);

        void S44_EI3_next(Object[] args);

        void S44_common_entry();

        void S45_EI2_next(Object[] args);

        void S45_EI3_next(Object[] args);

        void S45_common_entry();

        void S45_common_exit();

        void S46_EI2_next(Object[] args);

        void S46_EI3_next(Object[] args);

        void S46_common_entry();

        boolean isModelDataUpdate();
    }

    void entry(OperationExecutor exe, Object[] args) {
    }

    void exit(OperationExecutor exe, Object[] args) {
    }

    StateMachine next(OperationExecutor exe, Event evt, Object[] args) {
        return null;
    }

    Structure structure(StructureParams params) {
        return null;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.internal.StateMachine$60, reason: invalid class name */
    static /* synthetic */ class AnonymousClass60 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event;

        static {
            int[] iArr = new int[Event.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event = iArr;
            try {
                iArr[Event.EP_OUT_OF_MEMORY_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EA_START_APPLICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_NOTIFICATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_FATAL_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_PUSH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_CLOSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_VERSIONUP_CONFIRM_RESULT_REQUIRE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_VERSIONUP_CONFIRM_RESULT_ASK.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_VERSIONUP_CONFIRM_RESULT_NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_VERSIONUP_CONFIRM_FAILED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_DATABASE_ACCESS_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_NFC_TAG_RECEIVE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_CONFIG_CHANGE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_LOCK_STATE_CHANGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EI_MFS_MOVEMENT_RECEIVE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_POSITIVE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_NEGATIVE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_TUTORIAL_SHOW.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_TUTORIAL_UNSHOWN.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_SERVICE_START.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_INITIALIZE_INCOMPLETE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_INITIALIZE_COMPLETE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_INITIALIZE_RETRY.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_UNSUPPORT_FELICA_SETTING_APP.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFCPERMINSPECT_ERROR.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_TIMEOUT_ERROR.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFC_OTHER_ERROR.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_OTHER_APP_USING_MFC_ERROR.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_RUNNING_BY_MFIC.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_LOCK_ERROR.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_OPEN_ERROR.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_FELICA_HTTP_ERROR.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFIC_VERSION_ERROR.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFC_DISABLED.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFC_UNINSTALLED.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_FELICA_SETTING_COMPLETE.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_FELICA_SETTING_INCOMPLETE.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_OTHERS.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_MFS_DISABLED.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_MFS_UNINSTALLED.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_MFS_SIGNATURE_UNMATCHED.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_FELICA_SETTING_ALREADY_INIT.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_START_KIND_MYSERVICE.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_START_KIND_NOTICE.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_START_KIND_CARD_READER.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_UPDATE.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_CREATE_SIM_DATA_COMPLETE.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_MFI_LOGIN_CANCEL.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFI_SERVICE_COMPLETE.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_REQUEST_ACTIVITY.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_NEED_CARD_RECOVERY.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_CARD_DETAIL_EX.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_SCREEN_MYSERVICE.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_SCREEN_RECOMMEND.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_SCREEN_CARD_READING.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_SCREEN_DELETE_CARD_LIST.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_NOTICE_LIST.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_SETTING.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_CARD_READER.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_FAQ.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_LOCK_SETTING.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RECOMMEND_DETAIL.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_SUPPORT_MENU.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_FP_SERVICE_LIST.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_CARD_DETAIL.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_DELETE_CARD_LIST.ordinal()] = 67;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_MFI_SERVICE_UPDATE.ordinal()] = 68;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_MFI_LOGIN.ordinal()] = 69;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_MFI_ACCOUNT_CANCEL.ordinal()] = 70;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_MFI_ACCOUNT_COMPLETE.ordinal()] = 71;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_NOTICE_DETAIL_SITE_ACCESS.ordinal()] = 72;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_TICKETING.ordinal()] = 73;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_CARD_READER_SETTING.ordinal()] = 74;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_SERVICE_START_SUCCESS.ordinal()] = 75;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_MYSERVICE.ordinal()] = 76;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RECOMMEND.ordinal()] = 77;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_NOTICE_DETAIL.ordinal()] = 78;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_LOCK_STATE_CHANGE.ordinal()] = 79;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_LOCK_CANCEL.ordinal()] = 80;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_PUSH_SETTING_CHAGE.ordinal()] = 81;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_TAP_INTERACTION_SETTING.ordinal()] = 82;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_REBOOT_COMPLETE.ordinal()] = 83;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_END.ordinal()] = 84;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RETRY.ordinal()] = 85;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_MRMORY_USAGE.ordinal()] = 86;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_ACCOUNT_CHANGE_HISTORY.ordinal()] = 87;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_TUTORIAL.ordinal()] = 88;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_RECOMMEND_DETAIL_TRANSITABLE.ordinal()] = 89;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_RECOMMEND_DETAIL_NOT_TRANSITABLE.ordinal()] = 90;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_ExtCard_TRANSITABLE.ordinal()] = 91;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_ExtCard_NOT_TRANSITABLE.ordinal()] = 92;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_TOS_SITE_UPGRADE.ordinal()] = 93;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_TOS_SITE_NONE_UPGRADE.ordinal()] = 94;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_CONSENT_TERMS_OF_SERVICE.ordinal()] = 95;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_MFI_LOGIN_RESULT.ordinal()] = 96;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_MFI_LOGIN_SUCCESS.ordinal()] = 97;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_MFI_LOGIN_CANCEL.ordinal()] = 98;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_RESULT_MFI_LOGIN_FAILURE.ordinal()] = 99;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EV_COMPLETE_TUTORIAL_SETTINGS.ordinal()] = 100;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_TUTORIAL_NOT_COMPLETE.ordinal()] = 101;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EP_JUDGE_TUTORIAL_COMPLETE.ordinal()] = 102;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateMachine$Event[Event.EM_COMPLETE_CARD_RECOVERY.ordinal()] = 103;
            } catch (NoSuchFieldError unused103) {
            }
        }
    }

    public enum Event {
        EV_CLOSE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.1
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_POSITIVE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.2
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_NEGATIVE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.3
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_SERVICE_START { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.4
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_MYSERVICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.5
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RECOMMEND { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.6
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_NOTICE_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.7
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.8
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.9
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_FAQ { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.10
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_LOCK_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.11
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_UPDATE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.12
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RECOMMEND_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.13
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_SERVICE_START_SUCCESS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.14
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_NOTICE_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.15
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_NOTICE_DETAIL_SITE_ACCESS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.16
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_TICKETING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.17
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_MRMORY_USAGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.18
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_TUTORIAL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.19
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_ACCOUNT_CHANGE_HISTORY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.20
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_TAP_INTERACTION_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.21
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_PUSH_SETTING_CHAGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.22
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_FELICA_SETTING_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.23
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_FELICA_SETTING_INCOMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.24
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_LOCK_STATE_CHANGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.25
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_LOCK_CANCEL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.26
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_REBOOT_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.27
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_END { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.28
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_SUPPORT_MENU { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.29
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_OTHERS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.30
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RETRY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.31
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_FP_SERVICE_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.32
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_MFI_SERVICE_UPDATE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.33
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_MFI_ACCOUNT_CANCEL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.34
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_MFI_ACCOUNT_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.35
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_MFI_LOGIN_SUCCESS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.36
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_MFI_LOGIN_CANCEL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.37
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_MFI_LOGIN_FAILURE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.38
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_CARD_DETAIL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.39
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EP_CARD_DETAIL_EX { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.40
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_CARD_RECOVERY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.41
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_CARD_READER_SETTING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.42
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_MFI_LOGIN_CANCEL { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.43
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_CONSENT_TERMS_OF_SERVICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.44
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_COMPLETE_TUTORIAL_SETTINGS { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.45
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_MFI_LOGIN { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.46
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_RESULT_FELICA_SETTING_ALREADY_INIT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.47
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EV_DELETE_CARD_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.48
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_USER_ACTION;
            }
        },
        EM_VERSIONUP_CONFIRM_RESULT_REQUIRE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.49
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_VERSIONUP_CONFIRM_RESULT_ASK { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.50
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_VERSIONUP_CONFIRM_RESULT_NONE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.51
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_INITIALIZE_INCOMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.52
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_INITIALIZE_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.53
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_INITIALIZE_RETRY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.54
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_CREATE_SIM_DATA_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.55
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_UNSUPPORT_FELICA_SETTING_APP { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.56
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EP_OUT_OF_MEMORY_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.57
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EM_VERSIONUP_CONFIRM_FAILED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.58
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFCPERMINSPECT_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.59
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_TIMEOUT_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.60
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFC_OTHER_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.61
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_OTHER_APP_USING_MFC_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.62
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_DATABASE_ACCESS_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.63
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_LOCK_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.64
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_OPEN_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.65
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_INVALID_RESPONSE_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.66
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFI_SERVICE_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.67
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_FELICA_HTTP_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.68
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFIC_VERSION_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.69
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_REQUEST_ACTIVITY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.70
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFC_DISABLED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.71
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFC_UNINSTALLED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.72
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_MFI_LOGIN_RESULT { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.73
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_TOS_SITE_UPGRADE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.74
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_TOS_SITE_NONE_UPGRADE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.75
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_NEED_CARD_RECOVERY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.76
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_COMPLETE_CARD_RECOVERY { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.77
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EM_RUNNING_BY_MFIC { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.78
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_MODEL;
            }
        },
        EP_START_KIND_MYSERVICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.79
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_START_KIND_NOTICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.80
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_START_KIND_CARD_READER { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.81
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_SCREEN_MYSERVICE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.82
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_SCREEN_RECOMMEND { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.83
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_SCREEN_CARD_READING { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.84
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_SCREEN_DELETE_CARD_LIST { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.85
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_TUTORIAL_SHOW { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.86
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_TUTORIAL_UNSHOWN { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.87
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_RECOMMEND_DETAIL_TRANSITABLE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.88
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_RECOMMEND_DETAIL_NOT_TRANSITABLE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.89
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_ExtCard_TRANSITABLE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.90
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_ExtCard_NOT_TRANSITABLE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.91
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_MFS_DISABLED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.92
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_MFS_UNINSTALLED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.93
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_MFS_SIGNATURE_UNMATCHED { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.94
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_TUTORIAL_NOT_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.95
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EP_JUDGE_TUTORIAL_COMPLETE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.96
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_RESULT_FROM_INTERNAL;
            }
        },
        EA_START_APPLICATION { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.97
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_APP_LIFECYCLE;
            }
        },
        EI_NOTIFICATION { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.98
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_PUSH { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.99
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_NFC_TAG_RECEIVE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.100
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_CONFIG_CHANGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.101
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_LOCK_STATE_CHANGE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.102
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_FATAL_ERROR { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.103
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        },
        EI_MFS_MOVEMENT_RECEIVE { // from class: com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event.104
            @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.Event
            Type type() {
                return Type.EVENT_TYPE_INTERRUPT;
            }
        };

        enum Type {
            EVENT_TYPE_NONE,
            EVENT_TYPE_USER_ACTION,
            EVENT_TYPE_RESULT_FROM_MODEL,
            EVENT_TYPE_RESULT_FROM_INTERNAL,
            EVENT_TYPE_APP_LIFECYCLE,
            EVENT_TYPE_INTERRUPT
        }

        Type type() {
            return Type.EVENT_TYPE_NONE;
        }
    }

    static class StructureParams {
        CardDetailFunc cardDetailFunc;
        MyCardInfoAgent cardInfoForDetail;
        CentralFuncAgent centralFuncAgent;
        DeleteCardListFunc deleteCardListFunc;
        ExtIcCardFuncAgent extIcCardFuncAgent;
        Object extra;
        MyServiceGroupInfoAgent groupInfoForDetail;
        boolean isAssetCacheUpdate;
        boolean isEnoughExtCardServiceInfo;
        boolean isFailTransitionExtCard;
        boolean isFelicaLock;
        boolean isFirstPageRecommend;
        boolean isForceCheck;
        boolean isMfiCacheUpdate;
        boolean isSimCacheUpdate;
        boolean isWarningLackCardInfo;
        boolean isWarningLackServiceInfo;
        boolean isWarningNotLogined;
        MemoryUsageFuncAgent memoryUsageFuncAgent;
        String mfiAccountName;
        MfiCardFuncAgent mfiCardFuncAgent;
        int missingAppInfo;
        NoticeFuncAgent noticeFuncAgent;
        NoticeInfoAgent noticeInfoAgent;
        RecommendInfoAgent recommendInfoAgent;
        MyServiceInfoAgent serviceInfoForDetail;
        Intent startIntentForMfiLogin;
        TermsOfServiceFuncAgent termsOfServiceFuncAgent;
        int extraAccountCode = 0;
        int resultCodeFromMfiLogin = MfiLoginResultCode.NONE.getCode();
        int specificError = 0;

        StructureParams() {
        }
    }
}
