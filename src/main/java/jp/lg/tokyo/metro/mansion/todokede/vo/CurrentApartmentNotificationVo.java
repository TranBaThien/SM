/*
 * @(#) CurrentApartmentNotificationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 4, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author hntvy
 *
 */
public class CurrentApartmentNotificationVo extends BaseVo {


	private String nextNotificationDate;
	
	private String acceptStatus;
	
	private int changeCount;
	
	private String typeCode;
	
	private String corresponDate;
	

	

	/**
	 * @param nextNotificationDate
	 * @param acceptStatus
	 * @param changeCount
	 */
	public CurrentApartmentNotificationVo(String nextNotificationDate, String acceptStatus, int changeCount) {
		super();
		this.nextNotificationDate = nextNotificationDate;
		this.acceptStatus = acceptStatus;
		this.changeCount = changeCount;
	}
	
	public CurrentApartmentNotificationVo(String corresponDate, String typeCode) {
		super();
		this.corresponDate = corresponDate;
		this.typeCode = typeCode;
	}

	
	/**
	 * 
	 */
	public CurrentApartmentNotificationVo() {
		super();
	}


	/**
	 * @return the nextNotificationDate
	 */
	public String getNextNotificationDate() {
		return nextNotificationDate;
	}

	/**
	 * @param nextNotificationDate the nextNotificationDate to set
	 */
	public void setNextNotificationDate(String nextNotificationDate) {
		this.nextNotificationDate = nextNotificationDate;
	}

	/**
	 * @return the acceptStatus
	 */
	public String getAcceptStatus() {
		return acceptStatus;
	}

	/**
	 * @param acceptStatus the acceptStatus to set
	 */
	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	/**
	 * @return the changeCount
	 */
	public int getChangeCount() {
		return changeCount;
	}

	/**
	 * @param changeCount the changeCount to set
	 */
	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	/**
	 * @return the corresponDate
	 */
	public String getCorresponDate() {
		return corresponDate;
	}


	/**
	 * @param corresponDate the corresponDate to set
	 */
	public void setCorresponDate(String corresponDate) {
		this.corresponDate = corresponDate;
	}

}
