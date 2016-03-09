package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/18.
 */
public class CourseBean {
    private String cuuId;
    private String courseName;
    private String remark;
    private String startTime;
    private String endTime;
    private int studyNumber;
    private String studyJd;
    private int getCirtification;
    private double point;
    private String hasTime;
    private List<PlateArrayBean> plateArray;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getHasTime() {
        return hasTime;
    }

    public void setHasTime(String hasTime) {
        this.hasTime = hasTime;
    }

    public String getCuuId() {
        return cuuId;
    }

    public void setCuuId(String cuuId) {
        this.cuuId = cuuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public List<PlateArrayBean> getPlateArray() {
        return plateArray;
    }

    public void setPlateArray(List<PlateArrayBean> plateArray) {
        this.plateArray = plateArray;
    }


    public int getGetCirtification() {
        return getCirtification;
    }

    public void setGetCirtification(int getCirtification) {
        this.getCirtification = getCirtification;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public CourseBean(String cuuId, String courseName, String remark, String startTime, String endTime, int studyNumber, String studyJd, int getCirtification, double point, String hasTime, List<PlateArrayBean> plateArray) {
        this.cuuId = cuuId;
        this.courseName = courseName;
        this.remark = remark;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studyNumber = studyNumber;
        this.studyJd = studyJd;
        this.getCirtification = getCirtification;
        this.point = point;
        this.hasTime = hasTime;
        this.plateArray = plateArray;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "cuuId='" + cuuId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", remark='" + remark + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", studyNumber=" + studyNumber +
                ", studyJd='" + studyJd + '\'' +
                ", getCirtification=" + getCirtification +
                ", point=" + point +
                ", hasTime=" + hasTime +
                ", plateArray=" + plateArray +
                '}';
    }
}
