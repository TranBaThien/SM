package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

public class SummaryItemVo {
	private String itemTop;
	private String itemBottom;

	private List<String> lstIdMansion;

	public SummaryItemVo() {
		super();
	}

	public SummaryItemVo(String itemTop, String itemBottom, List<String> lstIdMansion) {
		super();
		this.itemTop = itemTop;
		this.itemBottom = itemBottom;
		this.lstIdMansion = lstIdMansion;
	}

	public String getItemTop() {
		return itemTop;
	}

	public void setItemTop(String itemTop) {
		this.itemTop = itemTop;
	}

	public String getItemBottom() {
		return itemBottom;
	}

	public void setItemBottom(String itemBottom) {
		this.itemBottom = itemBottom;
	}

	public List<String> getLstIdMansion() {
		return lstIdMansion;
	}

	public void setLstIdMansion(List<String> lstIdMansion) {
		this.lstIdMansion = lstIdMansion;
	}
}
