package de.greendao.daoexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRIVINCE_CITY.
*/
public class PrivinceCityDao extends AbstractDao<PrivinceCity, Long> {

    public static final String TABLENAME = "PRIVINCE_CITY";

    /**
     * Properties of entity PrivinceCity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CountyName = new Property(1, String.class, "countyName", false, "COUNTY_NAME");
        public final static Property CountyCode = new Property(2, String.class, "countyCode", false, "COUNTY_CODE");
        public final static Property CityId = new Property(3, Long.class, "cityId", false, "CITY_ID");
    };


    public PrivinceCityDao(DaoConfig config) {
        super(config);
    }
    
    public PrivinceCityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRIVINCE_CITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'COUNTY_NAME' TEXT NOT NULL ," + // 1: countyName
                "'COUNTY_CODE' TEXT," + // 2: countyCode
                "'CITY_ID' INTEGER);"); // 3: cityId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRIVINCE_CITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PrivinceCity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCountyName());
 
        String countyCode = entity.getCountyCode();
        if (countyCode != null) {
            stmt.bindString(3, countyCode);
        }
 
        Long cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(4, cityId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PrivinceCity readEntity(Cursor cursor, int offset) {
        PrivinceCity entity = new PrivinceCity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // countyName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // countyCode
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // cityId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PrivinceCity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCountyName(cursor.getString(offset + 1));
        entity.setCountyCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCityId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PrivinceCity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PrivinceCity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}