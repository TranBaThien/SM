/*
 * @(#) BasicReportInfoForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author DLLy
 * Create Date: 2020/01/14
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.*;

import java.io.Serializable;

/**
 * @author DLLy
 *
 */
public class BasicNotificationInfoForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@MaxSize(val = 50)
	@SpecialCharacters
	@FieldName("マンション名")
	@Position(1)
	private String txtApartmentName;

	@Katakana
	@MaxSize(val = 100)
	@FieldName("マンション名フリガナ")
	@Position(2)
	private String txtApartmentNamePhonetic;

	@HaftSizeNumber
	@MinSizeValidBlank(val = 3)
	@MaxSize(val = 3)
	@FieldName("マンションの郵便番号")
	@Position(3)
	private String txtApartmentZipCode1;

	@HaftSizeNumber
	@MinSizeValidBlank(val = 4)
	@MaxSize(val = 4)
	@FieldName("マンションの郵便番号")
	@Position(4)
	private String txtApartmentZipCode2;

	private String lblApartmentAddress1;

	@MaxSize(val = 100)
	@FullSizeCharacter
	@FieldName("マンションの住所2")
	@Position(5)
	private String txtApartmentAddress2;

	public String getTxtApartmentName() {
		return txtApartmentName;
	}
	public void setTxtApartmentName(String txtApartmentName) {
		this.txtApartmentName = txtApartmentName;
	}
	public String getTxtApartmentNamePhonetic() {
		return txtApartmentNamePhonetic;
	}
	public void setTxtApartmentNamePhonetic(String txtApartmentNamePhonetic) {
		this.txtApartmentNamePhonetic = txtApartmentNamePhonetic;
	}
	public String getTxtApartmentZipCode1() {
		return txtApartmentZipCode1;
	}
	public void setTxtApartmentZipCode1(String txtApartmentZipCode1) {
		this.txtApartmentZipCode1 = txtApartmentZipCode1;
	}
	public String getTxtApartmentZipCode2() {
		return txtApartmentZipCode2;
	}
	public void setTxtApartmentZipCode2(String txtApartmentZipCode2) {
		this.txtApartmentZipCode2 = txtApartmentZipCode2;
	}
	public String getLblApartmentAddress1() {
		return lblApartmentAddress1;
	}
	public void setLblApartmentAddress1(String lblApartmentAddress1) {
		this.lblApartmentAddress1 = lblApartmentAddress1;
	}
	public String getTxtApartmentAddress2() {
		return txtApartmentAddress2;
	}
	public void setTxtApartmentAddress2(String txtApartmentAddress2) {
		this.txtApartmentAddress2 = txtApartmentAddress2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
