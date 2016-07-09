package mx.ivajotha.myapps.services;

import android.app.NotificationManager;
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

/**
 * Created by jonathan on 09/07/16.
 */
public class ServiceUninstall extends Service{
    public static final String ACTION_SEND_UNINSTALLED = "mx.ivajotha.myapps.ACTION_SEND_UNINSTALLED";
    private MyAsyncTask myAsyncTask;
    private static final int id = 1;

    private Handler myhandler = new Handler();
    private Runnable myrunnable = new Runnable() {
        @Override
        public void run() {
            myhandler.postDelayed(myrunnable,1500);
            Intent intent = new Intent(ACTION_SEND_UNINSTALLED);
            intent.putExtra("key_installed",false);
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
                    .setContentTitle(getString(R.string.title_serviceUninstall))
                    .setContentText(getString(R.string.desc_serviceUninstall))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_delete))
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
                mNotif.setContentTitle(getString(R.string.title_serviceUninstall_ok));
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
