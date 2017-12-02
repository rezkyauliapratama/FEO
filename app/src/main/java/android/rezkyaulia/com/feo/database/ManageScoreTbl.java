package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTblDao;

import java.util.List;

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


    public ScoreTbl get(long id) {
        return dao.queryBuilder().where(ScoreTblDao.Properties.Id.eq(id)).unique();
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
