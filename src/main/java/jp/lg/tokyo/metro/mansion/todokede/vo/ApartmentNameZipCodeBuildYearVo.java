/*
 * @(#) ApartmentZipcodeBuildYearVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ApartmentNameZipCodeBuildYearVo {
    
    private String ApartmentName;
    
    private String ZipCode;
    
    private String BuildYear;

    public String getApartmentId() {
        return ApartmentName;
    }

    public void setApartmentId(String apartmentId) {
        ApartmentName = apartmentId;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getBuildYear() {
        return BuildYear;
    }

    public void setBuildYear(String buildYear) {
        BuildYear = buildYear;
    }
    
    public ApartmentNameZipCodeBuildYearVo() {}

    public ApartmentNameZipCodeBuildYearVo(String apartmentName, String zipCode, String buildYear) {
        ApartmentName = apartmentName;
        ZipCode = zipCode;
        BuildYear = buildYear;
    }
    
    
}
