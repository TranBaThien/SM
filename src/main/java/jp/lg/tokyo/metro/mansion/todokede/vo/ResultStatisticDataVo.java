package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

public class ResultStatisticDataVo {

	private String aggregateCredit;

	private List<SummaryItemVo> lstSummaryItem;

	public ResultStatisticDataVo() {
		super();
	}

	public ResultStatisticDataVo(String aggregateCredit, List<SummaryItemVo> lstSummaryItem) {
		super();
		this.aggregateCredit = aggregateCredit;
		this.lstSummaryItem = lstSummaryItem;
	}

	public String getAggregateCredit() {
		return aggregateCredit;
	}

	public void setAggregateCredit(String aggregateCredit) {
		this.aggregateCredit = aggregateCredit;
	}

	public List<SummaryItemVo> getLstSummaryItem() {
		return lstSummaryItem;
	}

	public void setLstSummaryItem(List<SummaryItemVo> lstSummaryItem) {
		this.lstSummaryItem = lstSummaryItem;
	}
}
