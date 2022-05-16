/*
 * @(#)EMailInfoVo.java
 *
 * Copyright(C) 2007 by Hitachi Information Systems CO., LTD
 *
 * Last_Update 2008/01/09
 * Version 1.00
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * <p>
 * メール情報.
 * </p>
 * @author PVHung3
 * @version 1.7
 */
public class EMailInfoVo {

    /**
     * To.
     */
    private String[] to;

    /**
     * From.
     */
    private String from;

    /**
     * Subject.
     */
    private String subject;

    /**
     * CC.
     */
    private String[] cc;

    /**
     * BCC.
     */
    private String[] bcc;
    
    /**
     * Reply-to.
     */
    private String replyTo;

	/**
     * get BCC.
     * @return Bcc
     */
    public String[] getBcc() {
        return this.bcc;
    }

    /**
     * set BCC.
     * @param bcc BCC
     */
    public void setBcc(String[] bcc) {

        this.bcc = bcc;
    }

    /**
     * get CC.
     * @return Cc
     */
    public String[] getCc() {

        return this.cc;
    }

    /**
     * set CC.
     * @param cc CC
     */
    public void setCc(String[] cc) {

        this.cc = cc;
    }

    /**
     * get From.
     * @return From
     */
    public String getFrom() {

        return from;
    }

    /**
     * set From.
     * @param from From
     */
    public void setFrom(String from) {

        this.from = from;
    }

    /**
     * get Subject.
     * @return Subject
     */
    public String getSubject() {

        return subject;
    }

    /**
     * set Subject.
     * @param subject Subject
     */
    public void setSubject(String subject) {

        this.subject = subject;
    }

    /**
     * get To.
     * @return To
     */
    public String[] getTo() {

        return to;
    }

    /**
     * set To.
     * @param to To
     */
    public void setTo(String[] to) {

        this.to = to;
    }
    
	/**
     * get Reply-to.
     * @return Reply-to
     */
    public String getReplyTo() {
		return this.replyTo;
	}

    /**
     * set Reply-to.
     * @param replyTo Reply-to
     */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
}
