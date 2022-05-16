package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbm005 database table.
 * 
 */
@Entity
@Table(name="tbm005")
public class TBM005Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USED_CODE", unique=true, nullable=false, length=2)
	private String usedCode;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="MAIL_REPLY_TO", length=120)
	private String mailReplyTo;

	@Column(name="MAIL_SEND_FROM", length=120)
	private String mailSendFrom;

	@Column(name="MAIL_SUBJECT", length=80)
	private String mailSubject;

	@Column(name="MAIL_TEMPLATE", length=3000)
	private String mailTemplate;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBM005Entity() {
	}

	public String getUsedCode() {
		return this.usedCode;
	}

	public void setUsedCode(String usedCode) {
		this.usedCode = usedCode;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getMailReplyTo() {
		return this.mailReplyTo;
	}

	public void setMailReplyTo(String mailReplyTo) {
		this.mailReplyTo = mailReplyTo;
	}

	public String getMailSendFrom() {
		return this.mailSendFrom;
	}

	public void setMailSendFrom(String mailSendFrom) {
		this.mailSendFrom = mailSendFrom;
	}

	public String getMailSubject() {
		return this.mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailTemplate() {
		return this.mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
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

}