package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2017/4/5.
 */

public class StudyCenterBean {

    /**
     * stateNow : 1
     * stateWill : 1
     * stateEnd : 0
     * stateOut : 1
     * courseCount : 2
     * studyNow : [{"cuuId":"debcnohiw107r5px","courseName":"重症检测的患者安全","courseType":1,"hasTime":"15天后结束","puuId":"febcaomwv2u7sr8x","plateName":"管理质量认证","cwuuId":"fgbnlkwv10tsr59x","courseWareName":"管理质量认证-2","studyJd":"您已学习1/13课件","teacherName":"邱海波、管向东、严静、许媛、曹相原","isPass":0,"remark":"通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮\u2026\u2026","stateType":1}]
     * studyWill : [{"cuuId":"dbamj3v21r5p49yx","courseName":"重症检测的患者安全","courseType":1,"hasTime":"40天后结束","puuId":"","plateName":"","cwuuId":"","courseWareName":"","studyJd":"0/0课件","teacherName":"","isPass":0,"remark":"通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮\u2026\u2026","stateType":1}]
     * studyEnd : [{"cuuId":"debcnohiw107r5px","courseName":"重症检测的患者安全","courseType":1,"hasTime":"15天后结束","puuId":"febcaomwv2u7sr8x","plateName":"管理质量认证","cwuuId":"fgbnlkwv10tsr59x","courseWareName":"管理质量认证-2","studyJd":"您已学习1/13课件","teacherName":"邱海波、管向东、严静、许媛、曹相原","isPass":0,"remark":"通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮\u2026\u2026","stateType":1}]
     * outTime : [{"cuuId":"gebnlkhiwt754pyx","courseName":"新概念kodsjfasuiooijkojzxcjodfojuiazsdaios的骄傲圣诞卡SNH48贷记卡华盛顿会计","courseType":2,"hasTime":"18天前已结束","puuId":"","plateName":"","cwuuId":"","courseWareName":"","studyJd":"0/0课件","teacherName":"严静、许媛、曹相原","isPass":0,"remark":"本课程为全新模块，全新的授课风格，引入当前最先进的技术讲解，聘请全国最\u2026\u2026"}]
     */

    private int stateNow;
    private int stateWill;
    private int stateEnd;
    private int stateOut;
    private int courseCount;
    private List<StudyNowBean> studyNow;
    private List<StudyWillBean> studyWill;
    private List<StudyEndBean> studyEnd;
    private List<OutTimeBean> outTime;

    public int getStateNow() {
        return stateNow;
    }

    public void setStateNow(int stateNow) {
        this.stateNow = stateNow;
    }

    public int getStateWill() {
        return stateWill;
    }

    public void setStateWill(int stateWill) {
        this.stateWill = stateWill;
    }

    public int getStateEnd() {
        return stateEnd;
    }

    public void setStateEnd(int stateEnd) {
        this.stateEnd = stateEnd;
    }

    public int getStateOut() {
        return stateOut;
    }

    public void setStateOut(int stateOut) {
        this.stateOut = stateOut;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public List<StudyNowBean> getStudyNow() {
        return studyNow;
    }

    public void setStudyNow(List<StudyNowBean> studyNow) {
        this.studyNow = studyNow;
    }

    public List<StudyWillBean> getStudyWill() {
        return studyWill;
    }

    public void setStudyWill(List<StudyWillBean> studyWill) {
        this.studyWill = studyWill;
    }

    public List<StudyEndBean> getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(List<StudyEndBean> studyEnd) {
        this.studyEnd = studyEnd;
    }

    public List<OutTimeBean> getOutTime() {
        return outTime;
    }

    public void setOutTime(List<OutTimeBean> outTime) {
        this.outTime = outTime;
    }

    public static class StudyNowBean {
        /**
         * cuuId : debcnohiw107r5px
         * courseName : 重症检测的患者安全
         * courseType : 1
         * hasTime : 15天后结束
         * puuId : febcaomwv2u7sr8x
         * plateName : 管理质量认证
         * cwuuId : fgbnlkwv10tsr59x
         * courseWareName : 管理质量认证-2
         * studyJd : 您已学习1/13课件
         * teacherName : 邱海波、管向东、严静、许媛、曹相原
         * isPass : 0
         * remark : 通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮……
         * stateType : 1
         */

        private String cuuId;
        private String courseName;
        private int courseType;
        private String hasTime;
        private String puuId;
        private String plateName;
        private String cwuuId;
        private String courseWareName;
        private String studyJd;
        private String teacherName;
        private int isPass;
        private String remark;
        private int stateType;

        public String getCuuId() {
            return cuuId;
        }

        public void setCuuId(String cuuId) {
            this.cuuId = cuuId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getHasTime() {
            return hasTime;
        }

        public void setHasTime(String hasTime) {
            this.hasTime = hasTime;
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

        public String getCwuuId() {
            return cwuuId;
        }

        public void setCwuuId(String cwuuId) {
            this.cwuuId = cwuuId;
        }

        public String getCourseWareName() {
            return courseWareName;
        }

        public void setCourseWareName(String courseWareName) {
            this.courseWareName = courseWareName;
        }

        public String getStudyJd() {
            return studyJd;
        }

        public void setStudyJd(String studyJd) {
            this.studyJd = studyJd;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStateType() {
            return stateType;
        }

        public void setStateType(int stateType) {
            this.stateType = stateType;
        }
    }

    public static class StudyWillBean {
        /**
         * cuuId : dbamj3v21r5p49yx
         * courseName : 重症检测的患者安全
         * courseType : 1
         * hasTime : 40天后结束
         * puuId :
         * plateName :
         * cwuuId :
         * courseWareName :
         * studyJd : 0/0课件
         * teacherName :
         * isPass : 0
         * remark : 通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮……
         * stateType : 1
         */

        private String cuuId;
        private String courseName;
        private int courseType;
        private String hasTime;
        private String puuId;
        private String plateName;
        private String cwuuId;
        private String courseWareName;
        private String studyJd;
        private String teacherName;
        private int isPass;
        private String remark;
        private int stateType;

        public String getCuuId() {
            return cuuId;
        }

        public void setCuuId(String cuuId) {
            this.cuuId = cuuId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getHasTime() {
            return hasTime;
        }

        public void setHasTime(String hasTime) {
            this.hasTime = hasTime;
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

        public String getCwuuId() {
            return cwuuId;
        }

        public void setCwuuId(String cwuuId) {
            this.cwuuId = cwuuId;
        }

        public String getCourseWareName() {
            return courseWareName;
        }

        public void setCourseWareName(String courseWareName) {
            this.courseWareName = courseWareName;
        }

        public String getStudyJd() {
            return studyJd;
        }

        public void setStudyJd(String studyJd) {
            this.studyJd = studyJd;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStateType() {
            return stateType;
        }

        public void setStateType(int stateType) {
            this.stateType = stateType;
        }
    }

    public static class StudyEndBean {
        /**
         * cuuId : debcnohiw107r5px
         * courseName : 重症检测的患者安全
         * courseType : 1
         * hasTime : 15天后结束
         * puuId : febcaomwv2u7sr8x
         * plateName : 管理质量认证
         * cwuuId : fgbnlkwv10tsr59x
         * courseWareName : 管理质量认证-2
         * studyJd : 您已学习1/13课件
         * teacherName : 邱海波、管向东、严静、许媛、曹相原
         * isPass : 0
         * remark : 通过开展本项目，我们致力于推动重症检测，尤其是血气分析水平的提高，以帮……
         * stateType : 1
         */

        private String cuuId;
        private String courseName;
        private int courseType;
        private String hasTime;
        private String puuId;
        private String plateName;
        private String cwuuId;
        private String courseWareName;
        private String studyJd;
        private String teacherName;
        private int isPass;
        private String remark;
        private int stateType;

        public String getCuuId() {
            return cuuId;
        }

        public void setCuuId(String cuuId) {
            this.cuuId = cuuId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getHasTime() {
            return hasTime;
        }

        public void setHasTime(String hasTime) {
            this.hasTime = hasTime;
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

        public String getCwuuId() {
            return cwuuId;
        }

        public void setCwuuId(String cwuuId) {
            this.cwuuId = cwuuId;
        }

        public String getCourseWareName() {
            return courseWareName;
        }

        public void setCourseWareName(String courseWareName) {
            this.courseWareName = courseWareName;
        }

        public String getStudyJd() {
            return studyJd;
        }

        public void setStudyJd(String studyJd) {
            this.studyJd = studyJd;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStateType() {
            return stateType;
        }

        public void setStateType(int stateType) {
            this.stateType = stateType;
        }
    }

    public static class OutTimeBean {
        /**
         * cuuId : gebnlkhiwt754pyx
         * courseName : 新概念kodsjfasuiooijkojzxcjodfojuiazsdaios的骄傲圣诞卡SNH48贷记卡华盛顿会计
         * courseType : 2
         * hasTime : 18天前已结束
         * puuId :
         * plateName :
         * cwuuId :
         * courseWareName :
         * studyJd : 0/0课件
         * teacherName : 严静、许媛、曹相原
         * isPass : 0
         * remark : 本课程为全新模块，全新的授课风格，引入当前最先进的技术讲解，聘请全国最……
         */

        private String cuuId;
        private String courseName;
        private int courseType;
        private String hasTime;
        private String puuId;
        private String plateName;
        private String cwuuId;
        private String courseWareName;
        private String studyJd;
        private String teacherName;
        private int isPass;
        private String remark;
        private int stateType;

        public int getStateType() {
            return stateType;
        }

        public void setStateType(int stateType) {
            this.stateType = stateType;
        }

        public String getCuuId() {
            return cuuId;
        }

        public void setCuuId(String cuuId) {
            this.cuuId = cuuId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getHasTime() {
            return hasTime;
        }

        public void setHasTime(String hasTime) {
            this.hasTime = hasTime;
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

        public String getCwuuId() {
            return cwuuId;
        }

        public void setCwuuId(String cwuuId) {
            this.cwuuId = cwuuId;
        }

        public String getCourseWareName() {
            return courseWareName;
        }

        public void setCourseWareName(String courseWareName) {
            this.courseWareName = courseWareName;
        }

        public String getStudyJd() {
            return studyJd;
        }

        public void setStudyJd(String studyJd) {
            this.studyJd = studyJd;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
