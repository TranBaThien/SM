package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl205 database table.
 * 
 */
@Embeddable
public class TBL205EntityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TEMP_NO", unique=true, nullable=false, length=10)
	private String tempNo;

	@Column(name="APARTMENT_ID", unique=true, nullable=false, length=10)
	private String apartmentId;

	public TBL205EntityPK() {
	}

	public TBL205EntityPK(String tempNo, String apartmentId) {
		this.tempNo = tempNo;
		this.apartmentId = apartmentId;
	}

    public String getTempNo() {
		return this.tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
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
		if (!(other instanceof TBL205EntityPK)) {
			return false;
		}
		TBL205EntityPK castOther = (TBL205EntityPK)other;
		return 
			this.tempNo.equals(castOther.tempNo)
			&& this.apartmentId.equals(castOther.apartmentId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tempNo.hashCode();
		hash = hash * prime + this.apartmentId.hashCode();
		
		return hash;
	}

    @Override
    public String toString() {
        return "tempNo=" + tempNo + ", apartmentId=" + apartmentId;
    }
}