package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2016/1/4.
 */
public class CityBean {
    private int cityId;
    private String cityName;
    private boolean isChecked;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CityBean(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public CityBean() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public CityBean(int cityId, String cityName, boolean isChecked) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.isChecked = isChecked;
    }
}
