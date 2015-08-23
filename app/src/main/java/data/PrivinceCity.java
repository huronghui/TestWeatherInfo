package data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hrh on 2015/8/21.
 */
public class PrivinceCity implements Parcelable{
    private Long id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public PrivinceCity() {
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

    public static final Creator<PrivinceCity> CREATOR = new Creator<PrivinceCity>() {

        @Override
        public PrivinceCity createFromParcel(Parcel source) {
            PrivinceCity item = new PrivinceCity();
            item.id = source.readLong();
            item.countyName = source.readString();
            item.countyCode = source.readString();
            item.cityId = source.readInt();
            return item;
        }

        @Override
        public PrivinceCity[] newArray(int size) {
            return new PrivinceCity[size];
        }
    };

    @Override
    public String toString() {
        return "PrivinceCity [id = " + id + " , countyName = " + countyName + " , countyCode = " + countyCode
                + " , cityId = " + cityId + "]";
    }
}
