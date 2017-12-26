package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 12/26/2017.
 */
@Entity(nameInDb = "SubscriptionTbl",indexes = {@Index(value = "SubscriptionId", unique = true)})
public class SubscriptionTbl {
    @Id
    @Property(nameInDb = "SubscriptionId")
    private Long SubscriptionId;

    @Property(nameInDb = "UserId")
    private Long UserId;

    @Property(nameInDb = "PlanId")
    private String PlanId;

    @Property(nameInDb = "SubscriptionStartTimestamp")
    private String SubscriptionStartTimestamp;

    @Property(nameInDb = "SubscriptionEndTimestamp")
    private String SubscriptionEndTimestamp;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "ActiveFlag")
    private String ActiveFlag;

    @Generated(hash = 1750581802)
    public SubscriptionTbl(Long SubscriptionId, Long UserId, String PlanId,
            String SubscriptionStartTimestamp, String SubscriptionEndTimestamp, String CreatedDate,
            String ActiveFlag) {
        this.SubscriptionId = SubscriptionId;
        this.UserId = UserId;
        this.PlanId = PlanId;
        this.SubscriptionStartTimestamp = SubscriptionStartTimestamp;
        this.SubscriptionEndTimestamp = SubscriptionEndTimestamp;
        this.CreatedDate = CreatedDate;
        this.ActiveFlag = ActiveFlag;
    }

    @Generated(hash = 1887535069)
    public SubscriptionTbl() {
    }

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

    public String getPlanId() {
        return this.PlanId;
    }

    public void setPlanId(String PlanId) {
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

    public String getActiveFlag() {
        return this.ActiveFlag;
    }

    public void setActiveFlag(String ActiveFlag) {
        this.ActiveFlag = ActiveFlag;
    }




}
