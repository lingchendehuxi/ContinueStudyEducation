package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/23.
 */
public class LessonInfoBean {
    private String courseRemark;
    private int studyNumber;
    private String hasTime;
    private String courseName;
    private List<TeacherArrayBean> teacherArray;

    public List<TeacherArrayBean> getTeacherArray() {
        return teacherArray;
    }

    public void setTeacherArray(List<TeacherArrayBean> teacherArray) {
        this.teacherArray = teacherArray;
    }

    public String getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark;
    }

    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public String getHasTime() {
        return hasTime;
    }

    public void setHasTime(String hasTime) {
        this.hasTime = hasTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "LessonInfoBean{" +
                "courseRemark='" + courseRemark + '\'' +
                ", studyNumber=" + studyNumber +
                ", hasTime=" + hasTime +
                ", courseName='" + courseName + '\'' +
                ", teacherArray=" + teacherArray +
                '}';
    }
}
