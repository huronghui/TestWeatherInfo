package data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hrh on 2015/8/21.
 */
public class City implements Parcelable {

    private Long id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public City() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(cityName);
        dest.writeString(cityCode);
        dest.writeInt(provinceId);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {

        @Override
        public City createFromParcel(Parcel source) {
            City item = new City();
            item.id = source.readLong();
            item.cityCode = source.readString();
            item.cityName = source.readString();
            item.provinceId = source.readInt();
            return item;
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public String toString() {
        return "City [id = " + id + " , CityName = " + cityName + " , CityCode = " + cityCode
                + " , provinceId = " + provinceId + "]";
    }
}
