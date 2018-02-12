package android.rezkyaulia.com.feo.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */
@Entity(nameInDb = "PlanTbl",indexes = {@Index(value = "PlanId", unique = true)})
public class PlanTbl implements Parcelable{
    @Id
    @Property(nameInDb = "PlanId")
    private Long PlanId;

    @SerializedName("Name")
    @Property(nameInDb = "PlanName")
    private String PlanName;

    @Property(nameInDb = "Price")
    private int Price;

    @Property(nameInDb = "TotalMonth")
    private int TotalMonth;

    @Property(nameInDb = "ActiveFlag")
    private int ActiveFlag;

    @Property(nameInDb = "ImageName")
    private String ImageName;

    @Property(nameInDb = "PlanDesc")
    private String PlanDesc;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Generated(hash = 707661029)
    public PlanTbl(Long PlanId, String PlanName, int Price, int TotalMonth,
            int ActiveFlag, String ImageName, String PlanDesc, String CreatedDate) {
        this.PlanId = PlanId;
        this.PlanName = PlanName;
        this.Price = Price;
        this.TotalMonth = TotalMonth;
        this.ActiveFlag = ActiveFlag;
        this.ImageName = ImageName;
        this.PlanDesc = PlanDesc;
        this.CreatedDate = CreatedDate;
    }

    @Generated(hash = 1344337390)
    public PlanTbl() {
    }

    public Long getPlanId() {
        return this.PlanId;
    }

    public void setPlanId(Long PlanId) {
        this.PlanId = PlanId;
    }

    public String getPlanName() {
        return this.PlanName;
    }

    public void setPlanName(String PlanName) {
        this.PlanName = PlanName;
    }

    public int getPrice() {
        return this.Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getTotalMonth() {
        return this.TotalMonth;
    }

    public void setTotalMonth(int TotalMonth) {
        this.TotalMonth = TotalMonth;
    }

    public int getActiveFlag() {
        return this.ActiveFlag;
    }

    public void setActiveFlag(int ActiveFlag) {
        this.ActiveFlag = ActiveFlag;
    }

    public String getImageName() {
        return this.ImageName;
    }

    public void setImageName(String ImageName) {
        this.ImageName = ImageName;
    }

    public String getPlanDesc() {
        return this.PlanDesc;
    }

    public void setPlanDesc(String PlanDesc) {
        this.PlanDesc = PlanDesc;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.PlanId);
        dest.writeString(this.PlanName);
        dest.writeInt(this.Price);
        dest.writeInt(this.TotalMonth);
        dest.writeInt(this.ActiveFlag);
        dest.writeString(this.ImageName);
        dest.writeString(this.PlanDesc);
        dest.writeString(this.CreatedDate);
    }

    protected PlanTbl(Parcel in) {
        this.PlanId = (Long) in.readValue(Long.class.getClassLoader());
        this.PlanName = in.readString();
        this.Price = in.readInt();
        this.TotalMonth = in.readInt();
        this.ActiveFlag = in.readInt();
        this.ImageName = in.readString();
        this.PlanDesc = in.readString();
        this.CreatedDate = in.readString();
    }

    public static final Creator<PlanTbl> CREATOR = new Creator<PlanTbl>() {
        @Override
        public PlanTbl createFromParcel(Parcel source) {
            return new PlanTbl(source);
        }

        @Override
        public PlanTbl[] newArray(int size) {
            return new PlanTbl[size];
        }
    };
}
