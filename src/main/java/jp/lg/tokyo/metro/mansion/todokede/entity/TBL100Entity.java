package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateAttributeConverter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


/**
 * The persistent class for the tbl100 database table.
 * 
 */
@Entity
@Table(name="tbl100")
public class TBL100Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="APARTMENT_ID", unique=true, nullable=false, length=10)
	private String apartmentId;

	@Column(name="ADDRESS", length=100)
	private String address;

	@Column(name="APARTMENT_LOST_FLAG", length=1)
	private String apartmentLostFlag;

	@Column(name="APARTMENT_NAME", length=50)
	private String apartmentName;

	@Column(name="APARTMENT_NAME_PHONETIC", length=100)
	private String apartmentNamePhonetic;

	@Column(name="BUILD_DAY", length=2)
	private String buildDay;

	@Column(name="BUILD_MONTH", length=2)
	private String buildMonth;

	@Column(name="BUILD_YEAR", length=4)
	private String buildYear;

	@Column(name="BUILDING_AREA")
	private double buildingArea;

	@Column(name="BUILDING_STRUCTURE", length=30)
	private String buildingStructure;

	@Column(name="CITY_CODE", length=6)
	private String cityCode;

	@Column(name="CITY_NAME", length=5)
	private String cityName;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="FLOOR_NUMBER")
	private Integer floorNumber;

	@Column(name="HOUSE_NUMBER")
	private Integer houseNumber;

	@Column(name="MAIL_ADDRESS", length=120)
	private String mailAddress;

	@Column(name="NEWEST_NOTIFICATION_ADDRESS", length=100)
	private String newestNotificationAddress;

	@Column(name="NEWEST_NOTIFICATION_NAME", length=34)
	private String newestNotificationName;

	@Column(name="NEWEST_NOTIFICATION_NO", length=10)
	private String newestNotificationNo;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NEXT_NOTIFICATION_DATE", length=8)
	private LocalDate nextNotificationDate;

	@Column(name="NOTIFICATION_KBN", length=1)
	private String notificationKbn;

	@Column(name="PRELIMINARY1", length=30)
	private String preliminary1;

	@Column(name="PRELIMINARY2", length=30)
	private String preliminary2;

	@Column(name="PRELIMINARY3", length=30)
	private String preliminary3;

	@Column(name="PRELIMINARY4", length=30)
	private String preliminary4;

	@Column(name="PRELIMINARY5", length=30)
	private String preliminary5;

	@Column(name="PROPERTY_NUMBER", length=7)
	private String propertyNumber;

	@Column(name="REAL_ESTATE_NO", length=14)
	private String realEstateNo;

	@Column(name="REGISTRATION_ADDRESS", length=30)
	private String registrationAddress;

	@Column(name="REGISTRATION_HOUSE_NO", length=12)
	private String registrationHouseNo;

	@Column(name="REGISTRATION_NO", length=8)
	private String registrationNo;

	@Column(name="REPASSWORD_MAIL_ADDRESS", length=120)
	private String repasswordMailAddress;

	@Column(name="SITE_AREA")
	private double siteArea;

	@Column(name="SUPPORT", length=1)
	private String support;

	@Column(name="TOTAL_FLOOR_AREA")
	private double totalFloorArea;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="ZIP_CODE", length=8)
	private String zipCode;

	//bi-directional many-to-one association to TBL200Entity
	@OneToMany(mappedBy="tbl100")
	private List<TBL200Entity> tbl200s;
	
	@OneToOne(mappedBy="tBL100Entity", fetch = FetchType.LAZY)
	private TBL400Entity tBL400Entity;
	
	@OneToMany(mappedBy="tBL100Entity", fetch = FetchType.LAZY)
	private List<TBL300Entity> tbl300Entity;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APARTMENT_ID", nullable=false, insertable=false, updatable=false)
	private TBL110Entity tbl110;
	
	@OneToMany(mappedBy="tbl100", fetch = FetchType.LAZY)
	private List<TBL105Entity> tbl105;
	
	@OneToMany(mappedBy="tbl100", fetch = FetchType.LAZY)
	private List<TBL230Entity> tbl230s;
	
	@OneToMany(mappedBy="tbl100", fetch = FetchType.LAZY)
	private List<TBL240Entity> tbl240s;
	
	@OneToMany(mappedBy="tbl100", fetch = FetchType.LAZY)
	private List<TBL205Entity> tbl205s;

	public TBL100Entity() {
	}
	
	public List<TBL205Entity> getTbl205s() {
		return tbl205s;
	}

	public void setTbl205s(List<TBL205Entity> tbl205s) {
		this.tbl205s = tbl205s;
	}

	/**
	 * @return the tbl240s
	 */
	public List<TBL240Entity> getTbl240s() {
		return tbl240s;
	}

	/**
	 * @param tbl240s the tbl240s to set
	 */
	public void setTbl240s(List<TBL240Entity> tbl240s) {
		this.tbl240s = tbl240s;
	}

	public TBL400Entity gettBL400Entity() {
		return tBL400Entity;
	}

	public void settBL400Entity(TBL400Entity tBL400Entity) {
		this.tBL400Entity = tBL400Entity;
	}

	public List<TBL300Entity> getTbl300Entity() {
		return tbl300Entity;
	}

	public void setTbl300Entity(List<TBL300Entity> tbl300Entity) {
		this.tbl300Entity = tbl300Entity;
	}

	public TBL110Entity getTbl110() {
		return tbl110;
	}

	public void setTbl110(TBL110Entity tbl110) {
		this.tbl110 = tbl110;
	}

	public List<TBL105Entity> getTbl105() {
		return tbl105;
	}

	public void setTbl105(List<TBL105Entity> tbl105) {
		this.tbl105 = tbl105;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApartmentLostFlag() {
		return this.apartmentLostFlag;
	}

	public void setApartmentLostFlag(String apartmentLostFlag) {
		this.apartmentLostFlag = apartmentLostFlag;
	}

	public String getApartmentName() {
		return this.apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getApartmentNamePhonetic() {
		return this.apartmentNamePhonetic;
	}

	public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
		this.apartmentNamePhonetic = apartmentNamePhonetic;
	}

	public String getBuildDay() {
		return this.buildDay;
	}

	public void setBuildDay(String buildDay) {
		this.buildDay = buildDay;
	}

	public String getBuildMonth() {
		return this.buildMonth;
	}

	public void setBuildMonth(String buildMonth) {
		this.buildMonth = buildMonth;
	}

	public String getBuildYear() {
		return this.buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getBuildingStructure() {
		return this.buildingStructure;
	}

	public void setBuildingStructure(String buildingStructure) {
		this.buildingStructure = buildingStructure;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getFloorNumber() {
		return this.floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Integer getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getNewestNotificationAddress() {
		return this.newestNotificationAddress;
	}

	public void setNewestNotificationAddress(String newestNotificationAddress) {
		this.newestNotificationAddress = newestNotificationAddress;
	}

	public String getNewestNotificationName() {
		return this.newestNotificationName;
	}

	public void setNewestNotificationName(String newestNotificationName) {
		this.newestNotificationName = newestNotificationName;
	}

	public String getNewestNotificationNo() {
		return this.newestNotificationNo;
	}

	public void setNewestNotificationNo(String newestNotificationNo) {
		this.newestNotificationNo = newestNotificationNo;
	}

	public LocalDate getNextNotificationDate() {
		return this.nextNotificationDate;
	}

	public void setNextNotificationDate(LocalDate nextNotificationDate) {
		this.nextNotificationDate = nextNotificationDate;
	}

	public String getNotificationKbn() {
		return this.notificationKbn;
	}

	public void setNotificationKbn(String notificationKbn) {
		this.notificationKbn = notificationKbn;
	}

	public String getPreliminary1() {
		return this.preliminary1;
	}

	public void setPreliminary1(String preliminary1) {
		this.preliminary1 = preliminary1;
	}

	public String getPreliminary2() {
		return this.preliminary2;
	}

	public void setPreliminary2(String preliminary2) {
		this.preliminary2 = preliminary2;
	}

	public String getPreliminary3() {
		return this.preliminary3;
	}

	public void setPreliminary3(String preliminary3) {
		this.preliminary3 = preliminary3;
	}

	public String getPreliminary4() {
		return this.preliminary4;
	}

	public void setPreliminary4(String preliminary4) {
		this.preliminary4 = preliminary4;
	}

	public String getPreliminary5() {
		return this.preliminary5;
	}

	public void setPreliminary5(String preliminary5) {
		this.preliminary5 = preliminary5;
	}

	public String getPropertyNumber() {
		return this.propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getRealEstateNo() {
		return this.realEstateNo;
	}

	public void setRealEstateNo(String realEstateNo) {
		this.realEstateNo = realEstateNo;
	}

	public String getRegistrationAddress() {
		return this.registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public String getRegistrationHouseNo() {
		return this.registrationHouseNo;
	}

	public void setRegistrationHouseNo(String registrationHouseNo) {
		this.registrationHouseNo = registrationHouseNo;
	}

	public String getRegistrationNo() {
		return this.registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getRepasswordMailAddress() {
		return this.repasswordMailAddress;
	}

	public void setRepasswordMailAddress(String repasswordMailAddress) {
		this.repasswordMailAddress = repasswordMailAddress;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<TBL200Entity> getTbl200s() {
		return this.tbl200s;
	}

	public void setTbl200s(List<TBL200Entity> tbl200s) {
		this.tbl200s = tbl200s;
	}

	public TBL200Entity addTbl200(TBL200Entity tbl200) {
		getTbl200s().add(tbl200);
		tbl200.setTbl100(this);

		return tbl200;
	}

	public TBL200Entity removeTbl200(TBL200Entity tbl200) {
		getTbl200s().remove(tbl200);
		tbl200.setTbl100(null);

		return tbl200;
	}

	/**
	 * @return the tbl230s
	 */
	public List<TBL230Entity> getTbl230s() {
		return tbl230s;
	}

	/**
	 * @param tbl230s the tbl230s to set
	 */
	public void setTbl230s(List<TBL230Entity> tbl230s) {
		this.tbl230s = tbl230s;
	}

	public double getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public double getSiteArea() {
		return siteArea;
	}

	public void setSiteArea(double siteArea) {
		this.siteArea = siteArea;
	}

	public double getTotalFloorArea() {
		return totalFloorArea;
	}

	public void setTotalFloorArea(double totalFloorArea) {
		this.totalFloorArea = totalFloorArea;
	}
}