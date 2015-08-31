package de.greendao.daoexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table WEATHER.
*/
public class WeatherDao extends AbstractDao<Weather, Long> {

    public static final String TABLENAME = "WEATHER";

    /**
     * Properties of entity Weather.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property City = new Property(1, String.class, "city", false, "CITY");
        public final static Property Cityid = new Property(2, String.class, "cityid", false, "CITYID");
        public final static Property Temp1 = new Property(3, String.class, "temp1", false, "TEMP1");
        public final static Property Temp2 = new Property(4, String.class, "temp2", false, "TEMP2");
        public final static Property Weather = new Property(5, String.class, "weather", false, "WEATHER");
        public final static Property Ptime = new Property(6, String.class, "ptime", false, "PTIME");
    };


    public WeatherDao(DaoConfig config) {
        super(config);
    }
    
    public WeatherDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'WEATHER' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'CITY' TEXT," + // 1: city
                "'CITYID' TEXT," + // 2: cityid
                "'TEMP1' TEXT," + // 3: temp1
                "'TEMP2' TEXT," + // 4: temp2
                "'WEATHER' TEXT," + // 5: weather
                "'PTIME' TEXT);"); // 6: ptime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'WEATHER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Weather entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(2, city);
        }
 
        String cityid = entity.getCityid();
        if (cityid != null) {
            stmt.bindString(3, cityid);
        }
 
        String temp1 = entity.getTemp1();
        if (temp1 != null) {
            stmt.bindString(4, temp1);
        }
 
        String temp2 = entity.getTemp2();
        if (temp2 != null) {
            stmt.bindString(5, temp2);
        }
 
        String weather = entity.getWeather();
        if (weather != null) {
            stmt.bindString(6, weather);
        }
 
        String ptime = entity.getPtime();
        if (ptime != null) {
            stmt.bindString(7, ptime);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Weather readEntity(Cursor cursor, int offset) {
        Weather entity = new Weather( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // city
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // cityid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // temp1
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // temp2
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // weather
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // ptime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Weather entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCity(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCityid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTemp1(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTemp2(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWeather(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPtime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Weather entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Weather entity) {
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
