package android.rezkyaulia.com.feo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rezky Aulia Pratama on 11/8/2017.
 */

public class ReadableObj implements Parcelable{
    String word;
    int lenght;

    public ReadableObj(String word, int lenght) {
        this.word = word;
        this.lenght = lenght;
    }

    public ReadableObj() {
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.word);
        dest.writeInt(this.lenght);
    }

    protected ReadableObj(Parcel in) {
        this.word = in.readString();
        this.lenght = in.readInt();
    }

    public static final Creator<ReadableObj> CREATOR = new Creator<ReadableObj>() {
        @Override
        public ReadableObj createFromParcel(Parcel source) {
            return new ReadableObj(source);
        }

        @Override
        public ReadableObj[] newArray(int size) {
            return new ReadableObj[size];
        }
    };
}
