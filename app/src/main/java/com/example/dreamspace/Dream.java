package com.example.dreamspace;
import android.os.Parcel;
import android.os.Parcelable;

public class Dream implements Parcelable {
    private String dreamId;
    private String title;
    private String body;
    private int rating;
    private int type;
    private String date;

    public Dream() {
    }

    public Dream(String title, String body, int rating, int type, String date) {
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.type = type;
        this.date = date;
    }

    protected Dream(Parcel in) {
        title = in.readString();
        body = in.readString();
        rating = in.readInt();
        type = in.readInt();
        date = in.readString();
    }

    public static final Creator<Dream> CREATOR = new Creator<Dream>() {
        @Override
        public Dream createFromParcel(Parcel in) {
            return new Dream(in);
        }

        @Override
        public Dream[] newArray(int size) {
            return new Dream[size];
        }
    };
    public String getDreamId() {
        return dreamId;
    }
    public void setDreamId(String dreamId) {
        this.dreamId = dreamId;
    }
    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getRating() {
        return rating;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeInt(rating);
        dest.writeInt(type);
        dest.writeString(date);
    }
}
