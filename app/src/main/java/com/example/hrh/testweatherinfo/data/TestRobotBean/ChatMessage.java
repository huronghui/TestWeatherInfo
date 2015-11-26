package com.example.hrh.testweatherinfo.data.TestRobotBean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by hrh on 2015/11/26.
 */
public class ChatMessage implements Parcelable {

    public ChatMessage() {
    }

    private String name;

    public ChatMessage( String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String msg;
    private Type type;
    private Date date;

    public enum Type
    {
        INCOMING, OUTCOMING
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.msg);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeLong(date != null ? date.getTime() : -1);
    }

    protected ChatMessage(Parcel in) {
        this.name = in.readString();
        this.msg = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
