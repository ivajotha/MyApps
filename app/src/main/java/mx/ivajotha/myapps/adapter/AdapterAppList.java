package mx.ivajotha.myapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;

/**
 * Created by jonathan on 04/07/16.
 */
public class AdapterAppList extends ArrayAdapter<ModelAppList>{


    public AdapterAppList(Context context, int resource) {
        super(context,0, resource);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app,parent,false);


        TextView nameApp = (TextView) convertView.findViewById(R.id.row_txtNameApp);
        TextView detailsApp= (TextView) convertView.findViewById(R.id.row_txtDestailsApp);

        ModelAppList modelAppList = getItem(position);
        nameApp.setText(modelAppList.name);
        detailsApp.setText(modelAppList.details);

        return convertView;

    }
}
