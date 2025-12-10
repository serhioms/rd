package ca.mss.rd.trade.calypso.workflow;

public enum WkfRuleTransfer {

	ApplyDefaultSDI,
	AutoSplit,
	CheckCorrectSDI,
	CheckCutOffNetting,
	CheckKickOff,
	CheckKnownFlag,
	CheckNetting,
	CheckNonNettedSplitLimit,
	CheckSDI,
	CheckSettleDate,
	CheckSplitLimit,
	CheckStatementFlag,
	CheckTradeNetting,
	CheckUnauthorizedSDI,
	CheckValidCountry,
	CheckValidSDI,
	CreateReturnSL,
	PropagateUserComment,
	ResetKnownFlag,
	ResetWorkflowType,
	SecurityNetting,
	SetAccountMvtDate,
	SetAutomaticKnownFlag,
	SetKnownFlag,
	SetPairOffKnownFlag,
	StopFXOptFwdTransfer,
	
	
	
	CheckContact,
	CheckCorrectContact,
	ApplyDefaultContact,
	HandleAckNack,
	ThreeParty,
	CheckNoTermCreditEvent,
	CheckTerminationKeyword,
	CheckBook,
	CheckCCISCustomerCode,
	CheckTerminationFeeCCISCustomerCode,
	CheckNoTerminationKeyword,
	ThreePartyUndoTermination,
	CheckSTPRemainingTrade,
	CheckCounterPartyStatus,
	CheckMultipleUpFrontFee,
	CheckBackOfficeTerminate,
	CheckMature,
	CheckHedgeAmount,
	CheckAmtGreater500000,
	CheckAmtLower500000,
	CheckAmtGreater10m,
	CheckAmtLower10m;
}


