package com.example.ghazi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ghazi on 25/07/2016.
 */
public class SettingAdapter extends BaseAdapter {

        private Context context = null;
    private List<String> liste = null;
    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private Setting setting = null;


    public SettingAdapter(Context context, List<String> liste) {

        this.context = context;
        this.liste = liste;
        inflater = LayoutInflater.from(context);
        setting = new Setting(context);


    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.setting_item,null);


            viewHolder.option = (TextView) convertView.findViewById(R.id.optionSetting);
            viewHolder.activer = (CheckBox) convertView.findViewById(R.id.activationSetting);


            convertView.setTag(viewHolder);



        }else{


            viewHolder = (ViewHolder) convertView.getTag();

        }


        String option = (String) liste.get(position);

        if(option != null){

            viewHolder.option.setText(option);

// Setting 1
if(option.equals("Récupérer les traces")) {


    if(setting.getApplicationActive())
    viewHolder.activer.setChecked(true);
    else
        viewHolder.activer.setChecked(false);


}else if(option.equals("Envoyer l’Email avec")){
    viewHolder.activer.setVisibility(View.INVISIBLE);
}else if(option.equals("Envoyer l’Email chaque")){

    viewHolder.activer.setVisibility(View.INVISIBLE);

}




        }

        return convertView;
    }



    class ViewHolder {

        public CheckBox activer;
        public TextView option;

    }


}
