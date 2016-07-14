package mx.ivajotha.myapps.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;
import mx.ivajotha.myapps.sql.ItemDataSource;

/**
 * Created by jonathan on 14/07/16.
 */
public class FragmentEdit extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container,false);


        return view;
    }

    @Override
    public void onClick(View view) {

    }

    /* Method set views */
    public static FragmentEdit newInstance(ModelAppList modelAppList) {
        
        Bundle args = new Bundle();
        FragmentEdit fragment = new FragmentEdit();

        args.putInt("key_Id", modelAppList.id);
        args.putString("key_nameApp",modelAppList.name);
        args.putString("key_nameDev",modelAppList.nameDeveloper);
        args.putString("key_detailsApp",modelAppList.details);
        args.putInt("key_updateApp",modelAppList.updated);
        fragment.setArguments(args);
        return fragment;
    }
}
