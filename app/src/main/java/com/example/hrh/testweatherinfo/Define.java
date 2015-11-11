package com.example.hrh.testweatherinfo;

/**
 * Created by hrh on 2015/8/21.
 */
public class Define {

    public static final String WEATHER_LIST_PATH = "http://www.weather.com.cn/data/list3/city.xml";
    public static final String PRIVINCE_CITY_PATH = "http://www.weather.com.cn/data/list3/city";
    public static final String PRIVINCE_CITY_WEATHER_PATH = "http://www.weather.com.cn/data/list3/city";
    public static final String CITY_WEATHER_PATH = "http://www.weather.com.cn/data/cityinfo/";
    public static final String IMOOCURL = "http://www.imooc.com/api/teacher?type=4&num=30";

    public enum NetErrorReason {
        NOT_KNOWN(-1), NO_MORE(1), NETWORK_ERROR(2), SERVER_DIE(3), WRONG_ARGU(4);

        int type;

        NetErrorReason(int type) {
            this.type = type;
        }

        public int getReason() {
            return type;
        }

    }
}
