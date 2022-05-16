package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbm002 database table.
 * 
 */
@Embeddable
public class TBM002EntityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CITY_CODE", unique=true, nullable=false, length=6)
	private String cityCode;

	@Column(name="USED_CODE", unique=true, nullable=false, length=2)
	private String usedCode;

	public TBM002EntityPK() {
	}
	public String getCityCode() {
		return this.cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getUsedCode() {
		return this.usedCode;
	}
	public void setUsedCode(String usedCode) {
		this.usedCode = usedCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TBM002EntityPK)) {
			return false;
		}
		TBM002EntityPK castOther = (TBM002EntityPK)other;
		return 
			this.cityCode.equals(castOther.cityCode)
			&& this.usedCode.equals(castOther.usedCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cityCode.hashCode();
		hash = hash * prime + this.usedCode.hashCode();
		
		return hash;
	}

    @Override
    public String toString() {
        return "cityCode=" + cityCode + ", usedCode=" + usedCode;
    }
}