package mx.ivajotha.myapps.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jonathan on 04/07/16.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME ="myApps_bd";
    private final static int DATABASE_VERSION=1;
    public static final String TABLE_NAME ="myapps";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_APP_NAME = "name";
    public static final String COLUMN_APP_DETAILS = "details";
    public static final String COLUMN_APP_RESOURCE = "resource_id";
    public static final String COLUMN_APP_NAMEDEV = "nameDeveloper";
    public static final String COLUMN_APP_INSTALLED = "installed";
    public static final String COLUMN_APP_UPDATED = "updated";

    private static final String CREATE_TABLE ="create table "+TABLE_NAME+
            "("+COLUMN_ID+" integer primary key autoincrement,"+
            COLUMN_APP_NAME+" TEXT NOT NULL,"+
            COLUMN_APP_DETAILS+ " TEXT NOT NULL,"+
            COLUMN_APP_RESOURCE+ " INT NOT NULL,"+
            COLUMN_APP_NAMEDEV+" TEXT NOT NULL,"+
            COLUMN_APP_INSTALLED+ " BOOLEAN NOT NULL,"+
            COLUMN_APP_UPDATED+ " BOOLEAN NOT NULL)";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete(CREATE_TABLE, null, null);

    }
}
