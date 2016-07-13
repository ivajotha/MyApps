package mx.ivajotha.myapps.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import mx.ivajotha.myapps.R;
import mx.ivajotha.myapps.fragment.FragmentDetails;
import mx.ivajotha.myapps.sql.ItemDataSource;

/**
 * Created by jonathan on 09/07/16.
 */
public class ServiceUpdate extends Service{

    public static final String ACTION_SEND_UPDATED = "mx.ivajotha.myapps.SEND_UPDATED";
    private MyAsyncTask myAsyncTask;
    private static final int id = 0;

    private int id_app;
    private ItemDataSource itemDataSource;

    private Handler myhandler = new Handler();
    private Runnable myrunnable = new Runnable() {
        @Override
        public void run() {
            myhandler.postDelayed(myrunnable,1500);
            Intent intent = new Intent(ACTION_SEND_UPDATED);
            intent.putExtra("key_update",true);
            sendBroadcast(intent);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        myhandler.post(myrunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myhandler.removeCallbacks(myrunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        itemDataSource = new ItemDataSource(getApplicationContext());

        if(intent.getExtras()!=null && intent.getExtras().containsKey("key_id")){

            id_app = intent.getExtras().getInt("key_id");
            itemDataSource.updateApp(id_app);
        }

        if(myAsyncTask==null)
        {
            myAsyncTask= new MyAsyncTask();
            myAsyncTask.execute();
        }

        return START_STICKY;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean>
    {
        private NotificationCompat.Builder mNotif;

        @Override
        protected void onPreExecute() {

            mNotif = new NotificationCompat
                    .Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.title_serviceUdate))
                    .setContentText(getString(R.string.desc_serviceUdate))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_autorenew))
                    .setSmallIcon(android.R.drawable.ic_dialog_email);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            for (int i=0;i<6;i++)
            {
                publishProgress(i);
                try {
                    Thread.sleep(1000*1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mNotif.setProgress(6,values[0],false);
            NotificationManager manager  = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(id,mNotif.build());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                mNotif.setProgress(0,0,false);
                mNotif.setContentTitle(getString(R.string.title_serviceUdate_ok));
                mNotif.setContentText(getString(R.string.desc_serviceUdate_ok));
                mNotif.setAutoCancel(true);
                NotificationManager manager  = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(id,mNotif.build());
            }
            myAsyncTask=null;
            stopSelf();
        }
    }
}
