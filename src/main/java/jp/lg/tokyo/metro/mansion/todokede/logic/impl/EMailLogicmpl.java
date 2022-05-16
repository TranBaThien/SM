/*
 * @(#)EMailServiceImpl.java 01-00 2008/01/09
 *
 * Copyright(C) 2007 by Hitachi Information Systems CO., LTD
 *
 * Last_Update 2008/01/09
 * Version 1.00
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM002DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM005DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM005Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMailVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.EMailInfoVo;

/**
 * <p>
 * Implements of Email service.
 * </p>
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 * @see EMailService
 */
@SuppressWarnings("deprecation")
@Service
public class EMailLogicmpl implements IEMailLogic, Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger(EMailLogicmpl.class);

	/**
     * VelocityEngine object.
     */
	@Autowired
    private transient VelocityEngine velocityEngine;

    /**
     * JavaMailSender object.
     */
    @Autowired
    private transient JavaMailSender mailSender;

    @Autowired
    private TBM005DAO tbm005DAO;

    @Autowired
    private TBM002DAO tbm002DAO;

    @Autowired
    private TBM001DAO tbm001DAO;

    private int retryCount;
    private long retryInterval;

    /**
     * <p>
     * Set value to JavaMailSender object.
     * </p>
     * @param mailSender JavaMailSender
     */
    public void setMailSender(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    /**
     * <p>
     * Set value to VelocityEngine object.
     * </p>
     * @param velocityEngine VelocityEngine
     */
    public void setVelocityEngine(VelocityEngine velocityEngine) {

        this.velocityEngine = velocityEngine;
    }

    /**
     * リトライ回数を取得します。
     * @return リトライ回数
     */
    public int getRetryCount() {
		return retryCount;
	}

    /**
     * リトライ回数を設定します。
     * @param retryCount リトライ回数<
     */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * リトライ間隔を取得します。
	 * @return リトライ間隔
	 */
	public long getRetryInterval() {
		return retryInterval;
	}

	/**
	 * リトライ間隔を設定します。<
	 * @param retryInterval リトライ間隔
	 */
	public void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}

    /**
     * <p>
     * Send mail.
     * </p>
     * @param strTempFileName テンプレートのファイル名
     * @param emailInfo メール情報
     * @param content Object オブジェクト
     * @param attachedFiles 添付ファイル
     * @throws MailException MailException
     * @param strTempFileName
     * @param emailInfo
     * @param content Object Object
     * @param attachedFiles
     * @throws MailException MailException
     */
    public void sendMail(final String strTempFileName,
                    final EMailInfoVo emailInfo, final Object content,
                    final List<File> attachedFiles) throws MailException {
        LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        // Create inner class MimeMessagePreparator
        MimeMessagePreparator preperator = new MimeMessagePreparator() {

            /**
             * prepare.
             * @param mm MimeMessage
             * throws Exception
             * @implements MimeMessagePreparator.prepare
             */
            public void prepare(MimeMessage mm) throws Exception {
            	if (attachedFiles == null) {
            		// Create MimeMessageHelper instance
                    MimeMessageHelper message = new MimeMessageHelper(mm, false);

                    // Set mail info for MimeMessageHelper
                    setMailInfo(message, emailInfo);
                    setAttachment(message, attachedFiles);
                    String emailContent = createContent(strTempFileName, content);
                    String match = "\\$\\{CONTENT\\.[a-zA-Z0-9]*\\}";
                    emailContent = emailContent.replaceAll(match, "");
                    // Set text with utf8 encoding and text/html format
                    message.setText(convertToUtf8(emailContent), true);
            	} else {
            		// Create MimeMessageHelper instance
                    MimeMessageHelper message = new MimeMessageHelper(mm, true);

                    // Set mail info for MimeMessageHelper
                    setMailInfo(message, emailInfo);
                    setAttachment(message, attachedFiles);
                    String emailContent = createContent(strTempFileName, content);
                    String match = "\\$\\{CONTENT\\.[a-zA-Z0-9]*\\}";
                    emailContent = emailContent.replaceAll(match, "");
                    // Set text with utf8 encoding and text/html format
                    message.setText(convertToUtf8(emailContent), true);
            	}
            }
        };

        // Call send method of JavaMailSender
        try{
        	this.mailSender.send(preperator);
        } catch(MailSendException e){
        	//規定の回数再実行を実施する。
        	final int maxRetryCount = this.getRetryCount();
        	// 接続エラー以外はリトライしない
        	final String connectErrorMessage = "Mail server connection failed";

        	if (!e.getMessage().startsWith(connectErrorMessage)) {
        		throw e;
        	}
        	int retryCount = 1;
        	//再送回数
        	if( retryCount > maxRetryCount) {
        		//再送は実施しない（maxRetryCount=0の場合）ため、そのまま例外スロー
        		throw e;
        	}

        	while (true){
        		//送信成功するか、再送規定回数を超えるまで繰り返す
        		try{
        			try {
						Thread.sleep(this.getRetryInterval());
					} catch (Exception e1) {
					}
        			if (LOG.isDebugEnabled()) {
        				LOG.debug("メール再送を実施します。（" + retryCount + "回目）");
        			}
        			this.mailSender.send(preperator);
        		} catch(MailSendException e2){
                	// 接続エラー以外はリトライしない
        			if (!e2.getMessage().startsWith(connectErrorMessage)) {
        				LOG.info("メール送信に失敗しました。再送"+ retryCount + "回にてエラーになりました。");
                		throw e2;
                	}
        			//再送失敗　再リトライ判定を実施する
        			retryCount++;
        			//再送回数
        			if( retryCount > maxRetryCount){
        				LOG.info("メール送信に失敗しました。");
        				throw e2;
        			}
        			continue;
        		}
        		//ログ出力処理を実装
        		LOG.info("メール送信に失敗しました。再送"+ retryCount + "回にて送信成功しました");
        		break;
        	}
        }
        LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * <p>
     * Set mail information.
     * </p>
     * @param message MimeMessageHelper
     * @param emailInfo メール情報
     * @throws javax.mail.MessagingException MessagingException
     */
    private void setMailInfo(MimeMessageHelper message, EMailInfoVo emailInfo)
                    throws MessagingException {

        if (emailInfo.getFrom() == null) {
            message.setFrom("");
        } else {
            message.setFrom(emailInfo.getFrom());
        }

        if (emailInfo.getTo() == null) {
            message.setTo("");
        } else {
            message.setTo(emailInfo.getTo());
        }

        if (emailInfo.getCc() != null) {
            message.setCc(emailInfo.getCc());
        }

        if (emailInfo.getBcc() != null) {
            message.setBcc(emailInfo.getBcc());
        }

        if (emailInfo.getSubject() == null) {
            message.setSubject("");
        } else {
            message.setSubject(emailInfo.getSubject());
        }

        if (emailInfo.getReplyTo() != null) {
            message.setReplyTo(emailInfo.getReplyTo());
        }
    }

    /**
     * <p>
     * Set Attachment.
     * </p>
     * @param message MimeMessageHelper
     * @param attachedFiles 添付ファイル
     * @param attachedFiles File đinh kem
     * @throws javax.mail.MessagingException MessagingException
     */
    private void setAttachment(MimeMessageHelper message,
                    List<File> attachedFiles) throws MessagingException {

        if (attachedFiles != null) {
            // foreach file in attachedFiles, add to message
            for (Iterator<File> iter = attachedFiles.iterator(); iter.hasNext();) {
                File element = iter.next();
                // Add attached file to message
                message.addAttachment(element.getName(),
                                new FileDataSource(element));
            }
        }
    }

    /**
     * <p>
     * Create content of email.
     * </p>
     * @param strTempFileName テンプレートのファイル名
     * @param strTempFileName Ten file template
     * @param content Content
     * @return content String
     * @throws java.io.IOException IOException
     */
	private String createContent(String strTempFileName, Object content)
            throws IOException {

        // Initializing String writer
        StringWriter sw = new StringWriter();
        Map<String,Object> contentMap = new HashMap<String,Object>();

        // Put content object to content map
        contentMap.put(CommonConstants.EMAIL_CONTENT_KEY, content);

        // Merge template file with content map
        VelocityContext context = new VelocityContext((Map) content);
        this.velocityEngine.mergeTemplate("templates/email/" + strTempFileName, CommonConstants.ENCODE_UTF8, context, sw);

        // Close String Writer object
        sw.close();
        return sw.toString();
    }
    /**
     * Use to convert some character which can not display in e-mail
     * @param convert string need to convert
     * @return string is converted
     */
    public static  String convertToUtf8(String convert) {

    	convert = convert.replace('\u2015', '\u2014'); //―(EM DASH)
    	convert = convert.replace('\uff5e', '\u301c'); //～(WAVE DASH)
    	convert = convert.replace('\u2225', '\u2016'); //∥(DOUBLE VERTICAL LINE)
    	convert = convert.replace('\uff0d', '\u2212'); //～(WAVE DASH)
    	convert = convert.replace('\uffe0', '\u00a2'); //～(WAVE DASH)
    	convert = convert.replace('\uffe1', '\u00a3'); //～(WAVE DASH)
    	convert = convert.replace('\uffe2', '\u00ac'); //～(WAVE DASH)

    	return convert;
    }

    @Override
    public  <T extends BaseMailVo> T getCommonTemplateMail(Class<T> type) throws BusinessException {
        LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String usedCode = null;
        String cityCode = null;
        String className = type.getSimpleName();

        try {
            switch (className) {
                case "ML010Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_USER_INFO_NOTI_EMAIL);
                    cityCode = tbm001DAO.getCityCodeByCityName(CommonConstants.CITY_NAME_TOKYO);
                    break;
                case "ML020Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_EXAM_RESULT_NOTI_EMAIL);
                    cityCode = tbm001DAO.getCityCodeByCityName(CommonConstants.CITY_NAME_TOKYO);
                    break;
                case "ML030Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_PASSWORD_REISSUE_NOTI_EMAIL_FOR_MA);
                    cityCode = tbm001DAO.getCityCodeByCityName(CommonConstants.CITY_NAME_TOKYO);
                    break;
                case "ML035Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_PASSWORD_REISSUE_NOTI_EMAIL_FOR_CITY);
                    cityCode = tbm001DAO.getCityCodeByCityName(CommonConstants.CITY_NAME_TOKYO);
                    break;
                case "ML040Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_NOTI_ACCEPT_EMAIL);
                    cityCode = SecurityUtil.getUserLoggedInInfo().getCityCode();
                    break;
                case "ML050Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_ADVICE_NOTI_EMAIL);
                    cityCode = SecurityUtil.getUserLoggedInInfo().getCityCode();
                    break;
                case "ML060Vo":
                    usedCode = CodeUtil.getValue(CommonConstants.CD046, CommonConstants.CD046_FOR_SURVEY_NOTI_EMAIL);
                    cityCode = SecurityUtil.getUserLoggedInInfo().getCityCode();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOG.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }

        BaseMailVo baseMailVo = new BaseMailVo();

        TBM005Entity tbm005 = tbm005DAO.getTemplateMailByUsedCode(usedCode);
        if (tbm005 != null) {

            baseMailVo.setMailTemplate(tbm005.getMailTemplate());
            baseMailVo.setMailSubject(tbm005.getMailSubject());
            baseMailVo.setMailSendFrom(tbm005.getMailSendFrom());
            baseMailVo.setMailReplyTo(tbm005.getMailReplyTo());

            TBM002Entity tbm002 = tbm002DAO.getWindowInformation(cityCode, usedCode);
            if (tbm002 != null) {
                baseMailVo.setCityName(tbm002.getWindowCityName());
                baseMailVo.setWindowBelong(tbm002.getWindowBelong());
                baseMailVo.setWindowTelNo(tbm002.getWindowTelNo());
                baseMailVo.setWindowFaxNo(tbm002.getWindowFaxNo());
                baseMailVo.setWindowMailAddress(tbm002.getWindowMailAddress());
            } else {
                throw new BusinessException(
                        String.format("No data for table TBM002 with cityCode= %s, usedCode= %s", cityCode, usedCode));
            }
        } else {
            throw new BusinessException(String.format("No data for table TBM005 with usedCode = %s", usedCode));
        }

        try {
            Constructor<?> cons = type.getDeclaredConstructor();
            cons.setAccessible(true);
            Object obj = cons.newInstance();
            CommonUtils.copyProperties(obj, baseMailVo);
            LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return type.cast(obj);
        } catch (Exception e) {
            LOG.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

    @Override
    public void sendMailWithContent(String strEmailContent, EMailInfoVo emailInfo, Object content, List<File> attachedFiles)
            throws MailException {
        LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        // Create inner class MimeMessagePreparator
        MimeMessagePreparator preperator = new MimeMessagePreparator() {

            /**
             * prepare.
             * @param mm MimeMessage
             * throws Exception
             * @implements MimeMessagePreparator.prepare
             */
            public void prepare(MimeMessage mm) throws Exception {
                if (attachedFiles == null) {
                    // Create MimeMessageHelper instance
                    MimeMessageHelper message = new MimeMessageHelper(mm, false);

                    // Set mail info for MimeMessageHelper
                    setMailInfo(message, emailInfo);
                    setAttachment(message, attachedFiles);
                    String emailContent = createContentFromMailTemplate(strEmailContent, content);
                    String match = "\\$\\{CONTENT\\.[a-zA-Z0-9]*\\}";
                    emailContent = emailContent.replaceAll(match, "");
                    emailContent = emailContent.replaceAll("(\\r\\n|\\n)", "<br/>");
                    // Set text with utf8 encoding and text/html format
                    message.setText(convertToUtf8(emailContent), true);
                } else {
                    // Create MimeMessageHelper instance
                    MimeMessageHelper message = new MimeMessageHelper(mm, false);

                    // Set mail info for MimeMessageHelper
                    setMailInfo(message, emailInfo);
                    setAttachment(message, attachedFiles);
                    String emailContent = createContentFromMailTemplate(strEmailContent, content);
                    String match = "\\$\\{CONTENT\\.[a-zA-Z0-9]*\\}";
                    emailContent = emailContent.replaceAll(match, "");
                    emailContent = emailContent.replaceAll("(\\r\\n|\\n)", "<br/>");
                    // Set text with utf8 encoding and text/html format
                    message.setText(convertToUtf8(emailContent), true);
                }
            }
        };

        // Call send method of JavaMailSender
        try{
            this.mailSender.send(preperator);
        } catch(MailSendException e){
            //規定の回数再実行を実施する。
            final int maxRetryCount = this.getRetryCount();
            // 接続エラー以外はリトライしない
            final String connectErrorMessage = "Mail server connection failed";

            if (!e.getMessage().startsWith(connectErrorMessage)) {
                throw e;
            }
            int retryCount = 1;
            //再送回数
            if( retryCount > maxRetryCount) {
                //再送は実施しない（maxRetryCount=0の場合）ため、そのまま例外スロー
                throw e;
            }

            while (true){
                //送信成功するか、再送規定回数を超えるまで繰り返す
                try{
                    try {
                        Thread.sleep(this.getRetryInterval());
                    } catch (Exception e1) {
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("メール再送を実施します。（" + retryCount + "回目）");
                    }
                    this.mailSender.send(preperator);
                } catch(MailSendException e2){
                    // 接続エラー以外はリトライしない
                    if (!e2.getMessage().startsWith(connectErrorMessage)) {
                        LOG.info("メール送信に失敗しました。再送"+ retryCount + "回にてエラーになりました。");
                        throw e2;
                    }
                    //再送失敗　再リトライ判定を実施する
                    retryCount++;
                    //再送回数
                    if( retryCount > maxRetryCount){
                        LOG.info("メール送信に失敗しました。");
                        throw e2;
                    }
                    continue;
                }
                //ログ出力処理を実装
                LOG.info("メール送信に失敗しました。再送"+ retryCount + "回にて送信成功しました");
                break;
            }
        }
        LOG.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * <p>
     * Create content of email.
     * </p>
     * @param strTempFileName テンプレートのファイル名
     * @param strTempFileName Ten file template
     * @param content Content
     * @return content String
     * @throws java.io.IOException IOException
     */
    private String createContentFromMailTemplate(String strMailContent, Object content)
            throws IOException {

        // replace jp variable with en variable
        strMailContent = replaceJapanVariable(strMailContent);

        // Initializing String writer
        StringWriter sw = new StringWriter();
        Map<String,Object> contentMap = new HashMap<String,Object>();

        // Put content object to content map
        contentMap.put(CommonConstants.EMAIL_CONTENT_KEY, content);

        // Merge template file with content map
        VelocityContext context = new VelocityContext((Map) content);
        this.velocityEngine.evaluate(context, sw, "", strMailContent);

        // Close String Writer object
        sw.close();
        return sw.toString();
    }

    private String replaceJapanVariable(String strMailContent) {
        strMailContent = strMailContent.replace("｛", "{");
        strMailContent = strMailContent.replace("｝", "}");
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_CITY_NAME_JP), wrapVariable(CommonConstants.ML_CITY_NAME));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_WINDOW_BELONG_TO_JP), wrapVariable(CommonConstants.ML_WINDOW_BELONG_TO));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_WINDOW_TEL_NO_JP), wrapVariable(CommonConstants.ML_WINDOW_TEL_NO));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_WINDOW_FAX_NO_JP), wrapVariable(CommonConstants.ML_WINDOW_FAX_NO));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_WINDOW_MAIL_ADDRESS_JP), wrapVariable(CommonConstants.ML_WINDOW_MAIL_ADDRESS));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_APARTMENT_NAME_JP), wrapVariable(CommonConstants.ML_APARTMENT_NAME));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_CONTACT_NAME_JP), wrapVariable(CommonConstants.ML_CONTACT_NAME));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_LOGIN_URL_JP), wrapVariable(CommonConstants.ML_LOGIN_URL));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_PASSWORD_PERIOD_JP), wrapVariable(CommonConstants.ML_PASSWORD_PERIOD));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_PASSWORD_JP), wrapVariable(CommonConstants.ML_PASSWORD));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_LOGIN_ID_JP), wrapVariable(CommonConstants.ML_LOGIN_ID));
        strMailContent = strMailContent.replace(wrapVariable(CommonConstants.ML_JUDGE_REMARKS_JP), wrapVariable(CommonConstants.ML_JUDGE_REMARKS));
        return strMailContent;
    }

    private String wrapVariable(String variable) {
        return "${" + variable + "}";
    }

}
