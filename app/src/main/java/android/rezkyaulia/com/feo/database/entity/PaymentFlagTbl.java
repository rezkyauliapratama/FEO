package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 1/20/2018.
 */
@Entity(nameInDb = "PaymentFlagTbl",indexes = {})
public class PaymentFlagTbl {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "PaymentFlagId")
    private Long PaymentFlagId;

    @Property(nameInDb = " ChannelId ")
    private String  ChannelId ;

    @Property(nameInDb = "Currency")
    private String Currency;

    @Property(nameInDb = "TransactioNo")
    private String TransactioNo;

    @Property(nameInDb = "TransactionAmount")
    private Long TransactionAmount;

    @Property(nameInDb = "TransactionDate")
    private String TransactionDate;

    @Property(nameInDb = "ChannelType")
    private String ChannelType;

    @Property(nameInDb = "TransactionStatus")
    private String TransactionStatus;

    @Property(nameInDb = "TransactionMessage")
    private String TransactionMessage;

    @Property(nameInDb = "CustomerAccount")
    private Long CustomerAccount;

    @Property(nameInDb = "FlagType")
    private String FlagType;

    @Property(nameInDb = "InsertId")
    private Long InsertId;

    @Property(nameInDb = "PaymentReffId")
    private String PaymentReffId;

    @Property(nameInDb = "AuthCode")
    private String AuthCode;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Property(nameInDb = "UpdatedDate")
    private String UpdatedDate;

    @Property(nameInDb = "DeletedDate")
    private String DeletedDate;

    @Generated(hash = 1118171989)
    public PaymentFlagTbl(Long id, Long PaymentFlagId, String ChannelId,
            String Currency, String TransactioNo, Long TransactionAmount,
            String TransactionDate, String ChannelType, String TransactionStatus,
            String TransactionMessage, Long CustomerAccount, String FlagType,
            Long InsertId, String PaymentReffId, String AuthCode,
            String CreatedDate, String UpdatedDate, String DeletedDate) {
        this.id = id;
        this.PaymentFlagId = PaymentFlagId;
        this.ChannelId = ChannelId;
        this.Currency = Currency;
        this.TransactioNo = TransactioNo;
        this.TransactionAmount = TransactionAmount;
        this.TransactionDate = TransactionDate;
        this.ChannelType = ChannelType;
        this.TransactionStatus = TransactionStatus;
        this.TransactionMessage = TransactionMessage;
        this.CustomerAccount = CustomerAccount;
        this.FlagType = FlagType;
        this.InsertId = InsertId;
        this.PaymentReffId = PaymentReffId;
        this.AuthCode = AuthCode;
        this.CreatedDate = CreatedDate;
        this.UpdatedDate = UpdatedDate;
        this.DeletedDate = DeletedDate;
    }

    @Generated(hash = 913621889)
    public PaymentFlagTbl() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTransactioNo() {
        return this.TransactioNo;
    }

    public void setTransactioNo(String TransactioNo) {
        this.TransactioNo = TransactioNo;
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

    public String getChannelType() {
        return this.ChannelType;
    }

    public void setChannelType(String ChannelType) {
        this.ChannelType = ChannelType;
    }

    public String getTransactionStatus() {
        return this.TransactionStatus;
    }

    public void setTransactionStatus(String TransactionStatus) {
        this.TransactionStatus = TransactionStatus;
    }

    public String getTransactionMessage() {
        return this.TransactionMessage;
    }

    public void setTransactionMessage(String TransactionMessage) {
        this.TransactionMessage = TransactionMessage;
    }

    public Long getCustomerAccount() {
        return this.CustomerAccount;
    }

    public void setCustomerAccount(Long CustomerAccount) {
        this.CustomerAccount = CustomerAccount;
    }

    public String getFlagType() {
        return this.FlagType;
    }

    public void setFlagType(String FlagType) {
        this.FlagType = FlagType;
    }

    public Long getInsertId() {
        return this.InsertId;
    }

    public void setInsertId(Long InsertId) {
        this.InsertId = InsertId;
    }

    public String getPaymentReffId() {
        return this.PaymentReffId;
    }

    public void setPaymentReffId(String PaymentReffId) {
        this.PaymentReffId = PaymentReffId;
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






}
