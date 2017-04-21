package com.example.ghazi.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by ghazi on 22/07/2016.
 */
public class FileAdapter extends BaseAdapter {




    private Context context = null;
    private LayoutInflater inflater = null;
    private List<File> liste = null;
    private FileHolder fileHolder = null;


    public FileAdapter(Context context,List<File> liste){

        this.context = context;
        this.liste = liste;
        inflater = LayoutInflater.from(context);

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

            fileHolder = new FileHolder();
           convertView = inflater.inflate(R.layout.file_layout,null);

            fileHolder.nameFile = (TextView) convertView.findViewById(R.id.nameList);
            fileHolder.sizeFile = (TextView) convertView.findViewById(R.id.sizeList);
            fileHolder.imageFile = (ImageView) convertView.findViewById(R.id.imageList);


            convertView.setTag(fileHolder);



        }else{


            fileHolder = (FileHolder) convertView.getTag();

        }


        File file = liste.get(position);

        if(file != null){

            fileHolder.nameFile.setText(file.getName());


            if(file.isDirectory()) {
                fileHolder.imageFile.setImageResource(R.drawable.folder);
                fileHolder.sizeFile.setText("");
            }else {
                fileHolder.imageFile.setImageResource(R.drawable.file);
                fileHolder.sizeFile.setText(file.length()+" Octets");
            }

        }



        return convertView;
    }






    class FileHolder{

        public TextView nameFile;
        public TextView sizeFile;
        public ImageView imageFile;

    }


}
