package mx.ivajotha.myapps.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;
import mx.ivajotha.myapps.sql.ItemDataSource;

/**
 * Created by jonathan on 07/07/16.
 */
public class FragmentDetails extends Fragment{

    private TextView appName;
    private TextView nameDev;
    private TextView details;
    private Integer resourceId;
    private CheckBox isUpdate;

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

}
