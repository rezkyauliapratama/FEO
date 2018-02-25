package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 12/26/2017.
 */
@Entity(nameInDb = "NotificationTbl",indexes = {@Index(value = "NotificationId", unique = true)})
public class NotificationTbl {
    @Id(autoincrement = true)
    @Property(nameInDb = "NotificationId")
    private Long NotificationId;

    @Property(nameInDb = "UserId")
    private Long UserId;

    @Property(nameInDb = "Title")
    private String Title;

    @Property(nameInDb = "Body")
    private String Body;

    @Property(nameInDb = "Action")
    private Long Action;

    @Property(nameInDb = "FlagRead")
    private int FlagRead = 1;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;


    @Generated(hash = 513918077)
    public NotificationTbl(Long NotificationId, Long UserId, String Title, String Body, Long Action,
            int FlagRead, String CreatedDate) {
        this.NotificationId = NotificationId;
        this.UserId = UserId;
        this.Title = Title;
        this.Body = Body;
        this.Action = Action;
        this.FlagRead = FlagRead;
        this.CreatedDate = CreatedDate;
    }

    @Generated(hash = 673865620)
    public NotificationTbl() {
    }


    public Long getNotificationId() {
        return this.NotificationId;
    }

    public void setNotificationId(Long NotificationId) {
        this.NotificationId = NotificationId;
    }

    public Long getUserId() {
        return this.UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getBody() {
        return this.Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    public Long getAction() {
        return this.Action;
    }

    public void setAction(Long Action) {
        this.Action = Action;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getFlagRead() {
        return this.FlagRead;
    }

    public void setFlagRead(int FlagRead) {
        this.FlagRead = FlagRead;
    }







}
