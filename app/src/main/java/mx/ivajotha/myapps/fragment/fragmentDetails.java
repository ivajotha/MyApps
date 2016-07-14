package mx.ivajotha.myapps.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;
import mx.ivajotha.myapps.services.ServiceUninstall;
import mx.ivajotha.myapps.services.ServiceUpdate;
import mx.ivajotha.myapps.sql.ItemDataSource;

/**
 * Created by jonathan on 07/07/16.
 */
public class FragmentDetails extends Fragment implements View.OnClickListener {

    private TextView appName;
    private TextView nameDev;
    private TextView details;
    private ImageView resourceId;
    private Integer isUpdate;
    private static Integer idApp;
    private Button btnUninstall;
    private Button btnUpdOpen;

    private ProgressBar progressbar;

    //private ItemDataSource itemDataSource;


    public static FragmentDetails newInstance(ModelAppList modelAppList)
    {
        FragmentDetails fragmentDetails = new FragmentDetails();
        Bundle bundle = new Bundle();

        idApp = modelAppList.id;
        bundle.putInt("key_idApp", modelAppList.id);
        bundle.putString("key_nameApp", modelAppList.name);
        bundle.putString("key_nameDevApp", modelAppList.nameDeveloper);
        bundle.putString("key_detailsApp", modelAppList.details);
        bundle.putInt("key_rescIdApp",modelAppList.resourceId);
        bundle.putInt("key_isUpdateApp",modelAppList.updated);

        fragmentDetails.setArguments(bundle);

        return fragmentDetails;
    }


    private BroadcastReceiver broadcastUninstall = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean key_installed = intent.getExtras().getBoolean("key_installed");
            if(key_installed){
                btnUninstall.setVisibility(View.INVISIBLE);
                btnUpdOpen.setVisibility(View.INVISIBLE);
                progressbar.setVisibility(View.GONE);
                //itemDataSource.deleteApp(idApp);
                getActivity().finish();
            }
        }
    };


    private BroadcastReceiver broadcastUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean key_update = intent.getExtras().getBoolean("key_update");
            if (key_update){
                Integer updated_ = 1;
                isUpdate = updated_;
                progressbar.setVisibility(View.GONE);
                btnUpdOpen.setText(getResources().getString(R.string.hit_appOpen));

            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,container,false);


        appName = (TextView) view.findViewById(R.id.fragDet_appName);
        appName.setText(getArguments().getString("key_nameApp"));

        nameDev = (TextView) view.findViewById(R.id.fragDet_appNameDev);
        nameDev.setText(getArguments().getString("key_nameDevApp"));

        details = (TextView) view.findViewById(R.id.fragDet_appDetails);
        details.setText(getArguments().getString("key_detailsApp"));

        resourceId = (ImageView) view.findViewById(R.id.fragDet_appImg);
        resourceId.setImageResource(getArguments().getInt("key_rescIdApp"));

        btnUninstall = (Button)view.findViewById(R.id.fragDet_btnUninstall);
        btnUninstall.setOnClickListener(this);

        btnUpdOpen = (Button)view.findViewById(R.id.fragDet_btnUpd_open);
        btnUpdOpen.setOnClickListener(this);

        progressbar = (ProgressBar) view.findViewById(R.id.fragDet_precess);

        isUpdate = getArguments().getInt("key_isUpdateApp");
        switch (isUpdate){
            case 0:
                btnUpdOpen.setText(getResources().getString(R.string.hit_appUpdate));
                break;

            case 1:
                btnUpdOpen.setText(getResources().getString(R.string.hit_appOpen));
                break;
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragDet_btnUninstall:
                //progressbar.setVisibility(View.VISIBLE);
                //getActivity().startService(new Intent(getActivity(), ServiceUninstall.class));
                //Toast.makeText(getActivity(),getResources().getString(R.string.hit_appStartingUnins), Toast.LENGTH_SHORT).show();

                /* Dialog Notification  */
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.txt_btnUnistall)
                        .setMessage(R.string.txt_dialogUnistall)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressbar.setVisibility(View.VISIBLE);

                                getActivity().startService(new Intent(getActivity(), ServiceUninstall.class).putExtra("key_id",getArguments().getInt("key_idApp")));
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }})
                        .setCancelable(false).create().show();

                /* End Dialog Notification */
                break;

            case R.id.fragDet_btnUpd_open:
                switch (isUpdate){
                    case 0:
                        progressbar.setVisibility(View.VISIBLE);
                        //getActivity().startService(new Intent(getActivity(), ServiceUpdate.class));
                        getActivity().startService(new Intent(getActivity(), ServiceUpdate.class).putExtra("key_id",getArguments().getInt("key_idApp")));
                        Toast.makeText(getActivity(),getResources().getString(R.string.hit_appStartingUpd), Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Intent myintent = new Intent(Intent.ACTION_VIEW);
                        myintent.setData(Uri.parse("http://dcd.tic.unam.mx/moodlecad/"));
                        getActivity().startActivity(myintent);
                        break;
                }
                break;

        }

    }

    /** Methods Live activity**/

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ServiceUninstall.ACTION_SEND_UNINSTALLED);
        getActivity().registerReceiver(broadcastUninstall,intentFilter);

        IntentFilter intentFilter_ = new IntentFilter();
        intentFilter_.addAction(ServiceUpdate.ACTION_SEND_UPDATED);
        getActivity().registerReceiver(broadcastUpdate,intentFilter_);
    }

    @Override
    public void onPause() {

        super.onPause();
        getActivity().unregisterReceiver(broadcastUninstall);
        getActivity().unregisterReceiver(broadcastUpdate);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(),ServiceUninstall.class));
        getActivity().stopService(new Intent(getActivity(),ServiceUpdate.class));
    }

}
