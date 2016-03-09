package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2015/12/27.
 */
public class PersonInfoEvent {
    private int type;
    private String content;

    public PersonInfoEvent(){}

    public PersonInfoEvent(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PersonInfoEvent{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
