package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;

public class TBL105DAOTest extends AbstractDaoTest {

	@Autowired
	private TBL105DAO tbl105DAO;

	private final String HISTORY_NUMBER = "1000000005";
	private final String APARTMENT_ID = "22";
	private final String ADDRESS = "11";
	private final String APARTMENT_LOST_FLAG = "1";
	private final String APARTMENT_NAME = "22";
	private final String APARTMENT_NAME_PHONETIC = "1111";
	private final String BUILD_DAY = "22";
	private final String BUILD_MONTH = "11";
	private final String BUILD_YEAR = "1990";
	private final double BUILDING_AREA = 1.2;
	private final String BUILDING_STRUCTURE = "11";
	private final String CITY_CODE = "333111";
	private final String CITY_NAME = "namee";
	private final String DELETE_FLAG = "1";
	private final int FLOOR_NUMBER = 1;
	private final int HOUSE_NUMBER = 1;
	private final String MAIL_ADDRESS = "55";
	private final String NEWEST_NOTIFICATION_ADDRESS = "11";
	private final String NEWEST_NOTIFICATION_NAME = "22";
	private final String NEWEST_NOTIFICATION_NO = "44";
	private final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.now();
	private final String NOTIFICATION_KBN = "1";
	private final String PRELIMINARY1 = "33";
	private final String PRELIMINARY2 = "33";
	private final String PRELIMINARY3 = "33";
	private final String PRELIMINARY4 = "33";
	private final String PRELIMINARY5 = "33";
	private final String PROPERTY_NUMBER = "33";
	private final String REAL_ESTATE_NO = "33";
	private final String REGISTRATION_ADDRESS = "33";
	private final String REGISTRATION_HOUSE_NO = "333";
	private final String REGISTRATION_NO = "22";
	private final String REPASSWORD_MAIL_ADDRESS = "111";
	private final double SITE_AREA = 2.2;
	private final String SUPPORT = "1";
	private final double TOTAL_FLOOR_AREA = 2.2;
	private final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID = "333";
	private final String ZIP_CODE = "111";
	
	
	/**
	 * 案件ID：GEB0110／チェックリストID：UT- GEB0110-001-08／区分：N／チェック内容：Test save success
	 * 案件ID：AAA0110／チェックリストID：UT- AAA0110-010／区分：N／チェック内容：Test save success
	 * 案件ID：AAA0110／チェックリストID：UT- SBA0110-001／区分：N／チェック内容：Test save success
	 */
	    @Test
	    public void save_WhenSuccess_ShouldBeCreated() {
	        // Prepare data
	        TBL105Entity expected = generateTBL105Entity();

	        // Execute
	        TBL105Entity actual = tbl105DAO.save(expected);

	        // Check result
	        assertEntityWithoutTBL105(actual);
	    }

	    /**
		  * 案件ID：GEB0110／チェックリストID：UT- GEB0110-001-09／区分：E／チェック内容：Test save fail will throw exception
		*/
	    
	    @Test(expected = InvalidDataAccessApiUsageException.class)
	    public void save_WhenFail_ShouldBeThrowException() {
	        tbl105DAO.save(null);
	    }
	
	private TBL105Entity generateTBL105Entity() {
		TBL105Entity entity = new TBL105Entity();

		entity.setHistoryNumber(HISTORY_NUMBER);
		entity.setApartmentId(APARTMENT_ID);
		entity.setAddress(ADDRESS);
		entity.setApartmentLostFlag(APARTMENT_LOST_FLAG);
		entity.setApartmentName(APARTMENT_NAME);
		entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		entity.setBuildDay(BUILD_DAY);
		entity.setBuildMonth(BUILD_MONTH);
		entity.setBuildYear(BUILD_YEAR);
		entity.setBuildingArea(BUILDING_AREA);
		entity.setBuildingStructure(BUILDING_STRUCTURE);
		entity.setCityCode(CITY_CODE);
		entity.setCityName(CITY_NAME);
		entity.setDeleteFlag(DELETE_FLAG);
		entity.setFloorNumber(FLOOR_NUMBER);
		entity.setHouseNumber(HOUSE_NUMBER);
		entity.setMailAddress(MAIL_ADDRESS);
		entity.setNewestNotificationAddress(NEWEST_NOTIFICATION_ADDRESS);
		entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
		entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE);
		entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME);
		entity.setNotificationKbn(NOTIFICATION_KBN);
		entity.setPreliminary1(PRELIMINARY1);
		entity.setPreliminary2(PRELIMINARY2);
		entity.setPreliminary3(PRELIMINARY3);
		entity.setPreliminary4(PRELIMINARY4);
		entity.setPreliminary5(PRELIMINARY5);
		entity.setPropertyNumber(PROPERTY_NUMBER);
		entity.setRealEstateNo(REAL_ESTATE_NO);
		entity.setRegistrationAddress(REGISTRATION_ADDRESS);
		entity.setRegistrationHouseNo(REGISTRATION_HOUSE_NO);
		entity.setRegistrationNo(REGISTRATION_NO);
		entity.setRepasswordMailAddress(REPASSWORD_MAIL_ADDRESS);
		entity.setSiteArea(SITE_AREA);
		entity.setSupport(SUPPORT);
		entity.setTotalFloorArea(TOTAL_FLOOR_AREA);
		entity.setUpdateDatetime(UPDATE_DATETIME);
		entity.setUpdateUserId(UPDATE_USER_ID);
		entity.setZipCode(ZIP_CODE);

		return entity;

	}
	
	private void assertEntityWithoutTBL105(TBL105Entity actual) {
		assertThat(actual.getHistoryNumber()).isEqualTo(HISTORY_NUMBER);
		assertThat(actual.getApartmentId()).isEqualTo(APARTMENT_ID);
		assertThat(actual.getAddress()).isEqualTo(ADDRESS);
		assertThat(actual.getApartmentLostFlag()).isEqualTo(APARTMENT_LOST_FLAG);
		assertThat(actual.getApartmentName()).isEqualTo(APARTMENT_NAME);
		assertThat(actual.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
		assertThat(actual.getBuildDay()).isEqualTo(BUILD_DAY);
		assertThat(actual.getBuildMonth()).isEqualTo(BUILD_MONTH);
		assertThat(actual.getBuildYear()).isEqualTo(BUILD_YEAR);
		assertThat(actual.getBuildingArea()).isEqualTo(BUILDING_AREA);
		assertThat(actual.getBuildingStructure()).isEqualTo(BUILDING_STRUCTURE);
		assertThat(actual.getCityCode()).isEqualTo(CITY_CODE);
		assertThat(actual.getCityName()).isEqualTo(CITY_NAME);
		assertThat(actual.getDeleteFlag()).isEqualTo(DELETE_FLAG);
		assertThat(actual.getFloorNumber()).isEqualTo(FLOOR_NUMBER);
		assertThat(actual.getHouseNumber()).isEqualTo(HOUSE_NUMBER);
		assertThat(actual.getMailAddress()).isEqualTo(MAIL_ADDRESS);
		assertThat(actual.getNewestNotificationAddress()).isEqualTo(NEWEST_NOTIFICATION_ADDRESS);
		assertThat(actual.getNewestNotificationName()).isEqualTo(NEWEST_NOTIFICATION_NAME);
		assertThat(actual.getNewestNotificationNo()).isEqualTo(NEWEST_NOTIFICATION_NO);
		assertThat(actual.getNextNotificationDate()).isEqualTo(NEXT_NOTIFICATION_DATE);
		assertThat(actual.getNotificationKbn()).isEqualTo(NOTIFICATION_KBN);
		assertThat(actual.getPreliminary1()).isEqualTo(PRELIMINARY1);
		assertThat(actual.getPreliminary2()).isEqualTo(PRELIMINARY2);
		assertThat(actual.getPreliminary3()).isEqualTo(PRELIMINARY3);
		assertThat(actual.getPreliminary4()).isEqualTo(PRELIMINARY4);
		assertThat(actual.getPreliminary5()).isEqualTo(PRELIMINARY5);
		assertThat(actual.getPropertyNumber()).isEqualTo(PROPERTY_NUMBER);
		assertThat(actual.getRealEstateNo()).isEqualTo(REAL_ESTATE_NO);
		assertThat(actual.getRegistrationAddress()).isEqualTo(REGISTRATION_ADDRESS);
		assertThat(actual.getRegistrationHouseNo()).isEqualTo(REGISTRATION_HOUSE_NO);
		assertThat(actual.getRegistrationNo()).isEqualTo(REGISTRATION_NO);
		assertThat(actual.getRepasswordMailAddress()).isEqualTo(REPASSWORD_MAIL_ADDRESS);
		assertThat(actual.getSiteArea()).isEqualTo(SITE_AREA);
		assertThat(actual.getSupport()).isEqualTo(SUPPORT);
		assertThat(actual.getTotalFloorArea()).isEqualTo(TOTAL_FLOOR_AREA);
		assertThat(actual.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
		assertThat(actual.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
		assertThat(actual.getZipCode()).isEqualTo(ZIP_CODE);

	}
}

