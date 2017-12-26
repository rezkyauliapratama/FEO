package android.rezkyaulia.com.feo.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 12/26/2017.
 */
@Entity(nameInDb = "NotificationTbl",indexes = {@Index(value = "_id,NotificationId", unique = true)})
public class NotificationTbl {
    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    private Long _id;

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

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Generated(hash = 646150182)
    public NotificationTbl(Long _id, Long NotificationId, Long UserId, String Title, String Body,
            Long Action, String CreatedDate) {
        this._id = _id;
        this.NotificationId = NotificationId;
        this.UserId = UserId;
        this.Title = Title;
        this.Body = Body;
        this.Action = Action;
        this.CreatedDate = CreatedDate;
    }

    @Generated(hash = 673865620)
    public NotificationTbl() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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






}
