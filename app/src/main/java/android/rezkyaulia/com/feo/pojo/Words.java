package android.rezkyaulia.com.feo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class Words implements Parcelable{
    List<String> strings;

    public Words(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.strings);
    }

    protected Words(Parcel in) {
        this.strings = in.createStringArrayList();
    }

    public static final Creator<Words> CREATOR = new Creator<Words>() {
        @Override
        public Words createFromParcel(Parcel source) {
            return new Words(source);
        }

        @Override
        public Words[] newArray(int size) {
            return new Words[size];
        }
    };
}
