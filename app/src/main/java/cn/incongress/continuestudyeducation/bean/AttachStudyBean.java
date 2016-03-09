package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/21.
 */
public class AttachStudyBean {
    private String teacherRemark;
    private String nextLongTime;
    private String picUrl;
    private int hasNext;
    private String teacherUrl;
    private String videoUrl;
    private String coursewareTitle;
    private String nextCwuuId;
    private String nextCoursewareTitle;
    private String nextTeacher;
    private String teacher;
    private String longTime;
    private int isFinish;
    private int nextIsFinish;

    public String getTeacherUrl() {
        return teacherUrl;
    }

    public void setTeacherUrl(String teacherUrl) {
        this.teacherUrl = teacherUrl;
    }

    public String getTeacherRemark() {
        return teacherRemark;
    }

    public void setTeacherRemark(String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }

    public String getNextLongTime() {
        return nextLongTime;
    }

    public void setNextLongTime(String nextLongTime) {
        this.nextLongTime = nextLongTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getHasNext() {
        return hasNext;
    }

    public void setHasNext(int hasNext) {
        this.hasNext = hasNext;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoursewareTitle() {
        return coursewareTitle;
    }

    public void setCoursewareTitle(String coursewareTitle) {
        this.coursewareTitle = coursewareTitle;
    }

    public String getNextCwuuId() {
        return nextCwuuId;
    }

    public void setNextCwuuId(String nextCwuuId) {
        this.nextCwuuId = nextCwuuId;
    }

    public String getNextCoursewareTitle() {
        return nextCoursewareTitle;
    }

    public void setNextCoursewareTitle(String nextCoursewareTitle) {
        this.nextCoursewareTitle = nextCoursewareTitle;
    }

    public String getNextTeacher() {
        return nextTeacher;
    }

    public void setNextTeacher(String nextTeacher) {
        this.nextTeacher = nextTeacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }


    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getNextIsFinish() {
        return nextIsFinish;
    }

    public void setNextIsFinish(int nextIsFinish) {
        this.nextIsFinish = nextIsFinish;
    }

    public AttachStudyBean() {
    }

    @Override
    public String toString() {
        return "AttachStudyBean{" +
                "teacherRemark='" + teacherRemark + '\'' +
                ", nextLongTime='" + nextLongTime + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", hasNext=" + hasNext +
                ", teacherUrl='" + teacherUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", coursewareTitle='" + coursewareTitle + '\'' +
                ", nextCwuuId='" + nextCwuuId + '\'' +
                ", nextCoursewareTitle='" + nextCoursewareTitle + '\'' +
                ", nextTeacher='" + nextTeacher + '\'' +
                ", teacher='" + teacher + '\'' +
                ", longTime='" + longTime + '\'' +
                ", isFinish=" + isFinish +
                ", nextIsFinish=" + nextIsFinish +
                '}';
    }
}
