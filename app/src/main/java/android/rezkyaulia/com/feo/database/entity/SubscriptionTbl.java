package android.rezkyaulia.com.feo.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 12/26/2017.
 */
@Entity(nameInDb = "SubscriptionTbl",indexes = {@Index(value = "SubscriptionId", unique = true)})
public class SubscriptionTbl implements Parcelable {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "SubscriptionId")
    private Long SubscriptionId;

    @Property(nameInDb = "UserId")
    private Long UserId;

    @Property(nameInDb = "PlanId")
    private Long PlanId;

    @Property(nameInDb = "SubscriptionStartTimestamp")
    private String SubscriptionStartTimestamp;

    @Property(nameInDb = "SubscriptionEndTimestamp")
    private String SubscriptionEndTimestamp;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "ActiveFlag")
    private Long ActiveFlag;

    @Property(nameInDb = "PaymentFlag")
    private Long PaymentFlag;

    

    public Long getSubscriptionId() {
        return this.SubscriptionId;
    }

    public void setSubscriptionId(Long SubscriptionId) {
        this.SubscriptionId = SubscriptionId;
    }

    public Long getUserId() {
        return this.UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public Long getPlanId() {
        return this.PlanId;
    }

    public void setPlanId(Long PlanId) {
        this.PlanId = PlanId;
    }

    public String getSubscriptionStartTimestamp() {
        return this.SubscriptionStartTimestamp;
    }

    public void setSubscriptionStartTimestamp(String SubscriptionStartTimestamp) {
        this.SubscriptionStartTimestamp = SubscriptionStartTimestamp;
    }

    public String getSubscriptionEndTimestamp() {
        return this.SubscriptionEndTimestamp;
    }

    public void setSubscriptionEndTimestamp(String SubscriptionEndTimestamp) {
        this.SubscriptionEndTimestamp = SubscriptionEndTimestamp;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public Long getActiveFlag() {
        return this.ActiveFlag;
    }

    public void setActiveFlag(Long ActiveFlag) {
        this.ActiveFlag = ActiveFlag;
    }

    public Long getPaymentFlag() {
        return this.PaymentFlag;
    }

    public void setPaymentFlag(Long PaymentFlag) {
        this.PaymentFlag = PaymentFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.SubscriptionId);
        dest.writeValue(this.UserId);
        dest.writeValue(this.PlanId);
        dest.writeString(this.SubscriptionStartTimestamp);
        dest.writeString(this.SubscriptionEndTimestamp);
        dest.writeString(this.CreatedDate);
        dest.writeValue(this.ActiveFlag);
        dest.writeValue(this.PaymentFlag);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected SubscriptionTbl(Parcel in) {
        this.SubscriptionId = (Long) in.readValue(Long.class.getClassLoader());
        this.UserId = (Long) in.readValue(Long.class.getClassLoader());
        this.PlanId = (Long) in.readValue(Long.class.getClassLoader());
        this.SubscriptionStartTimestamp = in.readString();
        this.SubscriptionEndTimestamp = in.readString();
        this.CreatedDate = in.readString();
        this.ActiveFlag = (Long) in.readValue(Long.class.getClassLoader());
        this.PaymentFlag = (Long) in.readValue(Long.class.getClassLoader());
    }

    @Generated(hash = 522419241)
    public SubscriptionTbl(Long id, Long SubscriptionId, Long UserId, Long PlanId,
            String SubscriptionStartTimestamp, String SubscriptionEndTimestamp, String CreatedDate,
            Long ActiveFlag, Long PaymentFlag) {
        this.id = id;
        this.SubscriptionId = SubscriptionId;
        this.UserId = UserId;
        this.PlanId = PlanId;
        this.SubscriptionStartTimestamp = SubscriptionStartTimestamp;
        this.SubscriptionEndTimestamp = SubscriptionEndTimestamp;
        this.CreatedDate = CreatedDate;
        this.ActiveFlag = ActiveFlag;
        this.PaymentFlag = PaymentFlag;
    }

    @Generated(hash = 1887535069)
    public SubscriptionTbl() {
    }

    public static final Creator<SubscriptionTbl> CREATOR = new Creator<SubscriptionTbl>() {
        @Override
        public SubscriptionTbl createFromParcel(Parcel source) {
            return new SubscriptionTbl(source);
        }

        @Override
        public SubscriptionTbl[] newArray(int size) {
            return new SubscriptionTbl[size];
        }
    };
}
