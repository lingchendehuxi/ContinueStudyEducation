package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/21.
 */
public class AttachStudyBean {

    /**
     * cwName : 管理质量认证-2
     * videoUrl : http://cmacmeyun.cdn.bcebos.com/1.%E7%AE%A1%E7%90%86%E8%B4%A8%E9%87%8F%E8%AE%A4%E8%AF%81_2.mp4
     * cwTime : 18: 45
     * teacherName : 邱海波
     * isFinish : 1
     * tuuId : fgde7rqa4nol8hix
     * teachRemark :        邱海波，现任东南大学附属中大医院副院长
     * imgUrl : http://114.80.200.3:80/source/file/images/2016-10-28_1477619939960.jpg
     * cwArray : [{"isFinish":1,"cwuuId":"fcmkhi3vu17s598x","cwName":"管理质量认证-1","type":1,"cwTime":"21: 51","teachName":"邱海波"},{"isFinish":0,"cwuuId":"fgbnlkwv10tsr59x","cwName":"管理质量认证-2","type":0,"cwTime":"18: 45","teachName":"邱海波"}]
     */

    private String cwName;
    private String videoUrl;
    private String cwTime;
    private String teacherName;
    private int isFinish;
    private String tuuId;
    private String teachRemark;
    private String imgUrl;
    private List<CwArrayBean> cwArray;

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCwTime() {
        return cwTime;
    }

    public void setCwTime(String cwTime) {
        this.cwTime = cwTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getTuuId() {
        return tuuId;
    }

    public void setTuuId(String tuuId) {
        this.tuuId = tuuId;
    }

    public String getTeachRemark() {
        return teachRemark;
    }

    public void setTeachRemark(String teachRemark) {
        this.teachRemark = teachRemark;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<CwArrayBean> getCwArray() {
        return cwArray;
    }

    public void setCwArray(List<CwArrayBean> cwArray) {
        this.cwArray = cwArray;
    }

    public static class CwArrayBean {
        /**
         * isFinish : 1
         * cwuuId : fcmkhi3vu17s598x
         * cwName : 管理质量认证-1
         * type : 1
         * cwTime : 21: 51
         * teachName : 邱海波
         */

        private int isFinish;
        private String cwuuId;
        private String cwName;
        private int type;
        private int canStudy;
        private String cwTime;
        private String teachName;

        public int getCanStudy() {
            return canStudy;
        }

        public void setCanStudy(int canStudy) {
            this.canStudy = canStudy;
        }

        public int getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(int isFinish) {
            this.isFinish = isFinish;
        }

        public String getCwuuId() {
            return cwuuId;
        }

        public void setCwuuId(String cwuuId) {
            this.cwuuId = cwuuId;
        }

        public String getCwName() {
            return cwName;
        }

        public void setCwName(String cwName) {
            this.cwName = cwName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCwTime() {
            return cwTime;
        }

        public void setCwTime(String cwTime) {
            this.cwTime = cwTime;
        }

        public String getTeachName() {
            return teachName;
        }

        public void setTeachName(String teachName) {
            this.teachName = teachName;
        }
    }
}
