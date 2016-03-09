package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/18.
 */
public class PlateArrayBean {
    private String puuId;
    private String plateName;
    private String plateRemark;
    private int studyNumber;
    private String studyJd;
    private int isFinish;

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getPuuId() {
        return puuId;
    }

    public void setPuuId(String puuId) {
        this.puuId = puuId;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getPlateRemark() {
        return plateRemark;
    }

    public void setPlateRemark(String plateRemark) {
        this.plateRemark = plateRemark;
    }

    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public String getStudyJd() {
        return studyJd;
    }

    public void setStudyJd(String studyJd) {
        this.studyJd = studyJd;
    }

    @Override
    public String toString() {
        return "PlateArrayBean{" +
                "puuId='" + puuId + '\'' +
                ", plateName='" + plateName + '\'' +
                ", plateRemark='" + plateRemark + '\'' +
                ", studyNumber=" + studyNumber +
                ", studyJd='" + studyJd + '\'' +
                ", isFinish=" + isFinish +
                '}';
    }
}
