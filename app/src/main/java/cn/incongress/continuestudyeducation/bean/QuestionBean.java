package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/30.
 */
public class QuestionBean {
    private int questionId;
    private String userImg;
    private String trueName;
    private String content;
    private String createTime;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "questionId=" + questionId +
                ", userImg='" + userImg + '\'' +
                ", trueName='" + trueName + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public QuestionBean(int questionId, String userImg, String trueName, String content, String createTime) {
        this.questionId = questionId;
        this.userImg = userImg;
        this.trueName = trueName;
        this.content = content;
        this.createTime = createTime;
    }
}
