package mx.ivajotha.myapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.sql.ItemDataSource;

/**
 * Created by jonathan on 06/07/16.
 */
public class AddActivity extends AppCompatActivity{
    private EditText appName;
    private EditText appNameDev;
    private EditText appDetails;
    private CheckBox appIsUpdate;

    private ItemDataSource itemDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        /* Instance DB */
        itemDataSource = new ItemDataSource(getApplicationContext());

        /* Add Toolbar*/
        Toolbar mytoolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /* Sets views*/
        appName = (EditText)findViewById(R.id.actAdd_appName);
        appNameDev = (EditText)findViewById(R.id.actAdd_appNameDev);
        appDetails = (EditText)findViewById(R.id.actAdd_appDetails);
        appIsUpdate = (CheckBox)findViewById(R.id.actAdd_appUpdate);

        findViewById(R.id.actAdd_btnAddApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(appName.getText().toString()) &&
                        !TextUtils.isEmpty(appNameDev.getText().toString()) &&
                        !TextUtils.isEmpty(appDetails.getText().toString())){

                    String appName_ = appName.getText().toString();
                    String appNameDev_ = appNameDev.getText().toString();
                    String appDetails_ = appDetails.getText().toString();
                    Boolean appUpdated_ = appIsUpdate.isChecked() ? true : false;
                    Boolean appInstaled_ = true;

                    Integer intRandom = (int)( Math.random() * 4 );
                    Integer resourcesId = getRandomImg(intRandom);

                    ModelAppList modelAppList = new ModelAppList();
                    modelAppList.name = appName_;
                    modelAppList.nameDeveloper = appNameDev_;
                    modelAppList.details = appDetails_;
                    modelAppList.installed = appInstaled_;
                    modelAppList.updated = appUpdated_;

                   itemDataSource.addApp(modelAppList);

/*
    long result = appDataSource.saveApp(modelApp);
                    modelItemList.resourceId = isWifi? R.drawable.ic_device_signal_wifi_4_bar: R.drawable.ic_action_settings_voice;
                    itemDS.saveItemList(modelItemList);
                    listView.setAdapter(new AdapterItemList(getActivity(),itemDS.getallItems()));


                     */






                }else{
                    Toast.makeText(getApplicationContext(),R.string.msgEmptyViews, Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    public int getRandomImg(Integer integer){
        final int[ ] myImageId = {
                R.drawable.ic_action_bug_report,
                R.drawable.ic_action_face_unlock,
                R.drawable.ic_action_perm_camera_mic,
                R.drawable.ic_action_translate,
                R.drawable.ic_app_ramdom_1
        };

        return myImageId[integer];

    }

    /** Methods Toolbar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
