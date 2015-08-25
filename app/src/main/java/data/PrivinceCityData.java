package data;

import android.os.Parcel;
import android.os.Parcelable;

import de.greendao.daoexample.PrivinceCity;

/**
 * Created by hrh on 2015/8/21.
 */
public class PrivinceCityData implements Parcelable{
    private Long id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public PrivinceCityData(Long id, String countyName, String countyCode) {
        this.id = id;
        this.countyName = countyName;
        this.countyCode = countyCode;
    }
    public PrivinceCityData () {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(countyName);
        dest.writeString(countyCode);
        dest.writeInt(cityId);
    }

    public static final Creator<PrivinceCityData> CREATOR = new Creator<PrivinceCityData>() {

        @Override
        public PrivinceCityData createFromParcel(Parcel source) {
            PrivinceCityData item = new PrivinceCityData();
            item.id = source.readLong();
            item.countyName = source.readString();
            item.countyCode = source.readString();
            item.cityId = source.readInt();
            return item;
        }

        @Override
        public PrivinceCityData[] newArray(int size) {
            return new PrivinceCityData[size];
        }
    };

    @Override
    public String toString() {
        return "PrivinceCityData [id = " + id + " , countyName = " + countyName + " , countyCode = " + countyCode
                + " , cityId = " + cityId + "]";
    }
}
