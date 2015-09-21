package com.example.hrh.testweatherinfo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCity implements Parcelable{

    private Long id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public DistrictCity() {
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

    public static final Parcelable.Creator<DistrictCity> CREATOR = new Parcelable.Creator<DistrictCity>() {

        @Override
        public DistrictCity createFromParcel(Parcel source) {
            DistrictCity item = new DistrictCity();
            item.id = source.readLong();
            item.cityCode = source.readString();
            item.cityName = source.readString();
            item.provinceId = source.readInt();
            return item;
        }

        @Override
        public DistrictCity[] newArray(int size) {
            return new DistrictCity[size];
        }
    };

    @Override
    public String toString() {
        return "DistrictCity [id = " + id + " , CityName = " + cityName + " , CityCode = " + cityCode
                + " , provinceId = " + provinceId + "]";
    }
}
