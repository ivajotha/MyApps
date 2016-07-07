package mx.ivajotha.myapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.ivajotha.myapps.Model.ModelAppList;
import mx.ivajotha.myapps.R;

/**
 * Created by jonathan on 04/07/16.
 */
public class AdapterAppList extends ArrayAdapter<ModelAppList>{


    public AdapterAppList(Context context, List<ModelAppList> resource) {
        super(context,0, resource);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app,parent,false);

        TextView nameApp = (TextView) convertView.findViewById(R.id.row_appName);
        TextView nameDevApp = (TextView) convertView.findViewById(R.id.row_appNameDev);
        ImageView imgApp = (ImageView) convertView.findViewById(R.id.row_appImg);
        TextView statusApp = (TextView)convertView.findViewById(R.id.row_appStatus);


        ModelAppList modelAppList = getItem(position);
        nameApp.setText(modelAppList.name.toUpperCase());
        nameDevApp.setText(modelAppList.nameDeveloper);
        imgApp.setImageResource(modelAppList.resourceId);
        statusApp.setText((modelAppList.updated==0)? getContext().getString(R.string.msgStatusInstall):getContext().getString(R.string.msgStatusUpdate));
        return convertView;

    }
}
