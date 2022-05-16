package jp.lg.tokyo.metro.mansion.todokede.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="tbm004")
public class TBM004Entity {

    @Id
    @Column(name="SET_NO", unique=true, nullable=false, length=6)
    private String setNo;

    @Column(name = "SET_TARGET_NAME_JP", length = 100)
    private String setTargetNameJp;

    @Column(name = "SET_TARGET_NAME_ENG", length = 100)
    private String setTargetNameEng;

    @Column(name = "SET_POINT", length = 100)
    private String setPoint;

    @Column(name = "COMMENT", length = 300)
    private String comment;

    @Column(name = "DELETE_FLAG", length = 1)
    private String deleteFlag;

    @Column(name = "UPDATE_USER_ID", length = 10)
    private String updateUserId;

    @Column(name = "UPDATE_DATETIME")
    private LocalDateTime updateDateTime;

    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    public String getSetTargetNameJp() {
        return setTargetNameJp;
    }

    public void setSetTargetNameJp(String setTargetNameJp) {
        this.setTargetNameJp = setTargetNameJp;
    }

    public String getSetTargetNameEng() {
        return setTargetNameEng;
    }

    public void setSetTargetNameEng(String setTargetNameEng) {
        this.setTargetNameEng = setTargetNameEng;
    }

    public String getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(String setPoint) {
        this.setPoint = setPoint;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
