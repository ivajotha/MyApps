package mx.ivajotha.myapps.fragment;

import android.app.Fragment;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;
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

    private Button btnOpenUrl;
    private Button btnUpdUnins;

    private ItemDataSource itemDataSource;

    public static FragmentDetails newInstance(ModelAppList modelAppList)
    {
        FragmentDetails fragmentDetails = new FragmentDetails();
        Bundle bundle = new Bundle();

        bundle.putInt("key_idApp", modelAppList.id);
        bundle.putString("key_nameApp", modelAppList.name);
        bundle.putString("key_nameDevApp", modelAppList.nameDeveloper);
        bundle.putString("key_detailsApp", modelAppList.details);
        bundle.putInt("key_rescIdApp",modelAppList.resourceId);
        bundle.putInt("key_isUpdateApp",modelAppList.updated);

        fragmentDetails.setArguments(bundle);

        return fragmentDetails;
    }

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

        btnOpenUrl = (Button)view.findViewById(R.id.fragDet_btnOpenUrl);
        btnOpenUrl.setOnClickListener(this);

        btnUpdUnins = (Button)view.findViewById(R.id.fragDet_btnUpd_Unins);
        btnUpdUnins.setOnClickListener(this);

        isUpdate = getArguments().getInt("key_isUpdateApp");
        switch (isUpdate){
            case 1:
                btnUpdUnins.setText(getResources().getString(R.string.hit_appUpdate));
                break;

            case 0:
                btnUpdUnins.setText(getResources().getString(R.string.hit_UnInstall));
                break;
        }
/*
        int idApp = getIntent().getExtras().getInt("key_idApp");
        String nameApp = getIntent().getExtras().getString("key_nameApp");
        String nameDevApp = getIntent().getExtras().getString("key_nameDevApp");
        String detailsApp = getIntent().getExtras().getString("key_detailsApp");
        int rescIdApp = getIntent().getExtras().getInt("key_rescIdApp");
        int isUpdateApp = getIntent().getExtras().getInt("key_isUpdateApp");
*/


/*      bundle.putInt(Keys.KEY_APP_ID, modelApp.id);
        bundle.putString(Keys.KEY_APP_DEVELOPER, modelApp.appDeveloperName);
        bundle.putString(Keys.KEY_APP_NAME, modelApp.appName);
        bundle.putInt(Keys.KEY_APP_RESOURCEID, modelApp.appResourceId);
        bundle.putInt(Keys.KEY_APP_UPDATED, modelApp.appUpdated);
        bundle.putString(Keys.KEY_APP_DETAIL, modelApp.appDetail);
*/

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragDet_btnOpenUrl :
                Intent myintent = new Intent(Intent.ACTION_VIEW);
                myintent.setData(Uri.parse("http://dcd.tic.unam.mx/moodlecad/"));
                getActivity().startActivity(myintent);
                break;

            case R.id.fragDet_btnUpd_Unins:
                switch (isUpdate){
                    case 1:
                        getActivity().startService(new Intent(getActivity(), ServiceUpdate.class));
                        Toast.makeText(getActivity(),"Click Actualizar", Toast.LENGTH_SHORT).show();
                        break;

                    case 0:
                        //getActivity().startService(new Intent(getActivity(), ServiceUninstal.class));
                        Toast.makeText(getActivity(),"Click Desintalar", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;

        }

    }
}
