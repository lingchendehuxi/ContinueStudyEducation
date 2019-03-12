package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/28.
 */
public class VideoRecordBean {
    private String cwUuid;
    private String userUuid;

    public String getCwUuid() {
        return cwUuid;
    }

    public void setCwUuid(String cwUuid) {
        this.cwUuid = cwUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public VideoRecordBean(String cwUuid, String userUuid) {
        this.cwUuid = cwUuid;
        this.userUuid = userUuid;
    }

    public VideoRecordBean() {
    }
}
