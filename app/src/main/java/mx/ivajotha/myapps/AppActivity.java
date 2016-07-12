package mx.ivajotha.myapps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.fragment.FragmentDetails;
import mx.ivajotha.myapps.services.ServiceUninstall;

/**
 * Created by jonathan on 07/07/16.
 */
public class AppActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        /* Add Toolbar*/
        Toolbar mytoolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        int idApp = getIntent().getExtras().getInt("key_idApp");
        String nameApp = getIntent().getExtras().getString("key_nameApp");

        String detailsApp = getIntent().getExtras().getString("key_detailsApp");
        String nameDevApp = getIntent().getExtras().getString("key_nameDevApp");
        int rescIdApp = getIntent().getExtras().getInt("key_rescIdApp");
        int isUpdateApp = getIntent().getExtras().getInt("key_isUpdateApp");
        Boolean instaledApp = true;
        /* Set Title Toolbar*/
        String fielRequired = getResources().getString(R.string.hit_appDetails);
        String titleToobar = fielRequired + ": " + nameApp;
        getSupportActionBar().setTitle(titleToobar.toUpperCase());


        ModelAppList modelAppList = new ModelAppList();
        modelAppList.id = idApp;
        modelAppList.name = nameApp;
        modelAppList.details = detailsApp;
        modelAppList.nameDeveloper = nameDevApp;
        modelAppList.resourceId = rescIdApp;
        modelAppList.updated = isUpdateApp;
        modelAppList.installed =  instaledApp;

        FragmentDetails fragmentDetail = FragmentDetails.newInstance(modelAppList);
        getFragmentManager().beginTransaction().replace(R.id.fragmentApp,fragmentDetail).commit();

    }

    /** Methods Toolbar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app,menu);
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
