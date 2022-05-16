package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120BDAO;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class UserListLogicImplTest {

    private final String ACCOUNT_AVAILABLE_FLAG_1 = "1";
    private final String ACCOUNT_AVAILABLE_FLAG_2 = "2";
    private final String CD024_1 = "利用可能";
    private final String CD024_2 = "利用不可";

    private final String CITYNAME_CODE_1 = "1";
    private final String CITYNAME_CODE_2 = "2";

    private final String LOGIN_ERROR_COUNT = "0";
    private final String LOGIN_ID = "G0000001";
    
    private final String LOGIN_STATUS_FLAG_1 = "0";
    private final String LOGIN_STATUS_FLAG_2 = "1";
    private final String CD026_1 = "ログインしていない";
    private final String CD026_2 = "ログイン中";

    private final String USER_ID_1 = "1000000011";
    private final String USER_ID_2 = "1000000012";
    private final String USER_NAME = "間には全";

    private final String USER_TYPE_1 = "1";
    private final String USER_TYPE_2 = "2";
    private final String CD027_1 = "都職員";
    private final String CD027_2 = "区市町村";


    @Mock
    private TBL120BDAO tbl120BDao;

    @InjectMocks
    private UserListLogicImpl userListLogicImpl;

    List<UserInforVo> getListUserInput1(){
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        UserInforVo one = new UserInforVo();
        one.setAvailability(ACCOUNT_AVAILABLE_FLAG_1);
        one.setCityName(CITYNAME_CODE_1);
        one.setLoginErrorCount(LOGIN_ERROR_COUNT);
        one.setLoginId(LOGIN_ID);
        one.setLoginStatusFlag(LOGIN_STATUS_FLAG_1);
        one.setUserId(USER_ID_1);
        one.setUserName(USER_NAME);
        one.setUserType(USER_TYPE_1);
        vo.add(one);
        return vo;
    }
    
    List<UserInforVo> getListUserInput2(){
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        UserInforVo two = new UserInforVo();
        two.setAvailability(ACCOUNT_AVAILABLE_FLAG_2);
        two.setCityName(CITYNAME_CODE_2);
        two.setLoginErrorCount(LOGIN_ERROR_COUNT);
        two.setLoginId(LOGIN_ID);
        two.setLoginStatusFlag(LOGIN_STATUS_FLAG_2);
        two.setUserId(USER_ID_2);
        two.setUserName(USER_NAME);
        two.setUserType(USER_TYPE_2);
        vo.add(two);
        return vo;
    }
    List<UserInforVo> getListUserOutput1(){
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        UserInforVo one = new UserInforVo();
        one.setAvailability(CD024_1);
        one.setCityName("-");
        one.setLoginErrorCount(LOGIN_ERROR_COUNT);
        one.setLoginId(LOGIN_ID);
        one.setLoginStatusFlag(CD026_1);
        one.setUserId(USER_ID_1);
        one.setUserName(USER_NAME);
        one.setUserType(CD027_1);
        vo.add(one);
        return vo;
    }

    List<UserInforVo> getListUserOutput2(){
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        UserInforVo two = new UserInforVo();
        two.setAvailability(CD024_2);
        two.setCityName(CITYNAME_CODE_2);
        two.setLoginErrorCount(LOGIN_ERROR_COUNT);
        two.setLoginId(LOGIN_ID);
        two.setLoginStatusFlag(CD026_2);
        two.setUserId(USER_ID_2);
        two.setUserName(USER_NAME);
        two.setUserType(CD027_2);
        vo.add(two);
        return vo;
    }

    List<UserInforVo> getListUserOutput3(){
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        UserInforVo two = new UserInforVo();
        two.setAvailability(CD024_2);
        two.setCityName(CITYNAME_CODE_2);
        two.setLoginErrorCount(LOGIN_ERROR_COUNT);
        two.setLoginId(LOGIN_ID);
        two.setLoginStatusFlag(CD026_2);
        two.setUserId(USER_ID_2);
        two.setUserName(USER_NAME);
        two.setUserType(null);
        vo.add(two);
        return vo;
    }

    private void assertEntity( List<UserInforVo> vo1,  List<UserInforVo> vo2){
        assertThat(vo1.get(0).getAvailability()).isEqualTo(vo2.get(0).getAvailability());
        assertThat(vo1.get(0).getCityName()).isEqualTo(vo2.get(0).getCityName());
        assertThat(vo1.get(0).getLoginErrorCount()).isEqualTo(vo2.get(0).getLoginErrorCount());
        assertThat(vo1.get(0).getLoginId()).isEqualTo(vo2.get(0).getLoginId());
        assertThat(vo1.get(0).getLoginStatusFlag()).isEqualTo(vo2.get(0).getLoginStatusFlag());
        assertThat(vo1.get(0).getUserName()).isEqualTo(vo2.get(0).getUserName());
        assertThat(vo1.get(0).getUserType()).isEqualTo(vo2.get(0).getUserType());
    }
    
    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0003／区分：N／チェック内容：Test get list User info When the DB return non record
     */
    @Test
    public void getListUserInfo_WhenDataIsEmpty () throws BusinessException { 
        List<UserInforVo> vo = new ArrayList<UserInforVo>();
        Mockito.when(tbl120BDao.getListUserB()).thenReturn(vo);
        List<UserInforVo> actual = userListLogicImpl.getListUserInfo();
        assertThat(actual).isEqualTo(new ArrayList<UserInforVo>());
    } 

    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0004／区分：N／チェック内容：Test get list User info When the User type is't City
     */
    @Test
    public void getListUserInfo_WhenDataExistRecordWithUsertypeIsNotCity () throws BusinessException { 
        List<UserInforVo> vo = getListUserInput1();
        Mockito.when(tbl120BDao.getListUserB()).thenReturn(vo);
        List<UserInforVo> actual = userListLogicImpl.getListUserInfo();
        assertEntity(actual, getListUserOutput1());
    }

    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0005／区分：N／チェック内容：Test get list User info When The User type is City
     */
    @Test
    public void getListUserInfo_WhenDataExistRecordWithUsertypeIsCity () throws BusinessException { 
        List<UserInforVo> vo = getListUserInput2();
        Mockito.when(tbl120BDao.getListUserB()).thenReturn(vo);
        List<UserInforVo> actual = userListLogicImpl.getListUserInfo();
        assertEntity(actual, getListUserOutput2());
    }

    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0006／区分：N／チェック内容：Test get list User info When User type is null
     */
    @Test
    public void getListUserInfo_WhenUsertypeIsNull () throws BusinessException { 
        List<UserInforVo> vo = getListUserInput2();
        vo.get(0).setUserType(null);
        Mockito.when(tbl120BDao.getListUserB()).thenReturn(vo);
        List<UserInforVo> actual = userListLogicImpl.getListUserInfo();
        assertEntity(actual, getListUserOutput3());
    }
    
    /**
     * 案件ID：ABA0110／チェックリストID：UT-ABA0110-001-0007／区分：E／チェック内容：Test get list User info When Failed
     */
    @Test(expected = NullPointerException.class)
    public void getListUserInfo_WhenFailed () throws BusinessException {
        List<UserInforVo> vo = getListUserInput2();
        vo.get(0).setUserType(null);
        Mockito.when(tbl120BDao.getListUserB()).thenReturn(null);
        List<UserInforVo> actual = userListLogicImpl.getListUserInfo();
    }
}
