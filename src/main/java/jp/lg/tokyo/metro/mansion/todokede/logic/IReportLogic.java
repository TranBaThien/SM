/*
 * @(#) IReportLogic.java 2019/MM/dd
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/MM/dd
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;

public interface IReportLogic {

    /**
     * Get report RP010
     * 
     * @param notificationNo String
     * @return RP010Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP010Vo getReportRP010(String notificationNo) throws BusinessException;

    /**
     * Get report RP020
     * 
     * @param acceptNo String
     * @param preScreenId String
     * @return RP020Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP020Vo getReportRP020(String acceptNo, String preScreenId) throws BusinessException;

    /**
     * Get report RP030
     * 
     * @param adviceNo String
     * @param preScreenId String
     * @return RP030Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP030Vo getReportRP030(String adviceNo, String preScreenId) throws BusinessException;

    /**
     * Get report RP040
     * 
     * @param inveseNo String
     * @param preScreenId String
     * @return RP040Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP040Vo getReportRP040(String inveseNo, String preScreenId) throws BusinessException;

    /**
     * Get report RP050
     * 
     * @Screen GIA00110
     * @param supervisedNoticeNo String
     * @param preScreenId String
     * @return RP050Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP050Vo getReportRP050(String supervisedNoticeNo, String preScreenId) throws BusinessException;

    /**
     * Get report RP060
     * 
     * @Screen GIA00110
     * @param supervisedNoticeNo String
     * @param preScreenId String
     * @return RP060Vo
     * @throws Business Exception
     * @author pdquang
     */
    RP060Vo getReportRP060(String supervisedNoticeNo, String preScreenId) throws BusinessException;

}
