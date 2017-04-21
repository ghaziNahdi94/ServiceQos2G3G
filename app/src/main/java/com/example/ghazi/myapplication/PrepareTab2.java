package com.example.ghazi.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 21/07/2016.
 */
public class PrepareTab2 {

    private MainActivity mainActivity = null;
    private Context context = null;



    private List<View> list = new ArrayList<View>();




    public PrepareTab2(MainActivity mainActivity,Context c){

        this.mainActivity = mainActivity;
        context = c;
    }


    public void putViews(String PATH_SMS, File smsFile,LinearLayout tab2){


        tab2.removeAllViews();

        try {
            FileInputStream fis = new FileInputStream(PATH_SMS);

            byte[] bytes = new byte[(int) smsFile.length()];
            fis.read(bytes);
            String texte = new String(bytes);


            if(texte.length() == 0){


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,20,0,0);
                layoutParams.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setText("Pas de SMS");
                textView.setTextSize(20);textView.setTextColor(Color.BLUE);
                textView.setLayoutParams(layoutParams);


                tab2.addView(textView);


            }


            String[] parts = texte.split("--------------------");


            for(int i=1;i<parts.length;i++){   //tjrs debut 1

                String[] lignes = parts[i].split("\n");



                SMSView smsView = new SMSView(context);

                smsView.setTitle1(lignes[1].split(":")[0]+" : ");
                smsView.setRep1(lignes[1].split(":")[1]);

                smsView.setTitle2(lignes[2].split(":")[0]+" : ");
                smsView.setRep2(lignes[2].split(":")[1]+":"+lignes[2].split(":")[2]+":"+lignes[2].split(":")[3]);

                smsView.setTitle3(lignes[3].split(":")[0]+" : ");
                smsView.setRep3(lignes[3].split(":")[1]);

                list.add(smsView);



            }


            boolean b = true;

            for(View view : list) {
                if(b) {
                    view.setBackgroundColor(Color.rgb(255, 240, 240));  b = false;
                }else{
                    b  = true;
                }
                tab2.addView(view);

            }


            mainActivity.setViewSMS(list);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
