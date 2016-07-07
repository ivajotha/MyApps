package mx.ivajotha.myapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_APP_ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Add Toolbar*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
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
                //return true;
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_APP_ADD == requestCode && resultCode == RESULT_OK){

            long respActAdd = data.getExtras().getLong("key_install", -1);
            String nameAppInstalled = data.getExtras().getString("key_nameApp","");

            if(respActAdd != -1)
                Toast.makeText(getApplicationContext(),"Respuesta Correcta, cargamos la lista...", Toast.LENGTH_SHORT).show();

                String fielRequired = getResources().getString(R.string.title_lastInstalledApp);
                String titleToobar = fielRequired + " " + nameAppInstalled.toUpperCase();
                getSupportActionBar().setTitle(titleToobar);

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
