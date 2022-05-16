/*
 * @(#) DefaultCaptchaLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2020/01/09
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.code.kaptcha.Constants;

@RunWith(SpringRunner.class)
public class DefaultCaptchaLogicImplTest {
    @Mock
    private HttpSession session;

    @InjectMocks
    private DefaultCaptchaLogicImpl defaultCaptchaLogic;

    private static final String SESSION_ID = "SESSION_ID";
    private static final String CAPTCHA_TEXT = "def05";

    /**
     * 案件ID:SCA0110/チェックリストID:UT- SCA0110-003-01/区分:N/チェック内容:test save katpcha text
     */
    @Test
    public void testSaveKatpcha() {
        defaultCaptchaLogic.save(SESSION_ID, CAPTCHA_TEXT);
    }

    /**
     * 案件ID:SCA0110/チェックリストID:UT- SCA0110-003-02/区分:I/チェック内容:test match katpcha success
     */
    @Test
    public void testMatchKatpchaSuccess() {
        when(session.getAttribute(Constants.KAPTCHA_SESSION_KEY)).thenReturn(CAPTCHA_TEXT);
        assertThat(defaultCaptchaLogic.match(SESSION_ID, CAPTCHA_TEXT)).isTrue();
    }

    /**
     * 案件ID:SCA0110/チェックリストID:UT- SCA0110-003-03/区分:E/チェック内容:test match katpcha failed
     */
    @Test
    public void testMatchKatpchaFailure() {
        String expected = "def99";
        when(session.getAttribute(Constants.KAPTCHA_SESSION_KEY)).thenReturn(CAPTCHA_TEXT);
        assertThat(defaultCaptchaLogic.match(SESSION_ID, expected)).isFalse();
    }

}
