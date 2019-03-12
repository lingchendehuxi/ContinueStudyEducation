package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Admin on 2017/6/8.
 */

public class MyCertificateBean {

    /**
     * state : 1
     * jsonArray : [{"courseName":"重症检测的患者安全","courseRemark":"通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮助医生更好地进行临床诊断和治疗，维护患者安全。","teacherName":"邱海波,管向东,严静,许媛,曹相原","imgUrl":"http://192.168.0.123:8080/files/zsFile/debcnohiw107r5px/13/certificate.jpg","zsState":2}]
     */

    private int state;
    private List<CertificateArrayBean> jsonArray;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<CertificateArrayBean> getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(List<CertificateArrayBean> jsonArray) {
        this.jsonArray = jsonArray;
    }

    public static class CertificateArrayBean {
        /**
         * courseName : 重症检测的患者安全
         * courseRemark : 通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮助医生更好地进行临床诊断和治疗，维护患者安全。
         * teacherName : 邱海波,管向东,严静,许媛,曹相原
         * imgUrl : http://192.168.0.123:8080/files/zsFile/debcnohiw107r5px/13/certificate.jpg
         * zsState : 2
         */

        private String courseName;
        private String courseRemark;
        private String teacherName;
        private String imgUrl;
        private int zsState;

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

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getZsState() {
            return zsState;
        }

        public void setZsState(int zsState) {
            this.zsState = zsState;
        }
    }
}
