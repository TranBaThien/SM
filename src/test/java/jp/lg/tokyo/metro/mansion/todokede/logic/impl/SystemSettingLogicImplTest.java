package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.dao.TBM004DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;

@RunWith(SpringRunner.class)
public class SystemSettingLogicImplTest {
    
    @Mock
    private TBM004DAO tbm004dao;
    
    @InjectMocks
    SystemSettingLogicImpl settingLogicImpl;
    
    private static final String SET_NO = "1";
    private static final String SET_TARGET_NAME_JP = "区市町村用アカウントロック期間";
    private static final String SET_TARGET_NAME_ENG = "G_ACCOUT_LOCK_PERIOD";
    private static final String SET_POINT = "60";
    private static final String COMMENT = "";
    private static final String DELETE_FLAG = "0";
    private static final String UPDATE_USER_ID = "";
    private static final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();

   private TBM004Entity newTBM004Entity() {
       TBM004Entity entity = new TBM004Entity();
       
       entity.setSetNo(SET_NO);
       entity.setSetTargetNameJp(SET_TARGET_NAME_JP);
       entity.setSetTargetNameEng(SET_TARGET_NAME_ENG);
       entity.setSetPoint(SET_POINT);
       entity.setComment(COMMENT);
       entity.setDeleteFlag(DELETE_FLAG);
       entity.setUpdateUserId(UPDATE_USER_ID);
       entity.setUpdateDateTime(UPDATE_DATETIME);
       
       return entity;
   }
   
   
   /**
     * 案件ID:GAA0110/チェックリストID:UT-GAA0110-038/区分:N/チェック内容:Test Get SystemSetting By Key When Success
    */
   @Test
   public void testGetSystemSettingByKeyWhenSuccess() {
       // prepare data
       TBM004Entity entity = newTBM004Entity();
       Mockito.when(tbm004dao.findBySetTargetNameEng(Mockito.anyString())).thenReturn(entity);
       
       //Execute
       entity = settingLogicImpl.getSystemSettingByKey(SET_TARGET_NAME_ENG);
       
       // prepare data
       assertResult(entity);
   }
   
   private void assertResult(TBM004Entity entity) {
       assertThat(entity.getSetNo()).isEqualTo(SET_NO);
       assertThat(entity.getSetTargetNameJp()).isEqualTo(SET_TARGET_NAME_JP);
       assertThat(entity.getSetTargetNameEng()).isEqualTo(SET_TARGET_NAME_ENG);
       assertThat(entity.getSetPoint()).isEqualTo(SET_POINT);
       assertThat(entity.getComment()).isEqualTo(COMMENT);
       assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
       assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
       assertThat(entity.getUpdateDateTime()).isEqualTo(UPDATE_DATETIME);
   }
   
   /**
    * 案件ID:GAA0110/チェックリストID:UT-GAA0110-039/区分:E/チェック内容:Test Get SystemSetting By Key When Failed
   */
  @Test
  public void testGetSystemSettingByKeyWhenFailed() {

      Mockito.when(tbm004dao.findBySetTargetNameEng(Mockito.anyString())).thenReturn(null);
      
      //Execute
      TBM004Entity entity = settingLogicImpl.getSystemSettingByKey(SET_TARGET_NAME_ENG);
      
      // prepare data
      assertThat(entity).isEqualTo(null);
  }
}
