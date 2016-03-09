package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/28.
 */
public class VideoRecordBean {
    private String cwUuid;
    private String userUuid;
    private int isFinish;
    private int isUploadSuccess;

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

    public int getIsUploadSuccess() {
        return isUploadSuccess;
    }

    public void setIsUploadSuccess(int isUploadSuccess) {
        this.isUploadSuccess = isUploadSuccess;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public VideoRecordBean(String cwUuid, String userUuid, int isFinish, int isUploadSuccess) {
        this.cwUuid = cwUuid;
        this.userUuid = userUuid;
        this.isFinish = isFinish;
        this.isUploadSuccess = isUploadSuccess;
    }

    public VideoRecordBean() {
    }
}
