package mx.ivajotha.myapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import mx.ivajotha.myapps.Model.ModelAppList;

/**
 * Created by jonathan on 04/07/16.
 */
public class ItemDataSource {

    private final SQLiteDatabase db;

    public ItemDataSource(Context context) {
        MySqliteHelper helper = new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }

    public void addApp(ModelAppList modelAppList)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelAppList.name);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAILS, modelAppList.details);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelAppList.resourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAMEDEV, modelAppList.nameDeveloper);
        contentValues.put(MySqliteHelper.COLUMN_APP_INSTALLED, modelAppList.installed);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelAppList.updated);
        db.insert(MySqliteHelper.TABLE_NAME,null,contentValues);
    }

    public void deleteApp(ModelAppList modelAppList)
    {
        db.delete(MySqliteHelper.TABLE_NAME,MySqliteHelper.COLUMN_ID+"=?",
                new String[]{String.valueOf(modelAppList.id)});
    }


}
