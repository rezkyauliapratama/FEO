package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTblDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import static android.rezkyaulia.com.feo.database.entity.ScoreTblDao.Properties.CreatedDate;

/**
 * Created by Rezky Aulia Pratama on 11/21/2017.
 */

public class ManageScoreTbl {


    private Facade facade;


    private ScoreTblDao dao;

    ManageScoreTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getScoreTblDao();
    }

    public long add(ScoreTbl object) {
        return dao.insert(object);
    }

    public void add(List<ScoreTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<ScoreTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public List<ScoreTbl> getAllEmptyScoreId() {
        return dao.queryBuilder().where(ScoreTblDao.Properties.ScoreId.isNull()).list();
    }


    public List<ScoreTbl> getDataForGraph(){
        /*
        SELECT ScoreTbl.*
        FROM
         ScoreTbl
        WHERE
        ScoreTbl.FlagAnswer = 1
        GROUP BY strftime('%Y-%m-%d', CreatedDate)
        ORDER BY Score desc
        Limit 7

         */

        return dao.queryBuilder().where(new WhereCondition.StringCondition("_id in (SELECT a._id\n" +
                "FROM\n" +
                " (SELECT b.*, strftime('%Y-%m-%d', b.CreatedDate) dte_normal from ScoreTbl b\n" +
                "                                                                      WHERE\n" +
                "                                                                       b.FlagAnswer = 1\n" +
                "                                                                      GROUP BY dte_normal\n" +
                "                                                                      ORDER BY b.Score asc) a\n" +
                "GROUP BY strftime('%Y-%m-%d', a.CreatedDate)\n" +
                "ORDER BY strftime('%Y-%m-%d', a.CreatedDate) desc\n" +
                "Limit 7)")).list();
    }
    public ScoreTbl get(long id) {
        return dao.queryBuilder().where(ScoreTblDao.Properties.Id.eq(id)).unique();
    }

    public ScoreTbl getHighScore() {
        return dao.queryBuilder().where(ScoreTblDao.Properties.UserId.eq(facade.getManagerUserTbl().get().getUserId()),ScoreTblDao.Properties.FlagAnswer.eq(1)).orderDesc(ScoreTblDao.Properties.Score).limit(1).unique();
    }


    public List<ScoreTbl> getAllData() {
        return dao.queryBuilder().orderDesc(ScoreTblDao.Properties.CreatedDate).list();
    }

    public ScoreTbl getHighScore(String guid) {
        return dao.queryBuilder().where(ScoreTblDao.Properties.Guid.eq(guid)).orderDesc(ScoreTblDao.Properties.Score).limit(1).unique();
    }

    public List<ScoreTbl> getbyGuid(String guid) {
        return dao.queryBuilder().where(ScoreTblDao.Properties.Guid.eq(guid)).list();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(ScoreTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
