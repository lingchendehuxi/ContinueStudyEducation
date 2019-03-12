package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/23.
 */
public class LessonInfoBean {

    /**
     * studyNumber : 4352
     * state : 1
     * courseName : 重症检测的患者安全
     * courseRemark : 通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮助医生更好地进行临床诊断和治疗，维护患者安全。
     * courseType : 1
     * hasTimeStr : 10天后结束
     * teacherCount : 5
     * plateArray : [{"plateName":"板块1：管理质量认证"},{"plateName":"板块2：质量控制"},{"plateName":"板块3：风险管理"},{"plateName":"板块4：错误预防"},{"plateName":"板块5：氧合监测的方法和临床意义"}]
     * teacherArray : [{"teacherName":"邱海波","teacherRemark":"邱海波，现任东南大学附属中大医院副院长，博士生导师，特聘教授（二级)，中华医学会重症医学分会第三届主任委员，中国医师协会重症医师分会副会长，江苏省重症医学会主任委员，中华重症医学电子杂志总编辑，中华危重病急救医学、中国呼吸与重症监护杂志副总编辑，Annals of Intensive Care, associated editor。","teacherUrl":"http://114.80.200.3:80/source/file/images/2016-09-28_1475057754934.jpg","shenfen":"教授"}]
     */

    private int studyNumber;
    private int state;
    private String courseName;
    private String courseRemark;
    private int courseType;
    private String hasTimeStr;
    private int teacherCount;
    private List<PlateArrayBean> plateArray;
    private List<TeacherArrayBean> teacherArray;

    public int getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(int studyNumber) {
        this.studyNumber = studyNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getHasTimeStr() {
        return hasTimeStr;
    }

    public void setHasTimeStr(String hasTimeStr) {
        this.hasTimeStr = hasTimeStr;
    }

    public int getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(int teacherCount) {
        this.teacherCount = teacherCount;
    }

    public List<PlateArrayBean> getPlateArray() {
        return plateArray;
    }

    public void setPlateArray(List<PlateArrayBean> plateArray) {
        this.plateArray = plateArray;
    }

    public List<TeacherArrayBean> getTeacherArray() {
        return teacherArray;
    }

    public void setTeacherArray(List<TeacherArrayBean> teacherArray) {
        this.teacherArray = teacherArray;
    }

    public static class PlateArrayBean {
        /**
         * plateName : 板块1：管理质量认证
         */

        private String plateName;

        public String getPlateName() {
            return plateName;
        }

        public void setPlateName(String plateName) {
            this.plateName = plateName;
        }
    }

    public static class TeacherArrayBean {
        /**
         * teacherName : 邱海波
         * teacherRemark : 邱海波，现任东南大学附属中大医院副院长，博士生导师，特聘教授（二级)，中华医学会重症医学分会第三届主任委员，中国医师协会重症医师分会副会长，江苏省重症医学会主任委员，中华重症医学电子杂志总编辑，中华危重病急救医学、中国呼吸与重症监护杂志副总编辑，Annals of Intensive Care, associated editor。
         * teacherUrl : http://114.80.200.3:80/source/file/images/2016-09-28_1475057754934.jpg
         * shenfen : 教授
         */

        private String teacherName;
        private String teacherRemark;
        private String teacherUrl;
        private String shenfen;

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

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

        public String getShenfen() {
            return shenfen;
        }

        public void setShenfen(String shenfen) {
            this.shenfen = shenfen;
        }
    }
}
