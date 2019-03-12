package cn.incongress.continuestudyeducation.bean;

import java.util.List;

/**
 * Created by Jacky on 2015/12/21.
 */
public class PlateDetailBean {

    /**
     * state : 1
     * plateCount : 5
     * plateArray : [{"puuId":"febcaomwv2u7sr8x","plateName":"管理质量认证","studyJd":"2/2课件","type":1,"cwArray":[{"cwuuId":"fcmkhi3vu17s598x","cwName":"管理质量认证-1","type":1,"canStudy":0},{"cwuuId":"fgbnlkwv10tsr59x","cwName":"管理质量认证-2","type":1,"canStudy":0}]},{"puuId":"fgdeanljw3tr5q4y","plateName":"质量控制","studyJd":"0/2课件","type":0,"cwArray":[{"cwuuId":"denlmi2v1utsp498","cwName":"重症患者的安全质量控制-基础QC","type":0,"canStudy":1},{"cwuuId":"gdclmkhwts7rqp4y","cwName":"重症患者的安全质量控制-传统QC与实时质量管理","type":0,"canStudy":0}]}]
     */

    private int state;
    private int plateCount;
    private List<PlateArrayBean> plateArray;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPlateCount() {
        return plateCount;
    }

    public void setPlateCount(int plateCount) {
        this.plateCount = plateCount;
    }

    public List<PlateArrayBean> getPlateArray() {
        return plateArray;
    }

    public void setPlateArray(List<PlateArrayBean> plateArray) {
        this.plateArray = plateArray;
    }

    public static class PlateArrayBean {
        /**
         * puuId : febcaomwv2u7sr8x
         * plateName : 管理质量认证
         * studyJd : 2/2课件
         * type : 1
         * cwArray : [{"cwuuId":"fcmkhi3vu17s598x","cwName":"管理质量认证-1","type":1,"canStudy":0},{"cwuuId":"fgbnlkwv10tsr59x","cwName":"管理质量认证-2","type":1,"canStudy":0}]
         */

        private String puuId;
        private String plateName;
        private String studyJd;
        private int type;
        private List<CwArrayBean> cwArray;

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

        public String getStudyJd() {
            return studyJd;
        }

        public void setStudyJd(String studyJd) {
            this.studyJd = studyJd;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<CwArrayBean> getCwArray() {
            return cwArray;
        }

        public void setCwArray(List<CwArrayBean> cwArray) {
            this.cwArray = cwArray;
        }

        public static class CwArrayBean {
            /**
             * cwuuId : fcmkhi3vu17s598x
             * cwName : 管理质量认证-1
             * type : 1
             * canStudy : 0
             */

            private String cwuuId;
            private String cwName;
            private int type;
            private int canStudy;

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

            public int getCanStudy() {
                return canStudy;
            }

            public void setCanStudy(int canStudy) {
                this.canStudy = canStudy;
            }
        }
    }
}
