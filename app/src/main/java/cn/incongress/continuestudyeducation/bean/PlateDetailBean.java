package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/21.
 */
public class PlateDetailBean {
    private String startTime;
    private String studyJd;
    private String plateRemark;
    private String plateName;
    private int studyNumber;
    private String endTime;
    private int hasTime;
    private List<CoursewareArrayBean> coursewareArray;

    public int getHasTime() {
        return hasTime;
    }

    public void setHasTime(int hasTime) {
        this.hasTime = hasTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStudyJd() {
        return studyJd;
    }

    public void setStudyJd(String studyJd) {
        this.studyJd = studyJd;
    }

    public String getPlateRemark() {
        return plateRemark;
    }

    public void setPlateRemark(String plateRemark) {
        this.plateRemark = plateRemark;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<CoursewareArrayBean> getCoursewareArray() {
        return coursewareArray;
    }

    public void setCoursewareArray(List<CoursewareArrayBean> coursewareArray) {
        this.coursewareArray = coursewareArray;
    }

    @Override
    public String toString() {
        return "PlateDetailBean{" +
                "startTime='" + startTime + '\'' +
                ", studyJd='" + studyJd + '\'' +
                ", plateRemark='" + plateRemark + '\'' +
                ", plateName='" + plateName + '\'' +
                ", studyNumber=" + studyNumber +
                ", endTime='" + endTime + '\'' +
                ", hasTime=" + hasTime +
                ", coursewareArray=" + coursewareArray +
                '}';
    }
}
