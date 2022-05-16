package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

public class TBL120BDAOTest extends AbstractDaoTest{
    @Autowired
    private TBL120BDAO tbl120BDAO;

    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String CITYNAME = "小笠原村";
    private final String LOGIN_ERROR_COUNT = "0";
    private final String LOGIN_ID = "G0000001";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String USER_ID = "1000000011";
    private final String USER_NAME = "間には全";
    private final String USER_TYPE = "1";

    private void assertEntity(UserInforVo entity) {
        assertThat(entity.getAvailability()).isEqualTo(ACCOUNT_AVAILABLE_FLAG);
        assertThat(entity.getCityName()).isEqualTo(CITYNAME);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
        assertThat(entity.getUserName()).isEqualTo(USER_NAME);
        assertThat(entity.getUserType()).isEqualTo(USER_TYPE);
    }

    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0001／区分：N／チェック内容：Test get list User When the DB return non record
     */
    @Test
    public void getListUserB_WhenExist_ShouldBeNull() {
        // Execute
        List<UserInforVo> actual = tbl120BDAO.getListUserB();
        assertThat(actual).isEqualTo(new ArrayList<UserInforVo>());
    }

    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0002／区分：N／チェック内容：Test get ListUsercomplete When the DB return one record
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-004.sql")
    public void getListUserB_WhenExist_ShouldBeExist() {
        // Execute
        List<UserInforVo> actual = tbl120BDAO.getListUserB();
        assertEntity(actual.get(0));
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0005／区分：N／チェック内容：Test get LoginID When he minimum Usable LoginId is 3
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-005.sql")
    public void getNewLoginId_WhenMissingLoginIdIs3() {
        // Execute
        String actual = tbl120BDAO.getNewLoginId();
        assertThat(actual).isEqualTo("3");
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0006／区分：N／チェック内容：Test get LoginID When he minimum Usable LoginId is 4
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-006.sql")
    public void getNewLoginId_WhenMissingLoginIdIs4() {
        // Execute
        String actual = tbl120BDAO.getNewLoginId();
        assertThat(actual).isEqualTo("4");
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0007／区分：N／チェック内容：Test get LoginID When he minimum Usable LoginId is 1
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-007.sql")
    public void getNewLoginId_WhenMissingLoginIdIs1() {
        // Execute
        String actual = tbl120BDAO.getNewLoginId();
        assertThat(actual).isEqualTo("1");
    }
}
