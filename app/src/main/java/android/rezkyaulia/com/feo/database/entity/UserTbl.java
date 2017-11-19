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
@Entity(nameInDb = "UserTbl",indexes = {@Index(value = "UserId", unique = true)})
public class UserTbl implements Parcelable{
    @Id
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


    @Property(nameInDb = "UserKey")
    private String UserKey;

    @Property(nameInDb = "Token")
    private String Token;

    @Generated(hash = 1049392705)
    public UserTbl(Long UserId, String Name, String Username, String Email,
            String Password, String UserKey, String Token) {
        this.UserId = UserId;
        this.Name = Name;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.UserKey = UserKey;
        this.Token = Token;
    }

    @Generated(hash = 585658511)
    public UserTbl() {
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

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.UserId);
        dest.writeString(this.Name);
        dest.writeString(this.Username);
        dest.writeString(this.Email);
        dest.writeString(this.Password);
        dest.writeString(this.UserKey);
        dest.writeString(this.Token);
    }

    protected UserTbl(Parcel in) {
        this.UserId = (Long) in.readValue(Long.class.getClassLoader());
        this.Name = in.readString();
        this.Username = in.readString();
        this.Email = in.readString();
        this.Password = in.readString();
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
