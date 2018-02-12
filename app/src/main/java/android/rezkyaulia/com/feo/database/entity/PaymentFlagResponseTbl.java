package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 1/20/2018.
 */
@Entity(nameInDb = "PaymentFlagResponseTbl",indexes = {})
public class PaymentFlagResponseTbl {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "PaymentFlagResponseId")
    private Long PaymentFlagResponseId;

    @Property(nameInDb = "PaymentFlagId")
    private Long  PaymentFlagId;

    @Property(nameInDb = " ChannelId ")
    private String  ChannelId ;

    @Property(nameInDb = "Currency")
    private String Currency;


    @Property(nameInDb = "PaymentStatus")
    private String PaymentStatus;

    @Property(nameInDb = "PaymentMessage")
    private String PaymentMessage;

    @Property(nameInDb = "FlagType")
    private Long FlagType;
    
    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "UpdatedDate")
    private String UpdatedDate;

    @Property(nameInDb = "DeletedDate")
    private String DeletedDate;


    @Generated(hash = 1206460179)
    public PaymentFlagResponseTbl(Long id, Long PaymentFlagResponseId,
            Long PaymentFlagId, String ChannelId, String Currency,
            String PaymentStatus, String PaymentMessage, Long FlagType,
            String CreatedDate, String UpdatedDate, String DeletedDate) {
        this.id = id;
        this.PaymentFlagResponseId = PaymentFlagResponseId;
        this.PaymentFlagId = PaymentFlagId;
        this.ChannelId = ChannelId;
        this.Currency = Currency;
        this.PaymentStatus = PaymentStatus;
        this.PaymentMessage = PaymentMessage;
        this.FlagType = FlagType;
        this.CreatedDate = CreatedDate;
        this.UpdatedDate = UpdatedDate;
        this.DeletedDate = DeletedDate;
    }

    @Generated(hash = 1038441216)
    public PaymentFlagResponseTbl() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentFlagResponseId() {
        return this.PaymentFlagResponseId;
    }

    public void setPaymentFlagResponseId(Long PaymentFlagResponseId) {
        this.PaymentFlagResponseId = PaymentFlagResponseId;
    }

    public Long getPaymentFlagId() {
        return this.PaymentFlagId;
    }

    public void setPaymentFlagId(Long PaymentFlagId) {
        this.PaymentFlagId = PaymentFlagId;
    }

    public String getChannelId() {
        return this.ChannelId;
    }

    public void setChannelId(String ChannelId) {
        this.ChannelId = ChannelId;
    }

    public String getCurrency() {
        return this.Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public String getPaymentStatus() {
        return this.PaymentStatus;
    }

    public void setPaymentStatus(String PaymentStatus) {
        this.PaymentStatus = PaymentStatus;
    }


    public Long getFlagType() {
        return this.FlagType;
    }

    public void setFlagType(Long FlagType) {
        this.FlagType = FlagType;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getUpdatedDate() {
        return this.UpdatedDate;
    }

    public void setUpdatedDate(String UpdatedDate) {
        this.UpdatedDate = UpdatedDate;
    }

    public String getDeletedDate() {
        return this.DeletedDate;
    }

    public void setDeletedDate(String DeletedDate) {
        this.DeletedDate = DeletedDate;
    }

    public String getPaymentMessage() {
        return this.PaymentMessage;
    }

    public void setPaymentMessage(String PaymentMessage) {
        this.PaymentMessage = PaymentMessage;
    }


}
