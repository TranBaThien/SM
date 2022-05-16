/*
 * @(#) CodeForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/30
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

/**
 * @author PVHung3
 *
 */
public class CodeForm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String value;
	
	private String label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
