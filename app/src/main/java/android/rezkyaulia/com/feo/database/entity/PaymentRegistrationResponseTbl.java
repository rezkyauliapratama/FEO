package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 1/20/2018.
 */
@Entity(nameInDb = "PaymentRegistrationResponseTbl",indexes = {})
public class PaymentRegistrationResponseTbl{
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = " PaymentRegResponseId")
    private Long PaymentRegResponseId;

    @Property(nameInDb = "PaymentRegId")
    private Long  PaymentRegId;

    @Property(nameInDb = " ChannelId ")
    private String  ChannelId ;

    @Property(nameInDb = "Currency")
    private String Currency;


    @Property(nameInDb = "InsertStatus")
    private String InsertStatus;

    @Property(nameInDb = "InsertMessage")
    private String InsertMessage;

    @Property(nameInDb = "InsertId")
    private Long InsertId;

    @Property(nameInDb = "AdditionalData")
    private String AdditionalData;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "UpdatedDate")
    private String UpdatedDate;

    @Property(nameInDb = "DeletedDate")
    private String DeletedDate;


    @Generated(hash = 1772374335)
    public PaymentRegistrationResponseTbl() {
    }

    @Generated(hash = 2083041111)
    public PaymentRegistrationResponseTbl(Long id, Long PaymentRegResponseId,
            Long PaymentRegId, String ChannelId, String Currency,
            String InsertStatus, String InsertMessage, Long InsertId,
            String AdditionalData, String CreatedDate, String UpdatedDate,
            String DeletedDate) {
        this.id = id;
        this.PaymentRegResponseId = PaymentRegResponseId;
        this.PaymentRegId = PaymentRegId;
        this.ChannelId = ChannelId;
        this.Currency = Currency;
        this.InsertStatus = InsertStatus;
        this.InsertMessage = InsertMessage;
        this.InsertId = InsertId;
        this.AdditionalData = AdditionalData;
        this.CreatedDate = CreatedDate;
        this.UpdatedDate = UpdatedDate;
        this.DeletedDate = DeletedDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentRegResponseId() {
        return this.PaymentRegResponseId;
    }

    public void setPaymentRegResponseId(Long PaymentRegResponseId) {
        this.PaymentRegResponseId = PaymentRegResponseId;
    }

    public Long getPaymentRegId() {
        return this.PaymentRegId;
    }

    public void setPaymentRegId(Long PaymentRegId) {
        this.PaymentRegId = PaymentRegId;
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

    public String getInsertStatus() {
        return this.InsertStatus;
    }

    public void setInsertStatus(String InsertStatus) {
        this.InsertStatus = InsertStatus;
    }

    public String getInsertMessage() {
        return this.InsertMessage;
    }

    public void setInsertMessage(String InsertMessage) {
        this.InsertMessage = InsertMessage;
    }

    public Long getInsertId() {
        return this.InsertId;
    }

    public void setInsertId(Long InsertId) {
        this.InsertId = InsertId;
    }

    public String getAdditionalData() {
        return this.AdditionalData;
    }

    public void setAdditionalData(String AdditionalData) {
        this.AdditionalData = AdditionalData;
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


}
