package android.rezkyaulia.com.feo.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */
@Entity(nameInDb = "LibraryTbl",indexes = {})
public class LibraryTbl implements Parcelable {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "LibraryId")
    private Long LibraryId;

    @Property(nameInDb = "Title")
    private String Title;

    @Property(nameInDb = "Author")
    private String Author;

    @Property(nameInDb = "Content")
    private String Content;

    @Transient
    private int ReadFlag;

    @Generated(hash = 2003093814)
    public LibraryTbl(Long id, Long LibraryId, String Title, String Author,
            String Content) {
        this.id = id;
        this.LibraryId = LibraryId;
        this.Title = Title;
        this.Author = Author;
        this.Content = Content;
    }

    @Generated(hash = 247998327)
    public LibraryTbl() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibraryId() {
        return this.LibraryId;
    }

    public void setLibraryId(Long LibraryId) {
        this.LibraryId = LibraryId;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return this.Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getReadFlag() {
        return ReadFlag;
    }

    public void setReadFlag(int readFlag) {
        ReadFlag = readFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.LibraryId);
        dest.writeString(this.Title);
        dest.writeString(this.Author);
        dest.writeString(this.Content);
    }

    protected LibraryTbl(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.LibraryId = (Long) in.readValue(Long.class.getClassLoader());
        this.Title = in.readString();
        this.Author = in.readString();
        this.Content = in.readString();
    }

    public static final Creator<LibraryTbl> CREATOR = new Creator<LibraryTbl>() {
        @Override
        public LibraryTbl createFromParcel(Parcel source) {
            return new LibraryTbl(source);
        }

        @Override
        public LibraryTbl[] newArray(int size) {
            return new LibraryTbl[size];
        }
    };
}
