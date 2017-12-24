package android.rezkyaulia.com.feo.database.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.rezkyaulia.com.feo.model.api.ApiResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */
@Entity(nameInDb = "UserTbl",indexes = {@Index(value = "_id,UserId", unique = true)})
public class UserTbl implements Parcelable{
    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    private Long _id;

    @Property(nameInDb = "UserId")
    private Long UserId;

    @Property(nameInDb = "Name")
    private String Name;

    @Property(nameInDb = "Username")
    private String Username;

    @Property(nameInDb = "Email")
    private String Email;

    @Property(nameInDb = "Password")
    private String Password;


    @Property(nameInDb = "School")
    private String School;


    @Property(nameInDb = "ClassName")
    private String ClassName;


    @Property(nameInDb = "HomeAddress")
    private String HomeAddress;


    @Property(nameInDb = "SchoolAddress")
    private String SchoolAddress;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "UserKey")
    private String UserKey;

    @Property(nameInDb = "Token")
    private String Token;

    @Generated(hash = 135015433)
    public UserTbl(Long _id, Long UserId, String Name, String Username, String Email,
            String Password, String School, String ClassName, String HomeAddress,
            String SchoolAddress, String CreatedDate, String UserKey, String Token) {
        this._id = _id;
        this.UserId = UserId;
        this.Name = Name;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.School = School;
        this.ClassName = ClassName;
        this.HomeAddress = HomeAddress;
        this.SchoolAddress = SchoolAddress;
        this.CreatedDate = CreatedDate;
        this.UserKey = UserKey;
        this.Token = Token;
    }

    @Generated(hash = 585658511)
    public UserTbl() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getUserId() {
        return this.UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSchool() {
        return this.School;
    }

    public void setSchool(String School) {
        this.School = School;
    }

    public String getClassName() {
        return this.ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getHomeAddress() {
        return this.HomeAddress;
    }

    public void setHomeAddress(String HomeAddress) {
        this.HomeAddress = HomeAddress;
    }

    public String getSchoolAddress() {
        return this.SchoolAddress;
    }

    public void setSchoolAddress(String SchoolAddress) {
        this.SchoolAddress = SchoolAddress;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getUserKey() {
        return this.UserKey;
    }

    public void setUserKey(String UserKey) {
        this.UserKey = UserKey;
    }

    public String getToken() {
        return this.Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeValue(this.UserId);
        dest.writeString(this.Name);
        dest.writeString(this.Username);
        dest.writeString(this.Email);
        dest.writeString(this.Password);
        dest.writeString(this.School);
        dest.writeString(this.ClassName);
        dest.writeString(this.HomeAddress);
        dest.writeString(this.SchoolAddress);
        dest.writeString(this.CreatedDate);
        dest.writeString(this.UserKey);
        dest.writeString(this.Token);
    }

    protected UserTbl(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.UserId = (Long) in.readValue(Long.class.getClassLoader());
        this.Name = in.readString();
        this.Username = in.readString();
        this.Email = in.readString();
        this.Password = in.readString();
        this.School = in.readString();
        this.ClassName = in.readString();
        this.HomeAddress = in.readString();
        this.SchoolAddress = in.readString();
        this.CreatedDate = in.readString();
        this.UserKey = in.readString();
        this.Token = in.readString();
    }

    public static final Creator<UserTbl> CREATOR = new Creator<UserTbl>() {
        @Override
        public UserTbl createFromParcel(Parcel source) {
            return new UserTbl(source);
        }

        @Override
        public UserTbl[] newArray(int size) {
            return new UserTbl[size];
        }
    };
}
