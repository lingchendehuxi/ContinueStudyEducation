package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/23.
 */
public class TeacherArrayBean {
    private String teacherRemark;
    private String teacherUrl;
    private String teacherName;

    public String getTeacherRemark() {
        return teacherRemark;
    }

    public void setTeacherRemark(String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }

    public String getTeacherUrl() {
        return teacherUrl;
    }

    public void setTeacherUrl(String teacherUrl) {
        this.teacherUrl = teacherUrl;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "TeacherArrayBean{" +
                "teacherRemark='" + teacherRemark + '\'' +
                ", teacherUrl='" + teacherUrl + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
