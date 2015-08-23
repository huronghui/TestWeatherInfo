package data.WeatherBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hrh on 2015/8/22.
 */
public class Cityweatherbean implements Parcelable{

    private String city;
    private String cityid;
    private String temp1;
    private String temp2;
    private String weather;

    public String getCity() {
        return city;
    }

    public boolean setCity(String city) {
        this.city = city;
        return true;
    }

    public String getCityId() {
        return cityid;
    }

    public boolean setCityId(String cityid) {
        this.cityid = cityid;
        return true;
    }

    public String getTemp1() {
        return temp1;
    }

    public boolean setTemp1(String temp1) {
        this.temp1 = temp1;
        return true;
    }

    public String getTemp2() {
        return temp2;
    }

    public boolean setTemp2(String temp2) {
        this.temp2 = temp2;
        return true;
    }

    public String getWeather() {
        return weather;
    }

    public boolean setWeather(String weather) {
        this.weather = weather;
        return true;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(cityid);
        dest.writeString(temp1);
        dest.writeString(weather);
    }

    @Override
    public String toString() {
        return "Cityweatherbean [ city = " + city + " , cityid = " + cityid +
                " , temp1 = " + temp1 + ", temp2 = " + temp2 + " , weather  = " + weather + "]";
    }

}
