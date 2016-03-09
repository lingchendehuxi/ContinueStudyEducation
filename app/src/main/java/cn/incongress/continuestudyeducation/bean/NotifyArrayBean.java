package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/24.
 */
public class NotifyArrayBean {
    private int notifyId;
    private String content;
    private String dateTime;
    private String nuuId;

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNuuId() {
        return nuuId;
    }

    public void setNuuId(String nuuId) {
        this.nuuId = nuuId;
    }

    @Override
    public String toString() {
        return "NotifyArrayBean{" +
                "notifyId=" + notifyId +
                ", content='" + content + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", nuuId='" + nuuId + '\'' +
                '}';
    }
}
