/*
 * @(#) BasicReportInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/12
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class BasicReportInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String txtApartmentName;

	private String txtApartmentNamePhonetic;

	private String txtApartmentZipCode1;

	private String txtApartmentZipCode2;

	private String lblApartmentAddress1;

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
