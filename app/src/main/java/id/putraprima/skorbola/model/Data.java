package id.putraprima.skorbola.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String homeName;
    private String awayName;

    public Data(String homeName, String awayName) {
        this.homeName = homeName;
        this.awayName = awayName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.homeName);
        dest.writeString(this.awayName);
    }

    protected Data(Parcel in) {
        this.homeName = in.readString();
        this.awayName = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
