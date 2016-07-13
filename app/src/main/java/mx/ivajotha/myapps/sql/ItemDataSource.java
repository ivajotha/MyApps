package mx.ivajotha.myapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public long addApp(ModelAppList modelAppList)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelAppList.name);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAILS, modelAppList.details);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelAppList.resourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAMEDEV, modelAppList.nameDeveloper);
        contentValues.put(MySqliteHelper.COLUMN_APP_INSTALLED, modelAppList.installed);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelAppList.updated);

        long resultInsert = db.insert(MySqliteHelper.TABLE_NAME,null,contentValues);

        return resultInsert;
    }


    public void deleteApp(Integer IdApp)
    {
        db.delete(MySqliteHelper.TABLE_NAME,MySqliteHelper.COLUMN_ID+"=?",
                new String[]{String.valueOf(IdApp)});
    }

    public void updateApp(Integer IdApp){

        Integer  updated = 1;

        ContentValues cv = new ContentValues();
        cv.put(MySqliteHelper.COLUMN_APP_UPDATED,updated);
        String selection = "_id="+IdApp;
        db.update(MySqliteHelper.TABLE_NAME, cv, selection, null);

    }

    public List<ModelAppList> getAllApps(){

        List<ModelAppList> modelAppLists = new ArrayList<>();

        Cursor cursor= db.query(MySqliteHelper.TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            int id_app = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ID));
            String name_app =cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_NAME));
            String details_app = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DETAILS));
            String namedev_app = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_NAMEDEV));
            int resId_app = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_RESOURCE));
            int isUdpate_app = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_UPDATED));
            ModelAppList modelAppList = new ModelAppList();
            modelAppList.id = id_app;
            modelAppList.name = name_app;
            modelAppList.details = details_app;
            modelAppList.nameDeveloper = namedev_app;
            modelAppList.resourceId = resId_app;
            modelAppList.updated = isUdpate_app;
            modelAppLists.add(modelAppList);
        }

        return  modelAppLists;
    }


}
