package com.example.hrh.testweatherinfo.data.imoocInfo;

import android.content.ContentValues;
import android.content.Entity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hrh on 2015/11/10.
 */
public class ImoocInfoListDataBean implements Parcelable {
    /**
     * id : 1
     * name : Tony
     * picSmall : imgurl
     * picBig : BigImgUrl
     * description : mDescription
     * learner : 12312
     */

    private String id;
    private String name;
    private String picSmall;
    private String picBig;
    private String description;
    private String learner;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLearner(String learner) {
        this.learner = learner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicSmall() {
        return picSmall;
    }

    public String getPicBig() {
        return picBig;
    }

    public String getDescription() {
        return description;
    }

    public String getLearner() {
        return learner;
    }

    @Override
    public String toString() {
        return "ImoocInfoListDataBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picSmall='" + picSmall + '\'' +
                ", picBig='" + picBig + '\'' +
                ", description='" + description + '\'' +
                ", learner='" + learner + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.picSmall);
        dest.writeString(this.picBig);
        dest.writeString(this.description);
        dest.writeString(this.learner);
    }

    public ImoocInfoListDataBean() {
    }

    protected ImoocInfoListDataBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.picSmall = in.readString();
        this.picBig = in.readString();
        this.description = in.readString();
        this.learner = in.readString();
    }

    public static final Parcelable.Creator<ImoocInfoListDataBean> CREATOR = new Parcelable.Creator<ImoocInfoListDataBean>() {
        public ImoocInfoListDataBean createFromParcel(Parcel source) {
            return new ImoocInfoListDataBean(source);
        }

        public ImoocInfoListDataBean[] newArray(int size) {
            return new ImoocInfoListDataBean[size];
        }
    };
}
