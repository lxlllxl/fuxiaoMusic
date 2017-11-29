package com.example.administrator.fuxiaodemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 设置音乐的基本信息
 *
 */

public class Music implements Parcelable {
    private int id;
    private String fileName;
    private String data;
    private String musicName;

    public Music() {
    }

    protected Music(Parcel in) {
        id = in.readInt();
        fileName = in.readString();
        data = in.readString();
        musicName = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {


        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fileName);
        dest.writeString(data);
        dest.writeString(musicName);
    }
}
