package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/21.
 */
public class CoursewareArrayBean {
    private String teacher;
    private String cwuuId;
    private String coursewareTitle;
    private String longTime;
    private String picUrl;
    private int isFinish;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCwuuId() {
        return cwuuId;
    }

    public void setCwuuId(String cwuuId) {
        this.cwuuId = cwuuId;
    }

    public String getCoursewareTitle() {
        return coursewareTitle;
    }

    public void setCoursewareTitle(String coursewareTitle) {
        this.coursewareTitle = coursewareTitle;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
}
