package data.WeatherBean;

/**
 * Created by hrh on 2015/8/23.
 */
public class BaseWeatherBean {
    public Cityweatherbean weatherinfo;

    public void setWeatherinfo(Cityweatherbean weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
    public Cityweatherbean getWeatherinfo() {
        return weatherinfo;
    }

    public BaseWeatherBean() {

    }
}
