package com.example.hrh.testweatherinfo.data.WeatherBean;

/**
 * Created by hrh on 2015/9/30.
 */
public class Newsbean {

    private String newsTitle;
    private String newsIconUrl;
    private String newsCntext;

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return this.newsTitle;
    }

    public void setNewsContext(String newsCntext) {
        this.newsCntext = newsCntext;
    }

    public String getNewsCntext() {
        return this.newsCntext;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
    }

    public String getNewsIconUrl() {
        return this.newsIconUrl;
    }
}
