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

    private Button btnUninstall;
    private Button btnUpdOpen;

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

        btnUninstall = (Button)view.findViewById(R.id.fragDet_btnUninstall);
        btnUninstall.setOnClickListener(this);

        btnUpdOpen = (Button)view.findViewById(R.id.fragDet_btnUpd_open);
        btnUpdOpen.setOnClickListener(this);

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
                getActivity().startService(new Intent(getActivity(), ServiceUninstall.class));
                Toast.makeText(getActivity(),getResources().getString(R.string.hit_appStartingUnins), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fragDet_btnUpd_open:
                switch (isUpdate){
                    case 0:
                        getActivity().startService(new Intent(getActivity(), ServiceUpdate.class));
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
}
