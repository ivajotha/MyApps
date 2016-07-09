package mx.ivajotha.myapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.adapter.AdapterAppList;
import mx.ivajotha.myapps.sql.ItemDataSource;

public class MainActivity extends AppCompatActivity {

    private ItemDataSource itemDataSource;
    private ListView listView;
    private TextView textView;


    private static final int REQUEST_APP_ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Add Toolbar*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        /* set Views*/
        itemDataSource = new ItemDataSource(getApplicationContext());
        listView = (ListView)findViewById(R.id.contenApps);
        textView = (TextView) findViewById(R.id.actAdd_msgEmtyApps);
        getListApps();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AdapterAppList adapterAppList = (AdapterAppList)parent.getAdapter();
                ModelAppList modelAppList = adapterAppList.getItem(position);
                Intent intentApp = new Intent(getApplicationContext(),AppActivity.class);
                intentApp.putExtra("key_idApp", modelAppList.id);
                intentApp.putExtra("key_nameApp", modelAppList.name);
                intentApp.putExtra("key_detailsApp", modelAppList.details);
                intentApp.putExtra("key_nameDevApp", modelAppList.nameDeveloper);
                intentApp.putExtra("key_rescIdApp", modelAppList.resourceId);
                intentApp.putExtra("key_isUpdateApp", modelAppList.updated);
                startActivity(intentApp);
            }
        });

    }

    /* Load ListView*/
    @Override
    protected void onResume() {
        super.onResume();
        getListApps();
    }

    /* Inflate Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_add:
                startActivityForResult(new Intent(getApplicationContext(), AddActivity.class),REQUEST_APP_ADD);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_APP_ADD == requestCode && resultCode == RESULT_OK){

            long respActAdd = data.getExtras().getLong("key_install", -1);
            String nameAppInstalled = data.getExtras().getString("key_nameApp","");

            if(respActAdd != -1) {
                String fielRequired = getResources().getString(R.string.title_lastInstalledApp);
                String titleToobar = fielRequired + " " + nameAppInstalled.toUpperCase();
                getSupportActionBar().setTitle(titleToobar);
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    /* Method inflated ListView*/
    public void getListApps(){
        List<ModelAppList> modelAppLists = itemDataSource.getAllApps();
        if(modelAppLists.size() > 0 ){
            textView.setVisibility(View.INVISIBLE);
            listView.setAdapter(new AdapterAppList(getApplicationContext(),modelAppLists));
        }else{
            textView.setVisibility(View.VISIBLE);
        }
    }


}
