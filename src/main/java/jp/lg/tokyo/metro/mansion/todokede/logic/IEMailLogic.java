/*
 * @(#)IEMailLogic.java
 *
 * Copyright(C) 2007 by Hitachi Information Systems CO., LTD
 *
 * Last_Update 2008/01/09
 * Version 1.00
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.io.File;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMailVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.EMailInfoVo;

/**
 * <p>
 * Email service
 * </p>
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public interface IEMailLogic {

    /**
     * <p>
     * Set mail sender.
     * </p>
     * @param mailSender JavaMailSender
     */
    void setMailSender(JavaMailSender mailSender);

    /**
     * <p>
     * Set velocity engine.
     * </p>
     * @param velocityEngine VelocityEngine
     */
    void setVelocityEngine(VelocityEngine velocityEngine);

    /**
     * <p>
     * send mail.
     * </p>
     * @param strTempFileName テンプレートのファイル名
     * @param emailInfo メール情報
     * @param content Object オブジェクト
     * @param attachedFiles 添付ファイル
     * @throws MailException MailException
     */
    void sendMail(String strTempFileName, EMailInfoVo emailInfo, Object content,
                    List<File> attachedFiles) throws MailException;

    /**
     * <p>
     * send mail with content.
     * </p>
     * @param strContent メールの内容
     * @param emailInfo メール情報
     * @param content Object オブジェクト
     * @param attachedFiles 添付ファイル
     * @throws MailException MailException
     */
    void sendMailWithContent(String strEmailContent, EMailInfoVo emailInfo, Object content,
                    List<File> attachedFiles) throws MailException;

    /**
     * <p>Function to get common template mail.</p>
     * <p><b>Important: you must prepared data to table TBM002 and table TBM0025 for get template mail</b><p>
     * <b>Example get common template: </b>
     * <p><b>ML035Vo</b> ml035Vo = emailLogic.getCommonTemplateMail(<b>ML035Vo</b>.class);<p>
     * <p>That function return common template mail and set into ML035Vo, you must to set some properties of ML035. </p>
     * <b>Example set properties of ML035: </b>
     * <p>ml035Vo.setContactMailAddress(contactMailAddress);</p>
     * <p>ml035Vo.setContactName(contactName);</p>
     * <p>ml035Vo.setPasswordPeriod(passwordPeriod);</p>
     * <p>ml035Vo.setPassword(password);</p>
     * @param <T> is class type that instance of BaseMailVo
     * @param type
     * @return T
     * @throws BusinessException
     * @author tqvu1
     */
    <T extends BaseMailVo> T getCommonTemplateMail(Class<T> type) throws BusinessException;

}
