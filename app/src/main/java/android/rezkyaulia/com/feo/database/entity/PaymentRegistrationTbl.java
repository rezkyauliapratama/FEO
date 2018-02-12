package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 1/20/2018.
 */

@Entity(nameInDb = "PaymentRegistrationTbl",indexes = {})
public class PaymentRegistrationTbl {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "PaymentRegId")
    private Long PaymentRegId;

    @Property(nameInDb = "SubscriptionId")
    private Long SubscriptionId;

    @Property(nameInDb = " ChannelId ")
    private String  ChannelId ;

    @Property(nameInDb = "Currency")
    private String Currency;


    @Property(nameInDb = "TransactionNo")
    private String TransactionNo;

    @Property(nameInDb = "TransactionAmount")
    private Long TransactionAmount;

    @Property(nameInDb = "TransactionDate")
    private String TransactionDate;

    @Property(nameInDb = "TransactionExpire")
    private String TransactionExpire;

    @Property(nameInDb = "Description")
    private String Description;

    @Property(nameInDb = "CustomerAccount")
    private String CustomerAccount;

    @Property(nameInDb = "CustomerName")
    private String CustomerName;

    @Property(nameInDb = "AuthCode")
    private String AuthCode;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "UpdatedDate")
    private String UpdatedDate;

    @Property(nameInDb = "DeletedDate")
    private String DeletedDate;

    @Generated(hash = 561197319)
    public PaymentRegistrationTbl() {
    }


    @Generated(hash = 1378524638)
    public PaymentRegistrationTbl(Long id, Long PaymentRegId, Long SubscriptionId,
            String ChannelId, String Currency, String TransactionNo,
            Long TransactionAmount, String TransactionDate,
            String TransactionExpire, String Description, String CustomerAccount,
            String CustomerName, String AuthCode, String CreatedDate,
            String UpdatedDate, String DeletedDate) {
        this.id = id;
        this.PaymentRegId = PaymentRegId;
        this.SubscriptionId = SubscriptionId;
        this.ChannelId = ChannelId;
        this.Currency = Currency;
        this.TransactionNo = TransactionNo;
        this.TransactionAmount = TransactionAmount;
        this.TransactionDate = TransactionDate;
        this.TransactionExpire = TransactionExpire;
        this.Description = Description;
        this.CustomerAccount = CustomerAccount;
        this.CustomerName = CustomerName;
        this.AuthCode = AuthCode;
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

    public Long getPaymentRegId() {
        return this.PaymentRegId;
    }

    public void setPaymentRegId(Long PaymentRegId) {
        this.PaymentRegId = PaymentRegId;
    }

    public Long getSubscriptionId() {
        return this.SubscriptionId;
    }

    public void setSubscriptionId(Long SubscriptionId) {
        this.SubscriptionId = SubscriptionId;
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

   

    public Long getTransactionAmount() {
        return this.TransactionAmount;
    }

    public void setTransactionAmount(Long TransactionAmount) {
        this.TransactionAmount = TransactionAmount;
    }

    public String getTransactionDate() {
        return this.TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getTransactionExpire() {
        return this.TransactionExpire;
    }

    public void setTransactionExpire(String TransactionExpire) {
        this.TransactionExpire = TransactionExpire;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCustomerName() {
        return this.CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getAuthCode() {
        return this.AuthCode;
    }

    public void setAuthCode(String AuthCode) {
        this.AuthCode = AuthCode;
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

    public String getTransactionNo() {
        return this.TransactionNo;
    }

    public void setTransactionNo(String TransactionNo) {
        this.TransactionNo = TransactionNo;
    }


    public String getCustomerAccount() {
        return this.CustomerAccount;
    }


    public void setCustomerAccount(String CustomerAccount) {
        this.CustomerAccount = CustomerAccount;
    }




}
