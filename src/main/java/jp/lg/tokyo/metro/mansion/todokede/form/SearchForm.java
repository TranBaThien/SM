/*
 * @(#) SearchForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;

/**
 * @author tbthien
 *
 */
public class SearchForm {

	
	private String cityCode;		
	
	@Position(1)
	@MaxSize(val = 50)
	@SpecialCharacters
	@FieldName("マンション名")
	private String ApartmentName;
		
	@Position(2)
	@MaxSize(val = 10)
	@HalfsizeAlphanumeric
	@DateCustom(pattern = CommonConstants.SLASH_YYYYMMDD)
	@FieldName("登録日－開始")	
	private String StartTimeApply;
		
	@Position(3)
	@MaxSize(val = 10)
	@HalfsizeAlphanumeric
	@DateCustom(pattern = CommonConstants.SLASH_YYYYMMDD)
	@FieldName("登録日－終了")
	private String EndTimeApply;
	
	private String Unexamined;
	
	
	private String Approval;
	
	
	private String Reject;
		
	public String getCityCode() {
		return cityCode; 
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}		

	public String getApartmentName() {
		return ApartmentName;
	}

	public void setApartmentName(String apartmentName) {
		ApartmentName = apartmentName;
	}	

	public String getStartTimeApply() {
		return StartTimeApply;
	}

	public void setStartTimeApply(String startTimeApply) {
		StartTimeApply = startTimeApply;
	}

	public String getEndTimeApply() {
		return EndTimeApply;
	}

	public void setEndTimeApply(String endTimeApply) {
		EndTimeApply = endTimeApply;
	}

	public String getUnexamined() {
		return Unexamined;
	}

	public void setUnexamined(String unexamined) {
		Unexamined = unexamined;
	}

	public String getApproval() {
		return Approval;
	}

	public void setApproval(String approval) {
		Approval = approval;
	}

	public String getReject() {
		return Reject;
	}

	public void setReject(String reject) {
		Reject = reject;
	}

    @Override
    public String toString() {
        return "SearchForm [cityCode=" + cityCode + ", ApartmentName=" + ApartmentName + ", StartTimeApply="
                + StartTimeApply + ", EndTimeApply=" + EndTimeApply + ", Unexamined=" + Unexamined + ", Approval="
                + Approval + ", Reject=" + Reject + "]";
    }		
	
}
