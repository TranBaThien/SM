package jp.lg.tokyo.metro.mansion.todokede.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the tbl200 database table.
 * 
 */
@Embeddable
public class TBL200EntityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NOTIFICATION_NO", unique=true, nullable=false, length=10)
	private String notificationNo;

	@Column(name="APARTMENT_ID", unique=true, nullable=false, length=10)
	private String apartmentId;

	public TBL200EntityPK() {
	}

	public TBL200EntityPK(String notificationNo, String apartmentId) {
		this.notificationNo = notificationNo;
		this.apartmentId = apartmentId;
	}

	public String getNotificationNo() {
		return this.notificationNo;
	}
	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}
	public String getApartmentId() {
		return this.apartmentId;
	}
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TBL200EntityPK)) {
			return false;
		}
		TBL200EntityPK castOther = (TBL200EntityPK)other;
		return 
			this.notificationNo.equals(castOther.notificationNo)
			&& this.apartmentId.equals(castOther.apartmentId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.notificationNo.hashCode();
		hash = hash * prime + this.apartmentId.hashCode();
		
		return hash;
	}

    @Override
    public String toString() {
        return "notificationNo=" + notificationNo + ", apartmentId=" + apartmentId;
    }
}