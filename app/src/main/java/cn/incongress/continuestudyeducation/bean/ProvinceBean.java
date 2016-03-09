package cn.incongress.continuestudyeducation.bean;

/**
 * Created by Jacky on 2016/1/4.
 */
public class ProvinceBean {
    private int provinceId;
    private String provinceName;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public ProvinceBean() {
    }

    public ProvinceBean(int provinceId, String provinceName) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "ProvinceBean{" +
                "provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
