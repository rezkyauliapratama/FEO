package android.rezkyaulia.com.feo.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rezky Aulia Pratama on 11/21/2017.
 */
@Entity(nameInDb = "ScoreTbl",indexes = {})
public class ScoreTbl implements Parcelable{
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "ScoreId")
    private Long ScoreId;

    @Property(nameInDb = "UserId")
    private Long UserId;

    @Property(nameInDb = "Score")
    private int Score;

    @Property(nameInDb = "CreatedDate")
    private String CreatedDate;

    @Generated(hash = 1438947287)
    public ScoreTbl(Long id, Long ScoreId, Long UserId, int Score,
            String CreatedDate) {
        this.id = id;
        this.ScoreId = ScoreId;
        this.UserId = UserId;
        this.Score = Score;
        this.CreatedDate = CreatedDate;
    }

    @Generated(hash = 1017319051)
    public ScoreTbl() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScoreId() {
        return this.ScoreId;
    }

    public void setScoreId(Long ScoreId) {
        this.ScoreId = ScoreId;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getCreatedDate() {
        return this.CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    //parcalable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.ScoreId);
        dest.writeValue(this.UserId);
        dest.writeInt(this.Score);
        dest.writeString(this.CreatedDate);
    }

    protected ScoreTbl(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.ScoreId = (Long) in.readValue(Long.class.getClassLoader());
        this.UserId = (Long) in.readValue(Long.class.getClassLoader());
        this.Score = in.readInt();
        this.CreatedDate = in.readString();
    }

    public static final Creator<ScoreTbl> CREATOR = new Creator<ScoreTbl>() {
        @Override
        public ScoreTbl createFromParcel(Parcel source) {
            return new ScoreTbl(source);
        }

        @Override
        public ScoreTbl[] newArray(int size) {
            return new ScoreTbl[size];
        }
    };
}
